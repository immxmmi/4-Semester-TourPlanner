package at.technikum.tourplanner.business;

import at.technikum.tourplanner.models.Image;
import at.technikum.tourplanner.models.Route;

public interface MapQuestService {

    Route searchRoute(String from, String to);

    //3. SET IMAGE SETTINGS
    Route setImageSettingsToRoute(Route currentRoute);

    Route copyRouteDataToImage(Route currentRoute);

    Image saveImageOnline(Image currentImage);

    // CREATE IMAGE
    Route startRoute(String from, String to);

    Image downloadImage(Image image);

    Image reloadImage(Image image);
}
