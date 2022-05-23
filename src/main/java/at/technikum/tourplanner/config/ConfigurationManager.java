package at.technikum.tourplanner.config;

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

    int getErrorHeight();

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

    int getEditTourWidth();

    int getEditTourHeight();

    int getEditTourLogWidth();

    int getEditTourLogHeight();
}
