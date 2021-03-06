package at.technikum.tourplanner.database.sqlServer;

import at.technikum.tourplanner.database.common.AbstractDBTable;
import at.technikum.tourplanner.database.dao.TourDao;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;


public class TourDaoImpl extends AbstractDBTable implements TourDao {

    //LOGGER
    private final static Logger log = LogManager.getLogger(AbstractDBTable.class.getName());

    /*******************************************************************/
    /**                          Constructor                          **/
    /*******************************************************************/
    static RouteImageDaoImpl imageDaoImpl = new RouteImageDaoImpl();

    public TourDaoImpl() {
        this.tableName = "\"tour\"";
    }

    /*******************************************************************/


    /*******************************************************************/
    /**                            Builder                            **/
    /*******************************************************************/
    @Override
    public Tour buildClass(ResultSet result) {
        try {
            if (result.next()) {
                Tour tour = Tour.builder()
                        .tourID(result.getString("tourID"))
                        .title(result.getString("title"))
                        .transporter(Transporter.valueOf(result.getString("transporter")))
                        .from(result.getString("from"))
                        .to(result.getString("to"))
                        .description(result.getString("description"))
                        .distance(Double.valueOf(result.getString("distance")))
                        .time(Time.valueOf(result.getString("time")))
                        .routeImage(imageDaoImpl.getItemById(result.getString("routeImage")))
                        .date(Date.valueOf(result.getString("date")))
                        .build();

                this.closeStatement();

                return tour;
            }
        } catch (SQLException e) {
            log.error("GETOBJECT -ERRROR: ");
            log.error(e);
            //System.out.println(TextColor.ANSI_RED + "GETOBJECT -ERRROR: " + e + TextColor.ANSI_RESET);
            e.printStackTrace();
        }
        this.closeStatement();
        return null;
    }

    /*******************************************************************/


    @Override
    public Tour getItemById(String itemID) {
        this.parameter = new String[]{itemID};
        this.setStatement(
                "SELECT * FROM " + this.tableName + " WHERE \"tourID\" = ? " + ";",
                this.parameter
        );
        return buildClass(this.result);
    }

    @Override
    public Tour getItemByName(String title) {
        this.parameter = new String[]{title};
        this.setStatement(
                "SELECT * FROM " + this.tableName + " WHERE \"title\" = ? " + ";",
                this.parameter
        );
        return buildClass(this.result);
    }

    @Override
    public ArrayList<Tour> search(String value) {
        ArrayList<Tour> allTour = new ArrayList<>();
        ArrayList<String> allTourIDs = new ArrayList<>();
        value = "%" + value + "%";
        this.parameter = new String[]{value, value, value, value, value};
        this.setStatement("SELECT *  FROM " + this.tableName + "WHERE " +
                        "\"title\" like ? " +
                        "OR \"description\" like ? " +
                        "OR \"transporter\" like ? " +
                        "OR \"from\" like ? " +
                        "OR \"to\" like ? ;"
                , this.parameter);


        try {
            while (this.result.next()) {
                allTourIDs.add(result.getString("tourID"));
            }
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
        }

        if (allTourIDs.size() == 0) {
            this.parameter = new String[]{value, value, value};
            this.setStatement("SELECT *  FROM " + this.tableName + " JOIN \"tourLog\" ON \"tour\".\"tourID\"=\"tourLog\".\"tourID\" WHERE " +
                            "\"level\" like ? " +
                            "OR \"stars\" like ? " +
                            "OR \"comment\" like ? ;"
                    , this.parameter);

            try {
                while (this.result.next()) {
                    allTourIDs.add(result.getString("tourID"));
                }
            } catch (SQLException e) {
                log.error(e);
                e.printStackTrace();
            }

        }


        for (int i = 0; i < allTourIDs.size(); i++) {
            allTour.add(getItemById(allTourIDs.get(i)));
        }

        Set<Tour> set = new LinkedHashSet<Tour>(allTour);
        allTour = new ArrayList<Tour>(set);
        return allTour;
    }

    @Override
    public Tour insert(Tour item) {
        if (item == null) {
            log.error("INSERT TOUR - NULL");
            return null;
        }

        if (getItemById(item.getTourID()) == null) {
            this.parameter = new String[]{
                    "" + item.getTourID(),
                    "" + item.getTitle(),
                    "" + item.getFrom(),
                    "" + item.getTo(),
                    "" + item.getDistance(),
                    "" + item.getTime(),
                    "" + item.getTransporter(),
                    "" + item.getRouteImage().getImageID(),
                    "" + item.getDescription(),
                    "" + item.getDate()
            };


            this.setStatement("INSERT INTO " + this.tableName + " (\"tourID\",title,\"from\",\"to\",\"distance\",\"time\",\"transporter\",\"routeImage\",\"description\",\"date\")VALUES(?,?,?,?,?,?,?,?,?,?);", this.parameter);
            return getItemById(item.getTourID());
        }
        log.error("INSERT ITEM - NOT SUCCESSFUL");
        return null;
    }

    @Override
    public Tour update(Tour item) {
        if (item == null) {
            log.error("UPDATE TOUR - NULL");
            return null;
        }

        this.parameter = new String[]{
                "" + item.getTitle(),
                "" + item.getFrom(),
                "" + item.getTo(),
                "" + item.getDistance(),
                "" + item.getTime(),
                "" + item.getTransporter(),
                "" + item.getRouteImage().getImageID(),
                "" + item.getDescription(),
                "" + item.getDate(),
                "" + item.getTourID(),
        };


        this.setStatement(
                "UPDATE " + this.tableName +
                        " SET " +
                        "\"title\" = ?, " +
                        "\"from\" = ?, " +
                        "\"to\" = ?, " +
                        "\"distance\" = ?, " +
                        "\"time\" = ?, " +
                        "\"transporter\" = ?, " +
                        "\"routeImage\" = ?, " +
                        "\"description\" = ?, " +
                        "\"date\" = ? " +


                        "WHERE \"tourID\" = ? ;"
                , this.parameter
        );

        return getItemById(item.getTourID());
    }

    @Override
    public ArrayList<Tour> getAllTourOrderByName() {

        ArrayList<Tour> allTour = new ArrayList<>();
        ArrayList<String> allTourIDs = new ArrayList<>();

        this.parameter = new String[]{};

        this.setStatement("SELECT  *  FROM " + this.tableName + " ORDER BY \"title\";", this.parameter);

        try {

            while (this.result.next()) {
                allTourIDs.add(result.getString("tourID"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e);
        }


        for (int i = 0; i < allTourIDs.size(); i++) {
            allTour.add(getItemById(allTourIDs.get(i)));
        }

        return allTour;
    }
}
