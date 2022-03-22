package at.technikum.tourplanner.views;

import at.technikum.tourplanner.dao.CityDao;
import at.technikum.tourplanner.dao.ImageDao;
import at.technikum.tourplanner.dao.TourDao;
import at.technikum.tourplanner.model.City;
import at.technikum.tourplanner.model.Image;
import at.technikum.tourplanner.model.Tour;
import at.technikum.tourplanner.model.Transporter;
import eu.hansolo.tilesfx.Test;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import java.sql.Time;

public class MainControl {

    TourDao tourDao = new TourDao();


    static void createTestClass() {
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
    }


    protected CityDao cityDao = new CityDao();
    protected ImageDao imageDao = new ImageDao();




    @FXML
    private Label tourStatus;
    @FXML
    private TextField inputTourID;
    @FXML
    private TextField inputName;
    @FXML
    private TextField inputFrom;
    @FXML
    private TextField inputTo;
    @FXML
    private TextField inputImage;
    @FXML
    private TextField inputTransporter;
    @FXML
    private TextField inputDistance;
    @FXML
    private  TextField inputTime;
    @FXML
    private TextField inputDescription;


    @FXML
    private void insertTour(){

        Tour newTour = Tour.builder()
                .tourId(inputTourID.getText())
                .name(inputName.getText())
                .form(cityDao.getItemById(inputFrom.getText()))
                .to(cityDao.getItemById(inputTo.getText()))
                .routeImage(imageDao.getItemById(inputImage.getText()))
                .transporter(Transporter.valueOf(inputTransporter.getText()))
                .description(inputDescription.getText())
                .distance(10)
                .time(new Time(2,3,4))
                .build();

        if(newTour == null){
            tourStatus.setText("ERROR");
            tourStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            if(tourDao.insert(newTour)!=null){
                tourStatus.setText("OK!");
                tourStatus.setTextFill(Paint.valueOf("#13e452"));
            }else{
                tourStatus.setText("ERROR");
                tourStatus.setTextFill(Paint.valueOf("#e44f4f"));
            }
        }
    }

    @FXML
    private void deleteTour(){

        Tour currentTour = tourDao.getItemById(inputTourID.getText());

        if(tourDao.delete(currentTour) == false){
            tourStatus.setText("ERROR");
            tourStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            tourStatus.setText("OK!");
            tourStatus.setTextFill(Paint.valueOf("#13e452"));
        }

    }


    @FXML
    private void getTour(){
        Tour currentTour = tourDao.getItemById(inputTourID.getText());

        if(currentTour == null){
            tourStatus.setText("ERROR");
            tourStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            inputTourID.setText(currentTour.tourId);
            inputName.setText(currentTour.name);
            inputFrom.setText(currentTour.form.cityId);
            inputTo.setText(currentTour.to.cityId);
            inputImage.setText(currentTour.routeImage.imageId);
            inputTransporter.setText(currentTour.transporter.toString());
            inputDescription.setText(currentTour.description);
            inputDistance.setText(""+currentTour.distance);
            inputTime.setText("TIME");
        }

    }

    @FXML
    private void updateTour(){
        Tour currentTour = tourDao.getItemById(inputTourID.getId());
        Tour newTour = Tour.builder()
                .tourId(inputTourID.getText())
                .name(inputName.getText())
                .form(cityDao.getItemById(inputFrom.getText()))
                .to(cityDao.getItemById(inputTo.getText()))
                .routeImage(imageDao.getItemById(inputImage.getId()))
                .transporter(Transporter.valueOf(inputTransporter.getText()))
                .description(inputDescription.getText())
                .distance(Integer.parseInt(inputDistance.getText()))
                .build();


        if(currentTour == null){
            tourStatus.setText("ERROR");
            tourStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            tourDao.update(newTour);
            tourStatus.setText("OK!");
            tourStatus.setTextFill(Paint.valueOf("#13e452"));
        }

    }

}
