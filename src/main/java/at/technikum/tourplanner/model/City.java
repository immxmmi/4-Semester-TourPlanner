package at.technikum.tourplanner.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder (toBuilder = true)

public class City {

    private String cityID;

    private String name;

}
