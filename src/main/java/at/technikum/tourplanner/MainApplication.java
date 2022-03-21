package at.technikum.tourplanner;

import at.technikum.tourplanner.database.DBConnectImpl;

import java.sql.Connection;

public class MainApplication {
    public static void main(String args[]){
        Connection connection = DBConnectImpl.getInstance().getConnection();

    }
}
