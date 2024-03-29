package ra.common.network;

import ra.common.JSONSerializable;
import ra.common.JSONParser;
import ra.common.JSONPretty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkState implements JSONSerializable {

    public Network network;
    public NetworkPeer localPeer;
    public NetworkStatus networkStatus = NetworkStatus.DISCONNECTED;
    public Integer virtualPort;
    public Integer targetPort;
    public List<NetworkConnectionReport> connectionReports = new ArrayList<>();
    public Integer updateIntervalSeconds = 20 * 60; // 20 minutes
    public Integer updateIntervalHyperSeconds = 60; // Every minute

    public Map<String, Object> params = new HashMap<>();

    @Override
    public Map<String,Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        if(network!=null) m.put("network", network.name());
        if(networkStatus!=null) m.put("networkStatus", networkStatus.name());
        if(virtualPort!=null) m.put("virtualPort", virtualPort);
        if(targetPort!=null) m.put("targetPort", targetPort);
        if(localPeer!=null && localPeer.getDid().getUsername()!=null) m.put("username", localPeer.getDid().getUsername());
        if(localPeer!=null) m.put("fingerprint", localPeer.getDid().getPublicKey().getFingerprint());
        if(localPeer!=null) m.put("address", localPeer.getDid().getPublicKey().getAddress());
        if(connectionReports!=null) m.put("connectionReports",connectionReports);
        if(updateIntervalSeconds!=null) m.put("updateIntervalSeconds",updateIntervalSeconds);
        if(updateIntervalHyperSeconds!=null) m.put("updateIntervalHyperSeconds",updateIntervalHyperSeconds);
        if(params!=null) m.put("params", params);
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        if(m.get("network")!=null) {
            network = Network.valueOf((String)m.get("network"));
            localPeer = new NetworkPeer(network);
        };
        if(m.get("networkStatus")!=null) networkStatus = NetworkStatus.valueOf((String)m.get("networkStatus"));
        if(m.get("virtualPort")!=null) virtualPort = (Integer)m.get("virtualPort");
        if(m.get("targetPort")!=null) targetPort = (Integer)m.get("targetPort");
        if(localPeer!=null) {
            if(m.get("username")!=null) localPeer.getDid().setUsername((String)m.get("username"));
            if(m.get("fingerprint")!=null) localPeer.getDid().getPublicKey().setFingerprint((String)m.get("fingerprint"));
            if(m.get("address")!=null) localPeer.getDid().getPublicKey().setAddress((String)m.get("address"));
        }
        if(m.get("updateIntervalSeconds")!=null) updateIntervalSeconds = (Integer)m.get("updateIntervalSeconds");
        if(m.get("updateIntervalHyperSeconds")!=null) updateIntervalHyperSeconds = (Integer)m.get("updateIntervalHyperSeconds");
        if(m.get("params")!=null) params = (Map<String,Object>)m.get("params");
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
