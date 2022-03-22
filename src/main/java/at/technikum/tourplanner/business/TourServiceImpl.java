package at.technikum.tourplanner.business;

import at.technikum.tourplanner.data.TourDao;
import at.technikum.tourplanner.data.TourDaoImpl;
import at.technikum.tourplanner.model.Tour;

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
    public Boolean deleteTour(Tour tour) {
        return tourDao.delete(tour);
    }

    @Override
    public Tour getTour(String tourID) {
       return tourDao.getItemById(tourID);
    }

    @Override
    public Tour updateTour(Tour tour) {
        return tourDao.update(tour);
    }
}
