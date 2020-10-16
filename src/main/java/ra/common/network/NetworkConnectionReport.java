package ra.common.network;

import ra.common.content.JSON;

import java.util.Map;

public class NetworkConnectionReport extends JSON {
    public Long start;
    public Long end;
    public String url;
    public String code;
    public String codeDescription;
    public NetworkStatus resultantStatus;

    @Override
    public Map<String, Object> toMap() {
        Map<String,Object> m = super.toMap();
        if(start!=null) m.put("start",start);
        if(end!=null) m.put("end",end);
        if(url!=null) m.put("url",url);
        if(code!=null) m.put("code",code);
        if(codeDescription!=null) m.put("codeDescription",codeDescription);
        if(resultantStatus!=null) m.put("resultantStatus",resultantStatus.name());
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        super.fromMap(m);
        if(m.get("start")!=null) start = Long.parseLong((String)m.get("start"));
        if(m.get("end")!=null) end = Long.parseLong((String)m.get("end"));
        if(m.get("url")!=null) url = (String)m.get("url");
        if(m.get("code")!=null) code = (String)m.get("code");
        if(m.get("codeDescription")!=null) codeDescription = (String)m.get("codeDescription");
        if(m.get("resultantStatus")!=null) resultantStatus = NetworkStatus.valueOf((String)m.get("resultantStatus"));
    }
}
