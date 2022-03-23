package at.technikum.tourplanner.business;

import at.technikum.tourplanner.models.Tour;

public interface TourService {

    Boolean saveTour(Tour tour);
    Boolean deleteTour(String tourID);
    Tour getTour(String tourID);
    Tour updateTour(Tour tour);

}