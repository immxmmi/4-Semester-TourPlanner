package at.technikum.tourplanner.model;

import lombok.*;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder (toBuilder = true)
public class TourLog {



    private String tourLogId;
    private Date date;
    private Tour tour;
    private String report;
    private String comment;
    @Builder.Default
    private Difficulty difficulty = Difficulty.Normal;
    private int totalTime;
    @Builder.Default
    private Rating rating = Rating.pointDefault;

}
