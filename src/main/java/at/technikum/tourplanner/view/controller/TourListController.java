
package at.technikum.tourplanner.view.controller;

import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.view.viewmodel.TourViewModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TourListController extends AbstractTourList implements Initializable {

    //INIT
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadList();
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        col_from.setCellValueFactory(new PropertyValueFactory<>("from"));
        col_to.setCellValueFactory(new PropertyValueFactory<>("to"));
        col_distance.setCellValueFactory(new PropertyValueFactory<>("distance"));

        table_tourList.setRowFactory(tv -> {
            TableRow<TourViewModel> row = new TableRow<>();


            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {

                    TourViewModel rowData = row.getItem();

                    try {

                        //CODE
                        sCon.switchToShowTour(event, rowData);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });


        table_tourList.setItems(getObsTourList());
    }

    //RELOAD
    @FXML
    public void reloadList(ActionEvent actionEvent) throws IOException {
        sCon.switchToShowTourList(actionEvent);
    }

    //LOAD
    private void loadList() {
        setObsTourList(FXCollections.observableArrayList());
        ArrayList<Tour> tourList = getTourService().getAllTourOrderByName();
        for (Tour tour : tourList) {
            getObsTourList().add(new TourViewModel(tour));
        }
    }

}
