package ra.common;

public interface ProtocolSessionListener {
    void messageAvailable(ProtocolSession session, Integer port);
    void connected(ProtocolSession session);
    void disconnected(ProtocolSession session);
    void errorOccurred(ProtocolSession session, String message, Throwable throwable);
}
