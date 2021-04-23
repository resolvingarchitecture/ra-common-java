package ra.common;

import ra.common.content.JSON;
import ra.common.file.Multipart;
import ra.common.identity.DID;
import ra.common.messaging.*;
import ra.common.network.NetworkPeer;
import ra.common.route.*;
import ra.common.service.ServiceLevel;
import ra.util.JSONParser;
import ra.util.JSONPretty;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

/**
 * Wraps all data passed around in application to ensure a space for header type information.
 *
 */
public final class Envelope extends JSON {

    private static final Logger LOG = Logger.getLogger(Envelope.class.getName());

    public static final String CONTENT = "CONTENT";
    public static final String ENTITY = "ENTITY";
    public static final String EXCEPTIONS = "EXCEPTIONS";

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
    public static final String HEADER_CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_CONTENT_TYPE_JSON = "application/json";
    public static final String HEADER_USER_AGENT = "User-Agent";

    public enum MessageType {DOCUMENT, TEXT, EVENT, COMMAND, NONE}
    public enum Action{POST, PUT, DELETE, GET}

    private String id;
    private DynamicRoutingSlip dynamicRoutingSlip = new DynamicRoutingSlip();
    private Route route = null;
    private List<String> markers = new ArrayList<>();
    private DID did = new DID();
    private String client;
    private Boolean replyToClient = false;
    private String clientReplyAction = null;
    private URL url = null;
    private Multipart multipart = null;

    private Action action = null;
    private String commandPath = null;

    private Map<String, Object> headers = new HashMap<>();
    private Message message;
    private Integer sensitivity = 1;
    private Boolean delayed = false;
    private Integer minDelay = 0; // Seconds
    private Integer maxDelay = 0; // Seconds
    private Boolean copy = false;
    private Integer maxCopies = 0;
    private Integer minCopies = 0;
    private ServiceLevel serviceLevel = ServiceLevel.AtLeastOnce;

    public static Envelope commandFactory() {
        return new Envelope(UUID.randomUUID().toString(), new CommandMessage());
    }

    public static Envelope documentFactory() {
        return new Envelope(UUID.randomUUID().toString(), new DocumentMessage());
    }

    public static Envelope documentFactory(String id) {
        return new Envelope(id, new DocumentMessage());
    }

    public static Envelope headersOnlyFactory() {
        return new Envelope(UUID.randomUUID().toString(), null);
    }

    public static Envelope eventFactory(EventMessage.Type type) {
        return new Envelope(UUID.randomUUID().toString(), new EventMessage(type.name()));
    }

    public static Envelope textFactory() {
        return new Envelope(UUID.randomUUID().toString(), new TextMessage());
    }

    public static Envelope envelopeFactory(Envelope envelope){
        Envelope e = new Envelope(envelope.getId(), envelope.getHeaders(), envelope.getMessage(), envelope.getDynamicRoutingSlip());
        e.setClient(envelope.getClient());
        e.setClientReplyAction(envelope.getClientReplyAction());
        e.setDID(envelope.getDID());
        e.setReplyToClient(envelope.replyToClient());
        e.setRoute(envelope.getRoute());
        e.setMarkers(envelope.getMarkers());
        e.setURL(envelope.getURL());
        e.setAction(envelope.getAction());
        e.setCommandPath(envelope.getCommandPath());
        e.setContentType(envelope.getContentType());
        e.setMultipart(envelope.getMultipart());
        e.setMessage(envelope.getMessage());
        e.setServiceLevel(envelope.getServiceLevel());
        e.setHeaders(envelope.getHeaders());
        return e;
    }

    public Envelope() {}

    public Envelope(String id, Message message) {
        this(id, message, new HashMap<>());
    }

    public Envelope(String id, Message message, Map<String, Object> headers) {
        this.id = id;
        this.message = message;
        this.headers = headers;
        this.dynamicRoutingSlip = new DynamicRoutingSlip();
    }

    private Envelope(String id, Map<String, Object> headers, Message message, DynamicRoutingSlip dynamicRoutingSlip) {
        this(id, message, headers);
        this.dynamicRoutingSlip = dynamicRoutingSlip;
    }

    public String getId() {
        return id;
    }

    public void setHeader(String name, Object value) {
        headers.put(name, value);
    }

