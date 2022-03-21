package at.technikum.tourplanner.handler;

import at.technikum.tourplanner.database.AbstractDBTable;
import at.technikum.tourplanner.model.Image;
import at.technikum.tourplanner.model.Tour;
import at.technikum.tourplanner.model.Transporter;
import at.technikum.tourplanner.utils.TextColor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TourHandler extends AbstractDBTable implements Repository<Tour>{

    /*******************************************************************/
    /**                          Constructor                          **/
    /*******************************************************************/
     static  ImageHandler imageHandler = new ImageHandler();
     static  CityHandler cityHandler = new CityHandler();
    public TourHandler(){
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
                        .tourId(result.getString("tourid"))
                        .name(result.getString("name"))
                        .transporter(Transporter.valueOf(result.getString("transporter")))
                        .form(cityHandler.getItemById(result.getString("from")))
                        .to(cityHandler.getItemById(result.getString("to")))
                        .description(result.getString("description"))
                        .distance(0)
                        .time(null)
                        .routeImage(imageHandler.getItemById(result.getString("routeImage")))
                        .build();

                this.closeStatement();

                return tour;
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
    public Tour getItemById(String itemID) {
        this.parameter = new String[]{itemID};
        this.setStatement(
                "SELECT * FROM " + this.tableName + " WHERE \"tourId\" = ? " + ";",
                this.parameter
        );
        return buildClass(this.result);
    }

    @Override
    public Tour insert(Tour item) {
        if (item == null) {
            return null;
        }
        this.parameter = new String[]{
                "" + item.getTourId(),
                "" + item.getName(),
                "" + item.getForm().getCityId(),
                "" + item.getTo().getCityId(),
                "" + item.getDistance(),
                "" + item.getTime(),
                "" + item.getTransporter(),
                "" + item.getRouteImage().getImageId(),
                "" + item.getDescription()
        };


        this.setStatement("INSERT INTO " + this.tableName + " (\"tourId\",name,\"from\",\"to\",\"distance\",\"time\",\"transporter\",\"routeImage\",\"description\")VALUES(?,?,?,?,?,?,?,?,?);", this.parameter);

        return getItemById(item.getTourId());
    }

    @Override
    public Tour update(Tour item) {
        if (item == null) {
            return null;
        }

        this.parameter = new String[]{
                "" + item.getName(),
                "" + item.getForm().getCityId(),
                "" + item.getTo().getCityId(),
                "" + item.getDistance(),
                "" + item.getTime(),
                "" + item.getTransporter(),
                "" + item.getRouteImage().getImageId(),
                "" + item.getDescription(),
                "" + item.getTourId()
        };


        this.setStatement(
                "UPDATE " + this.tableName +
                        " SET name = ?, " +
                        "from = ? " +
                        "to = ? " +
                        "distance = ? " +
                        "time = ? " +
                        "transporter = ? " +
                        "routeImage = ? " +
                        "description = ? " +
                        "WHERE tour_id = ? ;"
                , this.parameter
        );


        return getItemById(item.getTourId());
    }

    @Override
    public boolean delete(Tour item) {
        this.parameter = new String[]{item.getTourId()};
        this.setStatement("DELETE FROM "+this.tableName+" WHERE tourId = ? ;", this.parameter);
        this.closeStatement();
        return true;
    }
}
