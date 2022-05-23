package at.technikum.tourplanner.view.controller;

import at.technikum.tourplanner.business.tour.TourService;
import at.technikum.tourplanner.business.tour.TourServiceImpl;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    private ComboBox<Transporter> set_tour_transport;
    @FXML
    private TextArea set_tour_description;

    //ERROR
    @FXML
    private Label error_title;
    @FXML
    private Label error_transport;
    @FXML
    private Label error_from;
    @FXML
    private Label error_to;
    @FXML
    private Label error_description;

    //INIT
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTransporter();
    }

    // BUTTON - CREATE TOUR
    @FXML
    private void createTour(ActionEvent actionEvent) throws IOException {
        TourService tourService = new TourServiceImpl();
        boolean check = true;

        if (set_tour_transport.getValue() == null) {
            error_transport.setText("!");
            check = false;
        } else {
            error_transport.setText("");
        }

        if (set_tour_title.getText().equals("")) {
            error_title.setText("!");
            check = false;
        } else {
            error_title.setText("");
        }

        if (set_tour_from.getText().equals("")) {
            error_from.setText("!");
            check = false;
        } else {
            error_from.setText("");
        }

        if (set_tour_to.getText().equals("")) {
            error_to.setText("!");
            check = false;
        } else {
            error_to.setText("");
        }

        if (set_tour_description.getText().equals("")) {
            error_description.setText("!");
            check = false;
        } else {
            error_description.setText("");
        }


        if (check) {
            Tour tour = Tour.builder()
                    .title(set_tour_title.getText())
                    .from(set_tour_from.getText())
                    .to(set_tour_to.getText())
                    .transporter(set_tour_transport.getValue())
                    .description(set_tour_description.getText())
                    .build();

            tourService.saveTour(tour);

            if (tourService.getTourByID(tour.getTourID()) == null) {
                error_from.setText("! - Place may not exit");
                error_to.setText("! - Place may not exit");
                if (set_tour_transport.getValue().equals(Transporter.pedestrian)) {
                    error_transport.setText("! - Maybe too far to walk (limit: 200 miles / 320 km)");
                }
            } else {
                this.switchToMain(actionEvent);
            }
        }
    }

    // LOAD TRANSPORTER
    private void loadTransporter() {
        for (Transporter transporter : Transporter.values()) {
            set_tour_transport.getItems().add(transporter);
        }
    }

}
