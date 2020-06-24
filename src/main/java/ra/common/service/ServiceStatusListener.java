package ra.common.service;

/**
 * Used for updating the ServiceBus.
 *
 * @author objectorange
 */
public interface ServiceStatusListener {
    void serviceStatusChanged(String serviceFullName, ServiceStatus serviceStatus);
}
