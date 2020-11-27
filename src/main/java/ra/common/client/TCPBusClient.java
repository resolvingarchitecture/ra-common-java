package ra.common.client;

import ra.common.Envelope;
import ra.common.network.ControlCommand;
import ra.util.Wait;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class TCPBusClient {

    private static final Logger LOG = Logger.getLogger(TCPBusClient.class.getName());

    private Socket socket = null;

    boolean initiatedComm = false;

    private BufferedReader readFromServer;
    private TCPBusClientReceiveThread tcpBusClientReceiveThread;

    private PrintWriter writeToServer;
    private TCPBusClientSendThread tcpBusClientSendThread;

    public TCPBusClient() {}

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

    public void sendMessage(String message) {
        tcpBusClientSendThread.sendMessage(message);
    }

    public static void main(String[] args) {
        TCPBusClient TCPBusClient = new TCPBusClient();
        try {
            TCPBusClient.connect(2013);
        } catch (IOException e) {
            LOG.severe(e.getLocalizedMessage());
            System.exit(-1);
        }
        Envelope envelope = Envelope.documentFactory();
        envelope.setCommandPath(ControlCommand.InitiateComm.name());
        TCPBusClient.sendMessage(envelope.toJSONRaw());
        while(!TCPBusClient.initiatedComm) {
            LOG.info("Not initiated. Waiting.");
            Wait.aSec(1);
        }
        LOG.info("Initiated Comm; acking...");
        while(true) {
            Wait.aSec(1);
        }
    }
}
