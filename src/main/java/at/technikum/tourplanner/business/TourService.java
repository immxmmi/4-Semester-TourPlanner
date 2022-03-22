package at.technikum.tourplanner.business;

import at.technikum.tourplanner.model.Tour;

public interface TourService {
    Boolean saveTour(Tour tour);
    Boolean deleteTour(Tour tour);
    Tour getTour(String tourID);
    Tour updateTour(Tour tour);
}
