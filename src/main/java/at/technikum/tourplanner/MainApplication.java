package at.technikum.tourplanner;

import at.technikum.tourplanner.database.DBConnectImpl;
import at.technikum.tourplanner.model.Tour;
import at.technikum.tourplanner.model.Transporter;

import java.sql.Connection;

public class MainApplication {
    public static void main(String args[]){
      //  Connection connection = DBConnectImpl.getInstance().getConnection();

        Tour tour1 = Tour.builder()
                .name("Touraaaa")
                .transporter(Transporter.Bike)
                .build();
        System.out.println(tour1.transporter);
        System.out.println(tour1.name);
    }
}
