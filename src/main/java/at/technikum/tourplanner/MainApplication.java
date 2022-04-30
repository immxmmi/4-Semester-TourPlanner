package at.technikum.tourplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


public class MainApplication extends Application {

    static int width = 1199;
    static int height = 608;

    final Logger logger = LogManager.getRootLogger();

    @Override
    public void start(Stage stage) throws IOException {

        logger.info("Starting Tour Planner Pro...");

        Parent root = FXMLLoader.load(getClass().getResource("error.fxml"));
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