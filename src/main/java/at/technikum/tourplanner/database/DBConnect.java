package at.technikum.tourplanner.database;

import java.sql.Connection;

public interface DBConnect {
    Connection getConnection();
}
