package ra.common.service;

import ra.common.network.Request;
import ra.common.network.Response;
import ra.common.service.Operation;

/**
 * An Operation with a Request expecting a Response.
 */
public interface RequestReply extends Operation {
    Response operate(Request request);
}
