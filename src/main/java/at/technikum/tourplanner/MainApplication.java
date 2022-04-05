package at.technikum.tourplanner;

import at.technikum.tourplanner.business.MapQuestService;
import at.technikum.tourplanner.business.MapQuestServiceImpl;
import at.technikum.tourplanner.business.TourService;
import at.technikum.tourplanner.business.TourServiceImpl;
import at.technikum.tourplanner.database.dao.TourDao;
import at.technikum.tourplanner.database.sqlServer.TourDaoImpl;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;



public class MainApplication extends Application {

    static int width = 970;
    static int height = 780;

    final Logger logger = LogManager.getRootLogger();

    @Override
    public void start(Stage stage) throws IOException {

        logger.log(Level.ERROR,"Starting Tour Planner Pro...");

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
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



         TourDao tourDao = new TourDaoImpl();
         TourService tourService = new TourServiceImpl();
         MapQuestService mapQuestService = new MapQuestServiceImpl();
         // GUI ABFRAGE:
         // - NAME
         // - FROM
         // - TO
         // - TRANSPORTER
         // - DESCRIPTION
         // - TIME
         Tour tour = Tour.builder()
                 .title("wwwwwwwwwwwwwwwwwwwww")
                 .from("Linz")
                 .to("Berlin")
                 .transporter(Transporter.Walk)
                 .description("wwwwwww essfw")
                 .build();


         tour = tourService.saveTour(tour);

         if(tour == null){
             System.out.println("errorr");
         }else{

           // mapQuestService.downloadImage(tour.getRouteImage());
         }




        //     System.out.println(tour.getRouteImage().getDownloadURL());
       // FileAccess fileAccess = new FileAccessImpl();
      //  fileAccess.writeFile("newone.jpg",image.getImageData());
       // System.out.println(tourService.searchTourByName("me").toString());



        launch();

    }
}