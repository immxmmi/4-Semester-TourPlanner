package at.technikum.tourplanner.views;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import lombok.Data;

@Data
public class TourViewModel {

    // TOUR - READ
    @FXML
    private Label in_tour_title;
    @FXML
    private Label in_tour_from;
    @FXML
    private Label in_tour_to;
    @FXML
    private Label in_tour_time;
    @FXML
    private Label in_tour_transport;
    @FXML
    private Label in_tour_distance;
    @FXML
    private Label in_tour_description;
    @FXML
    private ImageView in_tour_image;


    SimpleStringProperty tourTitle;

}
