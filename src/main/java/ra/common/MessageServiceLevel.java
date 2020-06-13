package ra.common;

public enum MessageServiceLevel {
    // Can lead to messages being lost, but they cannot be redelivered or duplicated
    AtMostOnce,
    // Ensures messages are never lost but may be duplicated
    AtLeastOnce,
    // Guarantees each message processing (not delivery) happens once and only once
    ExactlyOnce
}
