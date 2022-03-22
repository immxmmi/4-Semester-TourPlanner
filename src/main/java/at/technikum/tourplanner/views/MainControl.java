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
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import java.sql.Time;

public class MainControl {

    // Wird noch im BL verschoben dient nur zum Testen
    TourDao tourDao = new TourDao();


    static void createTestClass() {

        // routImage
        Image image = Image.builder()
                .imageId("Image-1")
                .name("Wien-Berlin")
                .filePath("/root")
                .build();

        ImageDao imageDao = new ImageDao();
        imageDao.insert(image);
    }


    protected CityDao cityDao = new CityDao();
    protected ImageDao imageDao = new ImageDao();



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
                .imageId(inputImageId.getText())
                .name(inputImageName.getText())
                .from(cityDao.getItemById(inputImageFrom.getText()))
                .to(cityDao.getItemById(inputImageTo.getText()))
                .filePath(inputImagePath.getText())
                .build();


        if(newImage == null){
            imageStatus.setText("ERROR");
            imageStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            if(imageDao.insert(newImage)!=null){
                imageStatus.setText("OK!");
                imageStatus.setTextFill(Paint.valueOf("#13e452"));
            }else{
                imageStatus.setText("ERROR");
                imageStatus.setTextFill(Paint.valueOf("#e44f4f"));
            }
        }
    }

    @FXML
    private void deleteImage(){

        Image currentImage = imageDao.getItemById(inputImageId.getText());

        if(imageDao.delete(currentImage) == false){
            imageStatus.setText("ERROR");
            imageStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            imageStatus.setText("OK!");
            imageStatus.setTextFill(Paint.valueOf("#13e452"));
        }

    }

    @FXML
    private void getImage(){
        Image currentImage = imageDao.getItemById(inputImageId.getText());

        if(currentImage == null){
            imageStatus.setText("ERROR");
            imageStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            inputImageId.setText(currentImage.imageId);
            inputImageName.setText(currentImage.getName());
            inputImageFrom.setText(currentImage.from.getCityId());
            inputImageTo.setText(currentImage.to.getCityId());
            inputImagePath.setText(currentImage.filePath);

            imageStatus.setText("OK!");
            imageStatus.setTextFill(Paint.valueOf("#13e452"));
        }

    }

    @FXML
    private void updateImage(){
        Image currentImage = imageDao.getItemById(inputImageId.getText());
        Image newImage = Image.builder()
                .imageId(inputImageId.getText())
                .name(inputImageName.getText())
                .from(cityDao.getItemById(inputFrom.getText()))
                .to(cityDao.getItemById(inputTo.getText()))
                .filePath(inputImagePath.getText())
                .build();



        if(currentImage == null){
            imageStatus.setText("ERROR");
            imageStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            imageDao.update(newImage);
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
                .cityId(inputCityName.getText())
                .name(inputCityId.getText())
                .build();

        if(newCity == null){
            cityStatus.setText("ERROR");
            cityStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            if(cityDao.insert(newCity)!=null){
                cityStatus.setText("OK!");
                cityStatus.setTextFill(Paint.valueOf("#13e452"));
            }else{
                cityStatus.setText("ERROR");
                cityStatus.setTextFill(Paint.valueOf("#e44f4f"));
            }
        }
    }

    @FXML
    private void deleteCity(){

        City currentCity = cityDao.getItemById(inputCityId.getText());

        if(cityDao.delete(currentCity) == false){
            cityStatus.setText("ERROR");
            cityStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            cityStatus.setText("OK!");
            cityStatus.setTextFill(Paint.valueOf("#13e452"));
        }

    }

    @FXML
    private void getCity(){
        City currentCity = cityDao.getItemById(inputCityId.getText());

        if(currentCity == null){
            cityStatus.setText("ERROR");
            cityStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            inputCityId.setText(currentCity.cityId);
            inputCityName.setText(currentCity.name);

            cityStatus.setText("OK!");
            cityStatus.setTextFill(Paint.valueOf("#13e452"));
        }

    }

    @FXML
    private void updateCity(){
        City currentCity = cityDao.getItemById(inputCityId.getText());
        City newCity = City.builder()
                .cityId(inputCityName.getText())
                .name(inputCityId.getText())
                .build();


        if(currentCity == null){
            cityStatus.setText("ERROR");
            cityStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            cityDao.update(newCity);
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
            tourStatus.setText("OK!");
            tourStatus.setTextFill(Paint.valueOf("#13e452"));
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
