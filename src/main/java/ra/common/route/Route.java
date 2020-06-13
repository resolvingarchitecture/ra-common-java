package ra.common.route;

import ra.common.JSONSerializable;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public interface Route extends JSONSerializable {
    Route setRouteId(Long id);
    Long getRouteId();
    String getService();
    String getOperation();
    Route setRouted(Boolean routed);
    Boolean getRouted();
}
