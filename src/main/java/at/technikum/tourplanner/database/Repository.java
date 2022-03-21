package at.technikum.tourplanner.database;

import java.sql.ResultSet;

public interface Repository<T> {

    T buildClass(ResultSet result);

    T getItemById(String itemID);

    T insert(T item);

    T update(T item);

    boolean delete(T item);
}