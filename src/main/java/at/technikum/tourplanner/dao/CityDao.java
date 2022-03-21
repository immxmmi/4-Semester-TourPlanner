package at.technikum.tourplanner.dao;

import at.technikum.tourplanner.database.AbstractDBTable;
import at.technikum.tourplanner.database.Repository;
import at.technikum.tourplanner.model.City;
import at.technikum.tourplanner.utils.TextColor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CityDao extends AbstractDBTable implements Repository<City> {
    /*******************************************************************/
    /**                          Constructor                          **/
    /*******************************************************************/
    public CityDao() {
        this.tableName = "city";
    }
    /*******************************************************************/


    /*******************************************************************/
    /**                            Builder                            **/
    /*******************************************************************/
    @Override
    public City buildClass(ResultSet result) {
        try {
            if (result.next()) {
                City city = City.builder()
                        .cityId(result.getString("cityId"))
                        .build();

                this.closeStatement();

                return city;
            }
        } catch (SQLException e) {
            System.out.println(TextColor.ANSI_RED + "GETOBJECT -ERRROR: " + e + TextColor.ANSI_RESET);
            e.printStackTrace();
        }
        this.closeStatement();
        return null;
    }
    /*******************************************************************/




    @Override
    public City getItemById(String itemID) {
        this.parameter = new String[]{itemID};
        this.setStatement(
                "SELECT * FROM " + this.tableName + " WHERE \"cityId\" = ? " + ";",
                this.parameter
        );
        return buildClass(this.result);
    }

    @Override
    public City insert(City item) {
        if (item == null) {
            return null;
        }
        this.parameter = new String[]{
                "" + item.getCityId(),
        };


        this.setStatement("INSERT INTO " + this.tableName + " (\"cityId\")VALUES(?);", this.parameter);

        return getItemById(item.getCityId());
    }

    @Override
    public City update(City item) {
        if (item == null) {
            return null;
        }

        this.parameter = new String[]{
                "" + item.getCityId(),
        };



        this.setStatement(
                "UPDATE " + this.tableName +
                        " SET \"cityId\" = ? " +
                        "WHERE \"cityId\" = ? ;"
                , this.parameter
        );


        return getItemById(item.getCityId());
    }

    @Override
    public boolean delete(City item) {
        this.parameter = new String[]{item.getCityId()};
        this.setStatement("DELETE FROM "+this.tableName+" WHERE \"cityId\" = ? ;", this.parameter);
        this.closeStatement();
        return true;
    }
}
