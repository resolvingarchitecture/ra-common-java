package ra.common;

public interface InfoVaultDB extends LifeCycle {
    String DB = "ra.common.db";
    boolean save(InfoVault infoVault);
    boolean load(InfoVault infoVault);
    boolean find(InfoVault infoVault);
}
