/*
 * =====================================================================================
 *
 *       Filename:  mesh_def.c
 *
 *    Description:  o11s defence code
 *
 *         Author:  Dennis (), shangxindu@gmail.com
 *        Company:  
 *
 * =====================================================================================
 */
#include <linux/etherdevice.h>
#include <linux/list.h>
#include <linux/random.h>
#include <linux/spinlock.h>
#include <linux/string.h>
#include <net/mac80211.h>
#include "mesh.h"
#define CONFIG_MAC80211_MESH_DEFENCE_DEBUG
#ifdef CONFIG_MAC80211_MESH_DEFENCE_DEBUG
#define mdef_dbg(fmt, args...)   printk(KERN_DEBUG "Mesh Defence: " fmt, ##args)
#else
#define mdef_dbg(fmt, args...)   do { (void)(0); } while (0)
#endif

#define WLAN_EID_BL_ANN   71
#define WLAN_EID_BL_TOUT  72
#define DETECT_THRESHOLD   1000
#define BLACK_LIST_TIMEOUT (600 * HZ)
#define INIT_BL_TABLE_SIZE_ORDER 4
#define INIT_CHAIN_LEN   2
#define ETLNG 98

enum mbl_frame_type{
	MBL_ANN=0,
	MBL_TOUT,
};

/*-----------------------------------------------------------------------------
 *  
 *  struct mesh_bl_table
 *
 *  @hash_buckets: array of hash buckets of the table
 *  @hlock: array of locks to protect write operations, one per bucket
 *  @hash_mask: 2^size_order - 1, used to compute hash idx
 *  @hash_rnd: random value used for hash computations
 *  @entries: number of entries in the table
 *  @size_order: the size order of hash_bucket
 *  @chain_line: max length of buckets, if reach, it's need grow
 *  @sn: sequence number of this table
 *  @free_node: function to free nodes of the table
 *
 *-----------------------------------------------------------------------------*/
struct mesh_bl_table{
	struct hlist_head *hash_buckets;
	spinlock_t *hlock;
	unsigned int hash_mask;
	__u32 hash_rnd;
	atomic_t entries;
	int size_order;
	int chain_len;
	u32 sn;
	void (*free_node) (struct hlist_node *p);

};
/*-----------------------------------------------------------------------------
 *  
 *  struct mesh_bl_node
 *
 *  @list: hash list struct
 *  @rcu: rcu list struct
 *  @sdata: local subif
 *  @tbl: pointer to its blacklist table
 *  @addr[]: black node address
 *  @timer: valid timer
 *  @state: black node state, as specified in mbl_state
 *  @count: preq number in a short cycle
 *
 *-----------------------------------------------------------------------------*/
struct mesh_bl_node{
	struct hlist_node list;
	struct rcu_head rcu;
	struct ieee80211_sub_if_data *sdata;
	struct mesh_bl_table *tbl;
	u8 addr[ETH_ALEN];
	struct timer_list timer;
	enum mbl_state state;
	spinlock_t state_lock;
	atomic_t count;

};


static struct mesh_bl_table *flood_nodes;
static struct mesh_bl_table *bh_nodes;
static struct metric_sort_table *msort_table;
static const u8 broadcast_addr[ETH_ALEN] = {0xff, 0xff, 0xff, 0xff, 0xff, 0xff};

static DEFINE_RWLOCK(mbl_lock);
/**
 * mesh_bl_frame_tx--tx a ids category frame
 * @da: destination add, usually broadcast address
 * @flags: flags
 * @ttl: time to live
 * @sn: sequence number
 * @isflood: the blacklist of flood or black hole
 * @sdata: local sub if
 */
