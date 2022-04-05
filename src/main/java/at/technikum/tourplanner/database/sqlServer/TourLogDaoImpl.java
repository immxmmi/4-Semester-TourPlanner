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
                        .tourLogID(result.getString("tourLogID"))
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
                "SELECT * FROM " + this.tableName + " WHERE \"tourLogID\" = ? " + ";",
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
                    "" + item.getTour().getTourID(),
                    "" + item.getReport(),
                    "" + item.getComment(),
                    "" + item.getTotalTime(),
                    "" + item.getDifficulty(),
                    "" + item.getRating(),
            };


            this.setStatement("INSERT INTO " + this.tableName + " (\"tourLogID\",tourID,\"report\",\"comment\",\"totalTime\",\"difficulty\",\"rating\")VALUES(?,?,?,?,?,?,?,?);", this.parameter);
        }
        System.out.println(this.statement);
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

}
