package at.technikum.tourplanner;

import at.technikum.tourplanner.business.tour.TourService;
import at.technikum.tourplanner.business.tour.TourServiceImpl;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
    private TableView<TestViewModel> table;
    @FXML
    private TableColumn<TestViewModel, String> col_date;
    @FXML
    private TableColumn<TestViewModel, String> col_title;
    @FXML
    private TableColumn<TestViewModel, String> col_from;
    @FXML
    private TableColumn<TestViewModel, String> col_to;
    @FXML
    private TableColumn<TestViewModel, String> col_distance;

    ObservableList<TestViewModel> obsTourList = FXCollections.observableArrayList();

    private void loadList(){
        TourService tourService = new TourServiceImpl();
        ArrayList<Tour> tourList= tourService.getAllTourOrderByName();

       // ArrayList<Tour> test = new ArrayList<Tour>();
        //test.add(new Tour("test","test","test","test","test","test", 2.0,null,null, Transporter.Bike));

        obsTourList.add(new TestViewModel("test"));

        //for (Tour tour : test){
       //    this.obsTourList.add(tour);
       //}
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadList();
       // col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        //col_date.setCellValueFactory(new PropertyValueFactory<>("title"));
        //col_from.setCellValueFactory(new PropertyValueFactory<>("from"));
        //col_to.setCellValueFactory(new PropertyValueFactory<>("to"));
        //col_distance.setCellValueFactory(new PropertyValueFactory<>("title"));


        System.out.println(obsTourList);
        table.setItems(obsTourList);
    }

    public void switchToMain(ActionEvent actionEvent) throws IOException {
        SceneController sCon = new SceneController();
        sCon.switchToMain(actionEvent);
    }
}
