package at.technikum.tourplanner.view.controller;

import at.technikum.tourplanner.business.mapQuest.MapQuestService;
import at.technikum.tourplanner.business.mapQuest.MapQuestServiceImpl;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;

public class ShowTourController extends AbstractNavBar implements Initializable {

    private Tour tour = new Tour("test","test","test","test","test",2.0,null, new Time(00), Transporter.Bike);
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

    MapQuestService mapQuestService = new MapQuestServiceImpl();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        show_tour_title.setText(tour.getTitle());
        show_tour_from.setText(tour.getTitle());
        show_tour_to.setText(tour.getTitle());
        show_tour_time.setText(tour.getTitle());
        show_tour_transport.setText(tour.getTitle());
        show_tour_distance.setText(tour.getTitle());
        show_tour_description.setText(tour.getTitle());
        show_tour_image.setImage(mapQuestService.showRouteImage(tour.getRouteImage()));
    }
}
