package ra.common;

/**
 * An Operation with a Request expecting a Response.
 */
public interface RequestReply extends Operation {
    Response operate(Request request);
}
