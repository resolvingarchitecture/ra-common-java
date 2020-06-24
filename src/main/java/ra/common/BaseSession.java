package ra.common;

import ra.util.Protocol;
import ra.util.RandomUtil;

import java.util.*;

public abstract class BaseSession implements ProtocolSession {

    private final Integer id;
    protected Properties properties;
    protected ProtocolSession.Status status = ProtocolSession.Status.STOPPED;
    private List<ProtocolSessionListener> listeners = new ArrayList<>();
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

    public void addSessionListener(ProtocolSessionListener listener) {
        listeners.add(listener);
    }

    public void removeSessionListener(ProtocolSessionListener listener) {
        listeners.remove(listener);
    }

    @Override
    public Status getStatus() {
        return status;
    }

}
