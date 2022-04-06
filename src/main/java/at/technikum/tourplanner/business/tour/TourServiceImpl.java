package at.technikum.tourplanner.business.tour;

import at.technikum.tourplanner.business.mapQuest.MapQuestService;
import at.technikum.tourplanner.business.mapQuest.MapQuestServiceImpl;
import at.technikum.tourplanner.database.dao.RouteImageDao;
import at.technikum.tourplanner.database.dao.TourDao;
import at.technikum.tourplanner.database.sqlServer.RouteImageDaoImpl;
import at.technikum.tourplanner.database.sqlServer.TourDaoImpl;
import at.technikum.tourplanner.models.Route;
import at.technikum.tourplanner.models.RouteImage;
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
        RouteImageDao routeImageDao = new RouteImageDaoImpl();

        //ID - HASH-WERT
        tour.setTourID(tools.hashString(tour.getTitle()+tour.getDescription()));

        // ROUTE
        Route currentRoute = mapQuestService.startRoute(tour.getFrom(),tour.getTo());

        if(currentRoute == null){return null;}
        // IMAGE
        RouteImage routeImage = routeImageDao.getItemById(currentRoute.getRouteImage().getImageID());
        if(routeImage == null){return null;}
        tour.setRouteImage(routeImage);
        //DISTANCE
        tour.setDistance(currentRoute.getDistance());

        //TIME
        tour.setTime(currentRoute.getTime());

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
