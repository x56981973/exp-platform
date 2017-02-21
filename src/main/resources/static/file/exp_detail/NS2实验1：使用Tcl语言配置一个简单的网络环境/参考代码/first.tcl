#设定模拟需要的一些属性
set val(chan)		Channel/WirelessChannel
set val(prop)		Propagation/TwoRayGround
set val(netif)		Phy/WirelessPhy
set val(mac)		Mac/802_11
#将协议设置为DSR后，同时将队列设置为CMUPriQueue
set val(ifq)		CMUPriQueue	
set val(ll)		    LL
set val(ant)		Antenna/OmniAntenna
set val(ifqlen)		50
set val(rp)		DSR
#场景大小缺省值为1000*1000
set val(x)		1000
set val(y)		1000
#圆的半径缺省值为200
set val(r)		200
set val(nn) 12                           ;#移动节点的数目


#该过程用来根据用户的输入更改一些预设参数的值
proc getval {argc argv} {
	global val
lappend vallist r x y z
#argc为参数的个数，argv为整条参数构成的字符串
for {set i 0} {$i < $argc} {incr i} {
#变量arg为argv的第i部分，以空格为分界
set arg [lindex $argv $i]
#略过无字符“-”的字符串，一般是用户键入的数字
#string range $arg m n表示取字符串$arg的第m个字符到第n个字符
		if {[string range $arg 0 0] != "-"} continue
set name [string range $arg 1 end]
#更改预设变量（结点个数，半径，场景大小）
		set val($name) [lindex $argv [expr $i+1]]
	}
}

#调用getval过程
getval $argc $argv
#初始化全局变量
set ns [new Simulator]

#打开Trace文件
set namfd [open ns1.nam w]
$ns namtrace-all-wireless $namfd $val(x) $val(y)
set tracefd [open $val(tr) w]
$ns trace-all $tracefd

#建立一个拓扑对象，以记录移动节点在拓扑内移动的情况
set topo [new Topography]
#拓扑范围为1000m*1000m
$topo load_flatgrid $val(x) $val(y)
#创建物理信道对象
set chan [new $val(chan)]
#创建God对象
set god [create-god $val(nn)]

#设置移动节点属性
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

#设定移动节点的初始位置

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



#新建一个UDP Agent并把它绑定到节点0上
set udp0 [new Agent/UDP]
$ns attach-agent $node_(0) $udp0
#新建一个CBR流量发生器，设定分组大小为500Byte，发送间隔为5ms，然后绑定到udp0上
set cbr0 [new Application/Traffic/CBR]
$cbr0 set packetSize_ 500
$cbr0 set interval_ 0.005
$cbr0 attach-agent $udp0
#新建一个Null Agent并把它绑定到节点6上，Null是一种数据接收器
set null0 [new Agent/Null]
$ns attach-agent $node_(6) $null0
#将udp0和null0这两个Agent连接起来
$ns connect $udp0 $null0
#
$ns at 0.5 "$cbr0 start"
$ns at 4.5 "$cbr0 stop"

#在Nam中定义节点初始所在位置
for {set i 0 } {$i<$val(nn)} {incr i} {
#只有定义了移动模型后，这个函数才能被调用
$ns initial_node_pos $node_($i) 30
}

#定义节点模拟的结束时间
for {set i 0 } {$i<$val(nn)} {incr i} {

$ns at $val(stop) "$node_($i) reset";
}
$ns at $val(stop) "stop"
$ns at $val(stop) "puts \"NS EXITING...\"; $ns halt"
#stop函数
proc stop {} {
global ns tracefd namfd
  $ns flush-trace
close $tracefd
close $namfd
exec nam ns1.nam &
}

$ns run
