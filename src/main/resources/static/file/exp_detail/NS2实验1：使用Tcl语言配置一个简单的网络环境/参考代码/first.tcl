#�趨ģ����Ҫ��һЩ����
set val(chan)		Channel/WirelessChannel
set val(prop)		Propagation/TwoRayGround
set val(netif)		Phy/WirelessPhy
set val(mac)		Mac/802_11
#��Э������ΪDSR��ͬʱ����������ΪCMUPriQueue
set val(ifq)		CMUPriQueue	
set val(ll)		    LL
set val(ant)		Antenna/OmniAntenna
set val(ifqlen)		50
set val(rp)		DSR
#������СȱʡֵΪ1000*1000
set val(x)		1000
set val(y)		1000
#Բ�İ뾶ȱʡֵΪ200
set val(r)		200
set val(nn) 12                           ;#�ƶ��ڵ����Ŀ


#�ù������������û����������һЩԤ�������ֵ
proc getval {argc argv} {
	global val
lappend vallist r x y z
#argcΪ�����ĸ�����argvΪ�����������ɵ��ַ���
for {set i 0} {$i < $argc} {incr i} {
#����argΪargv�ĵ�i���֣��Կո�Ϊ�ֽ�
set arg [lindex $argv $i]
#�Թ����ַ���-�����ַ�����һ�����û����������
#string range $arg m n��ʾȡ�ַ���$arg�ĵ�m���ַ�����n���ַ�
		if {[string range $arg 0 0] != "-"} continue
set name [string range $arg 1 end]
#����Ԥ����������������뾶��������С��
		set val($name) [lindex $argv [expr $i+1]]
	}
}

#����getval����
getval $argc $argv
#��ʼ��ȫ�ֱ���
set ns [new Simulator]

#��Trace�ļ�
set namfd [open ns1.nam w]
$ns namtrace-all-wireless $namfd $val(x) $val(y)
set tracefd [open $val(tr) w]
$ns trace-all $tracefd

#����һ�����˶����Լ�¼�ƶ��ڵ����������ƶ������
set topo [new Topography]
#���˷�ΧΪ1000m*1000m
$topo load_flatgrid $val(x) $val(y)
#���������ŵ�����
set chan [new $val(chan)]
#����God����
set god [create-god $val(nn)]

#�����ƶ��ڵ�����
$ns node-config -adhocRouting $val(rp)	\
		-llType $val(ll)	\
		-macType $val(mac)	\
		-ifqType $val(ifq)	\
		-ifqLen $val(ifqlen)	\
		-antType $val(ant)	\
		-propType $val(prop)	\
		-phyType $val(netif)	\
		-channel $chan	\
		-topoInstance $topo	\
		-agentTrace ON	\
		-routerTrace ON	\
		-macTrace  OFF	\
		-movementTrace ON
for {set i 0} {$i < $val(nn)} {incr i} {
     set node_($i) [$ns node]
}

#�趨�ƶ��ڵ�ĳ�ʼλ��

$node_(1) set X_ 200
$node_(1) set Y_ 100
$node_(1) set Z_ 0

$node_(2) set X_ 186.6
$node_(2) set Y_ 150
$node_(2) set Z_ 0

$node_(3) set X_ 150
$node_(3) set Y_ 186.6
$node_(3) set Z_ 0

$node_(4) set X_ 100
$node_(4) set Y_ 200
$node_(4) set Z_ 0

$node_(5) set X_ 50
$node_(5) set Y_ 186.6
$node_(5) set Z_ 0

$node_(6) set X_ 13.4
$node_(6) set Y_ 150
$node_(6) set Z_ 0

$node_(7) set X_ 0
$node_(7) set Y_ 100
$node_(7) set Z_ 0

$node_(8) set X_ 13.4
$node_(8) set Y_ 50
$node_(8) set Z_ 0

$node_(9) set X_ 50
$node_(9) set Y_ 13.4
$node_(9) set Z_ 0

$node_(10) set X_ 100
$node_(10) set Y_ 0
$node_(10) set Z_ 0

$node_(11) set X_ 150
$node_(11) set Y_ 13.4
$node_(11) set Z_ 0

$node_(0) set X_ 186.6
$node_(0) set Y_ 50
$node_(0) set Z_ 0



#�½�һ��UDP Agent�������󶨵��ڵ�0��
set udp0 [new Agent/UDP]
$ns attach-agent $node_(0) $udp0
#�½�һ��CBR�������������趨�����СΪ500Byte�����ͼ��Ϊ5ms��Ȼ��󶨵�udp0��
set cbr0 [new Application/Traffic/CBR]
$cbr0 set packetSize_ 500
$cbr0 set interval_ 0.005
$cbr0 attach-agent $udp0
#�½�һ��Null Agent�������󶨵��ڵ�6�ϣ�Null��һ�����ݽ�����
set null0 [new Agent/Null]
$ns attach-agent $node_(6) $null0
#��udp0��null0������Agent��������
$ns connect $udp0 $null0
#
$ns at 0.5 "$cbr0 start"
$ns at 4.5 "$cbr0 stop"

#��Nam�ж���ڵ��ʼ����λ��
for {set i 0 } {$i<$val(nn)} {incr i} {
#ֻ�ж������ƶ�ģ�ͺ�����������ܱ�����
$ns initial_node_pos $node_($i) 30
}

#����ڵ�ģ��Ľ���ʱ��
for {set i 0 } {$i<$val(nn)} {incr i} {

$ns at $val(stop) "$node_($i) reset";
}
$ns at $val(stop) "stop"
$ns at $val(stop) "puts \"NS EXITING...\"; $ns halt"
#stop����
proc stop {} {
global ns tracefd namfd
  $ns flush-trace
close $tracefd
close $namfd
exec nam ns1.nam &
}

$ns run
