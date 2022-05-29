package at.technikum.tourplanner.business.tour;

import at.technikum.tourplanner.models.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class TourLogServiceImplTest {

    private TourLogService tourLogService = new TourLogServiceImpl();

    private TourLog tourLog = this.initTourLog();

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
    void saveTourLog() {
        TourLog tourLog = this.tourLog;
        assertTrue(tourLogService.saveTourLog(tourLog));
        assertTrue(tourLogService.deleteTourLog(tourLog.getTourLogID()));
        assertNull(tourLogService.getTourLog(tourLog.getTourLogID()));
    }

    @Test
    void deleteTourLog() {
        TourLog tourLog = this.tourLog;
        tourLog.setTourLogID("DELETE-TEST");
        assertTrue(tourLogService.deleteTourLog("TEST-DELETE"));
        assertNotNull(tourLogService.saveTourLog(tourLog));
        assertTrue(tourLogService.deleteTourLog(tourLog.getTourLogID()));
    }

    @Test
    void getTourLog() {
        TourLog tourLog = this.tourLog;
        tourLog.setTourLogID("GET YOUR TEST");
        assertNotNull(tourLogService.saveTourLog(tourLog));
        assertNotNull(tourLogService.getTourLog(tourLog.getTourLogID()));
        assertTrue(tourLogService.deleteTourLog(tourLog.getTourLogID()));
        assertNull(tourLogService.getTourLog(tourLog.getTourLogID()));
    }

    @Test
    void updateTourLog() {
        TourLog tourLog = this.tourLog;
        tourLog.setTourLogID("UPDATE TOURLOD TEST");
        assertNotNull(tourLogService.saveTourLog(tourLog));
        assertNotNull(tourLogService.getTourLog(tourLog.getTourLogID()));
        assertFalse(tourLog.getComment().equals("TEST UPDATE"));
        tourLog.setComment("TEST - UPDATE");
        assertTrue(tourLogService.deleteTourLog(tourLog.getTourLogID()));
    }

    @Test
    void getAllTourLogs() {
        TourLog tourLog = this.tourLog;
        assertNotNull(tourLogService.getAllTourLogs(tourLog.getTourLogID()));
        assertTrue(tourLogService.deleteTourLog(tourLog.getTourLogID()));
    }

    @Test
    void countTourLogsFromTour() {
        TourLog tourLog = this.tourLog;
        assertNotNull(tourLog);
        assertNotNull(tourLogService.countTourLogsFromTour(tourLog.getTourLogID()));
        assertTrue(tourLogService.deleteTourLog(tourLog.getTourLogID()));
    }

    @Test
    void avgTotalTimeFromTour() {
        TourLog tourLog = this.tourLog;
        assertNotNull(tourLog);
        //assertNotNull(tourLogService.avgTotalTimeFromTour(tourLog.getTourLogID()));
        assertTrue(tourLogService.deleteTourLog(tourLog.getTourLogID()));
    }

    @Test
    void countLevel() {
        TourLog tourLog = this.tourLog;
        assertNotNull(tourLog);
        assertNotNull(tourLogService.countLevelNormalFromTour(tourLog.getTourLogID()));
        assertTrue(tourLogService.deleteTourLog(tourLog.getTourLogID()));
    }

    @Test
    void countStars() {
        TourLog tourLog = this.tourLog;
        assertNotNull(tourLog);
        assertNotNull(tourLogService.countStarsOneFromTour(tourLog.getTourLogID()));
        assertTrue(tourLogService.deleteTourLog(tourLog.getTourLogID()));
    }

}