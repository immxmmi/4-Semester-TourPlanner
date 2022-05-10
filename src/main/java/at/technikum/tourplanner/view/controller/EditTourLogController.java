package at.technikum.tourplanner.view.controller;

import at.technikum.tourplanner.business.tour.TourLogService;
import at.technikum.tourplanner.business.tour.TourLogServiceImpl;
import at.technikum.tourplanner.business.tour.TourService;
import at.technikum.tourplanner.business.tour.TourServiceImpl;
import at.technikum.tourplanner.models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditTourLogController extends AbstractNavBar implements Initializable{
    @FXML
    private TextField set_tour_total_time;
    @FXML
    private ComboBox<Level> set_tourlog_level;
    @FXML
    private ComboBox<Stars> set_tourlog_stars;
    @FXML
    private TextArea set_tour_comment;

    @FXML
    private Label error_total;
    @FXML
    private Label error_level;
    @FXML
    private Label error_stars;
    @FXML
    private Label error_comment;

    TourLogService tourLogService = new TourLogServiceImpl();

    private TourLog currentTourLog;

    public void initialize(TourLog tourlog) {
        currentTourLog = tourlog;
        set_tour_total_time.setText(""+tourlog.getTotalTime());
        set_tour_comment.setText(tourlog.getComment());

    }


    @FXML
    private void editTourLog(ActionEvent actionEvent) throws IOException {

       boolean check = true;
       if (set_tourlog_level.getValue() == null) {
           error_level.setText("!");
           check = false;
       } else {
           error_level.setText("");
       }

        if (set_tourlog_stars.getValue() == null) {
            error_stars.setText("!");
            check = false;
        } else {
            error_stars.setText("");
        }

        //Total Time
        if (set_tour_total_time.getText() == null) {
            error_total.setText("!");
            check = false;
        } else {
            error_total.setText("");
        }

        Double totalTime = 0.0;
        //Total Time
        if (set_tour_total_time.getText().equals("")) {
            error_total.setText("!");
            check = false;
        } else {
            try {
                totalTime = Double.parseDouble(set_tour_total_time.getText());
            } catch (NumberFormatException e) {
                check = false;
                error_total.setText("!");
            }
            error_total.setText("");
        }



        //Comment
        if (set_tour_comment.getText() == null) {
            error_comment.setText("!");
            check = false;
        } else {
            error_comment.setText("");
        }


       if (check) {
           currentTourLog.setComment(set_tour_comment.getText());
           currentTourLog.setLevel(set_tourlog_level.getValue());
           currentTourLog.setStars(set_tourlog_stars.getValue());
           currentTourLog.setTotalTime(totalTime);
           tourLogService.updateTourLog(currentTourLog);
           this.switchToMain(actionEvent);
       }
    }

    // LOAD DATA
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadLevels();
        loadStars();
    }
    private void loadLevels() {
        for (Level level : Level.values()) {
            set_tourlog_level.getItems().add(level);
        }
    }

    private void loadStars() {
        for (Stars stars : Stars.values()) {
            set_tourlog_stars.getItems().add(stars);
        }
    }


}
