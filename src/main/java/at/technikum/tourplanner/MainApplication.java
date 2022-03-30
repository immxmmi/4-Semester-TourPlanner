package at.technikum.tourplanner;

import at.technikum.tourplanner.business.MapQuestServiceImpl;
import at.technikum.tourplanner.models.Image;
import at.technikum.tourplanner.models.Route;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.SQLException;


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
        //log.error("hjgjhghjg");
       // DBConnect.getInstance().getConnection();

        MapQuestServiceImpl mapQuest = new MapQuestServiceImpl();

        Image image = Image.builder().build();
        Route route = mapQuest.searchRoute("Wien", "Berlin");
        route = mapQuest.setImageSettingsToRoute(route,image);
        image = mapQuest.copyRouteDataToImage(image,route);
        System.out.println(image.getDownloadURL());
        mapQuest.reloadImage(image);
        System.out.println(image.getDownloadURL());








       // TourService tourService = new TourServiceImpl();
      //  System.out.println(tourService.getAllTourOrderByName().get(0));

        launch();

    }
}