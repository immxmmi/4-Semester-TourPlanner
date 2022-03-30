package at.technikum.tourplanner.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Image {

    // Image from-to
    private String imageID;

    // Image size range [170 - 1920]
    @Builder.Default
    private int width = 500;
    @Builder.Default
    private int height = 500;
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

    //Image in byte
    private byte[] imageData;

    //Path:  Tour/data/name.jpg
    @Builder.Default
    private String filePath="";
}
