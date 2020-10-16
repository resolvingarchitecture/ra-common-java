package ra.common.network;

/**
 * Network Status
 */
public enum NetworkStatus {
    // Network Starting Up
    UNINITIALIZED, // 1 - Initial state - Registered
    INITIALIZING, // 2 - Initializing Network's environment including configuration of Networking component
    NETWORK_STARTING, // 3 - Starting of Networking component
    WAITING,  // Optional 3.1 - means this sensor is waiting on a dependent sensor's status to change to STARTING, e.g. Bote waiting on I2P to begin starting up.
    // Sensor Networking
    WARMUP, // Optional 3.2 - means this sensor is waiting for a dependent sensor's status to change to NETWORK_CONNECTED, e.g. Bote waiting on I2P to actually connect.
    PORT_CONFLICT, // Optional 3.3 - means this sensor was unable to open the supplied port - likely being blocked; recommend changing ports
    CONNECTING, // 4 - Attempting to connect with network
    CONNECTED, // 5 - Network successfully connected and ready to handle requests
    VERIFIED, // 6 - Network has claimed to be connected (NETWORK_CONNECTED) and we have received a message from the network verifying it is
    STOPPING, // Network connection is hanging, e.g. unacceptable response times, begin looking at alternatives
    STOPPED, // Network connection failed, try another or recommend alternative
    BLOCKED, // Network connection being blocked.
    UNAVAILABLE, // Network is not available; either not installed in machine or not started
    ERROR // Error in Network; handle within Network component if possible yet make Network Service aware of likely service degradation.

}
