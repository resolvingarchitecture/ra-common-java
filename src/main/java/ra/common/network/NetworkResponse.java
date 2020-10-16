package ra.common.network;

import ra.common.service.Packet;

import java.util.Map;

public class NetworkResponse extends Packet {

    public String requestId;

    public NetworkResponse(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> m = super.toMap();
        if(requestId!=null) m.put("requestId",requestId);
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        super.fromMap(m);
        if(m.get("requestId")!=null) requestId = (String)m.get("requestId");
    }
}
