package at.technikum.tourplanner.views;

import at.technikum.tourplanner.dao.TourDao;
import at.technikum.tourplanner.model.City;
import at.technikum.tourplanner.model.Image;
import at.technikum.tourplanner.model.Tour;
import at.technikum.tourplanner.model.Transporter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Time;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {


        Image image = Image.builder()
                .imageId("IMAGE-TEST")
                .build();

        City city = City.builder()
                .cityId("City-TEST")
                .build();

        Time time = new Time(2,2,2);



        Tour tour = Tour.builder()
                .tourId("T-1")
                .name("TourHello")
                .routeImage(image)
                .description("Erster Tour")
                .form(city)
                .to(city)
                .time(time)
                .distance(10)
                .transporter(Transporter.Bike)
                .build();


        TourDao tourHandler = new TourDao();
        tourHandler.insert(tour);


        welcomeText.setText(tour.description);
    }
}