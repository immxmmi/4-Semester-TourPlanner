package at.technikum.tourplanner.views;

import at.technikum.tourplanner.business.CityService;
import at.technikum.tourplanner.business.ImageService;
import at.technikum.tourplanner.business.TourLogService;
import at.technikum.tourplanner.business.TourService;
import at.technikum.tourplanner.database.sqlServer.CityDaoImpl;
import at.technikum.tourplanner.database.sqlServer.ImageDaoImpl;
import at.technikum.tourplanner.database.sqlServer.TourDaoImpl;
import at.technikum.tourplanner.database.sqlServer.TourLogDaoImpl;
import at.technikum.tourplanner.models.City;
import at.technikum.tourplanner.models.Image;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import java.sql.Time;

public class MainControl {

    // Wird noch im BL verschoben dient nur zum Testen


    TourService tourService;
    CityService cityService;
    ImageService imageService;
    TourLogService tourLogService;


    static void createTestClass() {

        // routImage
        Image image = Image.builder()
                .imageID("Image-1")
                .name("Wien-Berlin")
                .filePath("/root")
                .build();

        ImageDaoImpl imageDaoImpl = new ImageDaoImpl();
        imageDaoImpl.insert(image);
    }





    // Image MODEL
    @FXML
    private Label imageStatus;
    @FXML
    private TextField inputImageId;
    @FXML
    private TextField inputImageName;

    @FXML
    private TextField inputImageFrom;
    @FXML
    private TextField inputImageTo;

    @FXML
    private TextField inputImagePath;


    // Image METHODE
    @FXML
    private void insertImage(){

        Image newImage = Image.builder()
                .imageID(inputImageId.getText())
                .name(inputImageName.getText())
                .from(cityService.getCity(inputImageFrom.getText()))
                .to(cityService.getCity(inputImageTo.getText()))
                .filePath(inputImagePath.getText())
                .build();

        if(imageService.saveImage(newImage)) {
            imageStatus.setText("OK!");
            imageStatus.setTextFill(Paint.valueOf("#13e452"));
        }
        imageStatus.setText("ERROR");
        imageStatus.setTextFill(Paint.valueOf("#e44f4f"));


    }

    @FXML
    private void deleteImage(){

        Image currentImage = imageService.getImage(inputImageId.getText());

        if(imageService.deleteImage(currentImage)) {
            imageStatus.setText("OK!");
            imageStatus.setTextFill(Paint.valueOf("#13e452"));
        }
        imageStatus.setText("ERROR");
        imageStatus.setTextFill(Paint.valueOf("#e44f4f"));

    }

    @FXML
    private void getImage(){
        Image currentImage = imageService.getImage(inputImageId.getText());

        if(currentImage == null){
            imageStatus.setText("ERROR");
            imageStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            inputImageId.setText(currentImage.getImageID());
            inputImageName.setText(currentImage.getName());
            inputImageFrom.setText(currentImage.getFrom().getCityID());
            inputImageTo.setText(currentImage.getTo().getCityID());
            inputImagePath.setText(currentImage.getFilePath());

            imageStatus.setText("OK!");
            imageStatus.setTextFill(Paint.valueOf("#13e452"));
        }

    }

    @FXML
    private void updateImage(){
        Image currentImage = imageService.getImage(inputImageId.getText());
        Image newImage = Image.builder()
                .imageID(inputImageId.getText())
                .name(inputImageName.getText())
                .from(cityService.getCity(inputFrom.getText()))
                .to(cityService.getCity(inputTo.getText()))
                .filePath(inputImagePath.getText())
                .build();



        if(currentImage == null){
            imageStatus.setText("ERROR");
            imageStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            imageService.updateImage(newImage);
            imageStatus.setText("OK!");
            imageStatus.setTextFill(Paint.valueOf("#13e452"));
        }

    }




    // CITY MODEL
    @FXML
    private Label cityStatus;
    @FXML
    private TextField inputCityId;
    @FXML
    private TextField inputCityName;


    // CITY METHODE
    @FXML
    private void insertCity(){

        City newCity = City.builder()
                .cityID(inputCityName.getText())
                .name(inputCityId.getText())
                .build();

        if(cityService.saveCity(newCity)) {
            cityStatus.setText("OK!");
            cityStatus.setTextFill(Paint.valueOf("#13e452"));
        }
            cityStatus.setText("ERROR");
            cityStatus.setTextFill(Paint.valueOf("#e44f4f"));
    }

