package at.technikum.tourplanner.business.tour;

import at.technikum.tourplanner.business.mapQuest.MapQuestService;
import at.technikum.tourplanner.business.mapQuest.MapQuestServiceImpl;
import at.technikum.tourplanner.database.dao.RouteImageDao;
import at.technikum.tourplanner.database.dao.TourDao;
import at.technikum.tourplanner.database.sqlServer.RouteImageDaoImpl;
import at.technikum.tourplanner.database.sqlServer.TourDaoImpl;
import at.technikum.tourplanner.models.*;
import at.technikum.tourplanner.serializer.TourSerializer;
import at.technikum.tourplanner.serializer.TourSerializerImpl;
import at.technikum.tourplanner.utils.Tools;
import at.technikum.tourplanner.utils.ToolsImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.sql.Date;
import java.util.ArrayList;


public class TourServiceImpl implements TourService {

    //LOGGER
    private final static Logger log = LogManager.getLogger(TourServiceImpl.class.getName());

    //DATE
    private Date date = new Date(System.currentTimeMillis());

    //SERVICES
    private TourLogService tourLogService = new TourLogServiceImpl();
    MapQuestService mapQuestService = new MapQuestServiceImpl();

    //DAO
    private TourDao tourDao = new TourDaoImpl();

    //current-TOUR
    private Tour currentTour = new Tour();

    //INSTANCE
    RouteImageDao routeImageDao = new RouteImageDaoImpl();


    //DAO
    @Override
    public Tour getTourByID(String tourID) {
        return tourDao.getItemById(tourID);
    }

    @Override
    public Tour saveTour(Tour tour) {
        log.debug("SAVE TOUR");
        Tools tools = new ToolsImpl();
        //DATE
        tour.setDate(date);
        //ID - HASH-WERT and DATE
        tour.setTourID(tools.hashString(tour.getTitle() + tour.getDescription()));
        tour.setTitle(tour.getTitle().toLowerCase());

        currentTour = tour;
        // ROUTE
        final Route[] currentRoute = {null};
        currentRoute[0] = mapQuestService.startRoute(tour);

        if (currentRoute[0] == null) {
            log.error("SAVE TOUR ROUTE=" + currentRoute);
            return null;
        }

        // IMAGE
        RouteImage routeImage = routeImageDao.getItemById(currentRoute[0].getRouteImage().getImageID());

        if (routeImage == null) {
            log.error("SAVE IMAGE =" + routeImage);
            return null;
        }

        // IMAGE
        tour.setRouteImage(routeImage);

        //DISTANCE
        tour.setDistance(currentRoute[0].getDistance());

        //TIME
        tour.setTime(currentRoute[0].getTime());

        if (tour.getDistance() == 0.0) {
            log.error("Distance : " + tour.getDistance());
            return null;
        }

        return tourDao.insert(tour);
    }

    @Override
    public Boolean deleteTour(String tourID) {
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

        // DELETE IMG
        routeImageDao.delete(tour.getRouteImage().getImageID());

        // ROUTE
        final Route[] currentRoute = {null};
        currentRoute[0] = mapQuestService.startRoute(tour);

        if (currentRoute[0] == null) {
            return null;
        }

        // IMAGE
        RouteImage routeImage = routeImageDao.getItemById(currentRoute[0].getRouteImage().getImageID());
        if (routeImage == null) {
            log.error("ROUTE IMAGE = " + routeImage);
            return null;
        }
        tour.setRouteImage(routeImage);

        // DISTANCE
        tour.setDistance(currentRoute[0].getDistance());

        // TIME
        tour.setTime(currentRoute[0].getTime());

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
        statistics.setNumberOfTourLog(tourLogService.countTourLogsFromTour(tourID));
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
        return statistics;
    }

    //JSON
    @Override
    public void saveTourLocal(File file, Tour tour) {
        TourSerializer tourSerializer = new TourSerializerImpl();
        tourSerializer.saveTourAsJSON(file, tour);
    }


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
