package at.technikum.tourplanner.models;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Route {

    // MapQuest - KEY
    private String key;

    // start
    private String from;
    // end
    private String to;
    //URL - 1 to get the route
    private String urlRoute;
    // route Body  (info)
    private JsonNode routeBody;

    // ElementFrom routeBody -> distance from - to
    private double distance;
    // ElementFrom routeBody -> distance formattedTime
    private Time time;

    @Builder.Default
    private String size = "640,480";
    // Elements from routeBody
    private String sessionID;
    private String boundingBox;

    // IMAGE DOWNLOAD LINK
    private String urlMap;

    //IMAGE
    @Builder.Default
    private RouteImage routeImage = RouteImage.builder().build();


}
