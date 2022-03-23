package at.technikum.tourplanner.business;

import at.technikum.tourplanner.model.Image;
import at.technikum.tourplanner.model.Tour;

public interface ImageService {

    Boolean saveImage(Image image);

    Boolean deleteImage(Image image);

    Image getImage(String imageID);

    Image updateImage(Image image);

}
