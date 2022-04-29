package at.technikum.tourplanner;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TestViewModel {


    final StringProperty textValue = new SimpleStringProperty("initial value");
    final StringProperty searchField = new SimpleStringProperty("initial value");




    public String getTextValue() {
        return textValue.get();
    }

    public StringProperty textValueProperty() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue.set(textValue);
    }

    public String getSearchField() {
        return searchField.get();
    }

    public StringProperty searchFieldProperty() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField.set(searchField);
    }
}
