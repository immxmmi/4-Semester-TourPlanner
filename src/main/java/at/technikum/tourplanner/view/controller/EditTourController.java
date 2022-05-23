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


public class EditTourController extends AbstractNavBar implements Initializable {

    // TOUR - EDIT
    @FXML
    private TextField set_tour_title;
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
    private Label error_description;

    private Tour currentTour;

    //INIT
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTransporter();
    }

    public void initialize(Tour tour) {
        currentTour = tour;
        set_tour_title.setText(tour.getTitle());
        set_tour_description.setText(tour.getDescription());
    }

    //EDIT
    @FXML
    private void editTour(ActionEvent actionEvent) throws IOException {
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

        if (set_tour_description.getText().equals("")) {
            error_description.setText("!");
            check = false;
        } else {
            error_description.setText("");
        }

        if (check) {
            currentTour.setTransporter(set_tour_transport.getValue());
            currentTour.setDescription(set_tour_description.getText());
            currentTour.setTitle(set_tour_title.getText());
            tourService.updateTour(currentTour);
            this.switchToMain(actionEvent);
        }
    }

    // LOAD TRANSPORTER
    private void loadTransporter() {
        for (Transporter transporter : Transporter.values()) {
            set_tour_transport.getItems().add(transporter);
        }
    }


}
