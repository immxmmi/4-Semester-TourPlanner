package at.technikum.tourplanner.business.tour;

import at.technikum.tourplanner.models.TourLog;

public interface TourLogService {

    Boolean saveTourLog(TourLog tourLog);
    Boolean deleteTourLog(String tourLogID);
    TourLog getTourLog(String tourLogID);
    TourLog updateTourLog(TourLog tourLog);
}
