package ra.common.identity;

/**
 * Identify objects carrying Personally Identifiable Information
 * and a method for clearing out the data. Normally done on a cloned
 * object being sent to another.
 *
 */
public interface PIIClearable {
    void clearSensitive();
}
