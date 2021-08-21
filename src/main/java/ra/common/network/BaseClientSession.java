package ra.common.network;

import ra.common.RandomUtil;

import java.util.*;

public abstract class BaseClientSession implements NetworkClientSession {

    private final Integer id;
    protected Properties properties;
    protected NetworkClientSession.Status status = NetworkClientSession.Status.STOPPED;
    private List<NetworkClientSessionListener> listeners = new ArrayList<>();
    protected String address;

    public BaseClientSession() {
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

    public void addSessionListener(NetworkClientSessionListener listener) {
        listeners.add(listener);
    }

    public void removeSessionListener(NetworkClientSessionListener listener) {
        listeners.remove(listener);
    }



    @Override
    public Status getStatus() {
        return status;
    }

}
