package at.technikum.tourplanner.handler;

import at.technikum.tourplanner.database.AbstractDBTable;
import at.technikum.tourplanner.model.Image;

import java.sql.ResultSet;

public class ImageHandler extends AbstractDBTable implements Repository<Image>{
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
        return false;
    }
}
