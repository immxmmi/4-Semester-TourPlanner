package at.technikum.tourplanner.view.controller;

import at.technikum.tourplanner.business.config.ConfigurationManager;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.view.viewmodel.TourViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public interface SceneController {

    void switchToErrorPage(ActionEvent event) throws IOException;
    void switchToMain(ActionEvent event) throws IOException;

    // Tour - Suchleiste
     void switchToSearchBar(ActionEvent event) throws IOException;

    // Tour - Erstellen
     void switchToCreateTour(ActionEvent event) throws IOException;

    // Tour + TourLogger Einträge - Anzeigen
     void switchToShowTour(ActionEvent event, TourViewModel tourViewModel) throws IOException;
     void switchToShowTour(MouseEvent event, TourViewModel tourViewModel) throws IOException;

    // TourLogger - Erstellen
     void switchToCreateTourLog(ActionEvent event) throws IOException;

    // TourListe + Tour Erstellen Button - Anzeigen
     void switchToShowTourList(ActionEvent event) throws IOException;
}