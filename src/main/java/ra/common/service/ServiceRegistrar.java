package ra.common.service;

import java.util.List;
import java.util.Properties;

/**
 * The responsibility to register and unregister a service along with its observers.
 */
public interface ServiceRegistrar {

    boolean registerService(Class serviceClass, Properties properties, List<ServiceStatusObserver> observers)
            throws ServiceNotAccessibleException, ServiceNotSupportedException, ServiceRegisteredException;

    boolean unregisterService(Class serviceClass);
}
