package ra.common.service;

/**
 * Used for updating clients on a specific service's status.
 */
public interface ServiceStatusObserver {
    void statusUpdated(ServiceStatus serviceStatus);
}
