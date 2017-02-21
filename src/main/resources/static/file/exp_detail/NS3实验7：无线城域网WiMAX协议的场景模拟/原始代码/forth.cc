#include "ns3/core-module.h"
#include "ns3/network-module.h"
#include "ns3/applications-module.h"
#include "ns3/mobility-module.h"
#include "ns3/config-store-module.h"
#include "ns3/wimax-module.h"
#include "ns3/internet-module.h"
#include "ns3/global-route-manager.h"
#include <iostream>

NS_LOG_COMPONENT_DEFINE ("wimaxIpV4Simulation");

using namespace ns3;

int main (int argc, char *argv[])
{
  // default values
  int nbSS = 4, duration = 7, schedType = 0;
  bool verbose = false;
  WimaxHelper::SchedulerType scheduler = WimaxHelper::SCHED_TYPE_SIMPLE;
  LogComponentEnable ("UdpClient", LOG_LEVEL_INFO);
  LogComponentEnable ("UdpServer", LOG_LEVEL_INFO);


  CommandLine cmd;
  cmd.AddValue ("nbSS", "number of subscriber station to create", nbSS);
  cmd.AddValue ("scheduler", "type of scheduler to use with the network devices", schedType);
  cmd.AddValue ("duration", "duration of the simulation in seconds", duration);
  cmd.AddValue ("verbose", "turn on all WimaxNetDevice log components", verbose);
  cmd.Parse (argc, argv);

  switch (schedType)
    {
    case 0:
      scheduler = WimaxHelper::SCHED_TYPE_SIMPLE;
      break;
    case 1:
      scheduler = WimaxHelper::SCHED_TYPE_MBQOS;
      break;
    case 2:
      scheduler = WimaxHelper::SCHED_TYPE_RTPS;
      break;
    default:
      scheduler = WimaxHelper::SCHED_TYPE_SIMPLE;
    }

  NodeContainer ssNodes;
  NodeContainer bsNodes;

  ssNodes.Create (nbSS);
  bsNodes.Create (1);

  WimaxHelper wimax;

  NetDeviceContainer ssDevs, bsDevs;

  ssDevs = wimax.Install (ssNodes,
                          WimaxHelper::DEVICE_TYPE_SUBSCRIBER_STATION,
                          WimaxHelper::SIMPLE_PHY_TYPE_OFDM,
                          scheduler);
  bsDevs = wimax.Install (bsNodes, WimaxHelper::DEVICE_TYPE_BASE_STATION, WimaxHelper::SIMPLE_PHY_TYPE_OFDM, scheduler);

  Ptr<SubscriberStationNetDevice>* ss = new Ptr<SubscriberStationNetDevice>[nbSS];

  for (int i = 0; i < nbSS; i++)
    {
      ss[i] = ssDevs.Get (i)->GetObject<SubscriberStationNetDevice> ();
      ss[i]->SetModulationType (WimaxPhy::MODULATION_TYPE_QAM16_12);
    }

  Ptr<BaseStationNetDevice> bs;
  bs = bsDevs.Get (0)->GetObject<BaseStationNetDevice> ();

  MobilityHelper mobility;
  mobility.Install (bsNodes);
  mobility.Install (ssNodes);

  InternetStackHelper stack;
  stack.Install (bsNodes);
  stack.Install (ssNodes);

  Ipv4AddressHelper address;
  address.SetBase ("10.1.1.0", "255.255.255.0");

  Ipv4InterfaceContainer SSinterfaces = address.Assign (ssDevs);
  Ipv4InterfaceContainer BSinterface = address.Assign (bsDevs);
  if (verbose)
    {
      wimax.EnableLogComponents ();  // Turn on all wimax logging
    }
  /*------------------------------*/
  UdpServerHelper* udpServer = new UdpServerHelper[nbSS / 2];
  ApplicationContainer* serverApps = new ApplicationContainer[nbSS / 2];
  UdpClientHelper* udpClient = new UdpClientHelper[nbSS / 2];
  ApplicationContainer* clientApps = new ApplicationContainer[nbSS / 2];

  for (int i = 0; i < nbSS / 2; i++)
    {
      // set server port to 100+(i*10)
      udpServer[i] = UdpServerHelper (100 + (i * 10));
      serverApps[i] = udpServer[i].Install (ssNodes.Get (i));
      serverApps[i].Start (Seconds (6));
      serverApps[i].Stop (Seconds (duration));

      udpClient[i] = UdpClientHelper (SSinterfaces.GetAddress (i), 100 + (i * 10));
      udpClient[i].SetAttribute ("MaxPackets", UintegerValue (1200));
      udpClient[i].SetAttribute ("Interval", TimeValue (Seconds (0.12)));
      udpClient[i].SetAttribute ("PacketSize", UintegerValue (800));

      clientApps[i] = udpClient[i].Install (ssNodes.Get (i + (nbSS / 2)));
      clientApps[i].Start (Seconds (6));
      clientApps[i].Stop (Seconds (duration));
    }

  Simulator::Stop (Seconds (duration + 0.1));
  /*
   * Setup 1 transport connections between each SS and the BS
   */
  for (int i = 0; i < nbSS / 2; i++)
    {
      IpcsClassifierRecord DlClassifierBe (Ipv4Address ("0.0.0.0"),
                                           Ipv4Mask ("0.0.0.0"),
                                           SSinterfaces.GetAddress (i),
                                           Ipv4Mask ("255.255.255.255"),
                                           0,
                                           65000,
                                           100 + (i * 10),
                                           100 + (i * 10),
                                           17,
                                           1);
      ServiceFlow DlServiceFlowBe = wimax.CreateServiceFlow (ServiceFlow::SF_DIRECTION_DOWN,
                                                             ServiceFlow::SF_TYPE_BE,
                                                             DlClassifierBe);
      ss[i]->AddServiceFlow (DlServiceFlowBe);
      IpcsClassifierRecord ulClassifierBe (SSinterfaces.GetAddress (i + (nbSS / 2)),
                                           Ipv4Mask ("255.255.255.255"),
                                           Ipv4Address ("0.0.0.0"),
                                           Ipv4Mask ("0.0.0.0"),
                                           0,
                                           65000,
                                           100 + (i * 10),
                                           100 + (i * 10),
                                           17,
                                           1);
      ServiceFlow ulServiceFlowBe = wimax.CreateServiceFlow (ServiceFlow::SF_DIRECTION_UP,
                                                             ServiceFlow::SF_TYPE_BE,
                                                             ulClassifierBe);
      ss[i + (nbSS / 2)]->AddServiceFlow (ulServiceFlowBe);

    }

  NS_LOG_INFO ("Starting simulation.....");
  Simulator::Run ();

  delete[] clientApps;
  delete[] udpClient;
  delete[] serverApps;
  delete[] udpServer;
  for (int i = 0; i < nbSS; i++)
    {
      ss[i] = 0;
    }
  delete[] ss;

  bs = 0;

  Simulator::Destroy ();
  NS_LOG_INFO ("Done.");

  return 0;
}

