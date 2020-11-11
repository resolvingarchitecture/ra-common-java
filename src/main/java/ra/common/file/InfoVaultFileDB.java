package ra.common.file;

import ra.common.InfoVault;
import ra.common.InfoVaultDB;
import ra.common.content.Content;
import ra.util.FileUtil;
import ra.util.JSONParser;

import java.io.File;
import java.util.List;
import java.util.Map;
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
            if(fileBytes!=null && fileBytes.length > 0) {
                infoVault.content = Content.newInstance((Map<String, Object>) JSONParser.parse(new String(fileBytes)));
            }
        } catch (Exception e) {
            LOG.warning(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean loadRange(String locationDirectory, Integer start, Integer count, List<InfoVault> infoVaults) {
        File dir = new File(locationDirectory);
        if(!dir.exists()) {
            return false;
        }
        File[] files = dir.listFiles();
        InfoVault iv;
        for(File f : files) {
            try {
                byte[] fileBytes = FileUtil.readFile(locationDirectory + f.getName());
                if (fileBytes!=null && fileBytes.length > 0) {
                    iv = new InfoVault();
                    iv.content = Content.newInstance((Map<String, Object>) JSONParser.parse(new String(fileBytes)));
                    infoVaults.add(iv);
                }
            } catch (Exception e) {
                LOG.warning(e.getLocalizedMessage());
                return false;
            }
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
