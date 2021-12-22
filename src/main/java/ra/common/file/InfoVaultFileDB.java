package ra.common.file;

import ra.common.InfoVault;
import ra.common.InfoVaultDB;
import ra.common.content.Content;
import ra.common.FileUtil;
import ra.common.JSONParser;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class InfoVaultFileDB implements InfoVaultDB {

    private static final Logger LOG = Logger.getLogger(InfoVaultFileDB.class.getName());

    private File baseDirectory;

    @Override
    public boolean setBaseURL(String baseURL) {
        baseDirectory = new File(baseURL);
        return baseDirectory.exists() || baseDirectory.mkdir();
    }

    @Override
    public boolean save(InfoVault infoVault) {
        if(infoVault.content==null) {
            LOG.warning("Content must be provided in InfoVault to save content.");
            return false;
        }
        if(infoVault.content.getLocation()==null || infoVault.content.getLocation().isEmpty()) {
            LOG.warning("Content location must be provided when saving InfoVault.");
            return false;
        }
        return FileUtil.writeFile(infoVault.content.toJSON().getBytes(), infoVault.content.getLocation());
    }

    @Override
    public InfoVault load(String id) {
        InfoVault iv = null;
        try {
            byte[] fileBytes = FileUtil.readFile(baseDirectory+id);
            if (fileBytes.length > 0) {
                iv = new InfoVault();
                iv.content = Content.newInstance((Map<String, Object>) JSONParser.parse(new String(fileBytes)));
            }
        } catch (Exception e) {
            LOG.warning(e.getLocalizedMessage());
        }
        return iv;
    }

    @Override
    public boolean reload(InfoVault infoVault) {
        if(infoVault.content==null) {
            LOG.warning("Content object must be provided in InfoVault to reload it.");
            return false;
        }
        if(infoVault.content.getLocation()==null || infoVault.content.getLocation().isEmpty()) {
            LOG.warning("Content location must be provided when reloading InfoVault.");
            return false;
        }
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
    public boolean loadRange(Integer start, Integer count, List<InfoVault> infoVaults) {
        File[] files = baseDirectory.listFiles();
        InfoVault iv;
        for(File f : files) {
            try {
                byte[] fileBytes = FileUtil.readFile(baseDirectory + f.getName());
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
    public boolean delete(String id) {
        File fileLocation = new File(baseDirectory, id);
        if(fileLocation.exists()) {
            if(!fileLocation.delete()) {
                LOG.warning("Unable to delete file.");
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
