package at.technikum.tourplanner.database.sqlServer;

import at.technikum.tourplanner.database.common.AbstractDBTable;
import at.technikum.tourplanner.database.dao.TourLogDao;
import at.technikum.tourplanner.models.TourLog;
import at.technikum.tourplanner.utils.TextColor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TourLogDaoImpl extends AbstractDBTable implements TourLogDao {

    /*******************************************************************/
    /**                          Constructor                          **/
    /*******************************************************************/
    public TourLogDaoImpl() {
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
                        .tourLogID(result.getString("tourLogId"))
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
        if(getItemById(item.getTourLogID()) == null) {
            this.parameter = new String[]{
                    "" + item.getTourLogID(),
            };


            this.setStatement("INSERT INTO " + this.tableName + " (\"tourLogId\")VALUES(?);", this.parameter);
        }
        return getItemById(item.getTourLogID());
    }

    @Override
    public TourLog update(TourLog item) {
        if (item == null) {
            return null;
        }

        this.parameter = new String[]{
                "" + item.getTourLogID(),
        };



        this.setStatement(
                "UPDATE " + this.tableName +
                        " SET \"tourLogId\" = ? " +
                        "WHERE \"tourLogId\" = ? ;"
                , this.parameter
        );


        return getItemById(item.getTourLogID());
    }

    @Override
    public boolean delete(TourLog item) {
        if(item == null){
            return false;
        }

        if(getItemById(item.getTourLogID()) == null)
        {
            return false;
        }
        this.parameter = new String[]{item.getTourLogID()};
        this.setStatement("DELETE FROM "+this.tableName+" WHERE \"tourLogId\" = ? ;", this.parameter);
        this.closeStatement();
        return true;
    }
}
