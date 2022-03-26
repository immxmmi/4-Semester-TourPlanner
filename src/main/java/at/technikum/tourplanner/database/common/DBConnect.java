package at.technikum.tourplanner.database.common;

import java.sql.Connection;

public interface DBConnect {
    Connection getConnection();
}
