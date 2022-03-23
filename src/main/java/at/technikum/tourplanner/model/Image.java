package at.technikum.tourplanner.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Image {

    private String imageID;

    private String filePath;

    private String name;

    private City from;

    private City to;

}
