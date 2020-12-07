package ra.common.client;

import ra.common.Envelope;
import ra.common.messaging.EventMessage;
import ra.common.network.ControlCommand;
import ra.common.notification.ClientSubscription;
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
                if(msg==null) {
                    LOG.info("Server likely shutdown. Shutting down client with re-connect attempt...");
                    tcpBusClient.shutdown(true);
                    continue;
                }
                if(msg.equalsIgnoreCase("exit")) {
                    LOG.info("Server notifying client of shutdown. Shutting down client...");
                    tcpBusClient.shutdown(false);
                    continue;
                }
                Envelope env = new Envelope();
                env.fromJSON(msg);
                LOG.info("Received Envelope...");
                ControlCommand cc = ControlCommand.valueOf(env.getCommandPath());
                LOG.info("ControlCommand: "+env.getCommandPath());
                switch (cc) {
                    case InitiateComm: {
                        tcpBusClient.initiatedComm = env.getValue("init")!=null && "true".equals(env.getValue("init"));
                        if(tcpBusClient.initiatedComm) {
                            tcpBusClient.serverId = env.getClient();
                        } else {
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
                    case Notify: {
                        EventMessage em = (EventMessage) env.getMessage();
                        ClientSubscription cs = tcpBusClient.subscriptions.get(em.getType());
                        if(cs!=null) {
                            cs.reply(env);
                        }
                        break;
                    }
                    case CloseClient: {
                        // Server telling client to shutdown
                        tcpBusClient.shutdown(false);
                        break;
                    }
                    default: {
                        tcpBusClient.client.reply(env);
                    }
                }
            }
        } catch(Exception e) {
            LOG.warning(e.getLocalizedMessage());
        }
    }

}
