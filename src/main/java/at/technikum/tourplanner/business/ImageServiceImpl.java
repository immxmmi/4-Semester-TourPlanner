package at.technikum.tourplanner.business;

import at.technikum.tourplanner.data.ImageDao;
import at.technikum.tourplanner.data.ImageDaoImpl;
import at.technikum.tourplanner.data.TourDao;
import at.technikum.tourplanner.data.TourDaoImpl;
import at.technikum.tourplanner.model.Image;
import at.technikum.tourplanner.model.Tour;

public class ImageServiceImpl {
    private ImageDao imageDao;
    {
        imageDao = new ImageDaoImpl();
    }

    @Override
    public Boolean saveTour(Image image) {
        if(imageDao.insert(image) != null){
            return true;}
        return false;
    }

    @Override
    public Boolean deleteTour(Image image) {
        return imageDao.delete(image);
    }

    @Override
    public Tour getTour(String tourID) {
        return imageDao.getItemById(tourID);
    }

    @Override
    public Tour updateTour(Image image) {
        return imageDao.update(image);
    }
}
