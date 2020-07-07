package ra.common.service;

import ra.common.service.Service;
import ra.common.service.ServiceStatus;
import ra.util.Config;
import ra.util.Wait;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import static ra.common.service.Service.RA_SERVICE_IMPL;

/**
 * Daemon to be used with any Service implementing ra.common.service.Service.
 */
public class ServiceDaemon {

    private static Service service;

    public static void main(String[] args) {
        Properties config = Config.loadFromMainArgs(args);
        if(config.getProperty(RA_SERVICE_IMPL)==null) {
            System.err.println(RA_SERVICE_IMPL+" argument required.");
            System.exit(-1);
        }
        String serviceClass = config.getProperty(RA_SERVICE_IMPL);
        Object serviceObj = null;
        try {
            serviceObj = Class.forName(serviceClass).getConstructor().newInstance();
        } catch (Exception e) {
            System.err.println("Unable to instantiate: "+serviceClass);
            System.exit(-1);
        }
        if(!(serviceObj instanceof Service)) {
            System.err.println("Class must implement ra.http.service.Service: "+serviceClass);
            System.exit(-1);
        }
        service = (Service)serviceObj;
        if(!service.start(config)) {
            System.err.println(serviceClass+" failed to start.");
            System.exit(-1);
        }
        while(ServiceStatus.SHUTDOWN != service.getServiceStatus() || ServiceStatus.ERROR != service.getServiceStatus()) {
            Wait.aSec(30);
        }
        System.exit(0);
    }

}
