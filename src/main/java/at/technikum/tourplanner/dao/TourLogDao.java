package at.technikum.tourplanner.dao;

import at.technikum.tourplanner.database.AbstractDBTable;
import at.technikum.tourplanner.database.Repository;
import at.technikum.tourplanner.model.TourLog;

import java.sql.ResultSet;

public class TourLogDao extends AbstractDBTable implements Repository<TourLog> {

    public TourLogDao() {
        this.tableName = "tourLog";
    }

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
        this.parameter = new String[]{item.getTourLogId()};
        this.setStatement("DELETE FROM "+this.tableName+" WHERE tourLogId = ? ;", this.parameter);
        this.closeStatement();
        return true;
    }
}
