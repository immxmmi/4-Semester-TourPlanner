package at.technikum.tourplanner.business.mapQuest;

import at.technikum.tourplanner.models.RouteImage;
import at.technikum.tourplanner.models.Route;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import javafx.scene.image.Image;

public interface MapQuestService {

    Route searchRoute(String from, String to, Transporter transporter);
    // CREATE IMAGE
    Route startRoute(String from, String to, Transporter transporter);
    Route startRoute(Tour tour);



    Image showOnlineRouteImage(RouteImage routeImage);

    //6. DOWNLOAD IMAGE
    // TODO: 30.03.2022 IF Bedingung einbauen und Dataenbank updaten
    RouteImage downloadImage(Route route);

    RouteImage saveImageDataOnline(Route route);

    RouteImage reloadImage(RouteImage routeImage);
}
