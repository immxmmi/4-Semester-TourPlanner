package at.technikum.tourplanner.models;

import javafx.beans.property.StringProperty;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Tour {

    private Date date;

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
