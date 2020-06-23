package ra.common;

/**
 * Sends messages.
 */
public interface MessageProducer {
    boolean send(Envelope envelope);
    void ack(Envelope envelope);
}
