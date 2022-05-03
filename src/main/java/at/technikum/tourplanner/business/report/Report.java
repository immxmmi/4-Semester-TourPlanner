package at.technikum.tourplanner.business.report;

import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.TourLog;

import java.util.ArrayList;

public interface Report {
    void createTourReport(Tour tour, ArrayList<TourLog> tourLogs);
}
