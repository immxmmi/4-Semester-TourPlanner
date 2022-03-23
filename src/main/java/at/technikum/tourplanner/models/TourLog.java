package at.technikum.tourplanner.models;

import lombok.*;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder (toBuilder = true)
public class TourLog {

    private String tourLogID;

    private Date date;

    private Tour tour;

    private String report;

    private String comment;

    private int totalTime;

    @Builder.Default
    private Difficulty difficulty = Difficulty.Normal;

    @Builder.Default
    private Rating rating = Rating.pointDefault;

}
