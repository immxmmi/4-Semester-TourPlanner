package at.technikum.tourplanner;

import at.technikum.tourplanner.business.mapQuest.MapQuestService;
import at.technikum.tourplanner.business.mapQuest.MapQuestServiceImpl;
import at.technikum.tourplanner.business.tour.TourService;
import at.technikum.tourplanner.business.tour.TourServiceImpl;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import at.technikum.tourplanner.view.controller.AbstractNavBar;
import at.technikum.tourplanner.view.viewmodel.TourViewModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.DoubleBinaryOperator;

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



    @FXML
    public void loadData(TourViewModel tourViewModel){
        show:show_tour_title.textProperty().bindBidirectional(tourViewModel.titleProperty());
        show:show_tour_from.textProperty().bindBidirectional(tourViewModel.fromProperty());
        show:show_tour_to.textProperty().bindBidirectional(tourViewModel.toProperty());
        show:show_tour_description.textProperty().bindBidirectional(tourViewModel.descriptionProperty());
        show_tour_distance.setText(tourViewModel.distanceProperty().getValue().toString() + " km");
        show_tour_time.setText(tourViewModel.timeProperty().getValue().toString() + " min");
        show_tour_transport.setText(tourViewModel.transporterProperty().getValue().toString());
        show:show_tour_image.setImage(mapQuestService.showRouteImage(tourViewModel.getRoutImage()));
    }
}
