package at.technikum.tourplanner.dao;

import at.technikum.tourplanner.database.AbstractDBTable;
import at.technikum.tourplanner.database.DaoPattern;
import at.technikum.tourplanner.model.TourLog;
import at.technikum.tourplanner.utils.TextColor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TourLogDao extends AbstractDBTable implements DaoPattern<TourLog> {
    /*******************************************************************/
    /**                          Constructor                          **/
    /*******************************************************************/
    public TourLogDao() {
        this.tableName = "tourLog";
    }
    /*******************************************************************/


    /*******************************************************************/
    /**                            Builder                            **/
    /*******************************************************************/
    @Override
    public TourLog buildClass(ResultSet result) {
        try {
            if (result.next()) {
                TourLog tourLog = TourLog.builder()
                        .tourLogId(result.getString("tourLogId"))
                        .build();

                this.closeStatement();

                return tourLog;
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
    public TourLog getItemById(String itemID) {
        this.parameter = new String[]{itemID};
        this.setStatement(
                "SELECT * FROM " + this.tableName + " WHERE \"tourLogId\" = ? " + ";",
                this.parameter
        );
        return buildClass(this.result);
    }

    @Override
    public TourLog insert(TourLog item) {
        if (item == null) {
            return null;
        }
        if(getItemById(item.getTourLogId()) == null) {
            this.parameter = new String[]{
                    "" + item.getTourLogId(),
            };


            this.setStatement("INSERT INTO " + this.tableName + " (\"tourLogId\")VALUES(?);", this.parameter);
        }
        return getItemById(item.getTourLogId());
    }

    @Override
    public TourLog update(TourLog item) {
        if (item == null) {
            return null;
        }

        this.parameter = new String[]{
                "" + item.getTourLogId(),
        };



        this.setStatement(
                "UPDATE " + this.tableName +
                        " SET \"tourLogId\" = ? " +
                        "WHERE \"tourLogId\" = ? ;"
                , this.parameter
        );


        return getItemById(item.getTourLogId());
    }

    @Override
    public boolean delete(TourLog item) {
        if(item == null){
            return false;
        }

        if(getItemById(item.getTourLogId()) == null)
        {
            return false;
        }
        this.parameter = new String[]{item.getTourLogId()};
        this.setStatement("DELETE FROM "+this.tableName+" WHERE \"tourLogId\" = ? ;", this.parameter);
        this.closeStatement();
        return true;
    }
}
