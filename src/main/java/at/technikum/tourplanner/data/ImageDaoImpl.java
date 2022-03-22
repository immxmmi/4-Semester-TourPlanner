package at.technikum.tourplanner.data;

import at.technikum.tourplanner.database.AbstractDBTable;
import at.technikum.tourplanner.model.Image;
import at.technikum.tourplanner.utils.TextColor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageDaoImpl extends AbstractDBTable implements ImageDao {
    /*******************************************************************/
    /**                          Constructor                          **/
    /*******************************************************************/
    public ImageDaoImpl() {
        this.tableName = "image";
    }
    /*******************************************************************/


    /*******************************************************************/
    /**                            Builder                            **/
    /*******************************************************************/
    @Override
    public Image buildClass(ResultSet result) {
        CityDaoImpl cityDaoImpl = new CityDaoImpl();
        try {
            if (result.next()) {
                Image image = Image.builder()
                        .imageId(result.getString("imageId"))
                        .name(result.getString("name"))
                        .from(cityDaoImpl.getItemById(result.getString("from")))
                        .to(cityDaoImpl.getItemById(result.getString("to")))
                        .filePath(result.getString("filePath"))
                        .build();
                this.closeStatement();

                return image;
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
    public Image getItemById(String itemID) {
        this.parameter = new String[]{itemID};
        this.setStatement(
                "SELECT * FROM " + this.tableName + " WHERE \"imageId\" = ? " + ";",
                this.parameter
        );
        return buildClass(this.result);
    }

    @Override
    public Image insert(Image item) {
        if (item == null) {
            return null;
        }
        if(getItemById(item.getImageId()) == null) {
            this.parameter = new String[]{
                    "" + item.getImageId(),
                    "" + item.getName(),
                    "" + item.getFrom().getCityId(),
                    "" + item.getTo().getCityId(),
                    "" + item.getFilePath()
            };

            this.setStatement("INSERT INTO " + this.tableName + " (\"imageId\",\"name\",\"from\",\"to\",\"filePath\")VALUES(?,?,?,?,?);", this.parameter);
             return getItemById(item.getImageId());
        }
        return null;
    }

    @Override
    public Image update(Image item) {
        if (item == null) {
            return null;
        }

        item.getFrom().getName();
        this.parameter = new String[]{
                "" + item.getImageId(),
                "" + item.getName(),
                "" + item.getFrom().getCityId(),
                "" + item.getTo().getCityId(),
                "" + item.getFilePath()
        };

        this.setStatement(
                "UPDATE " + this.tableName +
                        " SET \"imageId\" = ? " +
                        "\"name\" = ?" +
                        "\"from\" = ?" +
                        "\"to\" = ?" +
                        "\"filePath\" = ?" +
                        "WHERE \"imageId\" = ? ;"
                , this.parameter
        );


        return getItemById(item.getImageId());
    }

    @Override
    public boolean delete(Image item) {
        if(item == null){
            return false;
        }

        if(getItemById(item.getImageId()) == null)
        {
            return false;
        }
        this.parameter = new String[]{item.getImageId()};
        this.setStatement("DELETE FROM "+this.tableName+" WHERE \"imageId\" = ? ;", this.parameter);
        this.closeStatement();
        return true;
    }
}
