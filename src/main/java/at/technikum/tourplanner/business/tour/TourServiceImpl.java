package at.technikum.tourplanner.business.tour;

import at.technikum.tourplanner.business.ThreadMaker;
import at.technikum.tourplanner.business.mapQuest.MapQuestService;
import at.technikum.tourplanner.business.mapQuest.MapQuestServiceImpl;
import at.technikum.tourplanner.database.dao.RouteImageDao;
import at.technikum.tourplanner.database.dao.TourDao;
import at.technikum.tourplanner.database.fileServer.FileAccess;
import at.technikum.tourplanner.database.fileServer.FileAccessImpl;
import at.technikum.tourplanner.database.sqlServer.RouteImageDaoImpl;
import at.technikum.tourplanner.database.sqlServer.TourDaoImpl;
import at.technikum.tourplanner.models.*;
import at.technikum.tourplanner.serializer.TourSerializer;
import at.technikum.tourplanner.serializer.TourSerializerImpl;
import at.technikum.tourplanner.utils.Tools;
import at.technikum.tourplanner.utils.ToolsImpl;
import com.google.gson.Gson;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TourServiceImpl implements TourService {

    //DATE
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    private Date date = new Date(System.currentTimeMillis());
    //SERVICES
    private TourLogService tourLogService = new TourLogServiceImpl();
    //DAO
    private TourDao tourDao;
    //current-TOUR
    private Tour currentTour;


    // INIT
    {
        tourDao = new TourDaoImpl();
        currentTour = new Tour();
    }

    //DAO
    @Override
    public Tour getTourByID(String tourID) {
        return tourDao.getItemById(tourID);
    }
    @Override
    public Tour saveTour(Tour tour) {

        Tools tools = new ToolsImpl();
        MapQuestService mapQuestService = new MapQuestServiceImpl();
        RouteImageDao routeImageDao = new RouteImageDaoImpl();


        //ID - HASH-WERT and DATE

        //DATE
        tour.setDate(date);
        tour.setTourID(tools.hashString(tour.getTitle() + tour.getDescription()));
        tour.setTitle(tour.getTitle().toLowerCase());

        currentTour = tour;

        // ROUTE
        final Route[] currentRoute = {null};
        currentRoute[0] = mapQuestService.startRoute(tour);


        if (currentRoute[0] == null) {
            return null;
        }

        // IMAGE
        RouteImage routeImage = routeImageDao.getItemById(currentRoute[0].getRouteImage().getImageID());

        if (routeImage == null) {
            return null;
        }

        // IMAGE
        tour.setRouteImage(routeImage);
        tour.setRouteImage(routeImage);

        //DISTANCE
        tour.setDistance(currentRoute[0].getDistance());

        //TIME
        tour.setTime(currentRoute[0].getTime());

        return tourDao.insert(tour);
    }
    @Override
    public Boolean deleteTour(String tourID) {
        TourLogService tourLogService = new TourLogServiceImpl();
        ArrayList<TourLog> tourLogs = tourLogService.getAllTourLogs(tourID);
        for (TourLog tourLog : tourLogs) {
            tourLogService.deleteTourLog(tourLog.getTourLogID());
        }
        RouteImageDao routeImageDao = new RouteImageDaoImpl();
        routeImageDao.delete(tourID);
        return tourDao.delete(tourID);
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

    //SEARCH
    @Override
    public Tour searchTourByName(String tourName) {
        return tourDao.getItemByName(tourName.toLowerCase());
    }
    @Override
    public ArrayList<Tour> searchTourAndTourLog(String search) {
        return tourDao.search(search);
    }
    
    //STATISTIK
    @Override
    public TourStatistics loadTourStatistics(String tourID) {

        TourStatistics statistics = new TourStatistics();
        ThreadMaker.multiRunInBackground(new Runnable() {
            @Override
            public void run() {
               // statistics.setAvgTotalTime(loadAvgFromTotalTime(tourID));
            }
        });
        ThreadMaker.multiRunInBackground(new Runnable() {
            @Override
            public void run() {
                //statistics.setAvgDistance(loadAvgFromDistance(tourID));
            }
        });
        ThreadMaker.runInBackground(new Runnable() {
            @Override
            public void run() {
                statistics.setNumberOfTourlogs(tourLogService.countTourLogsFromTour(tourID));
                statistics.setNumberOfLevelEasy(tourLogService.countLevelEasyFromTour(tourID));
                statistics.setNumberOfLevelNormal(tourLogService.countLevelNormalFromTour(tourID));
                statistics.setNumberOfLevelHard(tourLogService.countLevelHardFromTour(tourID));
                statistics.setNumberOfLevelExpert(tourLogService.countLevelExpertFromTour(tourID));
                statistics.setNumberOfStarsNone(tourLogService.countStarsNoneFromTour(tourID));
                statistics.setNumberOfStarsOne(tourLogService.countStarsOneFromTour(tourID));
                statistics.setNumberOfStarsTwo(tourLogService.countStarsTwoFromTour(tourID));
                statistics.setNumberOfStarsThree(tourLogService.countStarsThreeFromTour(tourID));
                statistics.setNumberOfStarsFour(tourLogService.countStarsFourFromTour(tourID));
                statistics.setNumberOfStarsFive(tourLogService.countStarsFiveFromTour(tourID));
            }
        });

        // statistics.setAvgTotalTime(tourLogService.avgTotalTimeFromTour(tourID));


        return statistics;
    }

    //JSON
    @Override
    public void saveTourLocal(File file,Tour tour){
        TourSerializer tourSerializer = new TourSerializerImpl();
        tourSerializer.saveTourAsJSON(file,tour);
    }


    // TODO: 09.05.2022 anpassen
    private double loadAvgFromTotalTime(String tourID) {
        int counter = 0;
        double sumTotalTime = 0.0;
        //System.out.println( tourLogService.getAllTourLogs(tourID));
      //  for (TourLog tourLog : tourLogService.getAllTourLogs(tourID)) {
      //      sumTotalTime += tourLog.getTotalTime();
      //      counter++;
      //  }
        return sumTotalTime / counter;
    }
    private double loadAvgFromDistance(String tourID) {
        int counter = 0;
        double sumDistance = 0.0;
        for (TourLog tourLog : tourLogService.getAllTourLogs(tourID)) {
            sumDistance += tourLog.getTotalTime();
            counter++;
        }

        return sumDistance / counter;
    }
}
