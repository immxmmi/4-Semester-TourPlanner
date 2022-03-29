package at.technikum.tourplanner.models;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Route {
    private String from;
    private String to;
    private String key;
    private String urlRoute;
    private JsonNode body;
    private String zoom;
    private String distance;
    private String defaultMarker;
    private String session;
    private String size;
    private String boundingBox;
    private String urlMap;
}
