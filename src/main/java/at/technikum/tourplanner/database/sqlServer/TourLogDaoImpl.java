package at.technikum.tourplanner.database.sqlServer;

import at.technikum.tourplanner.database.common.AbstractDBTable;
import at.technikum.tourplanner.database.dao.TourLogDao;
import at.technikum.tourplanner.models.Level;
import at.technikum.tourplanner.models.Stars;
import at.technikum.tourplanner.models.TourLog;
import at.technikum.tourplanner.utils.TextColor;
import at.technikum.tourplanner.utils.Tools;
import at.technikum.tourplanner.utils.ToolsImpl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// TODO: 11.05.2022 @Checked - TEST IMPL
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
                        .level(Level.valueOf(result.getString("level")))
                        .stars(Stars.valueOf(result.getString("stars")))
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


    //STATISTIK + COUNTER
    @Override
    public int countTourLogs(String tourID) {
        this.parameter = new String[]{tourID};
        this.setStatement(
                "SELECT  COUNT(*) AS total FROM " + this.tableName + " WHERE \"tourID\" = ? " + ";",
                this.parameter
        );
        int value = 0;

        try {
            if (result.next()) {
                value = this.result.getInt("total");
            }
            this.closeStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return value;
    }

    @Override
    public double avgTotalTime(String tourID) {
        this.parameter = new String[]{tourID};
        this.setStatement(
                "SELECT  AVG(totalTime) AS total FROM " + this.tableName + " WHERE \"tourID\" = ? " + ";",
                this.parameter
        );
        double value = 0;

        try {
            if (result.next()) {
                value = this.result.getInt("total");
            }
            this.closeStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return value;

    }

    @Override
    public int countStars(String tourID, Stars star) {
        this.parameter = new String[]{tourID, "" + star};
        this.setStatement(
                "SELECT  COUNT(*) AS total FROM " + this.tableName + " WHERE \"tourID\" = ? AND \"stars\" = ?" + ";",
                this.parameter
        );
        int value = 0;

        try {
            if (result.next()) {
                value = this.result.getInt("total");
            }
            this.closeStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return value;
    }

    @Override
    public int countLevel(String tourID, Level level) {
        this.parameter = new String[]{tourID, "" + level};
        this.setStatement(
                "SELECT  COUNT(*) AS total FROM " + this.tableName + " WHERE \"tourID\" = ? AND \"level\" = ?" + ";",
                this.parameter
        );
        int value = 0;

        try {
            if (result.next()) {
                value = this.result.getInt("total");
            }
            this.closeStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return value;
    }


    @Override
    public ArrayList<TourLog> getAllTourLog(String tourID) {

        ArrayList<TourLog> allTourLogs = new ArrayList<>();
        ArrayList<String> allTourLogIDs = new ArrayList<>();

        this.parameter = new String[]{tourID};

        this.setStatement("SELECT  *  FROM " + this.tableName + " WHERE \"tourID\" = ? ORDER BY \"date\";", this.parameter);

        try {

            while (this.result.next()) {
                allTourLogIDs.add(result.getString("tourLogID"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < allTourLogIDs.size(); i++) {
            allTourLogs.add(getItemById(allTourLogIDs.get(i)));
        }
        return allTourLogs;
    }

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

        Tools tools = new ToolsImpl();
        if (item == null) {
            return null;
        }
        if (item.getTourLogID() == null) {
            item.setTourLogID(tools.hashString((java.time.LocalTime.now() + item.getTourLogID())));
        }
        if (getItemById(item.getTourLogID()) == null) {
            this.parameter = new String[]{
                    "" + item.getTourLogID(),
                    "" + item.getTourID(),
                    "" + item.getComment(),
                    "" + item.getTotalTime(),
                    "" + item.getLevel(),
                    "" + item.getStars(),
                    "" + item.getDate()
            };


            this.setStatement("INSERT INTO " + this.tableName + " (\"tourLogID\",\"tourID\",\"comment\",\"totalTime\",\"level\",\"stars\",\"date\")VALUES(?,?,?,?,?,?,?);", this.parameter);
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
                "" + item.getLevel(),
                "" + item.getStars(),
                "" + item.getDate(),
                "" + item.getTourLogID(),
        };


        this.setStatement(
                "UPDATE " + this.tableName +
                        " SET \"tourLogID\" = ?, " +
                        "\"tourID\"= ?," +
                        "\"comment\"= ?," +
                        "\"totalTime\"= ?," +
                        "\"level\"= ?," +
                        "\"stars\"= ?," +
                        "\"date\" = ?" +
                        "WHERE \"tourLogID\" = ? ;"
                , this.parameter
        );


        return getItemById(item.getTourLogID());
    }


}
