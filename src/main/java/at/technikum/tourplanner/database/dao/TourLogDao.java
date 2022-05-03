package at.technikum.tourplanner.database.dao;

import at.technikum.tourplanner.database.common.DaoPattern;
import at.technikum.tourplanner.models.TourLog;

import java.util.ArrayList;

public interface TourLogDao extends DaoPattern<TourLog> {
    boolean delete(String itemID);
    ArrayList<TourLog> getAllTourLog(String tourID);
}