static int mesh_bl_frame_tx(enum mbl_frame_type action, 
		const u8 *da,
		u8 flags,
		u8 ttl,
		u32 sn,
		bool isflood,
		struct ieee80211_sub_if_data *sdata)
{
	struct ieee80211_local *local = sdata->local;
	struct sk_buff *skb = dev_alloc_skb(local->hw.extra_tx_headroom + 400);
	struct ieee80211_mgmt *mgmt;
	struct hlist_node *n;
	struct hlist_head *bucket;
	struct mesh_bl_table *tbl;
	struct mesh_bl_node *node;
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
	memcpy(mgmt->sa, sdata->dev->dev_addr, ETH_ALEN);
	/* BSSID == SA */
	memcpy(mgmt->bssid, sdata->dev->dev_addr, ETH_ALEN);
	mgmt->u.action.category = MESH_BL_CATEGORY;
	mgmt->u.action.u.mesh_action.action_code = MESH_BL_ACTION;
	
	

	switch (action) {
	case MBL_ANN:
		/*flags(1)+sn(4)+ttl(1)+len(4)+len*entries*/
		ie_len=10+isflood?atomic_read(&flood_nodes->entries)*ETH_ALEN:
			atomic_read(&bh_nodes->entries)*ETH_ALEN;
		pos = skb_put(skb, 2 + ie_len);
		*pos++=WLAN_EID_BL_ANN;
		break;
	case MBL_TOUT:
		/*flags(1)+sn(4)+ttl(1)+ETHLEN*/
		ie_len=6+ETH_ALEN;
		pos = skb_put(skb,2+ie_len);
		*pos++=WLAN_EID_BL_TOUT;
	default:
		kfree_skb(skb);
		return -ENOTSUPP;
		break;
	}
	*pos++ = ie_len;
	*pos++ = ttl;
	read_lock(&mbl_lock);
	if(MBL_ANN==action)
		if(isflood)
			memcpy(pos,&flood_nodes->sn,4);
		else
			memcpy(pos,&bh_nodes->sn,4);
	else
		memcpy(pos,&sn,4);
	pos+=4;
	if(MBL_ANN==action){
		if(isflood){
			memcpy(pos,&flood_nodes->entries, 4);
			tbl=rcu_dereference(flood_nodes);
		}else{
			memcpy(pos,&bh_nodes->entries, 4);
			tbl=rcu_dereference(bh_nodes);
		}
		bucket=tbl->hash_buckets;
		pos+=4;
		hlist_for_each_entry(node, n, bucket, list){
			memcpy(pos,&node->addr[0],ETH_ALEN);
		}
	}else
		memcpy(pos,da,ETH_ALEN);

	read_unlock(&mbl_lock);
	ieee80211_tx_skb(sdata, skb);
	return 0;
}

u32 mesh_bl_hash(u8 *addr, struct mesh_bl_table *tbl,
		struct ieee80211_sub_if_data *sdata)
{
	/* Use last four bytes of hw addr and interface index as hash index */
	return jhash_2words(*(u32 *)(addr+2), sdata->dev->ifindex, tbl->hash_rnd)
		& tbl->hash_mask;
}

static void mesh_bl_node_free(struct hlist_node *p )
{
	struct mesh_bl_node *node = hlist_entry(p, struct mesh_bl_node, list);
	hlist_del_rcu(p);
	del_timer_sync(&node->timer);
	kfree(node);
}
static void mesh_bl_node_recycle(struct rcu_head *p)
{
	struct mesh_bl_node *node = container_of( p,struct mesh_bl_node,rcu);
	mdef_dbg("freeing node %pM\n",node->addr);
	kfree(node);
}
void mesh_broadcast_blacklist(struct ieee80211_sub_if_data *sdata)
{
	struct ieee80211_if_mesh *ifmsh=&sdata->u.mesh;
	u8 ttl=ifmsh->mshcfg.dot11MeshTTL;

	ifmsh->last_broadcast_bl=jiffies;
	if(atomic_read(&flood_nodes->entries)!=0)
		mesh_bl_frame_tx(MBL_ANN,&broadcast_addr[0],0,ttl,0,true,sdata);
	if(atomic_read(&bh_nodes->entries)!=0)
		mesh_bl_frame_tx(MBL_TOUT,&broadcast_addr[0],0,ttl,0,false,sdata);
}

