package at.technikum.tourplanner.database.sqlServer;

import at.technikum.tourplanner.database.common.AbstractDBTable;
import at.technikum.tourplanner.database.dao.RouteImageDao;
import at.technikum.tourplanner.models.RouteImage;
import at.technikum.tourplanner.utils.TextColor;

import java.sql.ResultSet;
import java.sql.SQLException;
// TODO: 11.05.2022 @Checked - TEST IMPL
public class RouteImageDaoImpl extends AbstractDBTable implements RouteImageDao {
    /*******************************************************************/
    /**                          Constructor                          **/
    /*******************************************************************/
    public RouteImageDaoImpl() {
        this.tableName = "image";
    }
    /*******************************************************************/


    /*******************************************************************/
    /**                            Builder                            **/
    /*******************************************************************/
    @Override
    public RouteImage buildClass(ResultSet result) {
        try {
            if (result.next()) {
                RouteImage routeImage = RouteImage.builder()
                        .imageID(result.getString("imageID"))
                        .width(Integer.valueOf(result.getString("width")))
                        .height(Integer.valueOf(result.getString("height")))
                        .zoom(Integer.valueOf(result.getString("zoom")))
                        .from(result.getString("from"))
                        .to(result.getString("to"))
                        .downloadURL(result.getString("downloadURL"))
                        .local(Boolean.valueOf(result.getString("local")))
                        .defaultMarker(result.getString("defaultMarker"))
                        .filePath(result.getString("filePath"))
                        .data(result.getBytes("data"))
                        .build();
                this.closeStatement();

                return routeImage;
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
    public RouteImage getItemById(String itemID) {
        this.parameter = new String[]{itemID};
        this.setStatement(
                "SELECT * FROM " + this.tableName + " WHERE \"imageID\" = ? " + ";",
                this.parameter
        );
        return buildClass(this.result);
    }

    @Override
    public RouteImage insert(RouteImage item) {
        if (item == null) {
            return null;
        }
        if (getItemById(item.getImageID()) == null) {
            this.parameter = new String[]{
                    "" + item.getImageID(),
                    "" + item.getWidth(),
                    "" + item.getHeight(),
                    "" + item.getZoom(),
                    "" + item.getFrom(),
                    "" + item.getTo(),
                    "" + item.getDownloadURL(),
                    "" + item.isLocal(),
                    "" + item.getDefaultMarker(),
                    "" + item.getFilePath()
            };

            this.setStatement("INSERT INTO " + this.tableName + " (" +
                    "\"imageID\"," +
                    "\"width\"," +
                    "\"height\"," +
                    "\"zoom\"," +
                    "\"from\"," +
                    "\"to\"," +
                    "\"downloadURL\"," +
                    "\"local\"," +
                    "\"defaultMarker\"," +
                    "\"filePath\")" +
                    "VALUES(?,?,?,?,?,?,?,?,?,?);", this.parameter);
            return getItemById(item.getImageID());
        }
        return null;
    }

    @Override
    public RouteImage update(RouteImage item) {
        if (item == null) {
            return null;
        }

        this.parameter = new String[]{
                "" + item.getImageID(),
                "" + item.getWidth(),
                "" + item.getHeight(),
                "" + item.getZoom(),
                "" + item.getFrom(),
                "" + item.getTo(),
                "" + item.getDownloadURL(),
                "" + item.isLocal(),
                "" + item.getDefaultMarker(),
                "" + item.getFilePath(),
                "" + item.getImageID()
        };

        this.setStatement(
                "UPDATE " + this.tableName +
                        " SET " +
                        "\"imageID\" = ?, " +
                        "\"width\" = ?, " +
                        "\"height\" = ?, " +
                        "\"zoom\" = ?, " +
                        "\"from\" = ?, " +
                        "\"to\" = ?, " +
                        "\"downloadURL\" = ?, " +
                        "\"local\" = ?, " +
                        "\"defaultMarker\" = ?, " +
                        "\"filePath\"= ? " +
                        "WHERE \"imageID\" = ?; ",
                this.parameter);

        return getItemById(item.getImageID());
    }

    @Override
    public RouteImage updateImageData(RouteImage item){
        if (item == null) {return null;}
        this.setDataByte("UPDATE " + this.tableName + " SET " + "\"data\"= ? " + "WHERE \"imageID\" = ?; ", item.getData(),item.getImageID());
        return getItemById(item.getImageID());
    }


}
