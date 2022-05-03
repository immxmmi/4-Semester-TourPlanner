package at.technikum.tourplanner.business.tour;

import at.technikum.tourplanner.models.TourLog;

import java.util.ArrayList;

public interface TourLogService {

    ArrayList<TourLog> getAllTourLogs(String tourId);
    Boolean saveTourLog(TourLog tourLog);
    Boolean deleteTourLog(String tourLogID);
    TourLog getTourLog(String tourLogID);
    TourLog updateTourLog(TourLog tourLog);
}
