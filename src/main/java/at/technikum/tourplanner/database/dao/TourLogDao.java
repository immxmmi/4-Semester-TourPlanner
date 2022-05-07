package at.technikum.tourplanner.database.dao;

import at.technikum.tourplanner.database.common.DaoPattern;
import at.technikum.tourplanner.models.Level;
import at.technikum.tourplanner.models.Stars;
import at.technikum.tourplanner.models.TourLog;

import java.util.ArrayList;

public interface TourLogDao extends DaoPattern<TourLog> {
    boolean delete(String itemID);


    int countTourLogs(String tourID);

    double avgTotalTime(String tourID);


    int countStars(String tourID, Stars star);

    int countLevel(String tourID, Level level);

    ArrayList<TourLog> getAllTourLog(String tourID);

}
