package ra.common.route;

import ra.common.Network;
import ra.common.NetworkPeer;
import ra.util.JSONParser;
import ra.util.JSONPretty;

import java.util.Map;

public class SimpleExternalRoute extends SimpleRoute implements ExternalRoute {

    private NetworkPeer origination;
    private NetworkPeer destination;

    public SimpleExternalRoute() {}

    public SimpleExternalRoute(String service, String operation) {
        super(service, operation);
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
    public Map<String, Object> toMap() {
        Map<String, Object> m = super.toMap();
        if(origination!=null) {
            m.put("origination",origination.toMap());
            m.put("network-orig",origination.getNetwork().name());
        }
        if(destination!=null) {
            m.put("destination",destination.toMap());
            m.put("network-dest",destination.getNetwork().name());
        }
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        super.fromMap(m);
        if(m.get("origination")!=null) {
            Network network = Network.valueOf((String)m.get("network-orig"));
            origination = new NetworkPeer(network);
            origination.fromMap((Map<String, Object>)m.get("origination"));
        }
        if(m.get("destination")!=null) {
            Network network = Network.valueOf((String)m.get("network-dest"));
            destination = new NetworkPeer(network);
            destination.fromMap((Map<String, Object>)m.get("destination"));
        }
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
}
