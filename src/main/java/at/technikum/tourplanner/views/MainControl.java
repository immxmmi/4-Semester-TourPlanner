package at.technikum.tourplanner.views;

import at.technikum.tourplanner.business.mapQuest.MapQuestService;
import at.technikum.tourplanner.business.mapQuest.MapQuestServiceImpl;
import at.technikum.tourplanner.business.tour.TourLogService;
import at.technikum.tourplanner.business.tour.TourLogServiceImpl;
import at.technikum.tourplanner.business.tour.TourService;
import at.technikum.tourplanner.business.tour.TourServiceImpl;
import at.technikum.tourplanner.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class MainControl {


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
   private Label show_tour_time;

   @FXML
   private Label show_tour_transport;
   @FXML
   private Label show_tour_distance;
   @FXML
   private Label show_tour_description;
   @FXML
   private ImageView show_tour_image;

   private String tourID;

   @FXML
    private Label tourlog_tour_id;

   ///////////////////////
    @FXML
    private void searchTour(){
        MainViewModel main = new MainViewModel();
     //  System.out.println(getSearch_input());
     //  System.out.println(main);
     //  System.out.println(main.getSearch_input());
        if(search_input == null){
            return;
        }
        TourService tourService = new TourServiceImpl();
        MapQuestService mapQuestService = new MapQuestServiceImpl();
        Tour searchResult = tourService.searchTourByName(search_input.getText());

         show_tour_title.setText(searchResult.getTitle());

         show_tour_from.setText(searchResult.getFrom());

         show_tour_to.setText(searchResult.getTo());

         show_tour_transport.setText(searchResult.getTransporter().toString());

         show_tour_distance.setText(""+searchResult.getDistance());
         show_tour_description.setText(searchResult.getDescription());


         show_tour_time.setText(searchResult.getTime().toString());

         show_tour_image.setImage(mapQuestService.showRouteImage(searchResult.getRouteImage()));

         this.tourID = searchResult.getTourID();
         tourlog_tour_id.setText(tourID);
    }

    // Tour - DELETE
    @FXML
    private void deleteTour(){
        TourService tourService = new TourServiceImpl();
        tourService.deleteTour(tourID);
        show_tour_title.setText("gelöscht");
        show_tour_from.setText("gelöscht");
        show_tour_to.setText("gelöscht");
        show_tour_transport.setText("gelöscht");
        show_tour_distance.setText("gelöscht");
        show_tour_description.setText("gelöscht");
        show_tour_image.setImage(null);

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
                .build();

        tourService.saveTour(tour);


    }


    // TOURLog - CREATE
    @FXML
    private TextField set_tourlog_date;
    @FXML
    private TextField set_tourlog_rating;
    @FXML
    private TextField set_tourlog_difficulty;
    @FXML
    private TextField set_tour;
    @FXML
    private TextField set_tourlog_total_time;
    @FXML
    private TextArea set_tourlog_comment;

    @FXML
    private void saveTourLog(){
        TourLogService tourLogService = new TourLogServiceImpl();
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
       TourLog tourLog = TourLog.builder()
               .tourID(tourID)
               .tourLogID(tourID + set_tourlog_rating + set_tourlog_difficulty)
               .date(date)
               .rating(Rating.valueOf(set_tourlog_rating.getText()))
               .difficulty(Difficulty.valueOf(set_tourlog_difficulty.getText()))
               .comment(set_tourlog_comment.getText())
               .totalTime(Double.parseDouble(set_tourlog_total_time.getText()))
               .build();

        if(tourID != null){
            tourLogService.saveTourLog(tourLog);
        }


    }




}
