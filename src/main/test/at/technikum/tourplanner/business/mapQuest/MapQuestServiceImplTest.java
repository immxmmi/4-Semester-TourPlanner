package at.technikum.tourplanner.business.mapQuest;

import at.technikum.tourplanner.business.mapQuest.MapQuestService;
import at.technikum.tourplanner.business.mapQuest.MapQuestServiceImpl;
import at.technikum.tourplanner.database.dao.RouteImageDao;
import at.technikum.tourplanner.database.sqlServer.RouteImageDaoImpl;
import at.technikum.tourplanner.models.Route;
import at.technikum.tourplanner.models.RouteImage;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapQuestServiceImplTest {
    MapQuestService mapQuestService = new MapQuestServiceImpl();
    @Before
    private Tour initTour(){
        return Tour.builder()
                .title("SEARCH-TOUR-TEST")
                .from("Wien")
                .to("Berlin")
                .description("Das ist ein Test")
                .transporter(Transporter.fastest)
                .build();
    }

    @Test
    void searchRoute() {
        Route testRoute = mapQuestService.searchRoute("Wien","Berlin", Transporter.fastest);
        assertNotNull(testRoute);
        assertNotNull(testRoute.getRouteBody());

        Route failRoute = mapQuestService.searchRoute("Gsdadasdw","Berlin", Transporter.fastest);
        assertNull(failRoute);
    }

    @Test
    void setImageSettingsToRoute() {
        Route testRoute = mapQuestService.searchRoute("Wien","Berlin", Transporter.fastest);
        assertNotNull(testRoute);
    }

    @Test
    void startRoute() {
        Tour currentTour = initTour();
        assertNotNull( mapQuestService.startRoute(currentTour));
        currentTour.setFrom("sdfhbdsofw");
        assertNull( mapQuestService.startRoute(currentTour));
    }


}