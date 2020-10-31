package ra.common.route;

import ra.common.network.Network;
import ra.common.network.NetworkPeer;

import java.util.Map;
import java.util.logging.Logger;

public final class RelayedExternalRoute extends SimpleExternalRoute {

    private Logger LOG = Logger.getLogger(RelayedExternalRoute.class.getName());

    protected NetworkPeer fromPeer;
    protected NetworkPeer toPeer;

    protected Boolean delayed = false;
    protected Long minDelay = 0L;
    protected Long maxDelay = 0L;

    protected Boolean copy = false;
    protected Integer minCopies = 0;
    protected Integer maxCopies = 0;

    protected Integer sensitivity = 0;

    public RelayedExternalRoute() {
        super();
    }

    public NetworkPeer getFromPeer() {
        return fromPeer;
    }

    public RelayedExternalRoute setFromPeer(NetworkPeer fromPeer) {
        this.fromPeer = fromPeer;
        return this;
    }

    public NetworkPeer getToPeer() {
        return toPeer;
    }

    public RelayedExternalRoute setToPeer(NetworkPeer toPeer) {
        this.toPeer = toPeer;
        return this;
    }

    public Boolean getDelayed() {
        return delayed;
    }

    public void setDelayed(Boolean delayed) {
        this.delayed = delayed;
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

    public Boolean getCopy() {
        return copy;
    }

    public void setCopy(Boolean copy) {
        this.copy = copy;
    }

    public Integer getMinCopies() {
        return minCopies;
    }

    public void setMinCopies(Integer minCopies) {
        this.minCopies = minCopies;
    }

    public Integer getMaxCopies() {
        return maxCopies;
    }

    public void setMaxCopies(Integer maxCopies) {
        this.maxCopies = maxCopies;
    }

    public Integer getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(Integer sensitivity) {
        this.sensitivity = sensitivity;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> m = super.toMap();
        if(fromPeer != null) {
            m.put("fromPeer", fromPeer.toMap());
            m.put("fromPeer.network", fromPeer.getNetwork());
        }
        if(toPeer != null) {
            m.put("toPeer", toPeer.toMap());
            m.put("toPeer.network", toPeer.getNetwork());
        }
        if(delayed != null) m.put("delayed", delayed);
        if(minDelay != null) m.put("minDelay", minDelay);
        if(maxDelay != null) m.put("maxDelay", maxDelay);
        if(copy != null) m.put("copy", copy);
        if(minCopies != null) m.put("minCopies", minCopies);
        if(maxCopies != null) m.put("maxCopies", maxCopies);
        if(sensitivity != null) m.put("sensitivity", sensitivity);
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        super.fromMap(m);
        if(m.get("fromPeer") != null) {
            fromPeer = new NetworkPeer((String)m.get("fromPeer.network"));
            fromPeer.fromMap((Map<String, Object>)m.get("fromPeer"));
        }
        if(m.get("toPeer") != null) {
            toPeer = new NetworkPeer((String)m.get("toPeer.network"));
            toPeer.fromMap(((Map<String, Object>)m.get("toPeer")));
        }
        if(m.get("delayed") != null) delayed = (Boolean)m.get("delayed");
        if(m.get("minDelay") != null) minDelay = (Long)m.get("minDelay");
        if(m.get("maxDelay") != null) maxDelay = (Long)m.get("maxDelay");
        if(m.get("copy") != null) copy = (Boolean)m.get("copy");
        if(m.get("minCopies") != null) minCopies = (Integer)m.get("minCopies");
        if(m.get("maxCopies") != null) maxCopies = (Integer)m.get("maxCopies");
        if(m.get("sensitivity") != null) sensitivity = (Integer)m.get("sensitivity");
    }
}
