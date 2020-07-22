package ra.common.file;

import ra.common.InfoVault;
import ra.common.InfoVaultDB;
import ra.util.FileUtil;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class InfoVaultFileDB implements InfoVaultDB {

    private static final Logger LOG = Logger.getLogger(InfoVaultFileDB.class.getName());

    @Override
    public boolean save(InfoVault infoVault) {
        return FileUtil.writeFile(infoVault.content.toJSON().getBytes(), infoVault.content.getLocation());
    }

    @Override
    public boolean load(InfoVault infoVault) {
        try {
            byte[] fileBytes = FileUtil.readFile(infoVault.content.getLocation());
            if(fileBytes==null)
                return false;
            String jsonStr = new String(fileBytes);
            infoVault.content.fromJSON(jsonStr);
        } catch (IOException e) {
            LOG.warning(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean find(InfoVault infoVault) {
        return false;
    }

    @Override
    public boolean start(Properties properties) {
        return false;
    }

    @Override
    public boolean pause() {
        return false;
    }

    @Override
    public boolean unpause() {
        return false;
    }

    @Override
    public boolean restart() {
        return false;
    }

    @Override
    public boolean shutdown() {
        return false;
    }

    @Override
    public boolean gracefulShutdown() {
        return false;
    }
}
