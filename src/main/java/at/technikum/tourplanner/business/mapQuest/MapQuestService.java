package at.technikum.tourplanner.business.mapQuest;

import at.technikum.tourplanner.models.RouteImage;
import at.technikum.tourplanner.models.Route;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import javafx.scene.image.Image;

public interface MapQuestService {

    // SEARCH ROUTE WITHOUT SAVE IN DATABASE
    Route searchRoute(String from, String to, Transporter transporter);

    // SEARCH AND SAVE ROUTE IN DATABASE
    Route startRoute(String from, String to, Transporter transporter);

    Route startRoute(Tour tour);

    // LOAD IMAGE FROM DATABASE
    Image showOnlineRouteImage(RouteImage routeImage);

    // SAVE IMAGE LOCAL
    RouteImage downloadImage(Route route);
}
