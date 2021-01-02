package ra.common.route;

import ra.common.network.Network;
import ra.common.network.NetworkPeer;

import java.util.Map;

public class SimpleExternalRoute extends SimpleRoute implements ExternalRoute {

    protected NetworkPeer origination;
    protected NetworkPeer destination;
    protected Boolean sendContentOnly = false;
    protected Integer statusCode = 0;

    public SimpleExternalRoute() {}

    public SimpleExternalRoute(String service, String operation) {
        super(service, operation);
        sendContentOnly = true;
    }

    public SimpleExternalRoute(String service, String operation, NetworkPeer origination, NetworkPeer destination) {
        super(service, operation);
        this.origination = origination;
        this.destination = destination;
    }

    @Override
    public NetworkPeer getOrigination() {
        return origination;
    }

    public Route setOrigination(NetworkPeer origination) {
        this.origination = origination;
        return this;
    }

    @Override
    public NetworkPeer getDestination() {
        return destination;
    }

    public Route setDestination(NetworkPeer destination) {
        this.destination = destination;
        return this;
    }

    @Override
    public Boolean getSendContentOnly() {
        return sendContentOnly;
    }

    public void setSendContentOnly(Boolean sendContentOnly) {
        this.sendContentOnly = sendContentOnly;
    }

    @Override
    public Integer getStatusCode() {
        return statusCode;
    }

    @Override
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> m = super.toMap();
        if(origination!=null) {
            m.put("origination",origination.toMap());
            m.put("network-orig",origination.getNetwork());
        }
        if(destination!=null) {
            m.put("destination",destination.toMap());
            m.put("network-dest",destination.getNetwork());
        }
        if(sendContentOnly!=null) m.put("sendContentOnly", sendContentOnly ? "true" : "false");
        if(statusCode!=null) m.put("statusCode", statusCode);
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        super.fromMap(m);
        if(m.get("origination")!=null) {
            origination = new NetworkPeer(Network.valueOf((String)m.get("network-orig")));
            origination.fromMap((Map<String, Object>)m.get("origination"));
        }
        if(m.get("destination")!=null) {
            destination = new NetworkPeer(Network.valueOf((String)m.get("network-dest")));
            destination.fromMap((Map<String, Object>)m.get("destination"));
        }
        if(m.get("sendContentOnly")!=null) sendContentOnly = Boolean.parseBoolean((String)m.get("sendContentOnly"));
        if(m.get("statusCode")!=null) statusCode = (Integer)m.get("statusCode");
    }

}
