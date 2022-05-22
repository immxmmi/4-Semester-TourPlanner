package at.technikum.tourplanner.business.tour;

import at.technikum.tourplanner.business.tour.TourService;
import at.technikum.tourplanner.business.tour.TourServiceImpl;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class TourServiceImplTest {

    private TourService tourService = new TourServiceImpl();

    private Tour tour = this.initTour();

    @Before
    private Tour initTour(){
        return Tour.builder()
                .title("Test-Tour-1")
                .from("Wien")
                .to("Berlin")
                .description("Das ist ein Test")
                .transporter(Transporter.fastest)
                .build();
    }


    @Test
    void saveTour() {
        Tour currentTour = this.tour;
        assertNotNull(tourService.saveTour(currentTour));
        assertTrue(tourService.deleteTour(currentTour.getTourID()));
        currentTour.setFrom("testddddd");
        assertNull(tourService.saveTour(currentTour));
    }

    @Test
    void deleteTour() {
        assertTrue(tourService.deleteTour("TestDelete"));
        assertNotNull(tourService.saveTour(this.tour));
        assertTrue(tourService.deleteTour(this.tour.getTourID()));
    }

    @Test
    void searchTourByName() {
        assertNull(tourService.searchTourByName("TEST-FALSE-NAME"));
        assertNotNull(tourService.saveTour(this.tour));
        assertNotNull(tourService.searchTourByName(this.tour.getTitle()));
        assertTrue(tourService.deleteTour(this.tour.getTourID()));
        assertNull(tourService.searchTourByName(this.tour.getTitle()));
    }

    @Test
    void updateTour() {
         Tour updateTour = this.tour;
         assertNotNull(tourService.saveTour(updateTour));
         assertNotNull(tourService.searchTourByName(updateTour.getTitle()));
         updateTour.setDescription("Test - UPDATE");
         assertTrue(tourService.deleteTour(updateTour.getTourID()));
    }


    @Test
    void getTourByID() {
        assertNotNull(tourService.saveTour(this.tour));
        assertNotNull(tourService.getTourByID(this.tour.getTourID()));
        assertTrue(tourService.deleteTour(this.tour.getTourID()));
        assertNull(tourService.getTourByID(this.tour.getTourID()));
    }


    @Test
    void getAllTourOrderByName() {
        assertNotNull(tourService.saveTour(this.tour));
        //assertNotNull(tourService.getAllTourOrderByName(this.tour.getTitle()));
    }


    @Test
    void searchTourAndTourLog() {
        assertNull(tourService.searchTourAndTourLog("WRONG-SEARCH"));
        assertNotNull(tourService.saveTour(this.tour));
        assertNotNull(tourService.searchTourAndTourLog(this.tour.getTitle()));
        assertTrue(tourService.deleteTour(this.tour.getTourID()));
        assertNull(tourService.searchTourAndTourLog(this.tour.getTitle()));
    }

    @Test
    void loadTourStatistics() {
        assertNotNull(tourService.saveTour(this.tour));
        assertNotNull(tourService.loadTourStatistics(this.tour.getTourID()));
        assertTrue(tourService.deleteTour(this.tour.getTourID()));
        assertNull(tourService.loadTourStatistics(this.tour.getTourID()));
    }

    @Test
    void saveTourLocal() {
        //assertNull(tourService.saveTourLocal(file, this.tour.getTourID));
        //File file = new File();
        //assertNotNull(tourService.saveTourLocal(file, this.tour.getTourID()));
    }
}