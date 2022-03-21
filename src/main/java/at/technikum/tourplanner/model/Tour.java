package at.technikum.tourplanner.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Builder(toBuilder = true)
public class Tour {


    @Setter
    @Getter
    public String tourId;

    @Setter
    @Getter
    public String name;

    @Setter
    @Getter
    public String description;

    @Setter
    @Getter
    @Builder.Default
    public Transporter transporter = Transporter.Walk;

    @Setter
    @Getter
    public City form;

    @Setter
    @Getter
    public City to;

    @Setter
    @Getter
    public int distance;

    @Getter
    @Setter
    public Time time;

    @Getter
    @Setter
    public Image routeImage;


    @Override
    public String toString() {
        return "Tour{" +
                "tourId='" + tourId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", form='" + form.name + '\'' +
                ", to='" + to.name + '\'' +
                ", distance=" + distance +
                '}';
    }
}
