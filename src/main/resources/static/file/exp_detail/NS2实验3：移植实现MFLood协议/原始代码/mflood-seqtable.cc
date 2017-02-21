#include <mflood/mflood-seqtable.h>

// The Routing Table
MFlood_RTEntry::MFlood_RTEntry() {
	src_ = 0;
	for(int i=0;i<REM_SEQ_COUNT;i++)
		rt_seqnos[i] = 0xffffffff;
	max_seqno = 0;
	min_seqno = 0;
	seq_it = 0;
};

// The Routing Table
MFlood_RTEntry::MFlood_RTEntry(nsaddr_t src,u_int32_t seq) {
	src_ = src;
	for(int i=0;i<REM_SEQ_COUNT;i++)
		rt_seqnos[i] = 0xffffffff;
	rt_seqnos[0] = seq;
	max_seqno = seq;
	min_seqno = 0;
	seq_it = 1;
};

bool MFlood_RTEntry::isNewSeq(u_int32_t seq)
{
	if(seq > max_seqno)   //if the seq number is newer than every one before it¡¯s of couse
		return true;       //a new packet and not repetitive. 
	if(seq < min_seqno)   // min_seqno is a standard for whether a packet is too old.
		return false;
	for(int i=0;i<REM_SEQ_COUNT;i++)	//check the array to find whether the packet is 										//repetitive
		if(seq == rt_seqnos[i])
			return false;
	return true;
}

void MFlood_RTEntry::addSeq(u_int32_t seq)
{
	u_int16_t min_it = 0;
	if(seq < min_seqno)
		return;
	if(seq > max_seqno)
		max_seqno = seq;				//update max_seqno
	rt_seqnos[seq_it++] = seq;
	seq_it %= REM_SEQ_COUNT;	//this process is to reuse the array if the element overs 									//the max amount 
	min_seqno = 0xffffffff;				//update min_seqno
	for(int i=0;i<REM_SEQ_COUNT;i++)
		if(min_seqno > rt_seqnos[i])
			min_seqno = rt_seqnos[i];
}

// The Routing Table
MFlood_RTEntry*
MFlood_RTable::rt_lookup(nsaddr_t id) {
	MFlood_RTEntry *rt = rthead.lh_first;
	for(; rt; rt = rt->rt_link.le_next) {
		if(rt->src_ == id)
			break;
	}
	return rt;
}

void
MFlood_RTable::rt_delete(nsaddr_t id) {
	MFlood_RTEntry *rt = rt_lookup(id);
	if(rt) {
		LIST_REMOVE(rt, rt_link);
		delete rt;
	}
}
void MFlood_RTable::rt_print() {		//I guess this function is to print every RTEntry of the 									//RTable but it¡¯s quite confussing why the author 									//annotated up printf. Maybe he thought the print 									//fuction is just for debugging.
	MFlood_RTEntry *rt = rthead.lh_first;
	//printf("\n Seq table:\n");
	for(; rt; rt = rt->rt_link.le_next) {
		//printf("index: %d , seq: %d \n",rt->src_,rt->max_seqno);
	}
	return;
}
