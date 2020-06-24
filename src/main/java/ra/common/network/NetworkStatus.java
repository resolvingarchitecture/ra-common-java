package ra.common.network;

/**
 * Status States of a Protocol.
 */
public enum NetworkStatus {
    UNREGISTERED, // 0 - Unknown/not registered yet
    // Sensor Starting Up
    NOT_INITIALIZED, // 1 - Initial state - Registered
    INITIALIZING, // 2 - Initializing Sensor's environment including configuration of Networking component
    STARTING, // 3 - Starting of Networking component
    WAITING,  // Optional 3.1 - means this sensor is waiting on a dependent sensor's status to change to STARTING, e.g. Bote waiting on I2P to begin starting up.
    // Sensor Networking
    NETWORK_WARMUP, // Optional 3.2 - means this sensor is waiting for a dependent sensor's status to change to NETWORK_CONNECTED, e.g. Bote waiting on I2P to actually connect.
    NETWORK_PORT_CONFLICT, // Optional 3.3 - means this sensor was unable to open the supplied port - likely being blocked; recommend changing ports
    NETWORK_CONNECTING, // 4 - Attempting to connect with network
    NETWORK_CONNECTED, // 5 - Network successfully connected and ready to handle requests
    NETWORK_VERIFIED, // 6 - Network has claimed to be connected (NETWORK_CONNECTED) and we have received a message from the network verifying it is
    NETWORK_STOPPING, // Network connection is hanging, e.g. unacceptable response times, begin looking at alternatives
    NETWORK_STOPPED, // Network connection failed, try another or recommend alternative
    NETWORK_BLOCKED, // Network connection being blocked.
    NETWORK_UNAVAILABLE, // Network is not available; either not installed in machine or not started
    NETWORK_ERROR, // Error in Network; handle within Sensor if possible yet make Sensor Service aware of likely service degradation.
    // Sensor Pausing (Not Yet Supported In Any Sensors)
    PAUSING, // Queueing up requests both inbound and outbound waiting for pre-pausing requests to complete.
    PAUSED, // All pre-pausing requests completed.
    UNPAUSING, // Unblocking queued requests to allow them to continue on and not queueing further requests.
    // Sensor Shutdown
    SHUTTING_DOWN, // Shutdown imminent - not clean, process likely getting killed - perform the minimum ASAP
    GRACEFULLY_SHUTTING_DOWN, // Ideal clean teardown
    SHUTDOWN, // Was teardown forcefully - expect potential file / state corruption
    GRACEFULLY_SHUTDOWN, // Shutdown was graceful - safe to assume no file / state corruption
    // Sensor Restarting
    RESTARTING, // Short for GRACEFULLY_SHUTTING_DOWN then STARTING back up.
    // Sensor Error
    ERROR // Likely need of Sensor restart
}
