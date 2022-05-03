package at.technikum.tourplanner.view.controller;

import at.technikum.tourplanner.business.mapQuest.MapQuestService;
import at.technikum.tourplanner.business.mapQuest.MapQuestServiceImpl;
import at.technikum.tourplanner.business.report.Report;
import at.technikum.tourplanner.business.report.ReportImpl;
import at.technikum.tourplanner.business.tour.TourLogService;
import at.technikum.tourplanner.business.tour.TourLogServiceImpl;
import at.technikum.tourplanner.models.Difficulty;
import at.technikum.tourplanner.models.Rating;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.TourLog;
import at.technikum.tourplanner.view.viewmodel.TourLogViewModel;
import at.technikum.tourplanner.view.viewmodel.TourViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShowTourController extends AbstractNavBar {

    private Tour tour;
    private ArrayList<TourLog> tourLogs;


    // TOUR - READ
    @FXML
    private Text show_tour_title;
    @FXML
    private Label show_tour_from;
    @FXML
    private Label show_tour_to;
    @FXML
    private Label show_tour_time;
    @FXML
    private Label show_tour_transport;
    @FXML
    private Label show_tour_distance;
    @FXML
    private Label show_tour_description;
    @FXML
    private ImageView show_tour_image;

    //TOURLOG - READ

     @FXML
     private TableView<TourLogViewModel> table_tourLog;

     @FXML
     private TableColumn<TourLogViewModel, String> col_date;
    @FXML
    private TableColumn<TourLogViewModel, Double> col_totalTime;
    @FXML
    private TableColumn<TourLogViewModel, String> col_rating;
    @FXML
    private TableColumn<TourLogViewModel, String> col_difficulty;
    @FXML
    private TableColumn<TourLogViewModel, String> col_comment;


    //Services
    private MapQuestService mapQuestService = new MapQuestServiceImpl();
    private TourLogService tourLogService = new TourLogServiceImpl();
    private ObservableList<TourLogViewModel> obsTourList = FXCollections.observableArrayList();


    @FXML
    public void createReport() {
        Report report = new ReportImpl();
        report.createTourReport(tour,tourLogs);
    }

    @FXML
    public void loadData(TourViewModel currentTour) {
        this.tour = currentTour.convertTourViewModelinTourModel(currentTour);
        show:
        show_tour_title.textProperty().bindBidirectional(currentTour.titleProperty());
        show:
        show_tour_from.textProperty().bindBidirectional(currentTour.fromProperty());
        show:
        show_tour_to.textProperty().bindBidirectional(currentTour.toProperty());
        show:
        show_tour_description.textProperty().bindBidirectional(currentTour.descriptionProperty());
        show_tour_distance.setText(currentTour.distanceProperty().getValue().toString() + " km");
        show_tour_time.setText(currentTour.timeProperty().getValue().toString() + " h");
        show_tour_transport.setText(currentTour.transporterProperty().getValue().toString());
        show:show_tour_image.setImage(mapQuestService.showRouteImage(currentTour.getRoutImage()));

        // TourLog Table
        // load List
        loadTourLogList(currentTour.getTourID());
        // Column
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_totalTime.setCellValueFactory(new PropertyValueFactory<>("totalTime"));
        col_rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        col_difficulty.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        col_comment.setCellValueFactory(new PropertyValueFactory<>("comment"));

        table_tourLog.setRowFactory(tv -> {
            TableRow<TourLogViewModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    TourLogViewModel rowData = row.getItem();
                    System.out.println(rowData.getTourID());
                }
            });
            return row ;
        });

        //set list
        table_tourLog.setItems(obsTourList);

    }


    public void loadTourLogList(String tourID) {
        obsTourList = FXCollections.observableArrayList();
        ArrayList<TourLog> tourLogList = tourLogService.getAllTourLogs(tourID);

        for (TourLog tourLog : tourLogList) {
            obsTourList.add(new TourLogViewModel(tourLog));
        }
    }


}
