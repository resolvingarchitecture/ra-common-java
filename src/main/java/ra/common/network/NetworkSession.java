package ra.common.network;

import java.util.Properties;

/**
 * Define the means of sending and receiving messages.
 */
public interface NetworkSession {

    enum Status {CONNECTING, CONNECTED, DISCONNECTED, STOPPING, STOPPED, ERRORED}

    Integer getId();
    boolean init(Properties properties);
    String getAddress();
    boolean open(String address);
    boolean connect();
    boolean disconnect();
    boolean isConnected();
    boolean close();
    Boolean send(NetworkPacket packet);
    Status getStatus();
}
