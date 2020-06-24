package ra.common.network;

import ra.util.RandomUtil;

import java.util.*;

public abstract class BaseSession implements NetworkSession {

    private final Integer id;
    protected Properties properties;
    protected NetworkSession.Status status = NetworkSession.Status.STOPPED;
    private List<NetworkSessionListener> listeners = new ArrayList<>();
    protected String address;

    public BaseSession() {
        id = RandomUtil.nextRandomInteger();
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public boolean init(Properties properties) {
        this.properties = properties;
        return true;
    }

    public void addSessionListener(NetworkSessionListener listener) {
        listeners.add(listener);
    }

    public void removeSessionListener(NetworkSessionListener listener) {
        listeners.remove(listener);
    }

    @Override
    public Status getStatus() {
        return status;
    }

}
