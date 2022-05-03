package at.technikum.tourplanner.models;

import lombok.*;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder (toBuilder = true)
public class TourLog {

    private String tourLogID;

    private String tourID;

    private Date date;

    private String comment;

    private double totalTime;

    private Level level;

    private Stars stars;

}
