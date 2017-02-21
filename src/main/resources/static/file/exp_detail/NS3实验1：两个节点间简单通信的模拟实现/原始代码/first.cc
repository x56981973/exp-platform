#include "ns3/core-module.h"
#include "ns3/simulator-module.h
#include "ns3/node-module.h"
#include "ns3/helper-module.h"
using namespace ns3;

NS_LOG_COMPONENT_DEFINE ("FirstScriptExample"); ����//��������Ϊ��Example������־ģ��

int
main (int argc, char *argv[])
{
���� //���������������UdpEchoӦ�ó������־��¼���伶��ΪLOG_LEVEL_INFO������LogComponentEnable("UdpEchoClientApplication", LOG_LEVEL_INFO);
����LogComponentEnable("UdpEchoServerApplication", LOG_LEVEL_INFO);

����NodeContainer nodes; ����//1�����������ڵ�
����nodes.Create (2);  

����PointToPointHelper pointToPoint;����//2������P2P���͵�Helper
����pointToPoint.SetDeviceAttribute ("DataRate", StringValue ("5Mbps"));   //ʹ��Helper������·����
����pointToPoint.SetChannelAttribute ("Delay", StringValue ("2ms"));

����NetDeviceContainer devices;
����devices = pointToPoint.Install (nodes);����//ʹ��Helper��������װ���ڵ�

����InternetStackHelper stack;����//3����װIPЭ��ջ
����stack.Install (nodes);

����Ipv4AddressHelper address;����//4������IP��ַ
����address.SetBase ("10.1.1.0", "255.255.255.0");
����Ipv4InterfaceContainer interfaces = address.Assign (devices);����//���䵽����


����UdpEchoServerHelper echoServer (9); ����//5.1����װUdpServerӦ�÷���9��ʾ����˿�
����ApplicationContainer serverApps = echoServer.Install (nodes.Get (1));
����serverApps.Start (Seconds (1.0));
����serverApps.Stop (Seconds (10.0));
����serverApps.Start (Seconds (1.0)); ����//6.1��Server����ʱ��
����serverApps.Stop (Seconds (10.0));

����UdpEchoClientHelper echoClient (interfaces.GetAddress (1), 9); ����//5.2����װUdpClientӦ�÷�����Ҫָ��������IP�Լ�����˿�
����echoClient.SetAttribute ("MaxPackets", UintegerValue (1));
����echoClient.SetAttribute ("Interval", TimeValue (Seconds (1.)));
����echoClient.SetAttribute ("PacketSize", UintegerValue (1024));
����ApplicationContainer clientApps = echoClient.Install (nodes.Get (0));
����clientApps.Start (Seconds (2.0)); ����//6.2��Client����ʱ��
����clientApps.Stop (Seconds (10.0));

����Simulator::Run (); ����//6.3����������
����Simulator::Destroy ();
����return 0;
}
