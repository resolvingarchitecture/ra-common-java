package ra.common.network;

/**
 * Network Status
 */
public enum NetworkStatus {
    // Network Start Up
    NOT_INSTALLED, // Not all networking components are installed (run-time).
    CLOSED, // Network is closed - default starting state
    ERROR, // Error in Network, unable to start.
    PORT_CONFLICT, // this network was unable to open the supplied port - likely already in use; recommend changing ports
    WAITING,  // This network is waiting on a dependent network's status.
    WARMUP, // Network requires some time to establish itself prior to use. Requests may be queued, but won't be sent until Connected.
    CONNECTING, // Attempting to connect with network
    // Network Ops
    CONNECTED, // Network successfully connected and ready to handle requests
    VERIFIED, // Network has claimed to be connected (CONNECTED) and it has received a message from the network verifying it is
    HANGING, // Network connection is hanging, e.g. unacceptable response times
    FAILED, // Network connection failed, try another or recommend alternative
    BLOCKED, // Network connection being blocked (known indicators of explicit blocks)
    DISCONNECTED // Network is no longer connected
}
