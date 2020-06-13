package ra.common;

/**
 * Sends messages.
 */
public interface MessageProducer {
    /**
     *
     * @param envelope
     * @return
     */
    boolean send(Envelope envelope);
}
