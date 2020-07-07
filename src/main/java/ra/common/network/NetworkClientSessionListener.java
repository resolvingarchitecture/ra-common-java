package ra.common.network;

public interface NetworkClientSessionListener {
    void messageAvailable(NetworkClientSession session, Integer port);
    void connected(NetworkClientSession session);
    void disconnected(NetworkClientSession session);
    void errorOccurred(NetworkClientSession session, String message, Throwable throwable);
}
