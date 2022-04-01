package at.technikum.tourplanner.business;

import at.technikum.tourplanner.database.dao.ImageDao;
import at.technikum.tourplanner.database.dao.TourDao;
import at.technikum.tourplanner.database.sqlServer.ImageDaoImpl;
import at.technikum.tourplanner.database.sqlServer.TourDaoImpl;
import at.technikum.tourplanner.models.Image;
import at.technikum.tourplanner.models.Route;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.utils.Tools;
import at.technikum.tourplanner.utils.ToolsImpl;

import java.util.ArrayList;

public class TourServiceImpl implements TourService{

    private TourDao tourDao;
    {
        tourDao = new TourDaoImpl();
    }

    @Override
    public Tour saveTour(Tour tour) {

        // GUI ABFRAGE:
        // - NAME
        // - FROM
        // - TO
        // - TRANSPORTER
        // - DESCRIPTION
        // - TIME

        Tools tools = new ToolsImpl();
        MapQuestService mapQuestService = new MapQuestServiceImpl();
        ImageDao imageDao = new ImageDaoImpl();

        //ID - HASH-WERT
        tour.setTourID(tools.hashString(tour.getTitle()+tour.getDescription()));

        // ROUTE
        Route currentRoute = mapQuestService.startRoute(tour.getFrom(),tour.getTo());

        if(currentRoute == null){return null;}
        // IMAGE
        Image image = imageDao.getItemById(currentRoute.getImage().getImageID());
        if(image == null){return null;}
        tour.setRouteImage(image);
        tour.setDistance(currentRoute.getDistance());



       //  if(tourDao.insert(tour) != null){return true;}
         return tourDao.insert(tour);
    }

    @Override
    public Boolean deleteTour(String tourID) {
        return tourDao.delete(tourID);
    }

    @Override
    public Tour searchTourByName(String tourName) {
        MapQuestService mapQuestService  = new MapQuestServiceImpl();
       // mapQuestService.reloadImage(tourDao.getItemByName(tourName).getRouteImage());
       return tourDao.getItemByName(tourName);
    }

    @Override
    public Tour updateTour(Tour tour) {
        return tourDao.update(tour);
    }

    @Override
    public ArrayList<Tour> getAllTourOrderByName() {
        return tourDao.getAllTourOrderByName();
    }

}