int mesh_bl_node_del(u8 *addr,struct mesh_bl_table *tbl,
		struct ieee80211_sub_if_data *sdata)
{
	struct mesh_bl_node *node;
	struct hlist_head *bucket;
	struct hlist_node *n;
	int hash_idx = 0;
	int err = 0;
	read_lock(&mbl_lock);
	hash_idx = mesh_bl_hash(addr,tbl,sdata);
	bucket = &tbl->hash_buckets[hash_idx];

	spin_lock(&tbl->hlock[hash_idx]);
	hlist_for_each_entry(node, n, bucket, list){
		mdef_dbg("delete %pM invoked with address = %pM\n",addr,node->addr);
		if(node->sdata == sdata && memcmp(addr,node->addr,ETH_ALEN) == 0)
		{
			mdef_dbg("node %pM will be deleted\n",addr);
			spin_lock_bh(&node->state_lock);
			node->state = MESH_BL_INVALID;
			spin_unlock_bh(&node->state_lock);
			hlist_del_rcu(&node->list);
			call_rcu(&node->rcu,mesh_bl_node_recycle);
			atomic_dec(&tbl->entries);
			goto exit;
		}
	}
	err = -ENXIO;
exit:
	spin_unlock(&tbl->hlock[hash_idx]);
	read_unlock(&mbl_lock);
	return err;
}


void mesh_bl_timer(unsigned long data)
{
	struct mesh_bl_node *node= (struct mesh_bl_node *)data;
	mdef_dbg("timer func invoked,node address %pM\n",node->addr);
	mesh_bl_node_del(node->addr,node->tbl,node->sdata);
	return;
}

struct mesh_bl_table *mesh_bl_table_alloc(int size_order)
{
	int i;
	struct mesh_bl_table *newtable;	
	
	newtable=kmalloc(sizeof(struct mesh_bl_table),GFP_KERNEL);
	if(!newtable)
		return NULL;

	newtable->hash_buckets=kzalloc(sizeof(struct hlist_head)*
			(1<<size_order),GFP_KERNEL);
	if(!newtable){
		kfree(newtable);
		return NULL;
	}

	newtable->hlock=kmalloc(sizeof(spinlock_t)*(1 << size_order),GFP_KERNEL);
	
	if(!newtable){
		kfree(newtable->hash_buckets);
		kfree(newtable);
		return NULL;
	}

	newtable->size_order=size_order;
	newtable->hash_mask=(1<<size_order)-1;
	atomic_set(&newtable->entries,0);
	get_random_bytes(&newtable->hash_rnd, sizeof(newtable->hash_rnd));
	mdef_dbg("mesh black list mask: %x",newtable->hash_mask);
	for (i = 0; i <= newtable->hash_mask; i++)
		spin_lock_init(&newtable->hlock[i]);

	return newtable;

}

static void __mesh_bl_free(struct mesh_bl_table *bl)
{
	kfree(bl->hash_buckets);
	kfree(bl->hlock);
	kfree(bl);
}

void mesh_bl_free(struct mesh_bl_table *tbl)
{
	struct hlist_head *mesh_hash;
	struct hlist_node *p, *q;
	int i;

	mesh_hash = tbl->hash_buckets;
	for (i = 0; i <= tbl->hash_mask; i++) {
		spin_lock(&tbl->hlock[i]);
		hlist_for_each_safe(p, q, &mesh_hash[i]) {
			tbl->free_node(p);
			atomic_dec(&tbl->entries);
		}
		spin_unlock(&tbl->hlock[i]);
	}
	__mesh_bl_free(tbl);
}

struct mesh_bl_node *mesh_bl_lookup(u8 *addr,bool isflood,struct ieee80211_sub_if_data *sdata)
{
	struct hlist_node *n;
	struct hlist_head *bucket;
	struct mesh_bl_node *node;
	struct mesh_bl_table *tbl;

	tbl = isflood?rcu_dereference(flood_nodes):rcu_dereference(bh_nodes);
	bucket = &tbl->hash_buckets[mesh_bl_hash(addr,tbl,sdata)];
	hlist_for_each_entry_rcu(node, n, bucket, list) {
		if(node->sdata == sdata && memcmp(addr,node->addr,ETH_ALEN) == 0)
		{
			return node;
		}
	}
	return NULL;
}

