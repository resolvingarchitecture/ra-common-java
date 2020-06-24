package ra.common.service;

import ra.common.*;
import ra.common.messaging.*;
import ra.common.route.Route;
import ra.util.Config;
import ra.util.SystemSettings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * A base for all Services to provide a common framework for them.
 *
 */
public abstract class BaseService implements MessageConsumer, Service, LifeCycle {

    private static final Logger LOG = Logger.getLogger(BaseService.class.getName());

    protected boolean orchestrator = false;
    protected MessageProducer producer;
    private File serviceDirectory;
    private String version;

    private ServiceStatus serviceStatus;
    private Boolean registered;
    private List<ServiceStatusListener> serviceStatusListeners = new ArrayList<>();
    private List<ServiceStatusObserver> serviceStatusObservers = new ArrayList<>();

    protected Properties config;

    public BaseService() {

    }

    public BaseService(MessageProducer producer, ServiceStatusListener listener) {
        if(listener != null)
            serviceStatusListeners.add(listener);
        this.producer = producer;
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void registerServiceStatusListener(ServiceStatusListener listener) {
        serviceStatusListeners.add(listener);
    }

    public void unregisterServiceStatusListener(ServiceStatusListener listener) {
        serviceStatusListeners.remove(listener);
    }

    public void registerServiceStatusObservers(List<ServiceStatusObserver> observers) {
        serviceStatusObservers.addAll(observers);
    }

    public void unregisterServiceStatusObserver(ServiceStatusObserver observer) {
        serviceStatusObservers.remove(observer);
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    protected void updateStatus(ServiceStatus serviceStatus) {
        if(this.serviceStatus != serviceStatus) {
            // status has changed
            this.serviceStatus = serviceStatus;
            if (serviceStatusListeners != null) {
                for (ServiceStatusListener l : serviceStatusListeners) {
                    l.serviceStatusChanged(this.getClass().getName(), serviceStatus);
                }
            }
            if (serviceStatusObservers != null) {
                for (ServiceStatusObserver o : serviceStatusObservers) {
                    LOG.info("ServiceStatusObserver updating service: " + this.getClass().getName() + " with status: " + serviceStatus.name());
                    o.statusUpdated(serviceStatus);
                }
            }
        }
    }

    public MessageProducer getProducer() {
        return producer;
    }

    public void setProducer(MessageProducer producer) {
        this.producer = producer;
    }

    @Override
    public ServiceReport report() {
        ServiceReport report = new ServiceReport();
        report.version = version;
        report.running = serviceStatus == ServiceStatus.RUNNING;
        report.registered = registered;
        report.serviceClassName = this.getClass().getName();
        report.serviceStatus = serviceStatus;
        return report;
    }

    public boolean send(Envelope envelope) {
        LOG.info("Sending Envelope to Producer...");
        return producer.send(envelope);
    }

    @Override
    public final boolean receive(Envelope envelope) {
        LOG.finer("Envelope received by service. Handling...");
        if(envelope.getMessage() instanceof DocumentMessage)
            handleDocument(envelope);
        else if(envelope.getMessage() instanceof EventMessage)
            handleEvent(envelope);
        else if(envelope.getMessage() instanceof CommandMessage)
            runCommand(envelope);
        else
            handleHeaders(envelope);
        // If not orchestrator, always return a reply.
        // If orchestrator, it will determine if a reply should be sent.
        if(!orchestrator) {
            reply(envelope);
        }
        return true;
    }

    protected final void deadLetter(Envelope envelope) {
        LOG.warning("Can't route envelope:"+envelope);
    }

    protected final void endRoute(Envelope envelope) {
        LOG.fine("End of route and no client to return to:"+envelope);
    }

    @Override
    public void handleDocument(Envelope envelope) {LOG.warning(this.getClass().getName()+" has not implemented handleDocument().");}

    @Override
    public void handleEvent(Envelope envelope) {LOG.warning(this.getClass().getName()+" has not implemented handleEvent().");}

    @Override
    public void handleCommand(Envelope envelope) {LOG.warning(this.getClass().getName()+" has not implemented handleCommand().");}

    @Override
    public void handleHeaders(Envelope envelope) {LOG.warning(this.getClass().getName()+" has not implemented handleHeaders().");}

    /**
     * Supports synchronous high-priority calls from ServiceBus and asynchronous low-priority calls from receive()
     * @param envelope
     */
    final void runCommand(Envelope envelope) {
        LOG.finer("Running command by service...");
        CommandMessage m = (CommandMessage)envelope.getMessage();
        switch(m.getCommand()) {
            case Shutdown: {shutdown();break;}
            case Restart: {restart();break;}
            case Start: {
                Properties p = (Properties)envelope.getHeader(Properties.class.getName());
                start(p);
            }
        }
    }

    protected final void reply(Envelope envelope) {
        LOG.finest("Sending reply to service bus...");
        int maxAttempts = 30;
        int attempts = 0;
        // Create new Envelope instance with same ID, Headers, and Message so that Message Channel sees it as a different envelope.
        Envelope newEnvelope = Envelope.envelopeFactory(envelope);
        // Don't set if the orchestration service
        if(!orchestrator) {
            Route route = envelope.getRoute();
            if(route != null) route.setRouted(true);
        }
        while(!producer.send(newEnvelope) && ++attempts <= maxAttempts) {
            synchronized (this) {
                try {
                    this.wait(100);
                } catch (InterruptedException e) {}
            }
        }
    }

    public final File getServiceDirectory() {
        return serviceDirectory;
    }

    @Override
    public boolean start(Properties p) {
        try {
            config = Config.loadFromClasspath("ra-common.config", p, false);
        } catch (Exception e) {
            LOG.severe(e.getLocalizedMessage());
            return false;
        }
        version = config.getProperty("ra.version");

        File servicesFolder = null;
        try {
            servicesFolder = SystemSettings.getUserAppHomeDir(".ra", "services", true);
        } catch (IOException e) {
            LOG.severe(e.getLocalizedMessage());
            return false;
        }
        if(!servicesFolder.exists() && !servicesFolder.mkdir()) {
            LOG.severe("Unable to create services directory: " + servicesFolder.getAbsolutePath());
            return false;
        }

        config.setProperty("ra.dir.services", servicesFolder.getAbsolutePath());

        serviceDirectory = new File(servicesFolder, this.getClass().getName());
        if(!serviceDirectory.exists() && !serviceDirectory.mkdir()) {
            LOG.severe("Unable to create service directory: " + serviceDirectory.getAbsolutePath());
            return false;
        } else {
            if(!serviceDirectory.setWritable(true)) {
                LOG.severe("Unable to set service directory writable: "+serviceDirectory.getAbsolutePath());
                return false;
            }
            config.setProperty("ra.dir.services."+this.getClass().getName(), serviceDirectory.getAbsolutePath());
        }
        return true;
    }

    @Override
    public boolean pause() {
        return false;
    }

    @Override
    public boolean unpause() {
        return false;
    }

    @Override
    public boolean restart() {
        return false;
    }

    @Override
    public boolean shutdown() {
        return true;
    }

    @Override
    public boolean gracefulShutdown() {
        return true;
    }

}