package at.technikum.tourplanner.views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class MainControl {



    @FXML
    private ImageView imageV;
    @FXML
    private Button update_btn;

    @FXML
    private void test1(){
        System.out.println("TASTe");
        Image image2 = new Image(getClass().getResourceAsStream("..\\..\\..\\..\\..\\TourPlanner\\Data\\Linz-Berlin.jpg"));
        javafx.scene.image.Image image = new Image("..\\..\\..\\..\\..\\TourPlanner\\Data\\Linz-Berlin.jpg");
        ImageView imageView = new ImageView();
        imageView.setImage(image2);

        imageV = imageView;

        System.out.println(imageV.getImage());

       // File file = new File("C:\\TourPlanner\\Data\\Linz-Berlin.jpg");
        //javafx.scene.image.Image image = new javafx.scene.image.Image(file.toURI().toString());
       // ImageView imageView = new ImageView(image);
        //imageView_test = imageView;
    }

}
