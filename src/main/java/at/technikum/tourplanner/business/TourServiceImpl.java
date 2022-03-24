package at.technikum.tourplanner.business;

import at.technikum.tourplanner.database.dao.TourDao;
import at.technikum.tourplanner.database.sqlServer.TourDaoImpl;
import at.technikum.tourplanner.models.Tour;

import java.util.ArrayList;
import java.util.Collection;

public class TourServiceImpl implements TourService{

    private TourDao tourDao;
    {
        tourDao = new TourDaoImpl();
    }

    @Override
    public Boolean saveTour(Tour tour) {
         if(tourDao.insert(tour) != null){
             return true;}
         return false;
    }

    @Override
    public Boolean deleteTour(String tourID) {
        return tourDao.delete(tourID);
    }


    @Override
    public Tour getTour(String tourID) {
       return tourDao.getItemById(tourID);
    }

    @Override
    public Tour updateTour(Tour tour) {
        return tourDao.update(tour);
    }

    @Override
    public ArrayList<Tour> getAllTourOrderByName() {
        return tourDao.getAllTourOrderByName();
    }

}
