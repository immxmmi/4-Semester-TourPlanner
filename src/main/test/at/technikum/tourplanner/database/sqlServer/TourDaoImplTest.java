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

import java.sql.Date;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TourDao")
public class TourDaoImplTest {


    private Tour tour = this.initTour();;
    private TourDao tourDao = new TourDaoImpl();

    Date date = new Date(System.currentTimeMillis());


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
                .date(Date.valueOf("1998-11-11"))
                .description("Das ist ein Test")
                .build();
    }


    @Test
    void getItemById() {
        Tour currentTour = this.tour;
        currentTour.setTourID("gettourdaotest");
        assertNotNull(tourDao.insert(currentTour));
        assertNotNull(tourDao.getItemById(currentTour.getTourID()));
        tourDao.delete(currentTour.getTourID());
        assertNull(tourDao.getItemById(currentTour.getTourID()));
    }

    @Test
    void getItemByName() {
        Tour currentTour = this.tour;
        currentTour.setTitle("TEST-NAME");
        assertNotNull(tourDao.insert(currentTour));
        assertNotNull(tourDao.getItemByName(currentTour.getTitle()));
        assertTrue(tourDao.delete(this.tour.getTourID()));
        assertNull(tourDao.getItemByName(this.tour.getTitle()));
    }

    @Test
    void search() {
        assertNotNull(tourDao.search(this.tour.getTitle()));
        assertTrue(tourDao.delete(this.tour.getTourID()));
    }

    @Test
    public void insertTourTest(){
        this.tour = initTour();
        assertNull(tourDao.getItemById(this.tour.getTourID()));
        assertNotNull(tourDao.insert(tour));
        assertNotNull(tourDao.getItemById(this.tour.getTourID()));
        assertTrue(tourDao.delete(this.tour.getTourID()));
        assertNull(tourDao.getItemById(this.tour.getTourID()));
    }

    @Test
    public void getTourByIDTest(){
        Tour tour = initTour();
        //assertNull(tourDao.getItemById("TEST-ERROR"));
        //assertNull(tourDao.getItemById(this.tour.getTourID()));
        //assertNotNull(tourDao.insert(this.tour));
        //assertNotNull(tourDao.getItemById(this.tour.getTourID()));
        //assertTrue(tourDao.delete(this.tour.getTourID()));
        //assertNull(tourDao.getItemById(this.tour.getTourID()));
    }

    @Test
    public void deleteTourTest(){
        this.tour = initTour();
        assertNotNull(tourDao.insert(this.tour));
        assertTrue(tourDao.delete(this.tour.getTourID()));
        assertNull(tourDao.getItemById(this.tour.getTourID()));
    }


    @Test
    void update() {
        Tour tour = initTour();
        assertNotNull(tourDao.insert(tour));
        assertNotNull(tour.getTitle());
        tour.setTitle("NEW_TITLE");
        assertNotNull(tourDao.update(tour));
        assertEquals("NEW_TITLE", tour.getTitle());
        assertTrue(tourDao.delete(tour.getTourID()));
    }

    @Test
    void getAllTourOrderByName() {
    }
}
