package ra.common;

/**
 * Sends messages.
 *
 * @author objectorange
 */
public interface MessageProducer {
    /**
     *
     * @param envelope
     * @return
     */
    boolean send(Envelope envelope);
}
