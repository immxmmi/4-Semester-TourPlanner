package at.technikum.tourplanner;

import at.technikum.tourplanner.business.mapQuest.MapQuestService;
import at.technikum.tourplanner.business.mapQuest.MapQuestServiceImpl;
import at.technikum.tourplanner.business.report.Report;
import at.technikum.tourplanner.business.report.ReportImpl;
import at.technikum.tourplanner.business.tour.TourService;
import at.technikum.tourplanner.business.tour.TourServiceImpl;
import at.technikum.tourplanner.database.dao.TourDao;
import at.technikum.tourplanner.database.sqlServer.TourDaoImpl;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;


public class MainApplication extends Application {

    static int width = 1101;
    static int height = 780;

    final Logger logger = LogManager.getRootLogger();

    @Override
    public void start(Stage stage) throws IOException {

        logger.info("Starting Tour Planner Pro...");

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view1.fxml"));
        stage.setTitle("Tour Planner Pro (30 Days free Trial)");
        Scene scene = new Scene(fxmlLoader.load(), width-30, height-30);
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

    public static void main(String[] args) throws SQLException {


        MapQuestService mapQuestService = new MapQuestServiceImpl();
        TourService tourService = new TourServiceImpl();


        TourDao tourDao = new TourDaoImpl();


        // GUI ABFRAGE:
        // - NAME
        // - FROM
        // - TO
        // - TRANSPORTER
        // - DESCRIPTION
        // - TIME
       /* Tour tour = Tour.builder()
                .title("Test.9")
                .from("Wien")
                .to("Linz")
                .transporter(Transporter.Walk)
                .description("Test Tour2")
                .build();


        tour = tourService.saveTour(tour);

        Report report = new ReportImpl();
        report.createTourReport(tour);
        tourService.deleteTour(tour.getTourID());
*/
        // TourLogs
        // - Date
        // - comment
        // - difficulty
        // - total time
        // - Rating



       // System.out.println(tour.toString());

        //     System.out.println(tour.getRouteImage().getDownloadURL());
       // FileAccess fileAccess = new FileAccessImpl();
      //  fileAccess.writeFile("newone.jpg",image.getImageData());
       // System.out.println(tourService.searchTourByName("me").toString());



        launch();

    }
}