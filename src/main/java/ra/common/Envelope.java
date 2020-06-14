package ra.common;

import ra.common.route.DynamicRoutingSlip;
import ra.common.Multipart;
import ra.common.route.Route;
import ra.util.JSONParser;
import ra.util.JSONPretty;
import ra.util.RandomUtil;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Wraps all data passed around in application to ensure a space for header type information.
 *
 */
public final class Envelope implements Persistable, JSONSerializable {

    private static final Logger LOG = Logger.getLogger(Envelope.class.getName());

    public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";

    public static final String HEADER_CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";

    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_CONTENT_TYPE_JSON = "application/json";

    public static final String HEADER_USER_AGENT = "User-Agent";

    public enum MessageType {DOCUMENT, TEXT, EVENT, COMMAND, NONE}
    public enum Action{POST, PUT, DELETE, GET}

    private Integer id;
    private Boolean external = false;
    private DynamicRoutingSlip dynamicRoutingSlip;
    private Route route = null;
    private DID did = new DID();
    private Long client = 0L;
    private Boolean replyToClient = false;
    private String clientReplyAction = null;
    private URL url = null;
    private Multipart multipart = null;

    private Action action = null;
    private String commandPath = null;

    private Map<String, Object> headers;
    private Message message;
    private ManCon manCon = ManConStatus.MIN_REQUIRED_MANCON;
    private Long minDelay = 0L;
    private Long maxDelay = 0L;
    private ServiceLevel serviceLevel = ServiceLevel.AtLeastOnce;

    public static Envelope commandFactory() {
        return new Envelope(RandomUtil.nextRandomInteger(), new CommandMessage());
    }

    public static Envelope documentFactory() {
        return new Envelope(RandomUtil.nextRandomInteger(), new DocumentMessage());
    }

    public static Envelope documentFactory(Integer id) {
        return new Envelope(id, new DocumentMessage());
    }

    public static Envelope headersOnlyFactory() {
        return new Envelope(RandomUtil.nextRandomInteger(), null);
    }

    public static Envelope eventFactory(EventMessage.Type type) {
        return new Envelope(RandomUtil.nextRandomInteger(), new EventMessage(type.name()));
    }

    public static Envelope textFactory() {
        return new Envelope(RandomUtil.nextRandomInteger(), new TextMessage());
    }

    public static Envelope envelopeFactory(Envelope envelope){
        Envelope e = new Envelope(envelope.getId(), envelope.getHeaders(), envelope.getMessage(), envelope.getDynamicRoutingSlip());
        e.setExternal(envelope.getExternal());
        e.setClient(envelope.getClient());
        e.setClientReplyAction(envelope.getClientReplyAction());
        e.setDID(envelope.getDID());
        e.setReplyToClient(envelope.replyToClient());
        e.setRoute(envelope.getRoute());
        e.setURL(envelope.getURL());
        e.setAction(envelope.getAction());
        e.setCommandPath(envelope.getCommandPath());
        e.setContentType(envelope.getContentType());
        e.setMultipart(envelope.getMultipart());
        e.setMessage(envelope.getMessage());
        e.setManCon(envelope.getManCon());
        e.setServiceLevel(envelope.getServiceLevel());
        return e;
    }

    public Envelope() {}

    public Envelope(Integer id, Message message) {
        this(id, message, new HashMap<>());
    }

    public Envelope(Integer id, Message message, Map<String, Object> headers) {
        this.id = id;
        this.message = message;
        this.headers = headers;
        this.dynamicRoutingSlip = new DynamicRoutingSlip();
    }

    private Envelope(Integer id, Map<String, Object> headers, Message message, DynamicRoutingSlip dynamicRoutingSlip) {
        this(id, message, headers);
        this.dynamicRoutingSlip = dynamicRoutingSlip;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getExternal() {
        return external;
    }

    public void setExternal(Boolean external) {
        this.external = external;
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
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
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

    public Long getClient() {
        return client;
    }

    public void setClient(Long client) {
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

    public ManCon getManCon() {
        return manCon;
    }

    public void setManCon(ManCon manCon) {
        this.manCon = manCon;
    }

    public Long getMinDelay() {
        return minDelay;
    }

    public void setMinDelay(Long minDelay) {
        this.minDelay = minDelay;
    }

    public Long getMaxDelay() {
        return maxDelay;
    }

    public void setMaxDelay(Long maxDelay) {
        this.maxDelay = maxDelay;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        if(id!=null) m.put("id", id);
        if(external!=null) m.put("external",external);
        if(dynamicRoutingSlip!=null) m.put("dynamicRoutingSlip", dynamicRoutingSlip.toMap());
        if(route!=null) m.put("route", route.toMap());
        if(did!=null) m.put("did", did.toMap());
        if(client!=null) m.put("client", client);
        if(replyToClient!=null) m.put("replyToClient",replyToClient);
        if(clientReplyAction!=null) m.put("clientReplyAction",clientReplyAction);
        if(url!=null) m.put("url", url.toString());
        if(multipart!=null) m.put("multipart", multipart.toMap());
        if(action!=null) m.put("action", action.name());
        if(commandPath!=null) m.put("commandPath", commandPath);
        if(headers!=null) m.put("headers", headers);
        if(message!=null) m.put("message", message.toMap());
        if(manCon !=null) m.put("manCon", manCon.name());
        if(minDelay != null) m.put("minDelay", minDelay);
        if(maxDelay != null) m.put("maxDelay", maxDelay);
        if(serviceLevel != null) m.put("serviceLevel", serviceLevel.name());
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        if(m.get("id")!=null) id = Integer.parseInt((String)m.get("id"));
        if(m.get("external")!=null) external = Boolean.parseBoolean((String)m.get("external"));
        if(m.get(DynamicRoutingSlip.class.getSimpleName())!=null) {
            dynamicRoutingSlip = new DynamicRoutingSlip();
            dynamicRoutingSlip.fromMap((Map<String, Object>)m.get(DynamicRoutingSlip.class.getSimpleName()));
        }
        if(m.get("route")!=null) {
            String type = (String)m.get("type");
            if(type==null) {
                LOG.warning("type must not be null. unable to reconstruct route.");
            }
            try {
                route = (Route) Class.forName(type).getConstructor().newInstance();
                route.fromMap((Map<String, Object>)m.get("route"));
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
        if(m.get("did")!=null) {
            did = new DID();
            did.fromMap(m);
        }
        if(m.get("client")!=null) client = (Long)m.get("client");
        if(m.get("replyToClient")!=null) replyToClient = (Boolean)m.get("replyToClient");
        if(m.get("clientReplyAction")!=null) clientReplyAction = (String)m.get("clientReplyAction");
        try {
            if(m.get("url")!=null) url = new URL((String)m.get("url"));
        } catch (MalformedURLException e) {
            LOG.warning(e.getLocalizedMessage());
        }
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
                message = (Message) Class.forName((String)msgM.get("type")).getConstructor().newInstance();
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
        if(m.get("manCon")!=null) manCon = ManCon.valueOf((String)m.get("manCon"));
        if(m.get("minDelay")!=null) minDelay = (Long)m.get("minDelay");
        if(m.get("maxDelay")!=null) maxDelay = (Long)m.get("maxDelay");
        if(m.get("serviceLevel")!=null) serviceLevel = ServiceLevel.valueOf((String)m.get("serviceLevel"));
    }

    @Override
    public String toJSON() {
        return JSONPretty.toPretty(JSONParser.toString(toMap()), 4);
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
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Envelope &&
                ((Envelope)obj).getId().equals(id);
    }
}
