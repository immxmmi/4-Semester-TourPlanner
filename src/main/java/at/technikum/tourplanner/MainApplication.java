package at.technikum.tourplanner;

import at.technikum.tourplanner.business.ConfigurationManager;
import at.technikum.tourplanner.business.TourService;
import at.technikum.tourplanner.business.TourServiceImpl;
import at.technikum.tourplanner.database.common.DBConnect;
import at.technikum.tourplanner.database.common.DBConnectImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


public class MainApplication extends Application {

    //static Logger logger = LogManager.getRootLogger();

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        stage.setTitle("Tour Planner Pro (30 Days free Trial)");
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setMinWidth(500);
        stage.setMinHeight(500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {


        System.out.println(ConfigurationManager.GetConfigPropertyValue("db_username"));
  ///      Connection connect = DBConnectImpl.getInstance().getConnection();

//        connect.close();


       // TourService tourService = new TourServiceImpl();
      //  System.out.println(tourService.getAllTourOrderByName().get(0));

        launch();
    }
}