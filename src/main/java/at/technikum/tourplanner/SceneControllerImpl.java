package at.technikum.tourplanner;

import at.technikum.tourplanner.business.config.ConfigurationManager;
import at.technikum.tourplanner.models.Tour;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneControllerImpl implements SceneController {
    static int width = Integer.parseInt(ConfigurationManager.getConfigPropertyValue("stage_width"));
    static int height = Integer.parseInt(ConfigurationManager.getConfigPropertyValue("stage_height"));
    private Stage stage;
    private Scene scene;
    private Parent root;


    public void switchToErrorPage(ActionEvent event) throws IOException {
        width = 922;
        this.root = FXMLLoader.load(getClass().getResource(ConfigurationManager.getConfigPropertyValue("error_page")));
        createStage(event);
    }

    // Erstellt für die einzelnen Pages eine Stage
    private void createStage(ActionEvent event) {
        this.stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root, width, height);
        stage.setMinHeight(height);
        stage.setMinWidth(width);
        stage.setMaxHeight(height);
        stage.setMaxWidth(width);
        this.stage.setScene(scene);
        this.stage.show();
    }

    // Main
    public void switchToMain(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource(ConfigurationManager.getConfigPropertyValue("main_page")));
        createStage(event);
    }

    // Tour - Suchleiste
    public void switchToSearchBar(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("search-main.fxml"));
        createStage(event);
    }

    // Tour - Erstellen
    public void switchToCreateTour(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("create-tour-view.fxml"));
        height = height -30;
        createStage(event);
    }

    // Tour + TourLogger Einträge - Anzeigen
    public void switchToShowTour(ActionEvent event, Tour tour) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("show-tour-view.fxml"));
        createStage(event);
        System.out.println(tour);
    }

    // TourLogger - Erstellen
    public void switchToCreateTourLog(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("show-tour-view.fxml"));
        createStage(event);
    }

    // TourListe + Tour Erstellen Button - Anzeigen
    public void switchToShowTourList(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("show-tour-list-view.fxml"));
       //height = height -30;
       //width = width -30;
        createStage(event);
    }



}
