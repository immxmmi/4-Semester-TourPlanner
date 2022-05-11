package at.technikum.tourplanner.view.controller;

import at.technikum.tourplanner.SceneControllerImpl;
import at.technikum.tourplanner.business.tour.TourService;
import at.technikum.tourplanner.business.tour.TourServiceImpl;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.view.viewmodel.TourViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.IOException;
import java.util.ArrayList;

public class SearchTourController extends AbstractNavBar {

    @FXML
    private TextField searchField;
    @FXML
    private Label searchStatus;

    //SEARCH RESULT
    @FXML
    private void searchTour(ActionEvent actionEvent) throws IOException {
        TourService tourService = new TourServiceImpl();
        ArrayList<TourViewModel> searchList = new ArrayList<>();

        if (searchField.getText().equals("")) {
            searchStatus.setText("Not Found!");
            return;
        }

        ArrayList<Tour> searchResult = tourService.searchTourAndTourLog(searchField.getText());
        for (Tour tour : searchResult) {
            searchList.add(new TourViewModel(tour));
        }
        if (searchResult == null) {searchStatus.setText("Not Found!");return;}

        sCon.switchToSearchList(actionEvent, searchList);
    }


}
