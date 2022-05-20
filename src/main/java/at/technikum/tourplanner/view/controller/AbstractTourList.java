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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// TODO: 13.05.2022 @Checked
public abstract class AbstractTourList extends AbstractNavBar {

    @FXML
    public TableView<TourViewModel> table_tourList;
    @FXML
    public TableColumn<TourViewModel, String> col_date;
    @FXML
    public TableColumn<TourViewModel, String> col_title;
    @FXML
    public TableColumn<TourViewModel, String> col_from;
    @FXML
    public TableColumn<TourViewModel, String> col_to;
    @FXML
    public TableColumn<TourViewModel, Double> col_distance;
    @FXML
    public TableColumn<TourViewModel, Double> col_time;
    @FXML
    public VBox saveStage;

    private TourService tourService = new TourServiceImpl();
    private ObservableList<TourViewModel> obsTourList = FXCollections.observableArrayList();


    public TourService getTourService() {
        return tourService;
    }

    public ObservableList<TourViewModel> getObsTourList() {
        return obsTourList;
    }

    public void setObsTourList(ObservableList<TourViewModel> obsTourList) {
        this.obsTourList = obsTourList;
    }


    //SAVE - TOUR
    @FXML
    protected void saveTour(ActionEvent actionEvent) throws IOException {
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

    //DELETE - TOUR
    @FXML
    protected void deleteTour(ActionEvent actionEvent) throws IOException {
        TourViewModel tour = table_tourList.getSelectionModel().getSelectedItem();
        if (tour == null) {
            return;
        }
        tourService.deleteTour(tour.getTourID());
        table_tourList.getItems().removeAll(tour);
    }

    //SETTING - TOUR
    @FXML
    protected void settingsTour(ActionEvent actionEvent) throws IOException {
        TourViewModel tour = table_tourList.getSelectionModel().getSelectedItem();
        if (tour == null) {
            return;
        }
        sCon.switchToEditTour(actionEvent, tour.convertTourViewModelInTourModel(tour));
    }

    //SAVE - REPORT
    @FXML
    protected void saveReport(ActionEvent event) throws IOException {
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
        }
            doc.close();
    }

    //SAVE - FORMAT
    private void Format(FileChooser fileChooser) {
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON", "*.json"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML", "*.xml"));
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("JSON", "*.json"));
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("XML", "*.xml"));

    }

    //RELOAD - LIST
    @FXML
    public void reloadList(ActionEvent actionEvent) throws IOException {
        sCon.switchToShowTourList(actionEvent);
    }

    //LOAD FROM FILE
    @FXML
    protected void loadTourFromFile(ActionEvent actionEvent) throws IOException {
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

}
