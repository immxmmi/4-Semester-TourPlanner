package at.technikum.tourplanner.database.common;

import java.sql.ResultSet;

public interface DaoPattern<T> {

    T buildClass(ResultSet result);

    T getItemById(String itemID);

    T insert(T item);

    T update(T item);

    boolean delete(T item);
}