package at.technikum.tourplanner.business.mapQuest;

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

    //6. DOWNLOAD IMAGE
    // TODO: 30.03.2022 IF Bedingung einbauen und Dataenbank updaten
    RouteImage downloadImage(Route route);

    RouteImage reloadImage(RouteImage routeImage);
}
