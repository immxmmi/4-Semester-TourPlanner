package at.technikum.tourplanner;

import at.technikum.tourplanner.business.config.ConfigurationManager;
import at.technikum.tourplanner.business.config.ConfigurationManagerImpl;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.TourLog;
import at.technikum.tourplanner.view.controller.EditTourController;
import at.technikum.tourplanner.view.controller.EditTourLogController;
import at.technikum.tourplanner.view.controller.SearchListController;
import at.technikum.tourplanner.view.controller.ShowTourController;
import at.technikum.tourplanner.view.viewmodel.TourViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SceneControllerImpl implements SceneController {
    static ConfigurationManager config = new ConfigurationManagerImpl();
    static int width = config.getStageWidth();
    static int height = config.getStageHeight();
    private Stage stage;
    private Scene scene;
    private Parent root;

    // Erstellt für die einzelnen Pages eine Stage
    private void createStage() {
        this.scene = new Scene(root, width, height);
        stage.setMinHeight(height);
        stage.setMinWidth(width);
        stage.setMaxHeight(height);
        stage.setMaxWidth(width);
        this.stage.setScene(scene);
        this.stage.show();
    }

    // Main
    @Override
    public void switchToMain(ActionEvent event) throws IOException {
        width = config.getStageWidth();
        height = config.getStageHeight();
        this.root = FXMLLoader.load(getClass().getResource(config.getMainPage()));
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        createStage();
    }

    //ERROR
    @Override
    public void switchToErrorPage(ActionEvent event) throws IOException {
        width = config.getErrorWidth();;
        this.root = FXMLLoader.load(getClass().getResource(config.getErrorPage()));
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        createStage();
    }

    // Tour - Suchleiste
    @Override
    public void switchToSearchBar(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("search-main.fxml"));
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        createStage();
    }

    // Tour - Erstellen
    @Override
    public void switchToCreateTour(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("create-tour-view.fxml"));
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        height = config.getCreateTourHeight();
        width = config.getCreateTourWidth();
        createStage();
    }

    // Tour + TourLogger Einträge - Anzeigen
    @Override
    public void switchToShowTour(ActionEvent event, TourViewModel tourViewModel) throws IOException {
        width = config.getShowTourWidth();
        height = config.getShowTourHeight();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("show-tour-view.fxml"));
        this.root = loader.load();
        ShowTourController showTourController = loader.<ShowTourController>getController();
        showTourController.initialize(tourViewModel);
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        createStage();

    }

    @Override
    public void switchToShowTour(MouseEvent event, TourViewModel tourViewModel) throws IOException {
        width = config.getShowTourWidth();
        height = config.getShowTourHeight();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("show-tour-view.fxml"));
        this.root = loader.load();
        ShowTourController showTourController = loader.<ShowTourController>getController();
        showTourController.initialize(tourViewModel);
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        createStage();
    }


    // Tour + TourLogger Einträge - Anzeigen
    @Override
    public void switchToEditTour(ActionEvent event, Tour tour) throws IOException {
        width = config.getEditTourWidth();
        height = config.getEditTourHeight();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edit-tour-view.fxml"));
        this.root = loader.load();
        EditTourController editTourController = loader.<EditTourController>getController();
        editTourController.initialize(tour);
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        createStage();

    }

    @Override
    public void switchToEditTourLog(MouseEvent event, TourLog tourLog) throws IOException {
        System.out.println(tourLog);
       width = config.getEditTourLogWidth();
       height = config.getEditTourLogHeight();
       FXMLLoader loader = new FXMLLoader(getClass().getResource("edit-tourLog-view.fxml"));
       this.root = loader.load();
       EditTourLogController editTourLogController = loader.<EditTourLogController>getController();
       editTourLogController.initialize(tourLog);
       this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       createStage();
    }


    // TourLogger - Erstellen
    @Override
    public void switchToCreateTourLog(ActionEvent event) throws IOException {
        width = 800;
        height = 638;
        this.root = FXMLLoader.load(getClass().getResource("show-tour-view.fxml"));
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        createStage();
    }

    // TourListe + Tour Erstellen Button - Anzeigen
    @Override
    public void switchToShowTourList(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("show-tour-list-view.fxml"));
        width = config.getListTourWidth();
        height = config.getListTourHeight();
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        createStage();
    }

    @Override
    public void switchToSearchList(ActionEvent event, ArrayList<TourViewModel> searchResult) throws IOException {
        width = 1169;
        height = 700;


        FXMLLoader loader = new FXMLLoader(getClass().getResource("show-search-list-view.fxml"));
        this.root = loader.load();


        SearchListController searchListController = loader.<SearchListController>getController();
        searchListController.initialize(searchResult);
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        createStage();
    }

}