/*
 * mesh_bl_node_add--add a black node into list
 * @dst: black node address
 * @sdata: local subif
 *
 * return 0 if success
 */
int mesh_bl_node_add(u8 *dst,struct mesh_bl_table *tbl, 
		struct ieee80211_sub_if_data *sdata)
{
	struct mesh_bl_node *node, *new_node;
	struct hlist_head *bucket;
	struct hlist_node *n;
	int err=0;
	u32 hash_idx;

	if (memcmp(dst, sdata->dev->dev_addr, ETH_ALEN) == 0)
		return -ENOTSUPP;

	if (is_multicast_ether_addr(dst))
		return -ENOTSUPP;


	err = -ENOMEM;

	new_node = kmalloc(sizeof(struct mesh_bl_node), GFP_ATOMIC);
	if(!new_node)
		goto err_exit;
	read_lock(&mbl_lock);
	memcpy(new_node->addr,dst,ETH_ALEN);
	new_node->state= MESH_BL_ACTIVE;
	atomic_set(&new_node->count,0);
	init_timer(&new_node->timer);
	spin_lock_init(&new_node->state_lock);
	new_node->sdata = sdata;
	new_node->tbl = tbl;
	new_node->state=MESH_BL_ACTIVE;
	new_node->timer.data = (unsigned long) new_node;
	new_node->timer.function = mesh_bl_timer;
	new_node->timer.expires = jiffies + BLACK_LIST_TIMEOUT;
	add_timer(&new_node->timer);
	hash_idx = mesh_bl_hash(dst,tbl,sdata);
	bucket = &tbl->hash_buckets[hash_idx];
	spin_lock(&tbl->hlock[hash_idx]);
	
	err=-EEXIST;
	hlist_for_each_entry(node,n,bucket,list){
		if(node->sdata == sdata && memcmp(node->addr,dst,ETH_ALEN)==0)
			goto err_exist;
	}
	hlist_add_head_rcu(&new_node->list, bucket);
	
	err = -ETLNG;
	if(atomic_inc_return(&tbl->entries)>=(tbl->chain_len*(tbl->hash_mask+1)))
	{
		mdef_dbg("blacklist is to long, cannot insert any black node!\n");
		goto err_exist;
	}
	spin_unlock(&tbl->hlock[hash_idx]);
	read_unlock(&mbl_lock);
	mdef_dbg("add node successed!\n");
	return 0;
err_exist:
	spin_unlock(&tbl->hlock[hash_idx]);
	read_unlock(&mbl_lock);
	kfree(new_node);
err_exit:
	return err;
	}

int mesh_flood_detect(u8 *addr,struct ieee80211_sub_if_data *sdata)
{
		struct mesh_bl_node *node；
	//to be modified//查找是否在黑名单中
	int ret;//声明返回值变量
	if(!node)//若不在黑名单中
		//to be modified
		//标为不存在的节点
	else
		ret = node->state;//读取节点状态
	switch (ret)
	{
		//to be modified
		//根据不同状态进行变换，注意ACTIVE到SUPPRESSED的转换以及count的原子自加
	}
	return ret;
}

int mesh_bl_table_init(void)
{
	flood_nodes = mesh_bl_table_alloc(INIT_BL_TABLE_SIZE_ORDER);
	if (!flood_nodes)
		return -ENOMEM;
	flood_nodes->free_node = &mesh_bl_node_free;
	flood_nodes->chain_len = INIT_CHAIN_LEN;
	flood_nodes->sn=0;

	bh_nodes = mesh_bl_table_alloc(INIT_BL_TABLE_SIZE_ORDER);
	if (!bh_nodes) {
		mesh_bl_free(flood_nodes);
		return -ENOMEM;
	}
	bh_nodes->free_node = &mesh_bl_node_free;
	bh_nodes->chain_len = INIT_CHAIN_LEN;
	mdef_dbg("black list init complete!\n");
	return 0;
}

void mesh_bl_unregister(void)
{
	mesh_bl_free(flood_nodes);
	mesh_bl_free(bh_nodes);
	kfree(msort_table);
}

