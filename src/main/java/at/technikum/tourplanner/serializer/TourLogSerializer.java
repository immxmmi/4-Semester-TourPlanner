package at.technikum.tourplanner.serializer;

import at.technikum.tourplanner.models.TourLog;

import java.io.File;

public interface TourLogSerializer {
    void saveTourLogAsJSON(File file, TourLog tourLog);
}
