package ra.common;

import java.util.List;

public interface InfoVaultDB extends LifeCycle {
    String DB = "ra.common.db";
    void setBaseURL(String baseURL);
    boolean save(InfoVault infoVault);
    InfoVault load(String location, String id);
    boolean reload(InfoVault infoVault);
    boolean loadRange(String locationDirectory, Integer start, Integer count, List<InfoVault> infoVaultList);
    boolean find(InfoVault infoVault);
}
