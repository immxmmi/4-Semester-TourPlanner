package at.technikum.tourplanner.models;

import lombok.*;

import java.sql.Time;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Tour {

    private String tourID;

    private String name;

    private String description;

    private City form;

    private City to;

    private int distance;

    private Image routeImage;

    private Time time;

    @Builder.Default
    private Transporter transporter = Transporter.Walk;


    @Override
    public String toString() {
        return "Tour{" +
                "tourId='" + tourID + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", form='" + form.getName() + '\'' +
                ", to='" + to.getName() + '\'' +
                ", distance=" + distance +
                '}';
    }
}
