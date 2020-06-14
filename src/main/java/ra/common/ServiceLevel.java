package ra.common;

public enum ServiceLevel {
    // Can lead to Envelopes being lost, but they cannot be redelivered or duplicated
    AtMostOnce,
    // Ensures Envelopes are never lost but may be duplicated
    AtLeastOnce,
    // Guarantees each Envelopes processing (not delivery) happens once and only once
    ExactlyOnce
}
