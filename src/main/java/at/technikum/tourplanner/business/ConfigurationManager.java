package at.technikum.tourplanner.business;

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
}
