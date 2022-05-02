package at.technikum.tourplanner;

import at.technikum.tourplanner.business.tour.TourService;
import at.technikum.tourplanner.business.tour.TourServiceImpl;
import at.technikum.tourplanner.models.Tour;
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
import java.util.ResourceBundle;

public class TableController implements Initializable {

    @FXML
    private TableView<TourViewModel> table;
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

    ObservableList<TourViewModel> obsTourList = FXCollections.observableArrayList();

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

        System.out.println(obsTourList);
        table.setItems(obsTourList);
    }

    public void switchToMain(ActionEvent actionEvent) throws IOException {
        SceneController sCon = new SceneController();
        sCon.switchToMain(actionEvent);
    }

    public void reloadList(){
        loadList();
    }
}
