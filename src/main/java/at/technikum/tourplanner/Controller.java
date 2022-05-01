package at.technikum.tourplanner;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {




    // VIEWMODEL
    final StringProperty textValue = new SimpleStringProperty("initial value");
    final StringProperty searchField2 = new SimpleStringProperty("initial value");


    // @DataBinding
    @FXML
    Label testOutput;
    @FXML
    TextField searchField;



    @FXML
    private void testMe(ActionEvent event){
        System.out.println("Clicked");
        setTextValue("test");

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       //testOutput.textProperty().bind(textValueProperty());
    }





    public String getTextValue() {
        return textValue.get();
    }

    public StringProperty textValueProperty() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue.set(textValue);
    }

    public String getSearchField2() {
        return searchField2.get();
    }

    public StringProperty searchField2Property() {
        return searchField2;
    }

    public void setSearchField2(String searchField2) {
        this.searchField2.set(searchField2);
    }

    public Label getTestOutput() {
        return testOutput;
    }

    public void setTestOutput(Label testOutput) {
        this.testOutput = testOutput;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public void setSearchField(TextField searchField) {
        this.searchField = searchField;
    }
}
