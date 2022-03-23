package at.technikum.tourplanner.business;

import at.technikum.tourplanner.data.CityDao;
import at.technikum.tourplanner.data.CityDaoImpl;
import at.technikum.tourplanner.data.ImageDao;
import at.technikum.tourplanner.data.ImageDaoImpl;
import at.technikum.tourplanner.model.City;

public class CityServiceImpl implements CityService{

    private CityDao cityDao;
    {
        cityDao = new CityDaoImpl();
    }

    @Override
    public Boolean saveCity(City city) {
        if(cityDao.insert(city) != null){
            return true;}
        return false;
    }

    @Override
    public Boolean deleteCity(City city) {
            return  cityDao.delete(city);
    }

    @Override
    public City getCity(String cityID) {
        return cityDao.getItemById(cityID);
    }

    @Override
    public City updateCity(City city) {
        return cityDao.update(city);
    }
}
