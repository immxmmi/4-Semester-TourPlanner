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

public class SearchTourController extends AbstractNavBar{

    @FXML
    private TextField searchField;
    @FXML
    private Label searchStatus;

    @FXML
    private void searchTour(ActionEvent actionEvent) throws IOException {

        if(searchField == null){
            searchStatus.setText("Not Found!");
            return;
        }

        TourService tourService = new TourServiceImpl();
        Tour searchResult = tourService.searchTourByName(searchField.getText());

        if(searchResult == null){
            searchStatus.setText("Not Found!");
            return;
        }


       //showTourController.setDa(searchResult);

        sCon.switchToShowTour(actionEvent,new TourViewModel(searchResult));



    }

    @FXML
    private void  switchToCreateTour(ActionEvent actionEvent) throws IOException{
        sCon.switchToCreateTour(actionEvent);
    }

    @FXML
    private void  switchToShowTourList(ActionEvent actionEvent) throws IOException{
        sCon.switchToShowTourList(actionEvent);
    }

}
