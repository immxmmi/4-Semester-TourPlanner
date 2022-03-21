package at.technikum.tourplanner.views;

import at.technikum.tourplanner.dao.CityDao;
import at.technikum.tourplanner.dao.ImageDao;
import at.technikum.tourplanner.dao.TourDao;
import at.technikum.tourplanner.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Time;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {

        TourLog tourLog = TourLog.builder()
                .tourLogId("Test")
                .build();

        Image image = Image.builder()
                .imageId("IMAGE-TEST")
                .build();

        ImageDao imageDao = new ImageDao();

        imageDao.insert(image);

        City city = City.builder()
                .cityId("City-TEST")
                .build();

        CityDao cityDao = new CityDao();

        cityDao.insert(city);

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