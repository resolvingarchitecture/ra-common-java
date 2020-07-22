package ra.common.file;

import ra.common.LifeCycle;

public interface InfoVaultDB extends LifeCycle {
    boolean save(InfoVault infoVault);
    boolean load(InfoVault infoVault);
    boolean find(InfoVault infoVault);
}
