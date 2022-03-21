package at.technikum.tourplanner.handler;

import at.technikum.tourplanner.database.AbstractDBTable;
import at.technikum.tourplanner.model.TourLog;

import java.sql.ResultSet;

public class TourLogHandler extends AbstractDBTable implements Repository<TourLog>{
    @Override
    public TourLog buildClass(ResultSet result) {
        return null;
    }

    @Override
    public TourLog getItemById(String itemID) {
        this.parameter = new String[]{itemID};
        this.setStatement(
                "SELECT * FROM " + this.tableName + " WHERE tourLogId = ? " + ";",
                this.parameter
        );
        return buildClass(this.result);
    }

    @Override
    public TourLog insert(TourLog item) {
        return null;
    }

    @Override
    public TourLog update(TourLog item) {
        return null;
    }

    @Override
    public boolean delete(TourLog item) {
        return false;
    }
}
