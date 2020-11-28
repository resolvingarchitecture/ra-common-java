package ra.common.client;

import ra.common.Envelope;
import ra.common.network.ControlCommand;
import ra.util.Wait;

import java.io.*;
import java.util.logging.Logger;

public class TCPBusClientReceiveThread implements Runnable {

    private static Logger LOG = Logger.getLogger(TCPBusClientReceiveThread.class.getName());

    private TCPBusClient tcpBusClient;
    private BufferedReader readFromServer = null;
    private boolean running = false;

    public TCPBusClientReceiveThread(TCPBusClient tcpBusClient, BufferedReader readFromServer) {
        this.tcpBusClient = tcpBusClient;
        this.readFromServer = readFromServer;
    }

    public void shutdown() {
        running = false;
    }

    public void run() {
        running = true;
        try {
            while(running) {
                String msg = readFromServer.readLine();
                if(msg.equalsIgnoreCase("exit")) {
                    running = false;
                } else {
                    Envelope env = Envelope.documentFactory();
                    env.fromJSON(msg);
                    LOG.info("Received Envelope...");
                    ControlCommand cc = ControlCommand.valueOf(env.getCommandPath());
                    LOG.info("ControlCommand: "+env.getCommandPath());
                    switch (cc) {
                        case InitiateComm: {
                            tcpBusClient.initiatedComm = env.getValue("init")!=null && "true".equals(env.getValue("init"));
                            if(!tcpBusClient.initiatedComm) {
                                int initCount = (Integer)env.getValue("initAttempt");
                                if(initCount>60) {
                                    LOG.warning("Unable to initiate communications with Bus within 60 seconds, exiting.");
                                    running = false;
                                    break;
                                }
                                env.addNVP("initAttempt", initCount + 1);
                                Wait.aSec(1);
                                tcpBusClient.sendMessage(env);
                            }
                            break;
                        }
                        case Ack: {
                            String senderId = env.getClient();
                            if(tcpBusClient.id.toString().equals(senderId)) {
                                LOG.info("Sent Ack returned Acknowledged.");
                            } else {
                                LOG.info("Server requesting ack; returning...");
                                tcpBusClient.sendMessage(env);
                            }
                            break;
                        }
                        default: {
                            LOG.warning("ControlCommand not handled: "+cc.name());
                        }
                    }
                }
            }
        } catch(Exception e) {
            LOG.warning(e.getLocalizedMessage());
        }
    }

}
