package at.technikum.tourplanner.views;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import java.io.File;

public class MainControl {



    @FXML
    private ImageView imageView_test;

    @FXML
    private void test1(){
        File file = new File("C:\\TourPlanner\\Data\\Wien-Graz.jpg");
        javafx.scene.image.Image image = new javafx.scene.image.Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView_test = imageView;
    }

}
