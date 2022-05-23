package at.technikum.tourplanner.database.dao;

import at.technikum.tourplanner.database.common.DaoPattern;
import at.technikum.tourplanner.models.Tour;

import java.util.ArrayList;

public interface TourDao extends DaoPattern<Tour> {

    boolean delete(String itemID);

    Tour getItemByName(String name);

    ArrayList<Tour> search(String value);

    ArrayList<Tour> getAllTourOrderByName();

}
