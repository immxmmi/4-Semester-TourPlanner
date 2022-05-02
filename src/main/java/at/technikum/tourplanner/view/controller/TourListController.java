
package at.technikum.tourplanner.view.controller;
import at.technikum.tourplanner.business.tour.TourService;
import at.technikum.tourplanner.business.tour.TourServiceImpl;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.view.viewmodel.TourViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

public class TourListController extends AbstractNavBar implements Initializable{

    @FXML
    private TableView<TourViewModel> table_tourList;
    @FXML
    private TableColumn<TourViewModel, String> col_date;
    @FXML
    private TableColumn<TourViewModel, String> col_title;
    @FXML
    private TableColumn<TourViewModel, String> col_from;
    @FXML
    private TableColumn<TourViewModel, String> col_to;
    @FXML
    private TableColumn<TourViewModel, Double> col_distance;
    @FXML
    private TableColumn<TourViewModel, Double> col_time;


    private ObservableList<TourViewModel> obsTourList = FXCollections.observableArrayList();


    private void loadList(){
        obsTourList = FXCollections.observableArrayList();
        TourService tourService = new TourServiceImpl();
        ArrayList<Tour> tourList= tourService.getAllTourOrderByName();
        for(Tour tour : tourList){
            obsTourList.add(new TourViewModel(tour));
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadList();
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_from.setCellValueFactory(new PropertyValueFactory<>("from"));
        col_to.setCellValueFactory(new PropertyValueFactory<>("to"));
        col_distance.setCellValueFactory(new PropertyValueFactory<>("distance"));

        table_tourList.setItems(obsTourList);
    }

    @FXML
    public void showTour(ActionEvent actionEvent) throws IOException {

        System.out.println(table_tourList.getSelectionModel().getSelectedItem()
        );
        sCon.switchToShowTour(actionEvent);
    }

    @FXML
    public void deleteTour(ActionEvent actionEvent) throws IOException {

        System.out.println(table_tourList.getSelectionModel());
     table_tourList.getItems().removeAll(table_tourList.getSelectionModel().getSelectedItem());
       // sCon.switchToShowTour(actionEvent);
    }


@FXML
    public void reloadList(ActionEvent actionEvent) throws IOException {
        sCon.switchToShowTourList(actionEvent);
    }
}
