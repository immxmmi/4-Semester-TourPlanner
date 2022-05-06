package at.technikum.tourplanner.view.controller;

import at.technikum.tourplanner.business.tour.TourService;
import at.technikum.tourplanner.business.tour.TourServiceImpl;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.view.viewmodel.TourViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;

public class SearchListController  extends AbstractNavBar {

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


    private TourService tourService = new TourServiceImpl();
    private ObservableList<TourViewModel> obsTourList = FXCollections.observableArrayList();

    
    public void initialize(ArrayList<TourViewModel> searchList){
        loadList(searchList);

        System.out.println(obsTourList);
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        col_from.setCellValueFactory(new PropertyValueFactory<>("from"));
        col_to.setCellValueFactory(new PropertyValueFactory<>("to"));
        col_distance.setCellValueFactory(new PropertyValueFactory<>("distance"));

        table_tourList.setRowFactory(tv -> {
            TableRow<TourViewModel> row = new TableRow<>();


            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {

                    TourViewModel rowData = row.getItem();

                    try {

                        //CODE
                        sCon.switchToShowTour(event,rowData);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });



        table_tourList.setItems(obsTourList);
    }

    private void loadList(ArrayList<TourViewModel> searchList) {
        obsTourList = FXCollections.observableArrayList();
        for (TourViewModel tour : searchList) {
            obsTourList.add(tour);
        }
    }


    @FXML
    public void deleteTour(ActionEvent actionEvent) throws IOException {
        TourViewModel tour = table_tourList.getSelectionModel().getSelectedItem();
        tourService.deleteTour(tour.getTourID());
        table_tourList.getItems().removeAll(tour);
    }

    @FXML
    public void reloadList(ActionEvent actionEvent) throws IOException {
        sCon.switchToShowTourList(actionEvent);
    }
}
