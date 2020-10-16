package ra.common.service;

import ra.common.network.NetworkResponse;

public interface HandleResponse extends Operation {
    Boolean operate(NetworkResponse networkResponse);
}
