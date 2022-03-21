package at.technikum.tourplanner.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Builder (toBuilder = true)
public class TourLog {

    @Setter
    @Getter
    public String tourLogId;

    @Setter
    @Getter
    public Date date;

    @Setter
    @Getter
    public Tour tour;

    @Setter
    @Getter
    public String report;

    @Getter
    @Setter
    public String comment;

    @Getter
    @Setter
    @Builder.Default
    public Difficulty difficulty = Difficulty.Normal;

    @Getter
    @Setter
    public int totalTime;

    @Getter
    @Setter
    @Builder.Default
    public Rating rating = Rating.pointDefault;

}
