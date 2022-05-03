package at.technikum.tourplanner.view.controller;

import at.technikum.tourplanner.business.mapQuest.MapQuestService;
import at.technikum.tourplanner.business.mapQuest.MapQuestServiceImpl;
import at.technikum.tourplanner.business.report.Report;
import at.technikum.tourplanner.business.report.ReportImpl;
import at.technikum.tourplanner.business.tour.TourLogService;
import at.technikum.tourplanner.business.tour.TourLogServiceImpl;
import at.technikum.tourplanner.models.Level;
import at.technikum.tourplanner.models.Stars;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.TourLog;
import at.technikum.tourplanner.view.viewmodel.TourLogViewModel;
import at.technikum.tourplanner.view.viewmodel.TourViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

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


    //TOURLOG - Create
    @FXML
    private ComboBox<Level> get_tourlog_level;
    @FXML
    private ComboBox<Stars> get_tourlog_stars;
    @FXML
    private TextField get_tourLog_total;
    @FXML
    private DatePicker get_tourlog_date;
    @FXML
    private TextArea get_tourlog_commit;



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
        show_tour_title.textProperty().bindBidirectional(currentTour.titleProperty());
        show_tour_from.textProperty().bindBidirectional(currentTour.fromProperty());
        show_tour_to.textProperty().bindBidirectional(currentTour.toProperty());
        show_tour_description.textProperty().bindBidirectional(currentTour.descriptionProperty());
        show_tour_distance.setText(currentTour.distanceProperty().getValue().toString() + " km");
        show_tour_time.setText(currentTour.timeProperty().getValue().toString() + " h");
        show_tour_transport.setText(currentTour.transporterProperty().getValue().toString());
        show_tour_image.setImage(mapQuestService.showRouteImage(currentTour.getRoutImage()));

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


        //Create TourLog
        loadLevels();
        loadStars();
    }


    public void loadTourLogList(String tourID) {
        obsTourList = FXCollections.observableArrayList();
        ArrayList<TourLog> tourLogList = tourLogService.getAllTourLogs(tourID);

        for (TourLog tourLog : tourLogList) {
            obsTourList.add(new TourLogViewModel(tourLog));
        }
    }


    @FXML
    public void saveTourLog(ActionEvent actionEvent) throws IOException {
        TourLog tourLog = TourLog.builder()
                .tourID(tour.getTourID())
                .date(Date.valueOf(get_tourlog_date.getValue().toString()))
                .stars(get_tourlog_stars.getValue())
                .level(get_tourlog_level.getValue())
                .comment(get_tourlog_commit.getText())
                .totalTime(Double.parseDouble(get_tourLog_total.getText()))
                .build();
        tourLogService.saveTourLog(tourLog);
        reload(actionEvent);
    }


    public void reload(ActionEvent actionEvent) throws IOException {
        sCon.switchToShowTour(actionEvent, new TourViewModel(tour));
    }
    private void loadLevels(){
        for(Level level : Level.values()){
           get_tourlog_level.getItems().add(level);
        }
    }
    private void loadStars(){
        for(Stars stars : Stars.values()){
            get_tourlog_stars.getItems().add(stars);
        }
    }
}
