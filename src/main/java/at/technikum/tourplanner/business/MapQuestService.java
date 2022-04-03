package at.technikum.tourplanner.business;

import at.technikum.tourplanner.models.RouteImage;
import at.technikum.tourplanner.models.Route;
import javafx.scene.image.Image;

public interface MapQuestService {

    Route searchRoute(String from, String to);

    //3. SET IMAGE SETTINGS
    Route setImageSettingsToRoute(Route currentRoute);

    Route copyRouteDataToImage(Route currentRoute);

    RouteImage saveImageOnline(RouteImage currentRouteImage);

    //6 UPDATE
    RouteImage updateImageOnline(RouteImage currentRouteImage);

    // CREATE IMAGE
    Route startRoute(String from, String to);


    Image showRouteImage(RouteImage routeImage);

    RouteImage downloadImage(RouteImage routeImage);

    RouteImage reloadImage(RouteImage routeImage);
}
