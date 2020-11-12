package ra.common;

import java.util.List;

public interface InfoVaultDB extends LifeCycle {
    String DB = "ra.common.db";
    void setBaseURL(String baseURL);
    boolean save(InfoVault infoVault);
    InfoVault load(String id);
    boolean reload(InfoVault infoVault);
    boolean loadRange(Integer start, Integer count, List<InfoVault> infoVaultList);
    boolean find(InfoVault infoVault);
    boolean delete(String id);
}
