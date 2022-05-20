package at.technikum.tourplanner.database.sqlServer;

import at.technikum.tourplanner.database.dao.RouteImageDao;
import at.technikum.tourplanner.database.dao.TourDao;
import at.technikum.tourplanner.models.RouteImage;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TourDao")
public class TourDaoImplTest {


    private Tour tour;
    private TourDao tourDao = new TourDaoImpl();

    @Before
    public Tour initTour(){
        RouteImage image = RouteImage.builder()
                .imageID("Test-Image")
                .build();
        return Tour.builder()
                .tourID("Test-T1")
                .title("Test-Tour-1")
                .transporter(Transporter.fastest)
                .from("Wien")
                .to("Berlin")
                .routeImage(image)
                .distance(10)
                .time(Time.valueOf("11:11:11"))
                .description("Das ist ein Test")
                .build();
    }

    @Test
    public void insertTourTest(){
        Tour testTour = this.initTour();
        assertNotNull(testTour);
        assertTrue(tourDao.delete(testTour.getTourID()));
        tourDao.insert(testTour);
        assertNotNull(tourDao.getItemById(testTour.getTourID()));
        assertTrue(tourDao.delete(testTour.getTourID()));
    }


    @Test
    public void deleteTourTest(){
        assertTrue(tourDao.delete("test"));
        Tour testTour = this.initTour();
        assertNotNull(testTour);
        assertTrue(tourDao.delete(testTour.getTourID()));
        tourDao.insert(testTour);
        assertNotNull(tourDao.getItemById(testTour.getTourID()));
        assertTrue(tourDao.delete(testTour.getTourID()));
    }

    @Test
    void buildClass() {
    }

    @Test
    void getItemById() {
    }

    @Test
    void getItemByName() {
    }

    @Test
    void search() {
    }

    @Test
    void insert() {
    }

    @Test
    void update() {
    }

    @Test
    void getAllTourOrderByName() {
    }
}
