package at.technikum.tourplanner.view.controller;

import at.technikum.tourplanner.business.mapQuest.MapQuestService;
import at.technikum.tourplanner.business.mapQuest.MapQuestServiceImpl;
import at.technikum.tourplanner.business.report.Report;
import at.technikum.tourplanner.business.report.ReportImpl;
import at.technikum.tourplanner.business.tour.TourLogService;
import at.technikum.tourplanner.business.tour.TourLogServiceImpl;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShowTourController extends AbstractNavBar{

    private Tour tour;


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


   private MapQuestService mapQuestService = new MapQuestServiceImpl();

   private TourLogService tourLogService = new TourLogServiceImpl();


    @FXML
    public void createReport(){
        Report report = new ReportImpl();
        report.createTourReport(tour);
    }

    @FXML
    public void loadData(TourViewModel currentTour){
        this.tour = currentTour.convertTourViewModelinTourModel(currentTour);
        show:show_tour_title.textProperty().bindBidirectional(currentTour.titleProperty());
        show:show_tour_from.textProperty().bindBidirectional(currentTour.fromProperty());
        show:show_tour_to.textProperty().bindBidirectional(currentTour.toProperty());
        show:show_tour_description.textProperty().bindBidirectional(currentTour.descriptionProperty());
        show_tour_distance.setText(currentTour.distanceProperty().getValue().toString() + " km");
        show_tour_time.setText(currentTour.timeProperty().getValue().toString() + " min");
        show_tour_transport.setText(currentTour.transporterProperty().getValue().toString());
        show:show_tour_image.setImage(mapQuestService.showRouteImage(currentTour.getRoutImage()));

    }

}
