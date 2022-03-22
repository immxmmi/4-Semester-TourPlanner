package at.technikum.tourplanner.model;

import lombok.*;

import java.sql.Time;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Tour {


    private String tourId;
    private String name;
    private String description;

    @Builder.Default
    private Transporter transporter = Transporter.Walk;
    private City form;
    private City to;
    private int distance;
    private Time time;
    private Image routeImage;


    @Override
    public String toString() {
        return "Tour{" +
                "tourId='" + tourId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", form='" + form.getName() + '\'' +
                ", to='" + to.getName() + '\'' +
                ", distance=" + distance +
                '}';
    }
}
