package at.technikum.tourplanner.views;

import at.technikum.tourplanner.dao.CityDao;
import at.technikum.tourplanner.dao.ImageDao;
import at.technikum.tourplanner.dao.TourDao;
import at.technikum.tourplanner.model.City;
import at.technikum.tourplanner.model.Image;
import at.technikum.tourplanner.model.Tour;
import at.technikum.tourplanner.model.Transporter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Time;

public class MainControl {

    TourDao tourDao = new TourDao();
    static Tour createTestClass(){
        CityDao cityDao = new CityDao();

        // Form
        City cityA = City.builder()
                .cityId("CityID1")
                .name("Wien")
                .build();

        cityDao.insert(cityA);

        // To
        City cityB = City.builder()
                .cityId("CityID2")
                .name("Berlin")
                .build();

        cityDao.insert(cityB);

        // routImage
        Image image = Image.builder()
                .imageId("Image-1")
                .name("Wien-Berlin")
                .from(cityA)
                .to(cityB)
                .filePath("/root")
                .build();

        ImageDao imageDao = new ImageDao();

        imageDao.insert(image);

        Time time = new Time(11,22,3);

        return Tour.builder()
                .tourId("T-1")
                .name("Erste Tour")
                .distance(1000)
                .form(cityA)
                .to(cityB)
                .description("Der erste Ausflug")
                .transporter(Transporter.Bike)
                .routeImage(image)
                .time(time)
                .build();
    }
    static protected Tour testTour = createTestClass();







    @FXML
    private Label insertT;

    @FXML
    private Label deleteT;

    @FXML
    private Label getT;

    @FXML
    private Label updateT;


    @FXML
    private void insertTour(){
        if(tourDao.insert(this.testTour) == null){
            insertT.setText("ERROR");
        }else{
            insertT.setText("OK!");
        }
    }

    @FXML
    private void deleteTour(){

        if(tourDao.delete(this.testTour) == false){
            deleteT.setText("ERROR");
        }else{
            deleteT.setText("OK!");
        }

    }


    @FXML
    private void getTour(){

        if(tourDao.getItemById(this.testTour.getTourId()) == null){
            getT.setText("ERROR");
        }else{
            getT.setText("OK!");
        }

    }

    @FXML
    private void updateTour(){
        if(tourDao.update(this.testTour) == null){
            updateT.setText("ERROR");
        }else{
            updateT.setText("OK!");
        }

    }

}
