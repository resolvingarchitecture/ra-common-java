package ra.common.service;

import java.util.Properties;

/**
 * The responsibility to register and unregister a service along with its observers.
 */
public interface ServiceRegistrar {

    boolean registerService(String serviceClass, Properties properties)
            throws ServiceNotAccessibleException, ServiceNotSupportedException, ServiceRegisteredException;

    boolean unregisterService(String serviceClass);
}
