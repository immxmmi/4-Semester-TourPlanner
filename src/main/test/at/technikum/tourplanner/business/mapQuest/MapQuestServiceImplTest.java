package at.technikum.tourplanner.business.mapQuest;

import at.technikum.tourplanner.business.mapQuest.MapQuestService;
import at.technikum.tourplanner.business.mapQuest.MapQuestServiceImpl;
import at.technikum.tourplanner.database.dao.RouteImageDao;
import at.technikum.tourplanner.database.sqlServer.RouteImageDaoImpl;
import at.technikum.tourplanner.models.Route;
import at.technikum.tourplanner.models.RouteImage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapQuestServiceImplTest {

    @Test
    void searchRoute() {
        MapQuestService mapQuestService = new MapQuestServiceImpl();
        Route testRoute = mapQuestService.searchRoute("Wien","Berlin");
        assertNotNull(testRoute);
        assertNotNull(testRoute.getRouteBody());

        Route failRoute = mapQuestService.searchRoute("Gsdadasdw","Berlin");
        assertNull(failRoute);
    }

    @Test
    void setImageSettingsToRoute() {
        MapQuestService mapQuestService = new MapQuestServiceImpl();
        Route testRoute = mapQuestService.searchRoute("Wien","Berlin");
        assertNotNull(testRoute);
       // assertNotNull(mapQuestService.setImageSettingsToRoute(testRoute).getSize());
       // assertNotNull(mapQuestService.setImageSettingsToRoute(testRoute).getUrlMap());
    }

    @Test
    void copyRouteDataToImage() {
        MapQuestService mapQuestService = new MapQuestServiceImpl();
        Route testRoute = mapQuestService.searchRoute("Wien","Berlin");
        assertNotNull(testRoute);
        RouteImage imageTest = testRoute.getRouteImage();
        assertNull(imageTest.getImageID());
        //imageTest = mapQuestService.copyRouteDataToImage(testRoute).getRouteImage();
        assertNotNull(imageTest.getImageID());
    }

    @Test
    void saveImageOnline() {
        RouteImage routeImageSettings = RouteImage.builder().build();
        MapQuestService mapQuestService = new MapQuestServiceImpl();
        RouteImageDao imageDao = new RouteImageDaoImpl();
        Route testRoute = mapQuestService.searchRoute("Wien","Berlin");
        assertNotNull(testRoute);
        testRoute.setRouteImage(routeImageSettings);
       // testRoute = mapQuestService.setImageSettingsToRoute(testRoute);
        assertNotNull(testRoute);
       // testRoute = mapQuestService.copyRouteDataToImage(testRoute);
        assertNotNull(testRoute);
        assertTrue(imageDao.delete(testRoute.getRouteImage().getImageID()));
       // assertNotNull(mapQuestService.saveImageOnline(testRoute.getRouteImage()));
        assertNotNull(imageDao.getItemById(testRoute.getRouteImage().getImageID()));
        assertTrue(imageDao.delete(testRoute.getRouteImage().getImageID()));
        assertNull(imageDao.getItemById(testRoute.getRouteImage().getImageID()));
    }



    @Test
    void updateImageOnline() {
    }

    @Test
    void startRoute() {
    }

    @Test
    void showRouteImage() {
    }

    @Test
    void downloadImage() {
    }

    @Test
    void reloadImage() {
    }
}