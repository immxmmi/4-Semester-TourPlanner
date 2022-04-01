package at.technikum.tourplanner.business;

import at.technikum.tourplanner.models.Tour;

import java.util.ArrayList;

public interface TourService {

    Tour saveTour(Tour tour);

    Boolean deleteTour(String tourID);

    Tour searchTourByName(String tourName);

    Tour updateTour(Tour tour);

    ArrayList<Tour> getAllTourOrderByName();

}
