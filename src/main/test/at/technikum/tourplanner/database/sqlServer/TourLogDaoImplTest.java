package at.technikum.tourplanner.database.sqlServer;

import at.technikum.tourplanner.database.dao.TourLogDao;
import at.technikum.tourplanner.models.Level;
import at.technikum.tourplanner.models.Stars;
import at.technikum.tourplanner.models.TourLog;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TourLogDaoImplTest {

    private TourLogDao tourLogDao = new TourLogDaoImpl();

    @Before
    private TourLog initTourLog(){

        return TourLog.builder()
                .tourLogID("TEST-TOURLOG")
                .tourID("test")
                .comment("Kommentar - ist da")
                .level(Level.expert)
                .stars(Stars.none)
                .totalTime(10)
                .date(Date.valueOf("1998-11-11"))
                .build();
    }

    @Test
    void deleteTourLogTest(){
        assertTrue(tourLogDao.delete("ee"));
        TourLog testTourLog = initTourLog();
        assertNotNull(testTourLog);
        assertTrue(tourLogDao.delete(testTourLog.getTourLogID()));
        testTourLog = tourLogDao.insert(testTourLog);
        assertNotNull(testTourLog);
        testTourLog = tourLogDao.getItemById(testTourLog.getTourLogID());
        assertNotNull(testTourLog);
        assertTrue(tourLogDao.delete(testTourLog.getTourLogID()));
    }

    @Test
    void getTourLogByIDTest() {
        TourLog testTourLog = initTourLog();
        assertNotNull(testTourLog);
        assertTrue(tourLogDao.delete(testTourLog.getTourLogID()));
        testTourLog = tourLogDao.insert(testTourLog);
        assertNotNull(testTourLog);
        testTourLog = tourLogDao.getItemById(testTourLog.getTourLogID());
        assertNotNull(testTourLog);
        assertTrue(tourLogDao.delete(testTourLog.getTourLogID()));
    }

    @Test
    void insertTourLogTest() {
        TourLog testTourLog = initTourLog();
        assertNotNull(testTourLog);
        assertTrue(tourLogDao.delete(testTourLog.getTourLogID()));
        testTourLog = tourLogDao.insert(testTourLog);
        assertNotNull(testTourLog);
        testTourLog = tourLogDao.getItemById(testTourLog.getTourLogID());
        assertNotNull(testTourLog);
        assertTrue(tourLogDao.delete(testTourLog.getTourLogID()));
    }

    @Test
    void update() {
    }

    @Test
    void buildClass() {
    }

    @Test
    void countTourLogs() {
    }

    @Test
    void avgTotalTime() {
    }

    @Test
    void countStars() {
    }

    @Test
    void countLevel() {
    }

    @Test
    void getAllTourLog() {
    }

    @Test
    void getItemById() {
    }

    @Test
    void insert() {
    }


}