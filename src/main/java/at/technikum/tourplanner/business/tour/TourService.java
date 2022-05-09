package at.technikum.tourplanner.business.tour;

import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.TourStatistics;
import at.technikum.tourplanner.view.viewmodel.TourViewModel;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.ArrayList;

public interface TourService {

    Tour saveTour(Tour tour);

    Tour getTourByID(String tourID);

    Boolean deleteTour(String tourID);

    Tour searchTourByName(String tourName);

    ArrayList<Tour> searchTourAndTourLog(String search);

    Tour updateTour(Tour tour);

    ArrayList<Tour> getAllTourOrderByName();

    TourStatistics loadTourStatistics(String tourID);

    void saveTourAsJSON(File file, Tour tour);

    String convertTourToJsonString(Tour tour);
}
