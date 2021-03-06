package at.technikum.tourplanner;

import at.technikum.tourplanner.config.ConfigurationManager;
import at.technikum.tourplanner.config.ConfigurationManagerImpl;
import at.technikum.tourplanner.database.common.DBConnect;
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
import java.lang.management.BufferPoolMXBean;
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

    private Boolean checkDB() {
        return DBConnect.getInstance().getDBOnline();
    }

    // Main
    @Override
    public void switchToMain(ActionEvent event) throws IOException {
        if (checkDB()) {
            width = config.getStageWidth();
            height = config.getStageHeight();
            this.root = FXMLLoader.load(getClass().getResource(config.getMainPage()));
            this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            createStage();
        } else {
            switchToErrorPage(event);
        }
    }

    // Tour - Erstellen
    @Override
    public void switchToCreateTour(ActionEvent event) throws IOException {
        if (checkDB()) {
            this.root = FXMLLoader.load(getClass().getResource("create-tour-view.fxml"));
            this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            height = config.getCreateTourHeight();
            width = config.getCreateTourWidth();
            createStage();
        } else {
            switchToErrorPage(event);
        }
    }

    // Tour + TourLogger Einträge - Anzeigen
    @Override
    public void switchToShowTour(ActionEvent event, TourViewModel tourViewModel) throws IOException {
        if (checkDB()) {
            width = config.getShowTourWidth();
            height = config.getShowTourHeight();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("show-tour-view.fxml"));
            this.root = loader.load();
            ShowTourController showTourController = loader.<ShowTourController>getController();
            showTourController.initialize(tourViewModel);
            this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            createStage();
        } else {
            switchToErrorPage(event);
        }

    }

    @Override
    public void switchToShowTour(MouseEvent event, TourViewModel tourViewModel) throws IOException {
        if (checkDB()) {
            width = config.getShowTourWidth();
            height = config.getShowTourHeight();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("show-tour-view.fxml"));
            this.root = loader.load();
            ShowTourController showTourController = loader.<ShowTourController>getController();
            showTourController.initialize(tourViewModel);
            this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            createStage();
        }
    }

    // Tour + TourLogger Einträge - Anzeigen
    @Override
    public void switchToEditTour(ActionEvent event, Tour tour) throws IOException {
        if (checkDB()) {
            width = config.getEditTourWidth();
            height = config.getEditTourHeight();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit-tour-view.fxml"));
            this.root = loader.load();
            EditTourController editTourController = loader.<EditTourController>getController();
            editTourController.initialize(tour);
            this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            createStage();
        } else {
            switchToErrorPage(event);
        }

    }

    @Override
    public void switchToEditTourLog(MouseEvent event, TourLog tourLog) throws IOException {
        if (checkDB()) {
            width = config.getEditTourLogWidth();
            height = config.getEditTourLogHeight();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit-tourLog-view.fxml"));
            this.root = loader.load();
            EditTourLogController editTourLogController = loader.<EditTourLogController>getController();
            editTourLogController.initialize(tourLog);
            this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            createStage();
        }
    }

    // TourListe + Tour Erstellen Button - Anzeigen
    @Override
    public void switchToShowTourList(ActionEvent event) throws IOException {
        if (checkDB()) {
            this.root = FXMLLoader.load(getClass().getResource("show-tour-list-view.fxml"));
            width = config.getListTourWidth();
            height = config.getListTourHeight();
            this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            createStage();
        } else {
            switchToErrorPage(event);
        }
    }

    @Override
    public void switchToSearchList(ActionEvent event, ArrayList<TourViewModel> searchResult) throws IOException {
        if (checkDB()) {
            width = 1169;
            height = 700;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("show-search-list-view.fxml"));
            this.root = loader.load();

            SearchListController searchListController = loader.<SearchListController>getController();
            searchListController.initialize(searchResult);
            this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            createStage();
        } else {
            switchToErrorPage(event);
        }
    }

    // TourLogger - Erstellen
    private void switchToCreateTourLog(ActionEvent event) throws IOException {
        if (checkDB()) {
            width = 800;
            height = 638;
            this.root = FXMLLoader.load(getClass().getResource("show-tour-view.fxml"));
            this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            createStage();
        } else {
            switchToErrorPage(event);
        }
    }

    //ERROR
    private void switchToErrorPage(ActionEvent event) throws IOException {
        height = config.getErrorHeight();
        width = config.getErrorWidth();
        this.root = FXMLLoader.load(getClass().getResource(config.getErrorPage()));
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        createStage();
    }

    // Tour - Suchleiste
    private void switchToSearchBar(ActionEvent event) throws IOException {
        if (checkDB()) {
            this.root = FXMLLoader.load(getClass().getResource("search-main.fxml"));
            this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            createStage();
        } else {
            switchToErrorPage(event);
        }
    }

}
