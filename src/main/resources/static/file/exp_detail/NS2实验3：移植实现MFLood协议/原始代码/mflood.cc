
#include <mflood/mflood.h>
#include <mflood/mflood-packet.h>
#include <random.h>
#include <cmu-trace.h>


// New packet type
int hdr_mflood::offset_;

//===================================================================
/* MFloodHeaderClass is the derived class of PacketHeaderClass and it¡¯s function is to add the   information of mflood into the packet¡¯s header and edit the offset.*/
//===================================================================
static class MFloodHeaderClass : public PacketHeaderClass {
public:
	MFloodHeaderClass() : PacketHeaderClass("PacketHeader/MFlood", 
					      sizeof(hdr_mflood)) {
		bind_offset(&hdr_mflood::offset_);
	}
} class_mfloodhdr;



//====================================================================
/* class MFloodclass is to connect our new c++ project mflood with tcl. It must be noticed that every derived class of TclClass should be a static class so that NS2 will transfer the function¡¯s constructor when initializing at first */
//====================================================================
// TCL Hooks
static class MFloodclass : public TclClass {
public:
	MFloodclass() : TclClass("Agent/MFlood") {}
	TclObject* create(int argc, const char*const* argv) {
		assert(argc == 5);
		return (new MFlood((nsaddr_t) atoi(argv[4])));	// PBO agrv[4] is index_}
	}
} class_rtProtoMFlood;

//====================================================================
//Function command is for TCL to use functions of mflood in C++ 
//====================================================================
int MFlood::command(int argc, const char*const* argv) {
	Tcl& tcl = Tcl::instance();
	if(argc == 2) {		
		if(strncasecmp(argv[1], "id", 2) == 0) {
			tcl.resultf("%d", index_);
			return TCL_OK;
		}    							//Can be viewed as GetIndex_
		else if (strcmp(argv[1], "uptarget") == 0) {
			if (uptarget_ != 0)
				tcl.result(uptarget_->name());
			return (TCL_OK);
		}								//put out the name of uptarget
	} else if(argc == 3) {
		if(strcmp(argv[1], "index_") == 0) {
			index_ = atoi(argv[2]);			//can be viewed as SetIndex_
			return TCL_OK;
		} else if(strcmp(argv[1], "log-target") == 0 || strcmp(argv[1], "tracetarget") == 0) {
			logtarget = (Trace*) TclObject::lookup(argv[2]);
			if(logtarget == 0) return TCL_ERROR;				//as SetLogtarget
			return TCL_OK;
		}
		else if (strcmp(argv[1], "uptarget") == 0) {
			if (*argv[2] == '0') {
				target_ = 0;								//Clear target
				return (TCL_OK);
			}
			uptarget_ = (NsObject*)TclObject::lookup(argv[2]);	//Set uptarget
			if (uptarget_ == 0) {
				tcl.resultf("no such object %s", argv[2]);
				return (TCL_ERROR);
			}
			return (TCL_OK);
		}
		else if (strcasecmp(argv[1],"port-dmux")== 0){
		  TclObject *obj; 
          	  port_dmux_ = (NsObject *) obj; 				//Set port_dmux
          	  return TCL_OK; 
		}		
	}
	return Agent::command(argc, argv);
}
//====================================================================
//The constructor of MFlood class which will set the IP address of the node and initialize the //parameters.
//====================================================================
MFlood::MFlood(nsaddr_t id) : Agent(PT_MFLOOD), port_dmux_(0) {
	index_ = id;
	logtarget = 0;
	myseq_ = 0;
}

/*====================================================================
The fuction rt_resolve is to Decide whether forward a package or not. If the package¡¯ life end when it reachs the node or the package has already been forward by such node, according to the RTable, the node will drop it. Else, the node will forward it. 
In addition, if the node has never forward to the package¡¯s destination (as no record in RTable), then function rt_resolve will create a new RTEntry record into RTable. If the package¡¯s RTEntry already exists (as it has forward some package to such destination), and if the package¡¯s seq number is new (not exist in node¡¯s seqno array), then function rt_resolve will add the seq number into the seqno array.    								
====================================================================*/
void MFlood::rt_resolve(Packet *p) {
	struct hdr_cmn *ch = HDR_CMN(p);
	struct hdr_ip *ih = HDR_IP(p);
	struct hdr_mflood *fh = HDR_MFLOOD(p);
	MFlood_RTEntry* rt;

	rt = rtable_.rt_lookup(ih->saddr());
	if(rt == NULL) {
		rt = new MFlood_RTEntry(ih->saddr(), fh->seq_);

		LIST_INSERT_HEAD(&rtable_.rthead,rt,rt_link);		
	
		forward(rt,p,FORWARD_DELAY);
		
		rtable_.rt_print();		
		
	}
	else if(rt->isNewSeq(fh->seq_) )
	{
		forward(rt, p, FORWARD_DELAY);

		rt->addSeq(fh->seq_);

		rtable_.rt_print();		
	}
	else
	{
		drop(p, "LOWSEQ");
	}
}
//====================================================================
//Function recv is used when to create a packet of mflood or receive a packet from other //node.And function recv will operate the packet differently according to where it is from. 
//====================================================================
// Packet Reception Routines
void MFlood::recv(Packet *p, Handler*) {
	struct hdr_cmn *ch = HDR_CMN(p);
	struct hdr_ip *ih = HDR_IP(p);
	struct hdr_mflood *fh = HDR_MFLOOD(p);
	assert(initialized());

	if((ih->saddr() == index_) && (ch->num_forwards() == 0)) {	
								//Sotuation that the packet is create by the node itself
                                 //The recv function will edit header of the packet and 								 //then send it (by forward).
		ch->size() += IP_HDR_LEN;			// Add the IP Header
		ih->ttl_ = NETWORK_DIAMETER;
		fh->seq_ = myseq_++;			
		forward((MFlood_RTEntry*)1,p,0);		
		return;
	} else if(ih->saddr() == index_) {	// Sotuation that the node received a packet that it sent.  								// It refer that there happened probably a routing loop 								// so function recv will just drop the meaningless 									// packet
		drop(p, DROP_RTR_ROUTE_LOOP);
		return;
	} else {					// Sotuation that the packet the node received is for it to 								//forward. Function recv will just let function rt_resolve to 								//judge what should do next. 
		if(--ih->ttl_ == 0) {			// Check the TTL.  If it is zero, then discard.
			drop(p, DROP_RTR_TTL);
	 		return;
		}
	}

	rt_resolve(p);
}

//====================================================================
//As it¡¯s name, function forward is just to forward a packet. According to the delay, the function //will decide whether  
//====================================================================
// Packet Transmission Routines
void MFlood::forward(MFlood_RTEntry* rt, Packet *p, double delay) {
	struct hdr_cmn *ch = HDR_CMN(p);
	struct hdr_ip *ih = HDR_IP(p);

	assert(ih->ttl_ > 0);					//use function assert to assure that no unexpected 										//error happened (as a packet come to function 										//forward with ttl<0 after so many scans before)
	assert(rt != 0);
	ch->next_hop_ = -1;				//Broadcast address, next_hop_ as -1 means the 										//packet is a broadcast packet (no dst)
	ch->addr_type() = NS_AF_INET;		//mark as broadcast packet
	ch->direction() = hdr_cmn::DOWN;   //change the packet's direction and let scheduler 
									//send the packet to LL layer
	if(delay > 0.0) {			
 		Scheduler::instance().schedule(target_, p, Random::uniform(delay*2));
	} else {		
		Scheduler::instance().schedule(target_, p, 0.);
	}
}
