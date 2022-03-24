package at.technikum.tourplanner.database.dao;

import at.technikum.tourplanner.database.common.DaoPattern;
import at.technikum.tourplanner.models.Tour;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public interface TourDao extends DaoPattern<Tour> {

    ArrayList<Tour> getAllTourOrderByName();
}
