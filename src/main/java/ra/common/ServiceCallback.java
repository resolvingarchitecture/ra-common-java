package ra.common;

import java.io.Serializable;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public interface ServiceCallback extends Serializable {
    void reply(Envelope envelope);
}
