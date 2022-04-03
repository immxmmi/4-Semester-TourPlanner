package at.technikum.tourplanner.views;

import at.technikum.tourplanner.business.MapQuestService;
import at.technikum.tourplanner.business.MapQuestServiceImpl;
import at.technikum.tourplanner.business.TourService;
import at.technikum.tourplanner.business.TourServiceImpl;
import at.technikum.tourplanner.database.fileServer.FileAccess;
import at.technikum.tourplanner.database.fileServer.FileAccessImpl;
import at.technikum.tourplanner.models.RouteImage;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.sql.Time;

public class MainControl {



    @FXML
    private ImageView routeImageView;



    // SEARCH
   @FXML
   private TextField search_input;

   // TOUR - READ
   @FXML
   private Label show_tour_title;
   @FXML
   private Label show_tour_from;
   @FXML
   private Label show_tour_to;
   @FXML
   private Label show_tour_transport;
   @FXML
   private Label show_tour_distance;
    @FXML
    private ImageView show_tour_image;

    @FXML
    private void searchTour(){
        TourService tourService = new TourServiceImpl();
        MapQuestService mapQuestService = new MapQuestServiceImpl();
        Tour searchResult = tourService.searchTourByName(search_input.getText());

         show_tour_title.setText(searchResult.getTitle());

         show_tour_from.setText(searchResult.getFrom());

         show_tour_to.setText(searchResult.getTo());

         show_tour_transport.setText(searchResult.getTransporter().toString());

         show_tour_distance.setText(""+searchResult.getDistance());

         show_tour_image.setImage(mapQuestService.showRouteImage(searchResult.getRouteImage()));

    }


    // TOUR - CREATE
    @FXML
    private TextField set_tour_title;
    @FXML
    private TextField set_tour_from;
    @FXML
    private TextField set_tour_to;
    @FXML
    private TextField set_tour_transport;
    @FXML
    private TextArea set_tour_description;
    @FXML
    private TextField set_tour_time;

    @FXML
    private void createTour(){
        TourService tourService = new TourServiceImpl();

        Tour tour = Tour.builder()
                .title(set_tour_title.getText())
                .from(set_tour_from.getText())
                .to(set_tour_to.getText())
                .transporter(Transporter.valueOf(set_tour_transport.getText()))
                .description(set_tour_description.getText())
                .time(Time.valueOf(set_tour_time.getText()))
                .build();

        tourService.saveTour(tour);


    }


}
