package at.technikum.tourplanner.dao;

import at.technikum.tourplanner.database.AbstractDBTable;
import at.technikum.tourplanner.database.Repository;
import at.technikum.tourplanner.model.City;

import java.sql.ResultSet;

public class CityDao extends AbstractDBTable implements Repository<City> {

    public CityDao() {
        this.tableName = "city";
    }

    @Override
    public City buildClass(ResultSet result) {
        return null;
    }

    @Override
    public City getItemById(String itemID) {
        this.parameter = new String[]{itemID};
        this.setStatement(
                "SELECT * FROM " + this.tableName + " WHERE cityId = ? " + ";",
                this.parameter
        );
        return buildClass(this.result);
    }

    @Override
    public City insert(City item) {
        return null;
    }

    @Override
    public City update(City item) {
        return null;
    }

    @Override
    public boolean delete(City item) {
        this.parameter = new String[]{item.getCityId()};
        this.setStatement("DELETE FROM "+this.tableName+" WHERE cityId = ? ;", this.parameter);
        this.closeStatement();
        return true;
    }
}
