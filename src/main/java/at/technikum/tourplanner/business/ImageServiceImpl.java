package at.technikum.tourplanner.business;

import at.technikum.tourplanner.data.ImageDao;
import at.technikum.tourplanner.data.ImageDaoImpl;
import at.technikum.tourplanner.data.TourDao;
import at.technikum.tourplanner.data.TourDaoImpl;
import at.technikum.tourplanner.model.Image;
import at.technikum.tourplanner.model.Tour;

public class ImageServiceImpl implements ImageService{
    private ImageDao imageDao;
    {
        imageDao = new ImageDaoImpl();
    }

    @Override
    public Boolean saveImage(Image image) {
        if(imageDao.insert(image) != null){
            return true;}
        return false;
    }

    @Override
    public Boolean deleteImage(Image image) {
        return imageDao.delete(image);
    }

    @Override
    public Image getImage(String imageID) {
        return imageDao.getItemById(imageID);
    }

    @Override
    public Image updateImage(Image image) {
        return imageDao.update(image);
    }
}
