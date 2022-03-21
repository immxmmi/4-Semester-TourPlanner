package at.technikum.tourplanner.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(toBuilder = true)
public class Image {

    @Getter
    @Setter
    private int imageId;

    @Getter
    @Setter
    private String filePath;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    public City from;

    @Getter
    @Setter
    public City to;
}
