package at.technikum.tourplanner.business;

import at.technikum.tourplanner.models.Image;

public interface ImageService {

    Boolean saveImage(Image image);

    Boolean deleteImage(String imageID);

    Image getImage(String imageID);

    Image updateImage(Image image);

}
