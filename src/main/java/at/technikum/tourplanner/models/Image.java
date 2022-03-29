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

    private String imageID;

    private String from;

    private String to;

    private String name;

    private byte[] image;

    private String filePath;
}
