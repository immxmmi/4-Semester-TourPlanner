package at.technikum.tourplanner.view.controller;

import at.technikum.tourplanner.SceneController;
import at.technikum.tourplanner.SceneControllerImpl;
import at.technikum.tourplanner.database.common.DBConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.sql.Connection;

public class ErrorController{
    SceneController sC = new SceneControllerImpl();
    @FXML
    private void reload(ActionEvent event) throws IOException {
        sC.switchToMain(event);
    }
}
