package ra.common.client;

import ra.common.Envelope;
import ra.common.network.ControlCommand;
import ra.util.Wait;

import java.io.*;
import java.net.*;
import java.util.UUID;
import java.util.logging.Logger;

public class TCPBusClient implements Runnable {

    private static final Logger LOG = Logger.getLogger(TCPBusClient.class.getName());

    final UUID id;

    private Socket socket = null;

    boolean initiatedComm = false;

    private BufferedReader readFromServer;
    private TCPBusClientReceiveThread tcpBusClientReceiveThread;

    private PrintWriter writeToServer;
    private TCPBusClientSendThread tcpBusClientSendThread;

    private boolean shutdown = false;

    public TCPBusClient() {
        id = UUID.randomUUID();
    }

    @Override
    public void run() {
        init();
        while(!shutdown) {
            Wait.aSec(1);
        }
        LOG.info("Shutdown.");
    }

    public void init() {
        try {
            connect(2013);
        } catch (IOException e) {
            LOG.severe(e.getLocalizedMessage());
            System.exit(-1);
        }
        Envelope envelope = Envelope.documentFactory();
        envelope.setCommandPath(ControlCommand.InitiateComm.name());
        envelope.addNVP("initAttempt",1);
        sendMessage(envelope);
        while(!initiatedComm) {
            LOG.info("Not initiated. Waiting.");
            Wait.aSec(1);
        }
        LOG.info("Initiated Comm, running...");
    }

    public boolean isInitiated() {
        return initiatedComm;
    }

    public void shutdown() {
        // TODO: Once multiple clients are enabled for Service Bus, use this method to signal to TCP Bus Controller to close server socket and threads for this particular client instance
//        Envelope envelope = Envelope.documentFactory();
//        envelope.setCommandPath(ControlCommand.EndComm.name());
//        sendMessage(envelope);
        this.shutdown = true;
    }

    public void connect(int port) throws IOException {
        socket = new Socket("localhost", port);
        readFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writeToServer = new PrintWriter(socket.getOutputStream(), true);
        tcpBusClientReceiveThread = new TCPBusClientReceiveThread(this, readFromServer);
        tcpBusClientSendThread = new TCPBusClientSendThread(writeToServer);
        Thread receive = new Thread(tcpBusClientReceiveThread);
        Thread send = new Thread(tcpBusClientSendThread);
        receive.start();
        send.start();
    }

    public void sendMessage(Envelope message) {
        message.setClient(id.toString());
        tcpBusClientSendThread.sendMessage(message.toJSONRaw());
    }

    public static void main(String[] args) {
        TCPBusClient tcpBusClient = new TCPBusClient();
        tcpBusClient.init();
    }
}
