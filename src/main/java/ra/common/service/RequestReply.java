package ra.common.service;

import ra.common.network.NetworkRequest;
import ra.common.network.NetworkResponse;

/**
 * An Operation with a Request expecting a Response.
 */
public interface RequestReply extends Operation {
    NetworkResponse operate(NetworkRequest networkRequest);
}
