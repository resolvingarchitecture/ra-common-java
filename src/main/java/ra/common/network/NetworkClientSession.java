package ra.common.network;

import ra.common.Envelope;
import ra.common.route.RelayedExternalRoute;

import java.util.Properties;

/**
 * Define the means of sending and receiving messages.
 */
public interface NetworkClientSession {

    enum Status {CONNECTING, CONNECTED, DISCONNECTED, STOPPING, STOPPED, ERRORED}

    Integer getId();
    boolean init(Properties properties);
    String getAddress();
    boolean open(String address);
    boolean connect();
    boolean disconnect();
    boolean isConnected();
    boolean close();
    Boolean send(Envelope envelope);
    Status getStatus();
}
