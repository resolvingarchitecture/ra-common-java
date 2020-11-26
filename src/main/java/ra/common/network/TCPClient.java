package ra.common.network;

import ra.common.Envelope;
import ra.util.RandomUtil;
import ra.util.Wait;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public abstract class TCPClient implements Runnable {

    private static Logger LOG = Logger.getLogger(TCPClient.class.getName());

    private Boolean keepClientAlive = false;
    private Integer port;
    private Socket socket;
    private BufferedOutputStream out;
    private BufferedInputStream in;
    private boolean connected = false;
    private boolean disconnect = false;
    private boolean listening = false;

    /**
     * Setup for connecting with TCPBusController
     */
    public TCPClient() {
        this(2013, true);
    }

    public TCPClient(int port, boolean keepClientAlive) {
        this.port = port;
        this.keepClientAlive = keepClientAlive;
    }

    public boolean connect() throws IOException {
        socket = new Socket("127.0.0.1", port);
        socket.setKeepAlive(keepClientAlive);
        out = new BufferedOutputStream(socket.getOutputStream());
        in = new BufferedInputStream(socket.getInputStream());
        connected = socket.isConnected();
        if(connected) {
            // Kick of listener
            new Thread(this).start();
        }
        return connected;
    }

    public void disconnect() throws IOException {
        disconnect = true;
        in.close();
        out.close();
        if(socket.isConnected())
            socket.close();
    }

    public boolean sendMessage(Envelope envelope) throws IOException {
        if(!connected && !connect()) {
            LOG.warning("Unable to connect, giving up.");
            return false;
        }
        out.write(envelope.toJSON().getBytes());
        return true;
    }

    public abstract void receiveMessage(Envelope envelope);

    @Override
    public void run() {
        LOG.info("Socket listening for incoming messages....");
        listening = true;
        while(listening) {
            try {
                StringBuffer sb = new StringBuffer();
                int i;
                while((i = in.read()) != -1) {
                    sb.append((char)i);
                }
                Envelope envelope = Envelope.documentFactory();
                envelope.fromJSON(sb.toString());
                receiveMessage(envelope);
                if(disconnect) {
                    listening = false;
                }
            } catch (IOException e) {
                LOG.warning(e.getLocalizedMessage());
                Wait.aSec(1000);
            }
        }
    }
}
