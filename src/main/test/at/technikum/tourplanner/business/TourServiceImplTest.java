package at.technikum.tourplanner.business;

import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import org.junit.Before;
import org.junit.jupiter.api.Test;

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
                .transporter(Transporter.Walk)
                .build();
    }


    @Test
    void saveTour() {
        assertNotNull(tourService.saveTour(this.tour));
        assertTrue(tourService.deleteTour(this.tour.getTourID()));
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
        // Tour updateTour = this.tour;
        // assertNotNull(tourService.saveTour(this.tour));
        // assertNotNull(tourService.searchTourByName("Test"));
        // updateTour.setDescription("Test - UPDATE");
        // assertTrue(tourService.deleteTour(this.tour.getTourID()));
    }

    @Test
    void getAllTourOrderByName() {
    }
}