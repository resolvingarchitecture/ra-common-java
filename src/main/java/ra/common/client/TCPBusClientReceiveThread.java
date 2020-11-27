package ra.common.client;

import ra.common.Envelope;
import ra.common.network.ControlCommand;

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
                            tcpBusClient.initiatedComm = true;
                            env.setCommandPath(ControlCommand.Ack.name());
                            env.addNVP("count", 0);
                            tcpBusClient.sendMessage(env.toJSONRaw());
                            break;
                        }
                        case Ack: {
                            int count = (Integer)env.getContent();
                            LOG.info("Incoming count: "+count);
                            env.addContent(count+1);
                            tcpBusClient.sendMessage(env.toJSONRaw());
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
