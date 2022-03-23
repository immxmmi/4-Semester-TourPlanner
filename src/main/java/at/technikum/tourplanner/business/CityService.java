package at.technikum.tourplanner.business;

import at.technikum.tourplanner.model.City;
import at.technikum.tourplanner.model.Tour;

public interface CityService {
    Boolean saveCity(City city);
    Boolean deleteCity(City city);
    City getCity(String cityID);
    City updateCity(City city);
}
