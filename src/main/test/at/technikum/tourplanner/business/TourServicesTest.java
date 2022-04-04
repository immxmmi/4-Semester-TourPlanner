package at.technikum.tourplanner.business;

import at.technikum.tourplanner.database.dao.TourDao;
import at.technikum.tourplanner.database.sqlServer.TourDaoImpl;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TourServices")
public class TourServicesTest {



    private TourService tourService = new TourServiceImpl();

    private Tour tour = this.initTour();

    @Before
    private Tour initTour(){
        return Tour.builder()
                .title("Test-Tour-1")
                .from("Wien")
                .to("Berlin")
                .description("Das ist ein Test")
                .transporter(Transporter.Walk)
                .build();
    }

    @Test
    public void saveTourTest(){
        assertNotNull(tourService.saveTour(this.tour));
        assertTrue(tourService.deleteTour(this.tour.getTourID()));
    }

    @Test
    public void searchByNameTourTest(){
       assertNull(tourService.searchTourByName("TEST-FALSE-NAME"));
       assertNotNull(tourService.saveTour(this.tour));
       assertNotNull(tourService.searchTourByName(this.tour.getTitle()));
       assertTrue(tourService.deleteTour(this.tour.getTourID()));
       assertNull(tourService.searchTourByName(this.tour.getTitle()));
    }

    @Test
    public void deleteTourTest(){
        assertFalse(tourService.deleteTour("TestDelete"));
        assertNotNull(tourService.saveTour(this.tour));
        assertTrue(tourService.deleteTour(this.tour.getTourID()));
    }

    @Test
    public void updateTourTest(){
     // Tour updateTour = this.tour;
     // assertNotNull(tourService.saveTour(this.tour));
     // assertNotNull(tourService.searchTourByName("Test"));
     // updateTour.setDescription("Test - UPDATE");
     // assertTrue(tourService.deleteTour(this.tour.getTourID()));
    }
}
