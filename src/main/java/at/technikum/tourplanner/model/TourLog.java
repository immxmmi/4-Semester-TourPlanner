package at.technikum.tourplanner.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

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


}
