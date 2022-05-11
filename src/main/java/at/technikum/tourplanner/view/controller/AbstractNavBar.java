package at.technikum.tourplanner.view.controller;

import at.technikum.tourplanner.SceneController;
import at.technikum.tourplanner.SceneControllerImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public abstract class AbstractNavBar {

    public SceneController sCon = new SceneControllerImpl();

    // BACK - BUTTON
    @FXML
    public void switchToMain(ActionEvent actionEvent) throws IOException {
        sCon.switchToMain(actionEvent);
    }
    // LIST TOURS - BUTTON
    @FXML
    private void switchToShowTourList(ActionEvent actionEvent) throws IOException {
        sCon.switchToShowTourList(actionEvent);
    }

    @FXML
    private void switchToCreateTour(ActionEvent actionEvent) throws IOException {
        sCon.switchToCreateTour(actionEvent);
    }

}
