package at.technikum.tourplanner.views;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import lombok.Data;

@Data
public class TourViewModel {

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
}
