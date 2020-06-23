package ra.common;

/**
 * TODO: Add Description
 */
public interface MessageConsumer {
    boolean receive(Envelope envelope);
    Envelope receive();
    Envelope receive(int timeout);
    Envelope poll();
    void ack(Envelope envelope);
}
