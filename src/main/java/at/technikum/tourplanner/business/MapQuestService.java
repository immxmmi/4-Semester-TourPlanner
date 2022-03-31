package at.technikum.tourplanner.business;

import at.technikum.tourplanner.models.Image;
import at.technikum.tourplanner.models.Route;

public interface MapQuestService {

    Route searchRoute(String from, String to);

    //3. SET IMAGE SETTINGS
    Route setImageSettingsToRoute(Route currentRoute, Image emptyImage);

    Image copyRouteDataToImage(Image image, Route currentRoute);

    Image saveImageOnline(Image currentImage);

    Image downloadImage(Image image);

    Image reloadImage(Image image);
}
