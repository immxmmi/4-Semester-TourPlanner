package at.technikum.tourplanner;

import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.TourLog;
import at.technikum.tourplanner.view.viewmodel.TourViewModel;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;

public interface SceneController {

    void switchToMain(ActionEvent event) throws IOException;

    // Tour - Erstellen
    void switchToCreateTour(ActionEvent event) throws IOException;

    // Tour + TourLogger Einträge - Anzeigen
    void switchToShowTour(ActionEvent event, TourViewModel tourViewModel) throws IOException;
    void switchToShowTour(MouseEvent event, TourViewModel tourViewModel) throws IOException;

    // Tour + TourLogger Einträge - Anzeigen
    void switchToEditTour(ActionEvent event, Tour tour) throws IOException;
    void switchToEditTourLog(MouseEvent event, TourLog tourLog) throws IOException;

    // TourListe + Tour Erstellen Button - Anzeigen
    void switchToShowTourList(ActionEvent event) throws IOException;

    void switchToSearchList(ActionEvent event, ArrayList<TourViewModel> searchResult) throws IOException;

}
