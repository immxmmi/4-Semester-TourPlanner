package at.technikum.tourplanner;

import at.technikum.tourplanner.business.config.ConfigurationManager;
import at.technikum.tourplanner.business.config.ConfigurationManagerImpl;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;


public class MainApplication extends Application {


    final Logger logger = LogManager.getRootLogger();
    static ConfigurationManager config = new ConfigurationManagerImpl();

    static int width = config.getStageWidth();
    static int height =config.getStageHeight();


    @Override
    public void start(Stage stage) throws IOException {

        logger.info("Starting Tour Planner Pro...");

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
        logger.info("Stopping Tour Planner Pro...");
    }


    public static void main(String[] args){
        launch();
    }
}