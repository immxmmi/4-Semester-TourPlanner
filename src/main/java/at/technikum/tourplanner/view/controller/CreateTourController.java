package at.technikum.tourplanner.view.controller;

import at.technikum.tourplanner.SceneControllerImpl;
import at.technikum.tourplanner.business.tour.TourService;
import at.technikum.tourplanner.business.tour.TourServiceImpl;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CreateTourController extends AbstractNavBar {
    private SceneControllerImpl sCon = new SceneControllerImpl();
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
        System.out.println(set_tour_title);
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


}
