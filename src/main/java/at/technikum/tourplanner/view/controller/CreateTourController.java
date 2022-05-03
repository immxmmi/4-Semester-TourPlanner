package at.technikum.tourplanner.view.controller;

import at.technikum.tourplanner.SceneControllerImpl;
import at.technikum.tourplanner.business.tour.TourService;
import at.technikum.tourplanner.business.tour.TourServiceImpl;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateTourController extends AbstractNavBar implements Initializable {
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
    private ComboBox<Transporter> get_tour_transporter;


    @FXML
    private void createTour(ActionEvent actionEvent) throws IOException {
        TourService tourService = new TourServiceImpl();
        Tour tour = Tour.builder()
                .title(set_tour_title.getText())
                .from(set_tour_from.getText())
                .to(set_tour_to.getText())
                .transporter(Transporter.valueOf(set_tour_transport.getText()))
                .description(set_tour_description.getText())
                .build();

        tourService.saveTour(tour);
        this.switchToMain(actionEvent);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        transporterList();
    }

    public void transporterList(){
        for(Transporter transporter : Transporter.values()){
            //get_tour_transporter.getItems().add(transporter);
            //System.out.println(transporter);
        }
    }
}
