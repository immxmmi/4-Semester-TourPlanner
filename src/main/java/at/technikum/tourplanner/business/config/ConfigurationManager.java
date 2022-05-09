package at.technikum.tourplanner.business.config;

public interface ConfigurationManager {
    String getVersion();

    //DATABASE
    String getDbUrl();

    String getDbDatabase();

    String getDbUsername();

    String getDbPassword();

    String getDbPort();

    //FILE
    String getRoot();

    String getImage();

    String getReport();

    // STAGE SETTINGS
    int getShowTourHeight();

    int getShowTourWidth();

    int getListTourHeight();

    int getListTourWidth();

    int getErrorWidth();

    //VIEWSETTINGS
    int getStageWidth();

    int getStageHeight();

    String getMainPage();

    String getErrorPage();

    //MAPPER
    String getMapQuestID();

    int getImageHeight();

    int getImageWidth();

    int getCreateTourHeight();

    int getCreateTourWidth();
}
