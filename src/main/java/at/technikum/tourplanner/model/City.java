package at.technikum.tourplanner.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder (toBuilder = true)
public class City {

    @Getter
    @Setter
    public String cityId;
    @Getter
    @Setter
    public String name;
}
