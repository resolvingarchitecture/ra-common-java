package ra.common.service;

import ra.common.Envelope;

import java.io.Serializable;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public interface ServiceCallback extends Serializable {
    void reply(Envelope envelope);
}
