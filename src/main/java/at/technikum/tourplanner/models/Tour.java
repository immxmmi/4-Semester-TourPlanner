package at.technikum.tourplanner.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Tour {

    @SerializedName("tourID")
    private String tourID;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("from")
    private String from;
    @SerializedName("to")
    private String to;
    @SerializedName("distance")
    private double distance;
    @SerializedName("routImage")
    private RouteImage routeImage;
    @SerializedName("time")
    private Time time;
    @SerializedName("transport")
    @Builder.Default
    private Transporter transporter = Transporter.fastest;
    @SerializedName("tour-date")
    private Date date;

}
