package ra.common.network;

import ra.common.content.JSON;

import java.util.HashMap;
import java.util.Map;

public class NetworkState extends JSON {

    public String network;
    public NetworkPeer localPeer;
    public NetworkStatus networkStatus = NetworkStatus.UNINITIALIZED;
    public Integer virtualPort;
    public Integer targetPort;

    public Map<String, Object> params = new HashMap<>();

    @Override
    public Map<String,Object> toMap() {
        Map<String, Object> m = super.toMap();
        if(network!=null) m.put("network", network);
        if(networkStatus!=null) m.put("networkStatus", networkStatus.name());
        if(virtualPort!=null) m.put("virtualPort", virtualPort);
        if(targetPort!=null) m.put("targetPort", targetPort);
        if(localPeer!=null && localPeer.getDid().getUsername()!=null) m.put("username", localPeer.getDid().getUsername());
        if(localPeer!=null) m.put("fingerprint", localPeer.getDid().getPublicKey().getFingerprint());
        if(localPeer!=null) m.put("address", localPeer.getDid().getPublicKey().getAddress());
        if(params!=null) m.put("params", params);
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        super.fromMap(m);
        if(m.get("network")!=null) {
            network = (String)m.get("network");
            localPeer = new NetworkPeer(network);
        };
        if(m.get("networkStatus")!=null) networkStatus = NetworkStatus.valueOf((String)m.get("networkStatus"));
        if(m.get("virtualPort")!=null) virtualPort = Integer.parseInt((String)m.get("virtualPort"));
        if(m.get("targetPort")!=null) targetPort = Integer.parseInt((String)m.get("targetPort"));
        if(localPeer!=null) {
            if(m.get("username")!=null) localPeer.getDid().setUsername((String)m.get("username"));
            if(m.get("fingerprint")!=null) localPeer.getDid().getPublicKey().setFingerprint((String)m.get("fingerprint"));
            if(m.get("address")!=null) localPeer.getDid().getPublicKey().setAddress((String)m.get("address"));
        }
        if(m.get("params")!=null) params = (Map<String,Object>)m.get("params");
    }


}
