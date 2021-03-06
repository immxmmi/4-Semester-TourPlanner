package at.technikum.tourplanner.view.controller;

import at.technikum.tourplanner.business.handler.ThreadMaker;
import at.technikum.tourplanner.business.mapQuest.MapQuestService;
import at.technikum.tourplanner.business.mapQuest.MapQuestServiceImpl;
import at.technikum.tourplanner.business.report.Report;
import at.technikum.tourplanner.business.report.ReportImpl;
import at.technikum.tourplanner.business.tour.TourLogService;
import at.technikum.tourplanner.business.tour.TourLogServiceImpl;
import at.technikum.tourplanner.business.tour.TourService;
import at.technikum.tourplanner.business.tour.TourServiceImpl;
import at.technikum.tourplanner.models.*;
import at.technikum.tourplanner.view.viewmodel.TourLogViewModel;
import at.technikum.tourplanner.view.viewmodel.TourViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ShowTourController extends AbstractNavBar {

    // CURRENT TOUR + TOURLOGS
    private Tour tour;
    private ArrayList<TourLog> tourLogs;

    //Services
    private MapQuestService mapQuestService = new MapQuestServiceImpl();
    private TourLogService tourLogService = new TourLogServiceImpl();
    private ObservableList<TourLogViewModel> obsTourList = FXCollections.observableArrayList();

    // LOAD THE PAGE
    @FXML
    public void initialize(TourViewModel currentTour) {

        this.tour = currentTour.convertTourViewModelInTourModel(currentTour);

        loadTourStatistic(currentTour.getTourID());

        ThreadMaker.multiRunInBackground(new Runnable() {
            @Override
            public void run() {
                setTourTable(currentTour);
            }
        });

        ThreadMaker.multiRunInBackground(new Runnable() {
            @Override
            public void run() {
                loadTourLogs(tour.getTourID());
            }
        });


    }

    // TOUR
    // SET TOUR
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

    private void setTourTable(TourViewModel currentTour) {
        //RUN DATA
        ThreadMaker.multiRunInBackground(new Runnable() {
            @Override
            public void run() {
                show_tour_title.textProperty().bindBidirectional(currentTour.titleProperty());
                show_tour_from.textProperty().bindBidirectional(currentTour.fromProperty());
                show_tour_to.textProperty().bindBidirectional(currentTour.toProperty());
                show_tour_description.textProperty().bindBidirectional(currentTour.descriptionProperty());
                show_tour_distance.setText(currentTour.distanceProperty().getValue().toString() + " km");
                show_tour_time.setText(currentTour.timeProperty().getValue().toString() + " h");
                show_tour_transport.setText(currentTour.transporterProperty().getValue().toString());
            }
        });
        //RUN IMAGE
        ThreadMaker.multiRunInBackground(new Runnable() {
            @Override
            public void run() {

                show_tour_image.setImage(mapQuestService.showOnlineRouteImage(currentTour.getRoutImage()));

            }
        });
    }


    //TOURLOGS
    // SET TOURLOGS
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

    // CREATE TOURLOGS
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

    // LOADING TOURLOGS
    private void loadTourLogs(String tourID) {
        // TourLog Table
        // load List
        // ThreadMaker.multiRunInBackground(new Runnable() {
        //     @Override
        //     public void run() {
        loadTourLogList(tourID);
        //     }
        // });

        // ThreadMaker.multiRunInBackground(new Runnable() {
        //    @Override
        //    public void run() {
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_totalTime.setCellValueFactory(new PropertyValueFactory<>("totalTime"));
        col_rating.setCellValueFactory(new PropertyValueFactory<>("stars"));
        col_difficulty.setCellValueFactory(new PropertyValueFactory<>("level"));
        col_comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        //   }
        // });
        // Column
        // ThreadMaker.multiRunInBackground(new Runnable() {
        // @Override
        // public void run() {
        table_tourLog.setRowFactory(tv -> {
            TableRow<TourLogViewModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    TourLogViewModel rowData = row.getItem();
                    try {
                        sCon.switchToEditTourLog(event, rowData.convertTourLogViewModelInTourLogModel(rowData));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
        //}
        //});


        //set list
        table_tourLog.setItems(obsTourList);

        //Create TourLog
        loadLevels();
        loadStars();
    }

    private void loadTourLogList(String tourID) {
        obsTourList = FXCollections.observableArrayList();
        this.tourLogs = tourLogService.getAllTourLogs(tourID);
        for (TourLog tourLog : tourLogs) {
            obsTourList.add(new TourLogViewModel(tourLog));
        }
    }

    private void loadLevels() {
        for (Level level : Level.values()) {
            get_tourlog_level.getItems().add(level);
        }
    }

    private void loadStars() {
        for (Stars stars : Stars.values()) {
            get_tourlog_stars.getItems().add(stars);
        }
    }


    @FXML
    private Label error_date;
    @FXML
    private Label error_total;
    @FXML
    private Label error_level;
    @FXML
    private Label error_stars;
    @FXML
    private Label error_comment;

    @FXML
    private void saveTourLog(ActionEvent actionEvent) throws IOException {

        boolean check = true;

        //STARS
        if (get_tourlog_stars.getValue() == null) {
            error_stars.setText("!");
            check = false;
        } else {
            error_stars.setText("");
        }

        //LEVEL
        if (get_tourlog_level.getValue() == null) {
            error_level.setText("!");
            check = false;
        } else {
            error_level.setText("");
        }

        //Total Time
        if (get_tourLog_total.getText() == null) {
            error_total.setText("!");
            check = false;
        } else {
            error_total.setText("");
        }

        Double totalTime = 0.0;
        //Total Time
        if (get_tourLog_total.getText().equals("")) {
            error_total.setText("!");
            check = false;
        } else {
            try {
                totalTime = Double.parseDouble(get_tourLog_total.getText());
            } catch (NumberFormatException e) {
                check = false;
                error_total.setText("!");
            }
            error_total.setText("");
        }


        //Comment
        if (get_tourlog_commit.getText() == null) {
            error_comment.setText("!");
            check = false;
        } else {
            error_comment.setText("");
        }

        //DATE
        if (get_tourlog_date.getValue() == null) {
            error_date.setText("!");
            check = false;
        } else {
            error_date.setText("");
        }

        if (check) {
            TourLog tourLog = TourLog.builder()
                    .tourID(tour.getTourID())
                    .date(Date.valueOf(get_tourlog_date.getValue().toString()))
                    .stars(get_tourlog_stars.getValue())
                    .level(get_tourlog_level.getValue())
                    .comment(get_tourlog_commit.getText())
                    .totalTime(totalTime)
                    .build();
            tourLogService.saveTourLog(tourLog);
            reloadPage(actionEvent);
        }

    }

    @FXML
    private void deleteTourLog(ActionEvent actionEvent) throws IOException {
        TourLogViewModel tourLog = table_tourLog.getSelectionModel().getSelectedItem();
        if (tourLog == null) {
            return;
        }
        tourLogService.deleteTourLog(tourLog.getTourLogID());
        table_tourLog.getItems().removeAll(tourLog);
        reloadPage(actionEvent);
    }


    // REPORT SAVE - Button
    @FXML
    VBox saveStage;

    @FXML
    private void saveReport(ActionEvent event) throws IOException {
        String saveTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss"));
        String fileName = "report-" + tour.getTitle() + "-" + saveTime + ".pdf";

        Report report = new ReportImpl();
        Window stage = saveStage.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(fileName);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        fileChooser.setTitle("Save Report");
        // File pdf = //new File()

        File file = fileChooser.showSaveDialog(stage);
        PDDocument doc = report.createTourReport(tour, tourLogs);

        if (file != null) {
            doc.save(file);
        }
        doc.close();
    }

    // RELOAD PAGE
    @FXML
    public void reloadPage(ActionEvent actionEvent) throws IOException {
        sCon.switchToShowTour(actionEvent, new TourViewModel(tour));
    }

    // STATISTIC
    @FXML
    private BarChart barChart;

    private void loadTourStatistic(String tourID) {
        TourService tourService = new TourServiceImpl();
        TourStatistics tourStatistics = tourService.loadTourStatistics(tourID);

        XYChart.Series level = new XYChart.Series();
        level.setName("Level");
        level.getData().add(new XYChart.Data("none", tourStatistics.getNumberOfStarsNone()));
        level.getData().add(new XYChart.Data("easy", tourStatistics.getNumberOfLevelEasy()));
        level.getData().add(new XYChart.Data("normal", tourStatistics.getNumberOfLevelNormal()));
        level.getData().add(new XYChart.Data("hard", tourStatistics.getNumberOfLevelHard()));
        level.getData().add(new XYChart.Data("expert", tourStatistics.getNumberOfLevelExpert()));


        XYChart.Series stars = new XYChart.Series();
        stars.setName("Stars");
        level.getData().add(new XYChart.Data("1-Star", tourStatistics.getNumberOfStarsOne()));
        level.getData().add(new XYChart.Data("2-Star", tourStatistics.getNumberOfStarsTwo()));
        level.getData().add(new XYChart.Data("3-Star", tourStatistics.getNumberOfStarsThree()));
        level.getData().add(new XYChart.Data("4-Star", tourStatistics.getNumberOfStarsFour()));
        level.getData().add(new XYChart.Data("5-Star", tourStatistics.getNumberOfStarsFive()));

        barChart.getData().addAll(level, stars);

    }

    @FXML
    public void settingsTour(ActionEvent actionEvent) throws IOException {
        sCon.switchToEditTour(actionEvent, tour);
    }

    @FXML
    public void settingsTourLogs(MouseEvent actionEvent) throws IOException {
        //sCon.switchToEditTour(actionEvent,tour);
    }

}

