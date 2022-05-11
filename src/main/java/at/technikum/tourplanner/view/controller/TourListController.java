
package at.technikum.tourplanner.view.controller;

import at.technikum.tourplanner.business.report.Report;
import at.technikum.tourplanner.business.report.ReportImpl;
import at.technikum.tourplanner.business.tour.TourLogService;
import at.technikum.tourplanner.business.tour.TourLogServiceImpl;
import at.technikum.tourplanner.business.tour.TourService;
import at.technikum.tourplanner.business.tour.TourServiceImpl;
import at.technikum.tourplanner.database.fileServer.FileAccess;
import at.technikum.tourplanner.database.fileServer.FileAccessImpl;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.view.viewmodel.TourViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TourListController extends AbstractNavBar implements Initializable {

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

    @FXML
    VBox saveStage;

    //INIT
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadList();
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        col_from.setCellValueFactory(new PropertyValueFactory<>("from"));
        col_to.setCellValueFactory(new PropertyValueFactory<>("to"));
        col_distance.setCellValueFactory(new PropertyValueFactory<>("distance"));

        table_tourList.setRowFactory(tv -> {
            TableRow<TourViewModel> row = new TableRow<>();


            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {

                    TourViewModel rowData = row.getItem();

                    try {

                        //CODE
                        sCon.switchToShowTour(event, rowData);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });


        table_tourList.setItems(obsTourList);
    }

    //DELETE
    @FXML
    public void deleteTour(ActionEvent actionEvent) throws IOException {
        TourViewModel tour = table_tourList.getSelectionModel().getSelectedItem();
        if (tour == null) {
            return;
        }
        tourService.deleteTour(tour.getTourID());
        table_tourList.getItems().removeAll(tour);
    }

    //SAVE
    @FXML
    public void saveTour(ActionEvent actionEvent) throws IOException {
        TourViewModel tour = table_tourList.getSelectionModel().getSelectedItem();
        if (tour == null) {
            return;
        }
        Window stage = saveStage.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Tour");
        fileChooser.setInitialFileName(tour.getTitle());
        Format(fileChooser);
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            tourService.saveTourLocal(file, tour.convertTourViewModelInTourModel(tour));
        }

    }
    private void Format(FileChooser fileChooser) {
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON", "*.json"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML", "*.xml"));
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("JSON", "*.json"));
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("XML", "*.xml"));

    }

    //SETTING
    @FXML
    public void settingsTour(ActionEvent actionEvent) throws IOException {
        TourViewModel tour = table_tourList.getSelectionModel().getSelectedItem();
        if (tour == null) {
            return;
        }
        sCon.switchToEditTour(actionEvent, tour.convertTourViewModelInTourModel(tour));
    }

    //LOAD FROM FILE
    @FXML
    public void loadTourFromFile(ActionEvent actionEvent) throws IOException {
        Window stage = saveStage.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Tour");
        Format(fileChooser);
        File file = fileChooser.showOpenDialog(stage);
        FileAccess fileAccess = new FileAccessImpl();

        if (file != null) {
            Tour tour = fileAccess.readTourFile(file);
            tourService.saveTour(tour);
            this.reloadList(actionEvent);
        }

    }

    @FXML
    private void saveReport(ActionEvent event) throws IOException {
        TourViewModel tourViewModel = table_tourList.getSelectionModel().getSelectedItem();
        TourLogService tourLogService = new TourLogServiceImpl();
    if (tourViewModel == null) {
            return;
        }
        Tour tour = tourViewModel.convertTourViewModelInTourModel(tourViewModel);
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
        PDDocument doc = report.createTourReport(tour, tourLogService.getAllTourLogs(tour.getTourID()));

        if (file != null) {
            doc.save(file);
            doc.close();
        }
    }

    //RELOAD
    @FXML
    public void reloadList(ActionEvent actionEvent) throws IOException {
        sCon.switchToShowTourList(actionEvent);
    }

    //LOAD
    private void loadList() {
        obsTourList = FXCollections.observableArrayList();
        ArrayList<Tour> tourList = tourService.getAllTourOrderByName();
        for (Tour tour : tourList) {
            obsTourList.add(new TourViewModel(tour));
        }
    }


}
