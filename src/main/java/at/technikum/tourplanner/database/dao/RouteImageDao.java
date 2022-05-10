package at.technikum.tourplanner.database.dao;

import at.technikum.tourplanner.database.common.DaoPattern;
import at.technikum.tourplanner.models.RouteImage;

public interface RouteImageDao extends DaoPattern<RouteImage> {
     boolean delete(String itemID);

    RouteImage updateImageData(RouteImage item);
}
