package at.technikum.tourplanner.database.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {

    public static String GetConfigPropertyValue(String propertyName) {
        Properties properties = new Properties();
        String propertyFileName = "config.properties";
        InputStream inputStream = ConfigurationManager.class.getClassLoader().getResourceAsStream(propertyFileName);

        try {
            if (inputStream != null) {
                properties.load(inputStream);
                return properties.getProperty(propertyName);
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        return "";
    }
}
