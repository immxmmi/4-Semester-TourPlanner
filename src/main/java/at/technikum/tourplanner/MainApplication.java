package at.technikum.tourplanner;

import at.technikum.tourplanner.business.TourService;
import at.technikum.tourplanner.business.TourServiceImpl;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;


@Slf4j
public class MainApplication extends Application {

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

        TourService tourService = new TourServiceImpl();
        // GUI ABFRAGE:
        // - NAME
        // - FROM
        // - TO
        // - TRANSPORTER
        // - DESCRIPTION
        // - TIME
        Tour tour = Tour.builder()
                .name("Alt")
                .from("Berlin")
                .to("Salzburg")
                .transporter(Transporter.Walk)
                .description("hallo")
                .time(Time.valueOf("10:12:22"))
                .build();
        System.out.println(tourService.saveTour(tour));
        System.out.println(tourService.searchTourByName("me").toString());



        //launch();

    }
}