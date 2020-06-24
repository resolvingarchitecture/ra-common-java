package ra.common.messaging;

import ra.common.Envelope;

/**
 * TODO: Add Description
 */
public interface MessageConsumer {
    boolean receive(Envelope envelope);
}
