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
#��������Ԥ��Ϊ0�����û����롣����Ҫ���û�һ�����룬����ִ��ģ�⡣
set val(nn)		0
set val(rp)		DSR
#������СȱʡֵΪ1000*1000
set val(x)		1000
set val(y)		1000
#Բ�İ뾶ȱʡֵΪ400
set val(r)		400

#�ù�����������Ļ�ϴ�ӡ���ն�����ns circle.tcl ����Ӳ����ĸ�ʽ 
proc usage {} {
global argv0
puts "\nusage: $argv0 \[-nn nodes\] \[-r r\] \[-x x\] \[-y y\]\n"
puts "note: \[-nn nodes\] is essential, and the others are optional.\n"
}

#�ù������������û����������һЩԤ�������ֵ
proc getval {argc argv} {
__________
__________
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
#�û�û�����������ֻ������ns circle.tcl�����������Ϊ0
if { $val(nn) == 0 } {
#��ӡ�÷�
usage
exit
}
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

#�趨�ƶ��ڵ�ĳ�ʼλ�ã���ʾ��ͨ�����Ǻ���ʵ�ֽڵ���δ֪����µĽڵ�λ�����ã�
_____________________
_____________________
_____________________

#�½�һ��UDP Agent�������󶨵��ڵ�1��
                     
                     

#��node_(1)�ڵ���ֱ������Ľڵ��Ͻ���һ�����ݽ�����������ڵ����Ҫ���㣩
______________________
______________________

#��������Agent��������
______________________

##�½�һ��CBR�������������趨�����СΪ500Byte�����ͼ��Ϊ5ms��Ȼ��󶨵�udp0��
_____________________
_____________________
_____________________
_____________________

$ns at 0.5 "$cbr0 start"
$ns at 4.5 "$cbr0 stop"

#��Nam�ж���ڵ��ʼ����λ��
for {set i 0 } {$i<$val(nn)} {incr i} {
#ֻ�ж������ƶ�ģ�ͺ�����������ܱ�����
$ns initial_node_pos $node_($i) 30
}

#����ڵ�ģ��Ľ���ʱ��
for {set i 1 } {$i<$val(nn)} {incr i} {

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
