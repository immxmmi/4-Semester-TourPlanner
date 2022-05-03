package at.technikum.tourplanner.business.tour;

import at.technikum.tourplanner.database.dao.TourLogDao;
import at.technikum.tourplanner.database.sqlServer.TourLogDaoImpl;
import at.technikum.tourplanner.models.TourLog;

import java.util.ArrayList;

public class TourLogServiceImpl implements TourLogService{

    private TourLogDao tourLogDao;

    {
        tourLogDao = new TourLogDaoImpl();
    }

    @Override
    public ArrayList<TourLog> getAllTourLogs(String tourId) {
        return tourLogDao.getAllTourLog(tourId);
    }

    @Override
    public Boolean saveTourLog(TourLog tourLog) {
        if(tourLogDao.insert(tourLog) != null){
            return true;}
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
}
