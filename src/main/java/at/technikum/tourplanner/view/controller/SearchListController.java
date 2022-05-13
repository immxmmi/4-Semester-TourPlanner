package at.technikum.tourplanner.view.controller;

import at.technikum.tourplanner.view.viewmodel.TourViewModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
// TODO: 13.05.2022 @Checked
public class SearchListController extends AbstractTourList {

    private ArrayList<TourViewModel> currentSearchList = new ArrayList<TourViewModel>();

    //  INIT
    public void initialize(ArrayList<TourViewModel> searchList) {
        currentSearchList = searchList;
        loadList(searchList);

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

    // LOAD LIST
    private void loadList(ArrayList<TourViewModel> searchList) {
        setObsTourList(FXCollections.observableArrayList());
        for (TourViewModel tour : searchList) {
            getObsTourList().add(tour);
        }
    }

    // RELOAD LIST
    @FXML
    public void reloadList(ActionEvent actionEvent) throws IOException {
        sCon.switchToSearchList(actionEvent,currentSearchList);
    }

}
