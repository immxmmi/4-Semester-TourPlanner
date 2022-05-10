package at.technikum.tourplanner.business.tour;

import at.technikum.tourplanner.database.dao.TourLogDao;
import at.technikum.tourplanner.database.sqlServer.TourLogDaoImpl;
import at.technikum.tourplanner.models.Level;
import at.technikum.tourplanner.models.Stars;
import at.technikum.tourplanner.models.TourLog;

import java.util.ArrayList;

public class TourLogServiceImpl implements TourLogService {

    private static TourLogDao tourLogDao = new TourLogDaoImpl();

    //DAO
    @Override
    public Boolean saveTourLog(TourLog tourLog) {
        if (tourLogDao.insert(tourLog) != null) {
            return true;
        }
        return false;
    }
    @Override
    public Boolean deleteTourLog(String tourLogID) {
        return tourLogDao.delete(tourLogID);
    }
    @Override
    public TourLog getTourLog(String tourLogID) {
        return tourLogDao.getItemById(tourLogID);
    }
    @Override
    public TourLog updateTourLog(TourLog tourLog) {
        return tourLogDao.update(tourLog);
    }

    //liefert eine Liste mit allen TourLogs einer Tour zurück
    @Override
    public ArrayList<TourLog> getAllTourLogs(String tourId) {
        return tourLogDao.getAllTourLog(tourId);
    }

    // Statistik

    // zählt alle TourLogs
    @Override
    public int countTourLogsFromTour(String tourID) {
        return tourLogDao.countTourLogs(tourID);
    }

    // liefert die durchschnittliche Zeit zurück
    @Override
    public double avgTotalTimeFromTour(String tourID) {
        return tourLogDao.avgTotalTime(tourID);
    }

    //LEVEL
    @Override
    public int countLevelEasyFromTour(String tourID) {
        return tourLogDao.countLevel(tourID, Level.easy);
    }
    @Override
    public int countLevelNormalFromTour(String tourID) {
        return tourLogDao.countLevel(tourID, Level.normal);
    }
    @Override
    public int countLevelHardFromTour(String tourID) {
        return tourLogDao.countLevel(tourID, Level.hard);
    }
    @Override
    public int countLevelExpertFromTour(String tourID) {
        return tourLogDao.countLevel(tourID, Level.expert);
    }

    //STARS
    @Override
    public int countStarsNoneFromTour(String tourID) {
        return tourLogDao.countStars(tourID, Stars.none);
    }
    @Override
    public int countStarsOneFromTour(String tourID) {
        return tourLogDao.countStars(tourID, Stars.one);
    }
    @Override
    public int countStarsTwoFromTour(String tourID) {
        return tourLogDao.countStars(tourID, Stars.two);
    }
    @Override
    public int countStarsThreeFromTour(String tourID) {
        return tourLogDao.countStars(tourID, Stars.three);
    }
    @Override
    public int countStarsFourFromTour(String tourID) {
        return tourLogDao.countStars(tourID, Stars.four);
    }
    @Override
    public int countStarsFiveFromTour(String tourID) {return tourLogDao.countStars(tourID, Stars.five);}

}