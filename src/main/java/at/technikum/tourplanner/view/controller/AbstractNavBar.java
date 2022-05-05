package at.technikum.tourplanner.view.controller;

import at.technikum.tourplanner.SceneControllerImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public abstract class AbstractNavBar {
    public SceneController sCon = new SceneControllerImpl();
    @FXML
    public void switchToMain(ActionEvent actionEvent) throws IOException {
        sCon.switchToMain(actionEvent);
    }

}