    public boolean headerExists(String name) {
        return headers.containsKey(name);
    }

    public void removeHeader(String name) {
        headers.remove(name);
    }

    public Object getHeader(String name) {
        return headers.get(name);
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    private void setMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public DynamicRoutingSlip getDynamicRoutingSlip() {
        return dynamicRoutingSlip;
    }

    public Route getRoute() {
        if(route==null && dynamicRoutingSlip!=null) {
            route = dynamicRoutingSlip.getCurrentRoute();
        }
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<String> getMarkers() {
        return markers;
    }

    public void setMarkers(List<String> markers) {
        this.markers = markers;
    }

    public DID getDID() {
        return did;
    }

    public void setDID(DID did) {
        this.did = did;
    }

    public ServiceLevel getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(ServiceLevel serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Boolean replyToClient() {
        return replyToClient;
    }

    public void setReplyToClient(Boolean replyToClient) {
        this.replyToClient = replyToClient;
    }

    public String getClientReplyAction() {
        return clientReplyAction;
    }

    public void setClientReplyAction(String clientReplyAction) {
        this.clientReplyAction = clientReplyAction;
    }

    public URL getURL() {
        return url;
    }

    public void setURL(URL url) {
        this.url = url;
    }

    public Multipart getMultipart() {
        return multipart;
    }

    public void setMultipart(Multipart multipart) {
        this.multipart = multipart;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getCommandPath() {
        return commandPath;
    }

    public void setCommandPath(String commandPath) {
        this.commandPath = commandPath;
    }

    public String getContentType() {
        return (String)headers.get(HEADER_CONTENT_TYPE);
    }

    public void setContentType(String contentType) {
        headers.put(HEADER_CONTENT_TYPE, contentType);
    }

    public Integer getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(Integer sensitivity) {
        this.sensitivity = sensitivity;
    }

    public Boolean getDelayed() {
        return delayed;
    }

    public void setDelayed(Boolean delayed) {
        this.delayed = delayed;
    }

    public Integer getMinDelay() {
        return minDelay;
    }

    public void setMinDelay(Integer minDelay) {
        this.minDelay = minDelay;
    }

    public Integer getMaxDelay() {
        return maxDelay;
    }

    public void setMaxDelay(Integer maxDelay) {
        this.maxDelay = maxDelay;
    }

    public Boolean getCopy() {
        return copy;
    }

    public void setCopy(Boolean copy) {
        this.copy = copy;
    }

    public Integer getMaxCopies() {
        return maxCopies;
    }

    public void setMaxCopies(Integer maxCopies) {
        this.maxCopies = maxCopies;
    }

    public Integer getMinCopies() {
        return minCopies;
    }

    public void setMinCopies(Integer minCopies) {
        this.minCopies = minCopies;
    }

    // Helpers
    public void ratchet() {
        if(dynamicRoutingSlip!=null) {
            route = dynamicRoutingSlip.nextRoute();
        }
    }

    public boolean addRoute(Class service, String operation) {
        dynamicRoutingSlip.addRoute(new SimpleRoute(service.getName(),operation));
        return true;
    }

    public boolean addRoute(String service, String operation) {
        dynamicRoutingSlip.addRoute(new SimpleRoute(service,operation));
        return true;
    }

    public boolean addExternalRoute(Class service, String operation) {
        dynamicRoutingSlip.addRoute(new SimpleExternalRoute(service.getName(), operation));
        return true;
    }

    public boolean addExternalRoute(String service, String operation) {
        dynamicRoutingSlip.addRoute(new SimpleExternalRoute(service, operation));
        return true;
    }

    public boolean addExternalRoute(Class service, String operation, NetworkPeer origination, NetworkPeer destination) {
        dynamicRoutingSlip.addRoute(new SimpleExternalRoute(service.getName(), operation, origination, destination));
        return true;
    }

    public boolean addExternalRoute(String service, String operation, NetworkPeer origination, NetworkPeer destination) {
        dynamicRoutingSlip.addRoute(new SimpleExternalRoute(service, operation, origination, destination));
        return true;
    }

    public boolean addContent(Object content) {
        if(!(message instanceof DocumentMessage)) {
            return false;
        }
        ((DocumentMessage)message).data.get(0).put(CONTENT, content);
        return true;
    }

    public Object getContent() {
        if(!(message instanceof DocumentMessage)) {
            return null;
        }
        return ((DocumentMessage)message).data.get(0).get(CONTENT);
    }

    public boolean addEntity(Object entity) {
        if(!(message instanceof DocumentMessage)) {
            return false;
        }
        ((DocumentMessage)message).data.get(0).put(ENTITY, entity);
        return true;
    }

    public Object getEntity() {
        if(!(message instanceof DocumentMessage)) {
            return null;
        }
        return ((DocumentMessage)message).data.get(0).get(ENTITY);
    }

    public boolean addException(Exception e) {
        if(!(message instanceof DocumentMessage)) {
            return false;
        }
        List<Exception> exceptions = (List<Exception>)((DocumentMessage)message).data.get(0).get(EXCEPTIONS);
        if(exceptions == null) {
            exceptions = new ArrayList<>();
            ((DocumentMessage)message).data.get(0).put(EXCEPTIONS, exceptions);
        }
        exceptions.add(e);
        return true;
    }

    public List<Exception> getExceptions() {
        if(!(message instanceof DocumentMessage)) {
            return null;
        }
        List<Exception> exceptions = (List<Exception>)((DocumentMessage)message).data.get(0).get(EXCEPTIONS);
        if(exceptions == null) {
            exceptions = new ArrayList<>();
            ((DocumentMessage)message).data.get(0).put(EXCEPTIONS, exceptions);
        }
        return exceptions;
    }

    public void addErrorMessage(String errorMessage) {
        message.addErrorMessage(errorMessage);
    }

    public static List<String> getErrorMessages(Envelope envelope) {
        return envelope.getMessage().getErrorMessages();
    }

    public boolean addData(Class clazz, Object object) {
        if(!(message instanceof DocumentMessage)) {
            return false;
        }
        DocumentMessage dm = (DocumentMessage)message;
        dm.data.get(0).put(clazz.getName(), object);
        return true;
    }

    public Object getData(Class clazz) {
        if(!(message instanceof DocumentMessage)) {
            return null;
        }
        return ((DocumentMessage)message).data.get(0).get(clazz.getName());
    }

    public List<Map<String,Object>> getAllData() {
        if(!(message instanceof DocumentMessage)) {
            return null;
        }
        return ((DocumentMessage)message).data;
    }

    public boolean markerPresent(String marker) {
        return markers!=null && markers.contains(marker);
    }

    public boolean mark(String mark) {
        return markers.add(mark);
    }

    public boolean addNVP(String name, Object object){
        if(!(message instanceof DocumentMessage)) {
            return false;
        }
        DocumentMessage dm = (DocumentMessage)message;
        dm.data.get(0).put(name, object);
        return true;
    }

    public Object getValue(String name) {
        if(!(message instanceof DocumentMessage)) {
            return null;
        }
        return ((DocumentMessage)message).data.get(0).get(name);
    }

    public Map<String,Object> getValues() {
        if(!(message instanceof DocumentMessage)) {
            return null;
        }
        return ((DocumentMessage)message).data.get(0);
    }

    public EventMessage getEventMessage() {
        if(!(message instanceof EventMessage))
            return null;
        return (EventMessage)message;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        if(id!=null) m.put("id", id);
        if(dynamicRoutingSlip!=null) m.put(DynamicRoutingSlip.class.getSimpleName(), dynamicRoutingSlip.toMap());
        if(route!=null) m.put("route", route.toMap());
        if(did!=null) m.put("did", did.toMap());
        if(client!=null) m.put("client", client);
        if(replyToClient!=null) m.put("replyToClient",replyToClient);
        if(clientReplyAction!=null) m.put("clientReplyAction",clientReplyAction);
        if(url!=null) m.put("url", url.toString());
        if(markers!=null) m.put("markers", markers);
        if(multipart!=null) m.put("multipart", multipart.toMap());
        if(action!=null) m.put("action", action.name());
        if(commandPath!=null) m.put("commandPath", commandPath);
        if(headers!=null) m.put("headers", headers);
        if(message!=null) m.put("message", message.toMap());
        if(sensitivity!=null) m.put("sensitivity", sensitivity);
        if(delayed!=null) m.put("delayed", delayed ? "true":"false");
        if(minDelay != null) m.put("minDelay", minDelay);
        if(maxDelay != null) m.put("maxDelay", maxDelay);
        if(copy!=null) m.put("copy", copy ? "true":"false");
        if(minCopies!=null) m.put("minCopies", minCopies);
        if(maxCopies!=null) m.put("maxCopies", maxCopies);
        if(serviceLevel != null) m.put("serviceLevel", serviceLevel.name());
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        if(m.get("id")!=null) id = (String)m.get("id");
        if(m.get(DynamicRoutingSlip.class.getSimpleName())!=null) {
            dynamicRoutingSlip = new DynamicRoutingSlip();
            dynamicRoutingSlip.fromMap((Map<String, Object>)m.get(DynamicRoutingSlip.class.getSimpleName()));
        }
        if(m.get("route")!=null) {
            Map<String,Object> rm = (Map<String,Object>)m.get("route");
            String type = (String)rm.get("type");
            if(type==null) {
                LOG.warning("type must not be null. unable to reconstruct route.");
            }
            try {
                route = (Route) Class.forName(type).getConstructor().newInstance();
                route.fromMap(rm);
            } catch (Exception e) {
                LOG.warning(e.getLocalizedMessage());
            }
        }
        if(m.get("did")!=null) {
            did = new DID();
            did.fromMap(m);
        }
        if(m.get("client")!=null) client = (String)m.get("client");
        if(m.get("replyToClient")!=null) replyToClient = (Boolean)m.get("replyToClient");
        if(m.get("clientReplyAction")!=null) clientReplyAction = (String)m.get("clientReplyAction");
        try {
            if(m.get("url")!=null) url = new URL((String)m.get("url"));
        } catch (MalformedURLException e) {
            LOG.warning(e.getLocalizedMessage());
        }
        if(m.get("markers")!=null) markers = (List<String>)m.get("markers");
        if(m.get("multipart")!=null) {
            multipart = new Multipart();
            multipart.fromMap((Map<String, Object>)m.get("multipart"));
        }
        if(m.get("action")!=null) action = Action.valueOf((String)m.get("action"));
        if(m.get("commandPath")!=null) commandPath = (String)m.get("commandPath");
        if(m.get("headers")!=null) headers = (Map<String, Object>)m.get("headers");
        if(m.get("message")!=null) {
            Map<String, Object> msgM = (Map<String, Object>)m.get("message");
            try {
                message = (Message) Class.forName((String)msgM.get("clazz")).getConstructor().newInstance();
                message.fromMap(msgM);
            } catch (InstantiationException e) {
                LOG.warning(e.getLocalizedMessage());
            } catch (IllegalAccessException e) {
                LOG.warning(e.getLocalizedMessage());
            } catch (InvocationTargetException e) {
                LOG.warning(e.getLocalizedMessage());
            } catch (NoSuchMethodException e) {
                LOG.warning(e.getLocalizedMessage());
            } catch (ClassNotFoundException e) {
                LOG.warning(e.getLocalizedMessage());
            }
        }
        if(m.get("sensitivity")!=null) sensitivity = (Integer)m.get("sensitivity");
        if(m.get("delayed")!=null) delayed = m.get("delayed").equals("true");
        if(m.get("minDelay")!=null) minDelay = (Integer)m.get("minDelay");
        if(m.get("maxDelay")!=null) maxDelay = (Integer)m.get("maxDelay");
        if(m.get("copy")!=null) copy = m.get("copy").equals("true");
        if(m.get("minCopies")!=null) minCopies = (Integer)m.get("minCopies");
        if(m.get("maxCopies")!=null) maxCopies = (Integer)m.get("maxCopies");
        if(m.get("serviceLevel")!=null) serviceLevel = ServiceLevel.valueOf((String)m.get("serviceLevel"));
    }

    @Override
    public String toJSON() {
        return JSONPretty.toPretty(JSONParser.toString(toMap()), 4);
    }

    public String toJSONRaw() {
        return JSONParser.toString(toMap());
    }

    @Override
    public void fromJSON(String json) {
        fromMap((Map<String, Object>)JSONParser.parse(json));
    }

    @Override
    public String toString() {
        return toJSON();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Envelope &&
                ((Envelope)obj).getId().equals(id);
    }
}
