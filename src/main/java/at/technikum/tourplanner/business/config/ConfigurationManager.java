package at.technikum.tourplanner.business.config;

import org.testng.annotations.Test;

import java.io.*;
import java.util.Properties;

public class ConfigurationManager {


    public static String getConfigPropertyValue(String propertyName) {
        Properties properties = new Properties();
        final File initialFile = new File("src/config.properties");

        InputStream inputStream = null;
        try {

            inputStream = new DataInputStream(new FileInputStream(initialFile));
            properties.load(inputStream);
            return properties.getProperty(propertyName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    //DATABASE
    public String getDbUrl(){
        return getConfigPropertyValue("db_url");
    }
    public String getDbDatabase(){
        return getConfigPropertyValue("db_database");
    }
    public String getDbUsername(){
        return getConfigPropertyValue("db_username");
    }
    public String getDbPassword(){
        return getConfigPropertyValue("db_password");
    }
    public String getDbPort(){
        return getConfigPropertyValue("db_port");
    }


    //VIEWSETTINGS
    public String getStageWidth(){
        return getConfigPropertyValue("stage_width");
    }
    public String getStageHeight(){
        return getConfigPropertyValue("stage_height");
    }
    public String getMainPage(){
        return getConfigPropertyValue("main_page");
    }
    public String getErrorPage(){
        return getConfigPropertyValue("error_page");
    }

    //MAPPER
    public String getMapQuestID(){
        return getConfigPropertyValue("mapQuest_id");
    }

    //FILE
    public String getRoot(){
        return getConfigPropertyValue("root");
    }
    public String getImage(){
        return getConfigPropertyValue("image");
    }
    public String getReport(){
        return getConfigPropertyValue("report");
    }


}
