package at.technikum.tourplanner.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(toBuilder = true)
public class Image {

    @Getter
    @Setter
    public String imageId;

    @Getter
    @Setter
    public String filePath;

    @Getter
    @Setter
    public String name;

    @Getter
    @Setter
    public City from;

    @Getter
    @Setter
    public City to;
}
