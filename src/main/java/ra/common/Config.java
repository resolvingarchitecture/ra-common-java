package ra.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * TODO: Add Description
 *
 */
public class Config {

    public static String PROP_OPERATING_SYSTEM = "OPERATING_SYSTEM";
    public static String PROP_UI = "1m5.ui"; // true | false
    public static String PROP_UI_LAUNCH_ON_START = "1m5.ui.launchOnStart"; // true | false

    public enum OS {Android,Linux,OSX,Windows}

    private static final Logger LOG = Logger.getLogger(Config.class.getName());

    public static void logProperties(Properties p) {
        Enumeration e = p.keys();
        while(e.hasMoreElements()) {
            String key = (String)e.nextElement();
            String value = p.getProperty(key);
            LOG.info(key+":"+value);
        }
    }

    public static Properties loadAll(Properties clientProps, String configName) throws Exception {
        // Load system variables first
        Properties config = Config.loadFromSystemVariables();
        // Support stomping over System variables with environment variables
        config.putAll(Config.loadFromEnvironmentVariables());
        // Support stomping over environment variables with service configuration
        config.putAll(Config.loadFromClasspath(configName));
        // Supporting stomping over service configuration variables with client-supplied properties
        config.putAll(clientProps);
        return config;
    }

    /**
     * Default with = as delimiter.
     * @param args
     * @return
     */
    public static Properties loadFromMainArgs(String[] args) {
        return loadFromMainArgs(args, "=");
    }

    public static Properties loadFromMainArgs(String[] args, String delimiter) {
        Properties props = new Properties();
        for(String arg : args) {
            if(arg.contains(delimiter)) {
                String[] nvp = arg.split(delimiter);
                props.put(nvp[0],nvp[1]);
            }
        }
        return props;
    }

    public static Properties loadFromSystemVariables() {
        return SystemProperties.getProperties();
    }

    public static Properties loadFromEnvironmentVariables() {
        Properties properties = new Properties();
        properties.putAll(System.getenv());
        return properties;
    }

    public static Properties loadFromClasspath(String name) throws Exception {
        return loadFromClasspath(name, null, false);
    }

    public static Properties loadFromClasspath(String path, Properties inProps, boolean overrideSupplied) throws Exception {
        LOG.info("Loading properties file "+path+"...");
        Properties p = new Properties();
        if(inProps != null && overrideSupplied)
            p.putAll(inProps);
        InputStream is = null;
        try {
            is = Config.class.getClassLoader().getResourceAsStream(path);
            p.load(is);
            Enumeration propNames = p.propertyNames();
            while(propNames.hasMoreElements()){
                String propName = (String)propNames.nextElement();
                p.put(propName, p.getProperty(propName));
            }
        } catch (Exception e) {
            LOG.warning("Failed to load properties file "+path);
            throw e;
        } finally {
            if(is!=null)
                try { is.close();} catch (IOException e) {}
        }
        if(inProps != null && !overrideSupplied)
            p.putAll(inProps);
        return p;
    }

    public static Properties loadFromMainArgsAndClasspath(String[] args, String path, boolean overrideMainArgs) throws Exception {
        Properties p = loadFromMainArgs(args, "=");
        return loadFromClasspath(path, p, overrideMainArgs);
    }

    public static Properties loadFromMainArgsAndClasspath(String[] args, String delimiter, String path, boolean overrideMainArgs) throws Exception {
        Properties p = loadFromMainArgs(args, delimiter);
        return loadFromClasspath(path, p, overrideMainArgs);
    }

//    public static Properties loadFromBase(String name) throws IOException {
//        LOG.info("Loading properties file "+name+"...");
//        Properties p = new Properties();
//        InputStream is = null;
//        String path = OneMFiveAppContext.getInstance().getBaseDir()+"/"+name;
//        LOG.info("Loading properties file from "+path+"...");
//        File folder = new File(path);
//        boolean pathExists = true;
//        if(folder.exists()) {
//            try {
//                is = new FileInputStream(path);
//                p.load(is);
//                LOG.info("Loaded properties file " + path + " with following name-value pairs:");
//                Enumeration propNames = p.propertyNames();
//                while (propNames.hasMoreElements()) {
//                    String propName = (String) propNames.nextElement();
//                    LOG.info(propName + ":" + p.getProperty(propName));
//                }
//            } catch (Exception e) {
//                LOG.warning("Failed to load properties file " + path);
//                throw e;
//            } finally {
//                if (is != null)
//                    try {
//                        is.close();
//                    } catch (IOException e) {
//                    }
//            }
//        } else {
//            try {
//                pathExists = folder.createNewFile();
//            } catch (IOException e) {
//                LOG.warning("Failed to create new file at: "+path);
//                throw(e);
//            }
//        }
//        if(!pathExists) {
//            LOG.warning("Couldn't create path: "+path);
//        }
//
//        return p;
//    }

//    public static void saveToClasspath(String name, Properties props) throws IOException {
//        LOG.info("Saving properties file "+name+"...");
//        props.store(new FileWriter(name), null);
//    }

//    public static void saveToBase(String name, Properties props) throws IOException {
//        LOG.info("Saving properties file "+name+"...");
//        String path = OneMFiveAppContext.getInstance().getBaseDir()+"/"+name;
//        props.store(new FileWriter(path), null);
//    }

}
