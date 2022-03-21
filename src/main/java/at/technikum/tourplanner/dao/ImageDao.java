package at.technikum.tourplanner.dao;

import at.technikum.tourplanner.database.AbstractDBTable;
import at.technikum.tourplanner.database.Repository;
import at.technikum.tourplanner.model.Image;

import java.sql.ResultSet;

public class ImageDao extends AbstractDBTable implements Repository<Image> {
    public ImageDao() {
        this.tableName = "image";
    }

    @Override
    public Image buildClass(ResultSet result) {
        return null;
    }

    @Override
    public Image getItemById(String itemID) {
        this.parameter = new String[]{itemID};
        this.setStatement(
                "SELECT * FROM " + this.tableName + " WHERE imageId = ? " + ";",
                this.parameter
        );
        return buildClass(this.result);
    }

    @Override
    public Image insert(Image item) {
        return null;
    }

    @Override
    public Image update(Image item) {
        return null;
    }

    @Override
    public boolean delete(Image item) {

        this.parameter = new String[]{item.getImageId()};
        this.setStatement("DELETE FROM "+this.tableName+" WHERE imageId = ? ;", this.parameter);
        this.closeStatement();
        return true;
    }
}
