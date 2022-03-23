package at.technikum.tourplanner.data;

import at.technikum.tourplanner.database.AbstractDBTable;
import at.technikum.tourplanner.model.City;
import at.technikum.tourplanner.utils.TextColor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CityDaoImpl extends AbstractDBTable implements CityDao {

    /*******************************************************************/
    /**                          Constructor                          **/
    /*******************************************************************/
    public CityDaoImpl() {
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
                        .cityID(result.getString("cityId"))
                        .name(result.getString("name"))
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
        if(getItemById(item.getCityID()) == null){
            this.parameter = new String[]{
                    "" + item.getCityID(),
                    "" + item.getName()
            };

            this.setStatement("INSERT INTO " + this.tableName + " (\"cityId\",\"name\")VALUES(?,?);", this.parameter);

        }
        return getItemById(item.getCityID());
    }

    @Override
    public City update(City item) {
        if (item == null) {
            return null;
        }

        this.parameter = new String[]{
                "" + item.getCityID(),
        };



        this.setStatement(
                "UPDATE " + this.tableName +
                        " SET \"cityId\" = ? " +
                        "WHERE \"cityId\" = ? ;"
                , this.parameter
        );


        return getItemById(item.getCityID());
    }

    @Override
    public boolean delete(City item) {
        if(item == null){
            return false;
        }

        if(getItemById(item.getCityID()) == null)
        {
            return false;
        }
        this.parameter = new String[]{item.getCityID()};
        this.setStatement("DELETE FROM "+this.tableName+" WHERE \"cityId\" = ? ;", this.parameter);
        this.closeStatement();
        return true;
    }
}
