package at.technikum.tourplanner.business.report;

import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.TourLog;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.util.ArrayList;

public interface Report {
    void saveTourReport(Tour tour, ArrayList<TourLog> tourLogs);

    PDDocument createTourReport(Tour tour, ArrayList<TourLog> tourLogs);
}
