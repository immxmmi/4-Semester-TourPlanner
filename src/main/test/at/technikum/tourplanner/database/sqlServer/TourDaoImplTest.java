package at.technikum.tourplanner.database.sqlServer;

import at.technikum.tourplanner.database.dao.TourDao;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("TourDao")
public class TourDaoImplTest {


    private Tour tour;
    private TourDao tourDao = new TourDaoImpl();

    @Before
    public void initTour(){
        this.tour = Tour.builder()
                .title("Test-Tour-1")
                .from("Wien")
                .to("Berlin")
                .description("Das ist ein Test")
                .transporter(Transporter.Walk)
                .build();

    }

    @Test
    public void insertTourTest(){
        this.initTour();
        assertNotNull(tourDao.insert(this.tour));

       // assertFalse(false);

    }


    @Test
    public void deleteTourTest(){
        System.out.println(this.tour);
        tourDao.insert(this.tour);
        //tourDao.getItemById()
        assertFalse(tourDao.delete("test"));
    }

}
