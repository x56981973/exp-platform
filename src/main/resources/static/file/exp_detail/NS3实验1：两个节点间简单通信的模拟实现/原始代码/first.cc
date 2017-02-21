#include "ns3/core-module.h"
#include "ns3/simulator-module.h
#include "ns3/node-module.h"
#include "ns3/helper-module.h"
using namespace ns3;

NS_LOG_COMPONENT_DEFINE ("FirstScriptExample"); 　　//定义名称为“Example”的日志模块

int
main (int argc, char *argv[])
{
　　 //以下两个语句启用UdpEcho应用程序的日志记录，其级别为LOG_LEVEL_INFO。　　LogComponentEnable("UdpEchoClientApplication", LOG_LEVEL_INFO);
　　LogComponentEnable("UdpEchoServerApplication", LOG_LEVEL_INFO);

　　NodeContainer nodes; 　　//1、创建两个节点
　　nodes.Create (2);  

　　PointToPointHelper pointToPoint;　　//2、创建P2P类型的Helper
　　pointToPoint.SetDeviceAttribute ("DataRate", StringValue ("5Mbps"));   //使用Helper设置链路属性
　　pointToPoint.SetChannelAttribute ("Delay", StringValue ("2ms"));

　　NetDeviceContainer devices;
　　devices = pointToPoint.Install (nodes);　　//使用Helper将网卡安装到节点

　　InternetStackHelper stack;　　//3、安装IP协议栈
　　stack.Install (nodes);

　　Ipv4AddressHelper address;　　//4、分配IP地址
　　address.SetBase ("10.1.1.0", "255.255.255.0");
　　Ipv4InterfaceContainer interfaces = address.Assign (devices);　　//分配到网卡


　　UdpEchoServerHelper echoServer (9); 　　//5.1、安装UdpServer应用服务，9表示服务端口
　　ApplicationContainer serverApps = echoServer.Install (nodes.Get (1));
　　serverApps.Start (Seconds (1.0));
　　serverApps.Stop (Seconds (10.0));
　　serverApps.Start (Seconds (1.0)); 　　//6.1、Server启动时间
　　serverApps.Stop (Seconds (10.0));

　　UdpEchoClientHelper echoClient (interfaces.GetAddress (1), 9); 　　//5.2、安装UdpClient应用服务，需要指明服务器IP以及服务端口
　　echoClient.SetAttribute ("MaxPackets", UintegerValue (1));
　　echoClient.SetAttribute ("Interval", TimeValue (Seconds (1.)));
　　echoClient.SetAttribute ("PacketSize", UintegerValue (1024));
　　ApplicationContainer clientApps = echoClient.Install (nodes.Get (0));
　　clientApps.Start (Seconds (2.0)); 　　//6.2、Client启动时间
　　clientApps.Stop (Seconds (10.0));

　　Simulator::Run (); 　　//6.3、启动仿真
　　Simulator::Destroy ();
　　return 0;
}
