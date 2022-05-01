package at.technikum.tourplanner;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TestViewModel {


    final StringProperty title = new SimpleStringProperty("initial value");
    final StringProperty searchField = new SimpleStringProperty("initial value");

    public void setTitle(String title) {
        this.title.set(title);
    }

    public TestViewModel(String title) {
        this.title.set(title);
    }

    public String getTextValue() {
        return title.get();
    }

    public StringProperty textValueProperty() {
        return title;
    }

    public void setTextValue(String textValue) {
        this.title.set(textValue);
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
