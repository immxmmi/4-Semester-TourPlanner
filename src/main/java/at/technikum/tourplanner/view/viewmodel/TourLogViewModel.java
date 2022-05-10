package at.technikum.tourplanner.view.viewmodel;

import at.technikum.tourplanner.models.*;
import javafx.beans.property.*;

import java.sql.Date;

public class TourLogViewModel {

    private final StringProperty tourLogID;
    private final StringProperty tourID;
    private final StringProperty comment;
    private final DoubleProperty totalTime;
    private final ObjectProperty<Level> level;
    private final ObjectProperty<Stars> stars;
    private final ObjectProperty<Date> date;


    public TourLogViewModel() {
        this.tourLogID = new SimpleStringProperty("");
        this.tourID = new SimpleStringProperty("");
        this.comment = new SimpleStringProperty("");
        this.totalTime = new SimpleDoubleProperty(0.00);
        this.level = new SimpleObjectProperty<>(Level.normal);
        this.stars = new SimpleObjectProperty<>(Stars.none);
        this.date = new SimpleObjectProperty<>(null);
    }

    public TourLogViewModel(TourLog tourLog) {
        this.tourLogID = new SimpleStringProperty(tourLog.getTourLogID());
        this.tourID = new SimpleStringProperty(tourLog.getTourID());
        this.comment = new SimpleStringProperty(tourLog.getComment());
        this.totalTime = new SimpleDoubleProperty(tourLog.getTotalTime());
        this.level = new SimpleObjectProperty<>(tourLog.getLevel());
        this.stars = new SimpleObjectProperty<Stars>(tourLog.getStars());
        this.date = new SimpleObjectProperty<Date>((Date) tourLog.getDate());
    }

    public String getTourLogID() {
        return tourLogID.get();
    }

    public StringProperty tourLogIDProperty() {
        return tourLogID;
    }

    public void setTourLogID(String tourLogID) {
        this.tourLogID.set(tourLogID);
    }

    public String getTourID() {
        return tourID.get();
    }

    public StringProperty tourIDProperty() {
        return tourID;
    }

    public void setTourID(String tourID) {
        this.tourID.set(tourID);
    }

    public String getComment() {
        return comment.get();
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public double getTotalTime() {
        return totalTime.get();
    }

    public DoubleProperty totalTimeProperty() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime.set(totalTime);
    }

    public Level getLevel() {
        return level.get();
    }

    public ObjectProperty<Level> levelProperty() {
        return level;
    }

    public void setLevel(Level level) {
        this.level.set(level);
    }

    public Stars getStars() {
        return stars.get();
    }

    public ObjectProperty<Stars> starsProperty() {
        return stars;
    }

    public void setStars(Stars stars) {
        this.stars.set(stars);
    }

    public Date getDate() {
        return date.get();
    }

    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    public void setDate(Date date) {
        this.date.set(date);
    }
}
