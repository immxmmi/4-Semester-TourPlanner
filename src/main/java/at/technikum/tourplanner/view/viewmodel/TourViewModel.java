package at.technikum.tourplanner.view.viewmodel;

import at.technikum.tourplanner.models.RouteImage;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import javafx.beans.property.*;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Data
public class TourViewModel {

    private final StringProperty tourID;
    private final StringProperty title;
    private final StringProperty from;
    private final StringProperty description;
    private final StringProperty to;
    private final DoubleProperty distance;
    private final ObjectProperty<Date> date;
    private final ObjectProperty<Time> time;
    private final ObjectProperty<RouteImage> routImage;
    private final ObjectProperty<Transporter> transporter;

    public TourViewModel() {
        this.tourID = new SimpleStringProperty("");
        this.title = new SimpleStringProperty("");
        this.from = new SimpleStringProperty("");
        this.to = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
        this.distance = new SimpleDoubleProperty(0.00);
        this.time = new SimpleObjectProperty<>(new Time(000));
        this.date = new SimpleObjectProperty<>(null);
        this.routImage = new SimpleObjectProperty<>(null);
        this.transporter = new SimpleObjectProperty<>(Transporter.Walk);
    }

    // wandelt Model in ViewModel
    public TourViewModel(Tour tour) {
        this.tourID = new SimpleStringProperty(tour.getTourID());
        this.title = new SimpleStringProperty(tour.getTitle());
        this.from = new SimpleStringProperty(tour.getFrom());
        this.to = new SimpleStringProperty(tour.getTo());
        this.description = new SimpleStringProperty(tour.getDescription());
        this.distance = new SimpleDoubleProperty(tour.getDistance());
        this.date = new SimpleObjectProperty<>(tour.getDate());
        this.time = new SimpleObjectProperty<>(tour.getTime());
        this.routImage = new SimpleObjectProperty<>(tour.getRouteImage());
        this.transporter = new SimpleObjectProperty<>(tour.getTransporter());
    }

    // wandelt ViewModel in Model
    public Tour convertTourViewModelInTourModel(TourViewModel tourViewModel){
        return Tour.builder()
                .tourID(tourViewModel.getTourID())
                .title(tourViewModel.getTitle())
                .from(tourViewModel.getFrom())
                .to(tourViewModel.getTo())
                .description(tourViewModel.getDescription())
                .distance(tourViewModel.getDistance())
                .date(tourViewModel.getDate())
                .time(tourViewModel.getTime())
                .routeImage(tourViewModel.getRoutImage())
                .transporter(tourViewModel.getTransporter())
                .build();
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

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getFrom() {
        return from.get();
    }

    public StringProperty fromProperty() {
        return from;
    }

    public void setFrom(String from) {
        this.from.set(from);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getTo() {
        return to.get();
    }

    public StringProperty toProperty() {
        return to;
    }

    public void setTo(String to) {
        this.to.set(to);
    }

    public double getDistance() {
        return distance.get();
    }

    public DoubleProperty distanceProperty() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance.set(distance);
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

    public Time getTime() {
        return time.get();
    }

    public ObjectProperty<Time> timeProperty() {
        return time;
    }

    public void setTime(Time time) {
        this.time.set(time);
    }

    public RouteImage getRoutImage() {
        return routImage.get();
    }

    public ObjectProperty<RouteImage> routImageProperty() {
        return routImage;
    }

    public void setRoutImage(RouteImage routImage) {
        this.routImage.set(routImage);
    }

    public Transporter getTransporter() {
        return transporter.get();
    }

    public ObjectProperty<Transporter> transporterProperty() {
        return transporter;
    }

    public void setTransporter(Transporter transporter) {
        this.transporter.set(transporter);
    }
}
