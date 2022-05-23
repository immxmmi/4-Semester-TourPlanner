package at.technikum.tourplanner;

import at.technikum.tourplanner.config.ConfigurationManager;
import at.technikum.tourplanner.config.ConfigurationManagerImpl;
import at.technikum.tourplanner.database.common.DBConnect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;


public class MainApplication extends Application {

    private final static Logger log = LogManager.getLogger(MainApplication.class.getName());
    static ConfigurationManager config = new ConfigurationManagerImpl();

    static int width = config.getStageWidth();
    static int height =config.getStageHeight();

    @Override
    public void start(Stage stage) throws IOException {
        log.info("Starting Tour Planner Pro...");
        Parent root = FXMLLoader.load(getClass().getResource(config.getMainPage()));
        stage.setTitle("Tour Planner Pro (30 Days free Trial)");
        Scene scene = new Scene(root, width-30, height-30);
        stage.setMinHeight(height);
        stage.setMinWidth(width);
        stage.setMaxHeight(height);
        stage.setMaxWidth(width);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        DBConnect.getInstance().stopConnect();
        log.info("Stopping Tour Planner Pro...");
    }

    public static void main(String[] args){
        launch();
    }
}