    @FXML
    private void deleteCity(){

        City currentCity = cityService.getCity(inputCityId.getText());

        if(cityService.deleteCity(currentCity)) {
            cityStatus.setText("OK!");
            cityStatus.setTextFill(Paint.valueOf("#13e452"));
        }
        cityStatus.setText("ERROR");
        cityStatus.setTextFill(Paint.valueOf("#e44f4f"));

    }

    @FXML
    private void getCity(){

        City currentCity = cityService.getCity(inputCityId.getText());

        if(currentCity == null){
            cityStatus.setText("ERROR");
            cityStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            inputCityId.setText(currentCity.getCityID());
            inputCityName.setText(currentCity.getName());

            cityStatus.setText("OK!");
            cityStatus.setTextFill(Paint.valueOf("#13e452"));
        }

    }

    @FXML
    private void updateCity(){
        City currentCity = cityService.getCity(inputCityId.getText());
        City newCity = City.builder()
                .cityID(inputCityName.getText())
                .name(inputCityId.getText())
                .build();


        if(currentCity == null){
            cityStatus.setText("ERROR");
            cityStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            cityService.updateCity(newCity);
            cityStatus.setText("OK!");
            cityStatus.setTextFill(Paint.valueOf("#13e452"));
        }

    }





    // TOUR MODEL
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


    /// TOUR METHODE
    @FXML
    private void insertTour(){

        Tour newTour = Tour.builder()
                .tourID(inputTourID.getText())
                .name(inputName.getText())
                .form(cityService.getCity(inputFrom.getText()))
                .to(cityService.getCity(inputTo.getText()))
                .routeImage(imageService.getImage(inputImage.getText()))
                .transporter(Transporter.valueOf(inputTransporter.getText()))
                .description(inputDescription.getText())
                .distance(10)
                .time(new Time(2,3,4))
                .build();

        if(tourService.saveTour(newTour)) {
            tourStatus.setText("OK!");
            tourStatus.setTextFill(Paint.valueOf("#13e452"));
        }
        tourStatus.setText("ERROR");
        tourStatus.setTextFill(Paint.valueOf("#e44f4f"));

    }
    @FXML
    private void deleteTour(){

        Tour currentTour = tourService.getTour(inputTourID.getText());

        if(tourService.saveTour(currentTour)) {
            tourStatus.setText("OK!");
            tourStatus.setTextFill(Paint.valueOf("#13e452"));
        }
        tourStatus.setText("ERROR");
        tourStatus.setTextFill(Paint.valueOf("#e44f4f"));


    }

    @FXML
    private void getTour(){
        Tour currentTour = tourService.getTour(inputTourID.getText());

        if(currentTour == null){
            tourStatus.setText("ERROR");
            tourStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            inputTourID.setText(currentTour.getTourID());
            inputName.setText(currentTour.getName());
            inputFrom.setText(currentTour.getForm().getCityID());
            inputTo.setText(currentTour.getTo().getCityID());
            inputImage.setText(currentTour.getRouteImage().getImageID());
            inputTransporter.setText(currentTour.getTransporter().toString());
            inputDescription.setText(currentTour.getDescription());
            inputDistance.setText(""+currentTour.getDistance());
            inputTime.setText("TIME");
            tourStatus.setText("OK!");
            tourStatus.setTextFill(Paint.valueOf("#13e452"));
        }

    }
    @FXML
    private void updateTour(){
        Tour currentTour = tourService.getTour(inputTourID.getId());
        Tour newTour = Tour.builder()
                .tourID(inputTourID.getText())
                .name(inputName.getText())
                .form(cityService.getCity(inputFrom.getText()))
                .to(cityService.getCity(inputTo.getText()))
                .routeImage(imageService.getImage(inputImage.getId()))
                .transporter(Transporter.valueOf(inputTransporter.getText()))
                .description(inputDescription.getText())
                .distance(Integer.parseInt(inputDistance.getText()))
                .build();


        if(currentTour == null){
            tourStatus.setText("ERROR");
            tourStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            tourService.updateTour(newTour);
            tourStatus.setText("OK!");
            tourStatus.setTextFill(Paint.valueOf("#13e452"));
        }

    }

}
