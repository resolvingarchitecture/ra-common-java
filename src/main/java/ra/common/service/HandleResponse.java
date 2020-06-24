package ra.common.service;

import ra.common.network.Response;

public interface HandleResponse extends Operation {
    Boolean operate(Response response);
}
