package ra.common;

import java.sql.Connection;

public interface InfoVaultJDBC extends InfoVaultDB {
    Connection getConnection();
}
