package at.technikum.tourplanner.business;

import at.technikum.tourplanner.database.dao.ImageDao;
import at.technikum.tourplanner.database.dao.TourLogDao;
import at.technikum.tourplanner.database.sqlServer.ImageDaoImpl;
import at.technikum.tourplanner.database.sqlServer.TourLogDaoImpl;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.TourLog;

public interface TourLogService {

    Boolean saveTourLog(TourLog tourLog);
    Boolean deleteTourLog(String tourLogID);
    TourLog getTourLog(String tourLogID);
    TourLog updateTourLog(TourLog tourLog);
}
