package ra.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ControlClient implements Client {

    private ClientCommPort handshakeCCPort = new ClientCommPort();

    public ControlClient() {}

    public void init() throws IOException {
        handshakeCCPort.clientSocketPort = 2013;
        handshakeCCPort.clientSocket = new Socket("127.0.0.1", handshakeCCPort.clientSocketPort);
        handshakeCCPort.serverSocketPort = 2014;
        handshakeCCPort.serverSocket = new ServerSocket(handshakeCCPort.serverSocketPort, 1);
    }

    public void connect() throws IOException {
        OutputStream os = handshakeCCPort.clientSocket.getOutputStream();
        os.write(ControlCommand.InitiateComm.ordinal());
        os.flush();
        os.close();
    }

    public boolean sendBusCommand(ControlCommand cmd) {

        return false;
    }

    public boolean sendServiceCommand(ControlCommand cmd, String service) {

        return false;
    }

    public boolean sendEnvelope(Envelope envelope) {

        return false;
    }

    @Override
    public void reply(Envelope envelope) {

    }
}
