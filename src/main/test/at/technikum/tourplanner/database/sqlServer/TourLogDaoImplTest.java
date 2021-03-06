package at.technikum.tourplanner.database.sqlServer;

import at.technikum.tourplanner.database.dao.TourLogDao;
import at.technikum.tourplanner.models.Level;
import at.technikum.tourplanner.models.Stars;
import at.technikum.tourplanner.models.TourLog;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.Assert.*;


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
        TourLog testTourLog = initTourLog();
        testTourLog.setTourID("UPDATE INSERT");
        assertNotNull(tourLogDao.insert(testTourLog));
        assertNotNull(tourLogDao.getItemById(testTourLog.getTourLogID()));
        assertFalse(testTourLog.getComment().equals("TEST-COMMENT"));
        testTourLog.setComment("TEST-COMMENT");
        assertNotNull(tourLogDao.update(testTourLog));
        assertEquals("TEST-COMMENT", testTourLog.getComment());
        assertTrue(tourLogDao.delete(testTourLog.getTourLogID()));
        assertNull(tourLogDao.getItemById(testTourLog.getTourLogID()));
    }

    @Test
    void countTourLogs() {
        TourLog testTourLog = initTourLog();
        assertNotNull(testTourLog);
        assertNotNull(tourLogDao.countTourLogs(testTourLog.getTourLogID()));
        assertTrue(tourLogDao.delete(testTourLog.getTourLogID()));
    }

    @Test
    void avgTotalTime() {
        TourLog testTourLog = initTourLog();
        assertNotNull(testTourLog);
        //assertNotNull(tourLogDao.avgTotalTime(testTourLog.getTourLogID()));
        assertTrue(tourLogDao.delete(testTourLog.getTourLogID()));
    }

    @Test
    void countStars() {
        TourLog testTourLog = initTourLog();
        assertNotNull(testTourLog);
        assertNotNull(tourLogDao.countStars(testTourLog.getTourLogID(), testTourLog.getStars()));
        assertTrue(tourLogDao.delete(testTourLog.getTourLogID()));
    }

    @Test
    void countLevel() {
        TourLog testTourLog = initTourLog();
        assertNotNull(testTourLog);
        assertNotNull(tourLogDao.countLevel(testTourLog.getTourLogID(), testTourLog.getLevel()));
        assertTrue(tourLogDao.delete(testTourLog.getTourLogID()));
    }

    @Test
    void getAllTourLog() {
        TourLog testTourLog = initTourLog();
        assertNotNull(tourLogDao.getAllTourLog(testTourLog.getTourLogID()));
        assertTrue(tourLogDao.delete(testTourLog.getTourLogID()));
    }

    @Test
    void getItemById() {
        TourLog testTourLog = initTourLog();
        testTourLog.setTourLogID("get ID");
        assertNotNull(tourLogDao.insert(testTourLog));
        assertNotNull(tourLogDao.getItemById(testTourLog.getTourLogID()));
        assertTrue(tourLogDao.delete(testTourLog.getTourLogID()));
        assertNull(tourLogDao.getItemById(testTourLog.getTourLogID()));
    }


}