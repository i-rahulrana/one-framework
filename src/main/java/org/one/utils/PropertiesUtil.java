package org.one.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    public static final Logger LOGGER = LogManager.getLogger(PropertiesUtil.class);
    Properties props = null;
    public static Properties resourcePathProps = null;
    public static Properties applicationProps = null;
    InputStream inputStream = null;
    String CONFIG_PROPERTIES_PATH;

    public PropertiesUtil(String CONFIG_PROPERTIES_PATH) {
        try {
            props = new Properties();
            this.CONFIG_PROPERTIES_PATH = CONFIG_PROPERTIES_PATH;
            inputStream = new FileInputStream(CONFIG_PROPERTIES_PATH);
            props.load(inputStream);
        } catch (Exception ex) {
            LOGGER.info(ex);
        }
    }

    /**
     * This method establish connection with the resource properties file and return
     * properties instance.
     *
     * @param filePath
     * @return
     */
    public static Properties initResourcePathProps(String filePath) {
        resourcePathProps = new Properties();
        try {
            FileInputStream inputStream = new FileInputStream(FileUtility.getFile(filePath).getAbsolutePath());
            resourcePathProps.load(inputStream);
        } catch (Exception e) {
            LOGGER.info(e);
        }
        return resourcePathProps;
    }

    /**
     * This method establish connection with the global properties file and return
     * global properties instance.
     *
     * @param filePath
     */
    public static void initGlobalProps(String filePath) {
        applicationProps = new Properties();
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            applicationProps.load(inputStream);
        } catch (Exception e) {
            LOGGER.info(e);
        }
    }

    /**
     * This method provides value of the given key in the resource properties file.
     *
     * @param key
     * @return
     */
    public static String getResourcePathValue(String key) {
        String value;
        value = resourcePathProps.getProperty(key).trim();
        return value;
    }

    /**
     * This method provides value of the given key in the global properties file.
     *
     * @param key
     * @return
     */
    public static String getGlobalPropValue(String key) {
        String value;
        value = applicationProps.getProperty(key).trim();
        return value;
    }

    /**
     * This is the generic method provides value of the given key from the properties file.
     *
     * @param key
     * @return
     */
    public String getPropValue(String key) {
        String value;
        value = props.getProperty(key).trim();
        return value;
    }
}
