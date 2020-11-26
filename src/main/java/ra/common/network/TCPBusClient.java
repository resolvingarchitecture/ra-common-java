package ra.common.network;

import ra.common.Envelope;
import ra.util.Wait;

import java.io.IOException;
import java.util.logging.Logger;

public class TCPBusClient extends TCPClient {

    private static final Logger LOG = Logger.getLogger(TCPBusClient.class.getName());



    @Override
    public void receiveMessage(Envelope envelope) {
        LOG.info(envelope.toJSON());
    }

    public static void main(String[] args) {
        TCPBusClient client = new TCPBusClient();
        try {
            if(client.connect()) {
                Envelope env = Envelope.documentFactory();
                env.setCommandPath(ControlCommand.InitiateComm.name());
                client.sendMessage(env);
            }
        } catch (IOException e) {
            LOG.info(e.getLocalizedMessage());
        }
        while(true) {
            Wait.aSec(100);
        }
    }
}
