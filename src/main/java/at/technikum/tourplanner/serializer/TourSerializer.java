package at.technikum.tourplanner.serializer;

import at.technikum.tourplanner.models.Tour;

import java.io.File;

public interface TourSerializer {

    void saveTourAsJSON(File file, Tour tour);

}
