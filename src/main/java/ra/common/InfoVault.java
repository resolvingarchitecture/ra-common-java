package ra.common;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Properties;

/**
 * InfoVault
 *
 * Stores personal information securely while allowing access
 * by other parties with personal approval.
 */
public interface InfoVault {

    void setLocation(String location);
    String getLocation();

    void setName(String name);

    void save(String label, String key, byte[] content, boolean autoCreate) throws FileNotFoundException;

    Boolean delete(String label, String key);

    byte[] load(String label, String key) throws FileNotFoundException;

    List<byte[]> loadRange(String label, int start, int numberEntries);

    List<byte[]> loadAll(String label);

    Status getStatus();

    boolean init(Properties properties);

    boolean teardown();

}
