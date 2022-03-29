package at.technikum.tourplanner;

import at.technikum.tourplanner.database.common.DBConnect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class MainApplication extends Application {

    //static Logger logger = LogManager.getRootLogger();

    static int width = 970;
    static int height = 770;


    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view1.fxml"));
        stage.setTitle("Tour Planner Pro (30 Days free Trial)");
        Scene scene = new Scene(fxmlLoader.load(), height, width);
        stage.setMinHeight(height);
        stage.setMinWidth(width);
        stage.setMaxHeight(height);
        stage.setMaxWidth(width);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {

        DBConnect.getInstance().getConnection();


       // TourService tourService = new TourServiceImpl();
      //  System.out.println(tourService.getAllTourOrderByName().get(0));

        launch();

    }
}