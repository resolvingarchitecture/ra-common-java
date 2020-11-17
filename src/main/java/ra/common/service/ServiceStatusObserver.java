package ra.common.service;

public interface ServiceStatusObserver {
    void serviceStatusChanged(String serviceFullName, ServiceStatus serviceStatus);
}
