package at.technikum.tourplanner.models;

import at.technikum.tourplanner.business.config.ConfigurationManager;
import at.technikum.tourplanner.business.config.ConfigurationManagerImpl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RouteImage {

    private static ConfigurationManager config = new ConfigurationManagerImpl();

    // Image from-to
    private String imageID;

    // Image size range [170 - 1920]
    @Builder.Default
    private int width = config.getImageWidth();
    @Builder.Default
    private int height = config.getImageHeight();;
    @Builder.Default
    private int zoom = 5;

    // start
    private String from;

    // end
    private String to;

    // Image Download
    private String downloadURL;

    // Offline
    @Builder.Default
    private boolean local = false;

    // Marker
    @Builder.Default
    private String defaultMarker = "none";

    //Path:  Tour/data/name.jpg
    @Builder.Default
    private String filePath="";

}
