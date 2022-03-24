package at.technikum.tourplanner;

import at.technikum.tourplanner.business.TourService;
import at.technikum.tourplanner.business.TourServiceImpl;
import at.technikum.tourplanner.database.DBConnect;
import at.technikum.tourplanner.database.DBConnectImpl;
import at.technikum.tourplanner.database.dao.TourDao;
import at.technikum.tourplanner.database.sqlServer.TourDaoImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;


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

    public static void main(String[] args) {
       // protected Connection connection = DBConnectImpl.getInstance().getConnection(); // BESTEHENDE VERBINDUNG WIRD VERWENDET

        TourService tourService = new TourServiceImpl();
        System.out.println(tourService.getAllTourOrderByName().get(0));

        launch();
    }
}