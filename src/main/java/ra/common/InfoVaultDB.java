package ra.common;

import java.util.List;

public interface InfoVaultDB extends LifeCycle {
    String DB = "ra.common.db";
    boolean save(InfoVault infoVault);
    boolean load(InfoVault infoVault);
    boolean loadRange(String locationDirectory, Integer start, Integer count, List<InfoVault> infoVaultList);
    boolean find(InfoVault infoVault);
}
