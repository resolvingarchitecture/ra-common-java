package ra.common;

/**
 * TODO: Add Description
 */
public interface MessageConsumer {
    boolean receive(Envelope envelope);
}
