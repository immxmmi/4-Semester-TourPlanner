package at.technikum.tourplanner.view.viewmodel;

import at.technikum.tourplanner.models.*;
import javafx.beans.property.*;

import java.sql.Date;

public class TourLogViewModel {

    private final StringProperty tourLogID;
    private final StringProperty tourID;
    private final StringProperty comment;
    private final DoubleProperty totalTime;
    private final ObjectProperty<Level> difficulty;
    private final ObjectProperty<Rating> rating;
    private final ObjectProperty<Date> date;




    public TourLogViewModel() {
        this.tourLogID = new SimpleStringProperty("");
        this.tourID = new SimpleStringProperty("");
        this.comment = new SimpleStringProperty("");
        this.totalTime = new SimpleDoubleProperty(0.00);
        this.difficulty = new SimpleObjectProperty<>(Level.normal);
        this.rating = new SimpleObjectProperty<>(Rating.point1);
        this.date = new SimpleObjectProperty<>(null);
    }

    public TourLogViewModel(TourLog tourLog) {
        this.tourLogID = new SimpleStringProperty(tourLog.getTourLogID());
        this.tourID = new SimpleStringProperty(tourLog.getTourID());
        this.comment = new SimpleStringProperty(tourLog.getComment());
        this.totalTime = new SimpleDoubleProperty(tourLog.getTotalTime());
        this.difficulty = new SimpleObjectProperty<>(Level.normal);
        this.rating = new SimpleObjectProperty<>(Rating.point1);
        this.date = new SimpleObjectProperty<>(null);
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

    public Level getDifficulty() {
        return difficulty.get();
    }

    public ObjectProperty<Level> difficultyProperty() {
        return difficulty;
    }

    public void setDifficulty(Level level) {
        this.difficulty.set(level);
    }

    public Rating getRating() {
        return rating.get();
    }

    public ObjectProperty<Rating> ratingProperty() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating.set(rating);
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
