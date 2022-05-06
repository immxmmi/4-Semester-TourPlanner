package at.technikum.tourplanner.business.tour;

import at.technikum.tourplanner.models.Tour;

import java.util.ArrayList;

public interface TourService {

    Tour saveTour(Tour tour);

    Tour getTourByID(String tourID);

    Boolean deleteTour(String tourID);

    Tour searchTourByName(String tourName);

    ArrayList<Tour> searchTourAndTourLog(String search);

    Tour updateTour(Tour tour);

    ArrayList<Tour> getAllTourOrderByName();

}
