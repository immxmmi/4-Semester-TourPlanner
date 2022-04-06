package at.technikum.tourplanner.database.sqlServer;

import at.technikum.tourplanner.database.common.AbstractDBTable;
import at.technikum.tourplanner.database.dao.TourLogDao;
import at.technikum.tourplanner.models.Difficulty;
import at.technikum.tourplanner.models.Rating;
import at.technikum.tourplanner.models.TourLog;
import at.technikum.tourplanner.utils.TextColor;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TourLogDaoImpl extends AbstractDBTable implements TourLogDao {

    /*******************************************************************/
    /**                          Constructor                          **/
    /*******************************************************************/
    public TourLogDaoImpl() {
        this.tableName = "\"tourLog\"";
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
                        .tourID(result.getString("tourID"))
                        .comment(result.getString("comment"))
                        .totalTime(Double.valueOf(result.getString("totalTime")))
                        .difficulty(Difficulty.valueOf(result.getString("difficulty")))
                        .rating(Rating.valueOf(result.getString("rating")))
                        .date(Date.valueOf(result.getString("date")))
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
                    "" + item.getTourID(),
                    "" + item.getComment(),
                    "" + item.getTotalTime(),
                    "" + item.getDifficulty(),
                    "" + item.getRating(),
                    "" + item.getDate()
            };


            this.setStatement("INSERT INTO " + this.tableName + " (\"tourLogID\",\"tourID\",\"comment\",\"totalTime\",\"difficulty\",\"rating\",\"date\")VALUES(?,?,?,?,?,?,?);", this.parameter);
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
                "" + item.getTourID(),
                "" + item.getComment(),
                "" + item.getTotalTime(),
                "" + item.getDifficulty(),
                "" + item.getRating(),
                "" + item.getDate(),
                "" + item.getTourLogID(),
        };


        this.setStatement(
                "UPDATE " + this.tableName +
                        " SET \"tourLogId\" = ?, " +
                        "\"tourID\"= ?," +
                        "\"comment\"= ?," +
                        "\"totalTime\"= ?," +
                        "\"difficulty\"= ?," +
                        "\"rating\"= ?," +
                        "\"date\" = ?" +
                         "WHERE \"tourLogId\" = ? ;"
                , this.parameter
        );


        return getItemById(item.getTourLogID());
    }

}
