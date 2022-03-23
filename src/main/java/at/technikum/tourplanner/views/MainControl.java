package at.technikum.tourplanner.views;

import at.technikum.tourplanner.database.sqlServer.CityDaoImpl;
import at.technikum.tourplanner.database.sqlServer.ImageDaoImpl;
import at.technikum.tourplanner.database.sqlServer.TourDaoImpl;
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
    TourDaoImpl tourDaoImpl = new TourDaoImpl();


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


    protected CityDaoImpl cityDaoImpl = new CityDaoImpl();
    protected ImageDaoImpl imageDaoImpl = new ImageDaoImpl();



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
                .from(cityDaoImpl.getItemById(inputImageFrom.getText()))
                .to(cityDaoImpl.getItemById(inputImageTo.getText()))
                .filePath(inputImagePath.getText())
                .build();


        if(newImage == null){
            imageStatus.setText("ERROR");
            imageStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            if(imageDaoImpl.insert(newImage)!=null){
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

        Image currentImage = imageDaoImpl.getItemById(inputImageId.getText());

        if(imageDaoImpl.delete(currentImage) == false){
            imageStatus.setText("ERROR");
            imageStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            imageStatus.setText("OK!");
            imageStatus.setTextFill(Paint.valueOf("#13e452"));
        }

    }

    @FXML
    private void getImage(){
        Image currentImage = imageDaoImpl.getItemById(inputImageId.getText());

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
        Image currentImage = imageDaoImpl.getItemById(inputImageId.getText());
        Image newImage = Image.builder()
                .imageID(inputImageId.getText())
                .name(inputImageName.getText())
                .from(cityDaoImpl.getItemById(inputFrom.getText()))
                .to(cityDaoImpl.getItemById(inputTo.getText()))
                .filePath(inputImagePath.getText())
                .build();



        if(currentImage == null){
            imageStatus.setText("ERROR");
            imageStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            imageDaoImpl.update(newImage);
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

        if(newCity == null){
            cityStatus.setText("ERROR");
            cityStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            if(cityDaoImpl.insert(newCity)!=null){
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

        City currentCity = cityDaoImpl.getItemById(inputCityId.getText());

        if(cityDaoImpl.delete(currentCity) == false){
            cityStatus.setText("ERROR");
            cityStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            cityStatus.setText("OK!");
            cityStatus.setTextFill(Paint.valueOf("#13e452"));
        }

    }

    @FXML
    private void getCity(){
        City currentCity = cityDaoImpl.getItemById(inputCityId.getText());

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
        City currentCity = cityDaoImpl.getItemById(inputCityId.getText());
        City newCity = City.builder()
                .cityID(inputCityName.getText())
                .name(inputCityId.getText())
                .build();


        if(currentCity == null){
            cityStatus.setText("ERROR");
            cityStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            cityDaoImpl.update(newCity);
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
                .form(cityDaoImpl.getItemById(inputFrom.getText()))
                .to(cityDaoImpl.getItemById(inputTo.getText()))
                .routeImage(imageDaoImpl.getItemById(inputImage.getText()))
                .transporter(Transporter.valueOf(inputTransporter.getText()))
                .description(inputDescription.getText())
                .distance(10)
                .time(new Time(2,3,4))
                .build();

        if(newTour == null){
            tourStatus.setText("ERROR");
            tourStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            if(tourDaoImpl.insert(newTour)!=null){
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

        Tour currentTour = tourDaoImpl.getItemById(inputTourID.getText());

        if(tourDaoImpl.delete(currentTour) == false){
            tourStatus.setText("ERROR");
            tourStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            tourStatus.setText("OK!");
            tourStatus.setTextFill(Paint.valueOf("#13e452"));
        }

    }
    @FXML
    private void getTour(){
        Tour currentTour = tourDaoImpl.getItemById(inputTourID.getText());

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
        Tour currentTour = tourDaoImpl.getItemById(inputTourID.getId());
        Tour newTour = Tour.builder()
                .tourID(inputTourID.getText())
                .name(inputName.getText())
                .form(cityDaoImpl.getItemById(inputFrom.getText()))
                .to(cityDaoImpl.getItemById(inputTo.getText()))
                .routeImage(imageDaoImpl.getItemById(inputImage.getId()))
                .transporter(Transporter.valueOf(inputTransporter.getText()))
                .description(inputDescription.getText())
                .distance(Integer.parseInt(inputDistance.getText()))
                .build();


        if(currentTour == null){
            tourStatus.setText("ERROR");
            tourStatus.setTextFill(Paint.valueOf("#e44f4f"));
        }else{
            tourDaoImpl.update(newTour);
            tourStatus.setText("OK!");
            tourStatus.setTextFill(Paint.valueOf("#13e452"));
        }

    }

}
