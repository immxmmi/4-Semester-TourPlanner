package at.technikum.tourplanner.business.tour;

import at.technikum.tourplanner.business.ThreadMaker;
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

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TourServiceImpl implements TourService{

    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    Date date = new Date(System.currentTimeMillis());

    private TourDao tourDao;
    {
        tourDao = new TourDaoImpl();
    }

    @Override
    public Tour saveTour(Tour tour) {

        Tools tools = new ToolsImpl();
        MapQuestService mapQuestService = new MapQuestServiceImpl();
        RouteImageDao routeImageDao = new RouteImageDaoImpl();


        //ID - HASH-WERT and DATE
      //  ThreadMaker.multiRunInBackground(new Runnable() {
      //      @Override
      //      public void run() {
                //DATE
                tour.setDate(date);
                tour.setTourID(tools.hashString(tour.getTitle()+tour.getDescription()));
                tour.setTitle(tour.getTitle().toLowerCase());
      //     }
      // });
        // ROUTE
        final Route[] currentRoute = {null};
      //  ThreadMaker.runInBackground(new Runnable() {
      //      @Override
      //      public void run() {

                currentRoute[0] = mapQuestService.startRoute(tour.getFrom(),tour.getTo());
      //      }
      //  });


        if(currentRoute[0] == null){return null;}

        // IMAGE
        RouteImage routeImage = routeImageDao.getItemById(currentRoute[0].getRouteImage().getImageID());

        if(routeImage == null){return null;}

        // IMAGE
       //ThreadMaker.multiRunInBackground(new Runnable() {
       //    @Override
       //    public void run() {
                 tour.setRouteImage(routeImage);
                 tour.setRouteImage(routeImage);
       //     }
       // });
        //DATA
       // ThreadMaker.multiRunInBackground(new Runnable() {
       //     @Override
       //     public void run() {
                //DISTANCE
                tour.setDistance(currentRoute[0].getDistance());

                //TIME
                tour.setTime(currentRoute[0].getTime());
       //     }
       // });

       //  if(tourDao.insert(tour) != null){return true;}
         return tourDao.insert(tour);
    }

    @Override
    public Tour getTourByID(String tourID) {
        return tourDao.getItemById(tourID);
    }

    @Override
    public Boolean deleteTour(String tourID) {
        return tourDao.delete(tourID);
    }

    @Override
    public Tour searchTourByName(String tourName) {
       return tourDao.getItemByName(tourName.toLowerCase());
    }

    @Override
    public Tour updateTour(Tour tour) {
        tour.setDate(date);
        return tourDao.update(tour);
    }

    @Override
    public ArrayList<Tour> getAllTourOrderByName() {
        return tourDao.getAllTourOrderByName();
    }

}
