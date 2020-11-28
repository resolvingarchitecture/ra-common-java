package ra.common.messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: Add Description
 *
 */
public final class DocumentMessage extends BaseMessage {

    public List<Map<String, Object>> data;

    public DocumentMessage() {
        data = new ArrayList<>();
        data.add(new HashMap<>());
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String,Object> m = super.toMap();
        if(data!=null) m.put("data", data);
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        super.fromMap(m);
        if(m.get("data")!=null) data = (List<Map<String,Object>>)m.get("data");
    }
}
