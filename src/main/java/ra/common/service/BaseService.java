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

import static ra.common.messaging.EventMessage.Type.SERVICE_STATUS;

/**
 * A base for all Services to provide a common framework for them.
 */
public abstract class BaseService implements Service {

    private static final Logger LOG = Logger.getLogger(BaseService.class.getName());

    protected MessageProducer producer;
    private File serviceDirectory;
    private String version;

    private ServiceStatus serviceStatus;
    private Boolean registered;
    private final List<Tuple2<String,String>> serviceStatusListeners = new ArrayList<>();
    private final List<String> servicesDependentUpon = new ArrayList<>();
    private ServiceStatusObserver observer;

    protected Properties config;

    public BaseService() {}

    public BaseService(MessageProducer producer, ServiceStatusObserver observer) {
        this.producer = producer;
        this.observer = observer;
    }

    public void addDependentService(String dependentService) {
        servicesDependentUpon.add(dependentService);
    }

    public List<String> getServicesDependentUpon() {
        return servicesDependentUpon;
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public boolean registerServiceStatusListener(Tuple2<String,String> listener) {
        return serviceStatusListeners.add(listener);
    }

    public boolean unregisterServiceStatusListener(Tuple2<String,String> listener) {
        return serviceStatusListeners.remove(listener);
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    protected void updateStatus(ServiceStatus serviceStatus) {
        if(this.serviceStatus != serviceStatus) {
            // status has changed
            this.serviceStatus = serviceStatus;
            if (serviceStatusListeners.size() > 0) {
                for (Tuple2<String,String> l : serviceStatusListeners) {
                    Envelope lEnv = Envelope.eventFactory(SERVICE_STATUS);
                    EventMessage em = (EventMessage)lEnv.getMessage();
                    em.setMessage(report());
                    em.setName(this.getClass().getName());
                    lEnv.addRoute(l.first, l.second);
                    send(lEnv);
                }
            }
            if(observer!=null) {
                observer.serviceStatusChanged(this.getClass().getName(), serviceStatus);
            }
        }
    }

    public MessageProducer getProducer() {
        return producer;
    }

    public void setProducer(MessageProducer producer) {
        this.producer = producer;
    }

    public ServiceStatusObserver getObserver() {
        return observer;
    }

    public void setObserver(ServiceStatusObserver observer) {
        this.observer = observer;
    }

    @Override
    public ServiceReport report() {
        ServiceReport report = new ServiceReport();
        report.version = version;
        report.running = serviceStatus == ServiceStatus.RUNNING;
        report.registered = registered;
        report.serviceClassName = this.getClass().getName();
        report.serviceStatus = serviceStatus;
        report.servicesDependentUpon = servicesDependentUpon;
        return report;
    }

    public boolean send(Envelope envelope) {
        LOG.info("Sending Envelope to Producer...");
        return producer.send(envelope);
    }

    public boolean send(Envelope envelope, Client callback) {
        LOG.info("Sending Envelope to Producer with callback...");
        return producer.send(envelope, callback);
    }

    @Override
    public final boolean receive(Envelope envelope) {
        LOG.finer("Envelope received by service. Handling...");
        if(envelope.getMessage() instanceof DocumentMessage)
            handleDocument(envelope);
        else if(envelope.getMessage() instanceof EventMessage)
            handleEvent(envelope);
        else if(envelope.getMessage() instanceof CommandMessage)
            handleCommand(envelope);
        else
            handleHeaders(envelope);
        // Always return a reply.
        reply(envelope);
        return true;
    }

    protected void deadLetter(Envelope envelope) {
        LOG.warning("Can't route envelope:"+envelope);
        // TODO: Register a dead letter service
    }

    protected final void endRoute(Envelope envelope) {
        LOG.fine("End of route and no client to return to:"+envelope);
    }

    @Override
    public void handleDocument(Envelope envelope) {
        LOG.warning(this.getClass().getName()+" has not implemented handleDocument().");
    }

    @Override
    public void handleEvent(Envelope envelope) {
        LOG.fine(this.getClass().getName()+" has not implemented handleEvent().");
    }

    @Override
    public void handleCommand(Envelope envelope) {
        CommandMessage msg = (CommandMessage)envelope.getMessage();
        switch(msg.getCommand()) {
            case Start: {
                Properties p = null;
                if(envelope.getValues()!=null && envelope.getValues().size()>0) {
                    p = new Properties();
                    p.putAll(envelope.getValues());
                }
                if(config!=null) {
                    if(p!=null) {
                        // Allow Properties to overwrite Config
                        config.putAll(p);
                    }
                }
                envelope.addNVP("result", start(config));
                break;
            }
            case Restart: {
                envelope.addNVP("result", restart());
                break;
            }
            case Pause: {
                envelope.addNVP("result", pause());
                break;
            }
            case Unpause: {
                envelope.addNVP("result", unpause());
                break;
            }
            case Shutdown: {
                envelope.addNVP("result", shutdown());
                break;
            }
            case GracefullyShutdown: {
                envelope.addNVP("result", gracefulShutdown());
                break;
            }
            case RegisterStatusListener: {
                if(envelope.getValue("listener")!=null) {
                    Tuple2<String,String> listener = (Tuple2<String, String>)envelope.getValue("listener");
                    envelope.addNVP("result", registerServiceStatusListener(listener));
                } else {
                    envelope.addErrorMessage("No Listener provided in 'listener' nvp.");
                }
                break;
            }
            case UnregisterStatusListener: {
                if(envelope.getValue("listener")!=null) {
                    Tuple2<String,String> listener = (Tuple2<String, String>)envelope.getValue("listener");
                    envelope.addNVP("result", unregisterServiceStatusListener(listener));
                } else {
                    envelope.addErrorMessage("No Listener provided in 'listener' nvp.");
                }
            }
        }
    }

    @Override
    public void handleHeaders(Envelope envelope) {
        LOG.warning(this.getClass().getName()+" has not implemented handleHeaders().");
    }

    protected final void reply(Envelope envelope) {
        LOG.finest("Set Route status replied...");
        Route route = envelope.getRoute();
        if(route != null) route.setRouted(true);
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
