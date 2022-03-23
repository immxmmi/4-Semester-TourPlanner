package at.technikum.tourplanner.business;

import at.technikum.tourplanner.database.dao.CityDao;
import at.technikum.tourplanner.database.sqlServer.CityDaoImpl;
import at.technikum.tourplanner.models.City;

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
