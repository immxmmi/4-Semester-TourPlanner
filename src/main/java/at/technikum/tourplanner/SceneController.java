package at.technikum.tourplanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.control.action.ActionCheck;

import java.io.IOException;

public class SceneController {
    static int width = 1096;
    static int height = 616;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToSearchBar(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        this.stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root, width-30, height-30);
        stage.setMinHeight(height);
        stage.setMinWidth(width);
        stage.setMaxHeight(height);
        stage.setMaxWidth(width);
        this.stage.setScene(scene);
        this.stage.show();
    }

    public void switchToCreateTour(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("create-tour-view.fxml"));
        this.stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root, width-30, height-30);
        stage.setMinHeight(height);
        stage.setMinWidth(width);
        stage.setMaxHeight(height);
        stage.setMaxWidth(width);
        this.stage.setScene(scene);
        this.stage.show();
    }

    public void switchToShowTour(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("show-tour-view.fxml"));
        this.stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root, width-30, height-30);
        stage.setMinHeight(height);
        stage.setMinWidth(width);
        stage.setMaxHeight(height);
        stage.setMaxWidth(width);
        this.stage.setScene(scene);
        this.stage.show();
    }

    public void switchToShowTourList(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("show-tour-list-view.fxml"));
        this.stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root, width-30, height-30);
        stage.setMinHeight(height);
        stage.setMinWidth(width);
        stage.setMaxHeight(height);
        stage.setMaxWidth(width);
        this.stage.setScene(scene);
        this.stage.show();
    }
}
