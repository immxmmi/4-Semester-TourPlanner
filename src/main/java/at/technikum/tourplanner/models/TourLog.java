package at.technikum.tourplanner.models;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder (toBuilder = true)
public class TourLog {
    @SerializedName("tourLogID")
    private String tourLogID;
    @SerializedName("tourID")
    private String tourID;
    @SerializedName("date")
    private Date date;
    @SerializedName("comment")
    private String comment;
    @SerializedName("totalTime")
    private double totalTime;
    @SerializedName("level")
    private Level level;
    @SerializedName("stars")
    private Stars stars;

}
