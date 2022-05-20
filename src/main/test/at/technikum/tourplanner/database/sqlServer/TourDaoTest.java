package at.technikum.tourplanner.database.sqlServer;

import at.technikum.tourplanner.database.dao.TourDao;
import at.technikum.tourplanner.models.RouteImage;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TourDao")
public class TourDaoTest {
    Date date = new Date(System.currentTimeMillis());
    private TourDao tourDao = new TourDaoImpl();

    private Tour tour = this.initTour();

    @Before
    private Tour initTour(){

        RouteImage routeImage = RouteImage.builder()
                .imageID("test")
                .downloadURL("www.TEST")
                .filePath("/test/test")
                .build();

        return Tour.builder()
                .tourID("Test-ID")
                .title("Test-Tour-1")
                .from("Wien")
                .to("Berlin")
                .date(date)
                .description("Das ist ein Test")
                .routeImage(routeImage)
                .transporter(Transporter.fastest)
                .distance(11.0)
                .time(Time.valueOf("11:11:11"))
                .build();
    }


    @Test
    public void getTourByNameTest(){
        Tour testTour = initTour();
        testTour = tourDao.insert(testTour);
        assertNotNull(testTour);
        testTour = tourDao.getItemByName(testTour.getTitle());
        assertNotNull(testTour);
        assertTrue(tourDao.delete(testTour.getTourID()));
    }

    @Test
    public void insertTourTest(){
        this.tour = initTour();
        tourDao.insert(tour);
        assertNull(tourDao.getItemById(this.tour.getTourID()));
        assertNotNull(tourDao.insert(this.tour));
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

    //  @Test
    //public void updateTourTest(){
    //    assertNull(tourDao.getItemById(this.tour.getTourID()));
    //    assertNotNull(tourDao.insert(this.tour));
    //    Tour updateTour = tourDao.getItemById(this.tour.getTourID());
    //    assertNotNull(updateTour);
    //    String title = this.tour.getTitle();
    //    updateTour.setTitle("UPDATE-TEST");
    //    assertNotNull(tourDao.update(updateTour));
    //    assertTrue(updateTour.getTitle().equals("UPDATE-TEST"));
    //    assertTrue(tourDao.delete(updateTour.getTourID()));
    //    assertNull(tourDao.getItemById(updateTour.getTourID()));
    //}

}
