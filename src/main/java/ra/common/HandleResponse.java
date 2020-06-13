package ra.common;

public interface HandleResponse extends Operation {
    Boolean operate(Response response);
}
