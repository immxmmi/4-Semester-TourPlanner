package at.technikum.tourplanner.business.report;

import at.technikum.tourplanner.models.*;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ReportImplTest {

    private  Report report = new ReportImpl();
    @Test
    void createTourReport() {
       ArrayList<TourLog> tourLogs = new ArrayList<>();
       tourLogs.add(TourLog.builder()
                       .tourID("test")
                       .tourLogID("test")
                       .totalTime(2.3)
                       .stars(Stars.one)
                       .level(Level.normal)
                       .date(new Date())
                       .comment("")
               .build());


       //assertNotNull(report.createTourReport(Tour.builder()
       //                .date(null)
       //                .transporter(Transporter.Walk)
       //                .to("test")
       //                .from("test")
       //                .title("test")
       //                .time(null)
       //                .distance(2.2)
       //                .routeImage(RouteImage.builder().data(null).build())
       //                .tourID("3wdfwefd")
       //        .build(), tourLogs));

    }
}