package ra.common;

import ra.util.JSONParser;
import ra.util.JSONPretty;

import java.util.HashMap;
import java.util.Map;

public class NetworkState {

    public String network;
    public NetworkPeer localPeer;
    public ServiceStatus serviceStatus;
    public Integer virtualPort;
    public Integer targetPort;

    public Map<String, Object> params = new HashMap<>();

    @Override
    public String toString() {
        Map<String, Object> m = new HashMap<>();
        if(network!=null) m.put("network", network);
        if(serviceStatus!=null) m.put("serviceStatus", serviceStatus.name());
        if(virtualPort!=null) m.put("virtualPort", virtualPort);
        if(targetPort!=null) m.put("targetPort", targetPort);
        if(localPeer!=null && localPeer.getDid().getUsername()!=null) m.put("username", localPeer.getDid().getUsername());
        if(localPeer!=null) m.put("fingerprint", localPeer.getDid().getPublicKey().getFingerprint());
        if(localPeer!=null) m.put("address", localPeer.getDid().getPublicKey().getAddress());
        if(params!=null) m.put("params", params);
        return JSONPretty.toPretty(JSONParser.toString(m), 4);
    }
}
