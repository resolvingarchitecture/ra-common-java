package ra.common.network;

public interface NetworkSessionListener {
    void messageAvailable(NetworkSession session, Integer port);
    void connected(NetworkSession session);
    void disconnected(NetworkSession session);
    void errorOccurred(NetworkSession session, String message, Throwable throwable);
}
