package ra.common.client;

import ra.common.Client;
import ra.common.Envelope;
import ra.common.network.ControlCommand;
import ra.util.Wait;

import java.io.*;
import java.net.*;
import java.util.UUID;
import java.util.logging.Logger;

public class TCPBusClient implements Runnable {

    private static final Logger LOG = Logger.getLogger(TCPBusClient.class.getName());

    private static final Integer MAX_CONNECT_ATTEMPTS = 30;

    private Integer currentConnectAttempts = 0;

    final String clientId;
    String serverId;

    private Socket socket = null;

    boolean initiatedComm = false;
    Client client;

    private BufferedReader readFromServer;
    private TCPBusClientReceiveThread tcpBusClientReceiveThread;

    private PrintWriter writeToServer;
    private TCPBusClientSendThread tcpBusClientSendThread;

    private boolean connected = false;
    private boolean shutdown = false;

    public TCPBusClient() {
        clientId = UUID.randomUUID().toString();
    }

    public void setClient(Client client) {
        this.client = client;
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
        connected = false;
        currentConnectAttempts = 0;
        while(!connected) {
            try {
                connected = connect(2013);
            } catch (IOException e) {
                currentConnectAttempts++;
                if (currentConnectAttempts < MAX_CONNECT_ATTEMPTS) {
                    LOG.severe(e.getLocalizedMessage() + "; Wait 2 seconds and try again...");
                    Wait.aSec(2);
                } else {
                    LOG.severe(e.getLocalizedMessage() + "; Maximum attempts reached; exiting...");
                    return;
                }
            }
        }
        Envelope envelope = Envelope.documentFactory();
        envelope.setCommandPath(ControlCommand.InitiateComm.name());
        envelope.setClient(clientId);
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

    public void shutdown(boolean reconnect) {
        tcpBusClientSendThread.shutdown();
        tcpBusClientReceiveThread.shutdown();
        if(reconnect) {
            init();
        } else {
            this.shutdown = true;
        }
    }

    public boolean connect(int port) throws IOException {
        // Connect to the localhost server socket at supplied port by a client with local address and port picked from randomly available options supporting multiple clients.
        socket = new Socket("localhost", port, null, 0);
        readFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writeToServer = new PrintWriter(socket.getOutputStream(), true);
        tcpBusClientReceiveThread = new TCPBusClientReceiveThread(this, readFromServer);
        tcpBusClientSendThread = new TCPBusClientSendThread(writeToServer);
        Thread receive = new Thread(tcpBusClientReceiveThread);
        Thread send = new Thread(tcpBusClientSendThread);
        receive.start();
        send.start();
        return true;
    }

    public void sendMessage(Envelope message) {
        message.setClient(clientId);
        tcpBusClientSendThread.sendMessage(message.toJSONRaw());
    }

    public static void main(String[] args) {
        TCPBusClient tcpBusClient = new TCPBusClient();
        tcpBusClient.init();
    }
}
