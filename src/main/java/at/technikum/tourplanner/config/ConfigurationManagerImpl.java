package at.technikum.tourplanner.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;


public class ConfigurationManagerImpl implements ConfigurationManager {

    //LOGGER
    private final static Logger log = LogManager.getLogger(ConfigurationManagerImpl.class.getName());

    private static String getConfigPropertyValue(String propertyName) {
        Properties properties = new Properties();
        final File initialFile = new File("src/config/config.properties");


        try {
            InputStream inputStream = new DataInputStream(new FileInputStream(initialFile));
            properties.load(inputStream);
            return properties.getProperty(propertyName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error(e);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e);
        }

        return "";
    }

    //VERSION
    @Override
    public String getVersion() {
        return getConfigPropertyValue("version");
    }

    //DATABASE
    @Override
    public String getDbUrl() {
        return getConfigPropertyValue("db_url");
    }

    @Override
    public String getDbDatabase() {
        return getConfigPropertyValue("db_database");
    }

    @Override
    public String getDbUsername() {
        return getConfigPropertyValue("db_username");
    }

    @Override
    public String getDbPassword() {
        return getConfigPropertyValue("db_password");
    }

    @Override
    public String getDbPort() {
        return getConfigPropertyValue("db_port");
    }

    //PAGE
    @Override
    public String getMainPage() {
        return getConfigPropertyValue("main_page");
    }

    @Override
    public String getErrorPage() {
        return getConfigPropertyValue("error_page");
    }

    //MAPPER
    @Override
    public String getMapQuestID() {
        return getConfigPropertyValue("mapQuest_id");
    }

    //IMAGE SETTINGS
    @Override
    public String getImage() {
        return getConfigPropertyValue("image");
    }

    @Override
    public int getImageHeight() {
        return Integer.parseInt(getConfigPropertyValue("image-height"));
    }

    @Override
    public int getImageWidth() {
        return Integer.parseInt(getConfigPropertyValue("image-width"));
    }

    //CREATE SETTINGS
    @Override
    public int getCreateTourHeight() {
        return Integer.parseInt(getConfigPropertyValue("create_tour_height"));
    }

    @Override
    public int getCreateTourWidth() {
        return Integer.parseInt(getConfigPropertyValue("create_tour_width"));
    }

    @Override
    public int getEditTourWidth() {
        return Integer.parseInt(getConfigPropertyValue("edit_tour_width"));
    }

    @Override
    public int getEditTourHeight() {
        return Integer.parseInt(getConfigPropertyValue("edit_tour_height"));
    }

    @Override
    public int getEditTourLogWidth() {
        return Integer.parseInt(getConfigPropertyValue("edit_tourLog_width"));
    }

    @Override
    public int getEditTourLogHeight() {
        return Integer.parseInt(getConfigPropertyValue("edit_tourLog_height"));
    }

    //FILE
    @Override
    public String getRoot() {
        return getConfigPropertyValue("root");
    }

    @Override
    public String getReport() {
        return getConfigPropertyValue("report");
    }

    // STAGE SETTINGS
    @Override
    public int getShowTourHeight() {
        return Integer.parseInt(getConfigPropertyValue("show_tour_height"));
    }

    @Override
    public int getShowTourWidth() {
        return Integer.parseInt(getConfigPropertyValue("show_tour_width"));
    }

    @Override
    public int getListTourHeight() {
        return Integer.parseInt(getConfigPropertyValue("list_tour_height"));
    }

    @Override
    public int getListTourWidth() {
        return Integer.parseInt(getConfigPropertyValue("list_tour_width"));
    }

    @Override
    public int getErrorHeight() {
        return Integer.parseInt(getConfigPropertyValue("error_height"));
    }

    @Override
    public int getErrorWidth() {
        return Integer.parseInt(getConfigPropertyValue("error_width"));
    }

    //VIEWSETTINGS
    @Override
    public int getStageWidth() {
        return Integer.parseInt(getConfigPropertyValue("stage_width"));
    }

    @Override
    public int getStageHeight() {
        return Integer.parseInt(getConfigPropertyValue("stage_height"));
    }
}
