package ra.common;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public interface MessageConsumer {
    boolean receive(Envelope envelope);
}
