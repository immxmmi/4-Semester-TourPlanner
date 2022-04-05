package at.technikum.tourplanner.database.sqlServer;

import at.technikum.tourplanner.database.dao.TourLogDao;
import at.technikum.tourplanner.models.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

class TourLogDaoImplTest {

    @Before
    private TourLog initTourLog(){

        RouteImage routeImage = RouteImage.builder()
                .imageID("test")
                .downloadURL("www.TEST")
                .filePath("/test/test")
                .build();

        Tour tour =  Tour.builder()
                .tourID("Test-ID")
                .title("Test-Tour-1")
                .from("Wien")
                .to("Berlin")
                .description("Das ist ein Test")
                .routeImage(routeImage)
                .transporter(Transporter.Walk)
                .distance(11)
                .time(Time.valueOf("11:11:11"))
                .build();

        return TourLog.builder()
                .tourLogID("TEST-TOURLOG")
                .tour(tour)
                .comment("Kommentar - ist da")
                .difficulty(Difficulty.Expert)
                .rating(Rating.point1)
                .totalTime(10)
                .report("test text me")
                .build();
    }
    @Test
    void getItemById() {
    }

    @Test
    void insert() {
        TourLog tourLog = initTourLog();
        TourLogDao tourLogDao = new TourLogDaoImpl();
        tourLogDao.insert(tourLog);
    }

    @Test
    void update() {
    }
}