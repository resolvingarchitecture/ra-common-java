package ra.common;

public interface UncaughtExceptionHandler {
    void handleUncaughtException(Throwable throwable, boolean doShutDown);
}
