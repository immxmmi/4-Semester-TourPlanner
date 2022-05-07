package at.technikum.tourplanner.business.tour;

import at.technikum.tourplanner.models.TourLog;

import java.util.ArrayList;

public interface TourLogService {

    ArrayList<TourLog> getAllTourLogs(String tourId);

    int countTourLogsFromTour(String tourID);

    double avgTotalTimeFromTour(String tourID);

    //LEVEL
    int countLevelEasyFromTour(String tourID);

    int countLevelNormalFromTour(String tourID);

    int countLevelHardFromTour(String tourID);

    int countLevelExpertFromTour(String tourID);

    //LEVEL
    int countStarsNoneFromTour(String tourID);

    int countStarsOneFromTour(String tourID);

    int countStarsTwoFromTour(String tourID);

    int countStarsThreeFromTour(String tourID);

    int countStarsFourFromTour(String tourID);

    int countStarsFiveFromTour(String tourID);

    Boolean saveTourLog(TourLog tourLog);
    Boolean deleteTourLog(String tourLogID);
    TourLog getTourLog(String tourLogID);
    TourLog updateTourLog(TourLog tourLog);
}
