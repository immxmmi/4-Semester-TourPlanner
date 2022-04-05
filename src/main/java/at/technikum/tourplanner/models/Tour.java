package at.technikum.tourplanner.models;

import lombok.*;

import java.sql.Time;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Tour {

    private String tourID;

    private String title;

    private String description;

    private String from;

    private String to;

    private double distance;

    private RouteImage routeImage;

    private Time time;

    @Builder.Default
    private Transporter transporter = Transporter.Walk;


}
