package at.technikum.tourplanner.database.sqlServer;

import at.technikum.tourplanner.business.TourService;
import at.technikum.tourplanner.business.TourServiceImpl;
import at.technikum.tourplanner.database.dao.TourDao;
import at.technikum.tourplanner.models.RouteImage;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TourDao")
public class TourDaoTest {
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
                .description("Das ist ein Test")
                .routeImage(routeImage)
                .transporter(Transporter.Walk)
                .distance(11)
                .time(Time.valueOf("11:11:11"))
                .build();
    }


    @Test
    public void getTourByNameTest(){
        assertNull(tourDao.getItemByName(this.tour.getTitle()));
        assertNotNull(tourDao.insert(this.tour));
        assertNotNull(tourDao.getItemByName(this.tour.getTitle()));
        assertTrue(tourDao.delete(this.tour.getTourID()));
        assertNull(tourDao.getItemByName(this.tour.getTitle()));
    }

    @Test
    public void insertTourTest(){
       assertNull(tourDao.getItemById(this.tour.getTourID()));
       assertNotNull(tourDao.insert(this.tour));
       assertNotNull(tourDao.getItemById(this.tour.getTourID()));
       assertTrue(tourDao.delete(this.tour.getTourID()));
       assertNull(tourDao.getItemById(this.tour.getTourID()));
    }

    @Test
    public void getTourTest(){
        assertNull(tourDao.getItemById("TEST-ERROR"));
        assertNull(tourDao.getItemById(this.tour.getTourID()));
        assertNotNull(tourDao.insert(this.tour));
        assertNotNull(tourDao.getItemById(this.tour.getTourID()));
        assertTrue(tourDao.delete(this.tour.getTourID()));
        assertNull(tourDao.getItemById(this.tour.getTourID()));
    }

    @Test
    public void deleteTourTest(){
        assertNull(tourDao.getItemById(this.tour.getTourID()));
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
