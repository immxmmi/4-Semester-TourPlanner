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
    private Tour initTour() {
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
        Tour currentTour = this.tour;
        currentTour.setTourID("DELETE ME");
        assertTrue(tourService.deleteTour("TestDelete"));
        assertNotNull(tourService.saveTour(currentTour));
        assertTrue(tourService.deleteTour(currentTour.getTourID()));
    }

    @Test
    void searchTourByName() {
        Tour currentTour = this.tour;
        currentTour.setTourID("SEARCH BY NAME TEST");
        assertNull(tourService.searchTourByName("TEST-FALSE-NAME"));
        assertNotNull(tourService.saveTour(currentTour));
        assertNotNull(tourService.searchTourByName(currentTour.getTitle()));
        assertTrue(tourService.deleteTour(currentTour.getTourID()));
        assertNull(tourService.searchTourByName(currentTour.getTitle()));
    }

    @Test
    void updateTour() {
        Tour updateTour = this.tour;
        updateTour.setTourID("UPDATE TOUR TEST");
        assertNotNull(tourService.saveTour(updateTour));
        assertNotNull(tourService.getTourByID(updateTour.getTourID()));
        assertFalse(updateTour.getDescription().equals("Test - UPDATE"));
        updateTour.setDescription("Test - UPDATE");
        assertTrue(tourService.deleteTour(updateTour.getTourID()));
    }

    @Test
    void getTourByID() {
        Tour currentTour = this.tour;
        currentTour.setTourID("GET TOUR TEST");
        assertNotNull(tourService.saveTour(currentTour));
        assertNotNull(tourService.getTourByID(currentTour.getTourID()));
        assertTrue(tourService.deleteTour(this.tour.getTourID()));
        assertNull(tourService.getTourByID(this.tour.getTourID()));
    }

    @Test
    void searchTourAndTourLog() {
        Tour currentTour = this.tour;
        assertNotNull(tourService.saveTour(currentTour));
        assertNotNull(tourService.searchTourAndTourLog(currentTour.getTitle()));
        assertTrue(tourService.deleteTour(currentTour.getTourID()));
        assertNull(tourService.getTourByID(currentTour.getTourID()));
    }

    @Test
    void loadTourStatistics() {
        Tour currentTour = this.tour;
        assertNotNull(tourService.saveTour(currentTour));
        assertNotNull(tourService.loadTourStatistics(currentTour.getTourID()));
        assertTrue(tourService.deleteTour(currentTour.getTourID()));
    }

}