package at.technikum.tourplanner.business.report;

import at.technikum.tourplanner.business.tour.TourService;
import at.technikum.tourplanner.business.tour.TourServiceImpl;
import at.technikum.tourplanner.config.ConfigurationManager;
import at.technikum.tourplanner.config.ConfigurationManagerImpl;
import at.technikum.tourplanner.database.fileServer.FileAccess;
import at.technikum.tourplanner.database.fileServer.FileAccessImpl;
import at.technikum.tourplanner.models.RouteImage;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.TourLog;
import at.technikum.tourplanner.models.TourStatistics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReportImpl implements Report {

    //LOGGER
    private final static Logger log = LogManager.getLogger(ReportImpl.class.getName());
    //CONFIG
    ConfigurationManager config = new ConfigurationManagerImpl();

    //CREATE AND SAVE Report IN PROJECT FOLDER
    @Override
    public void saveTourReport(Tour tour, ArrayList<TourLog> tourLogs) {
        FileAccess fileAccess = new FileAccessImpl();
        fileAccess.createFolder("report");
        String saveTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss"));
        // Create Document
        PDDocument newReport = createTourReport(tour, tourLogs);
        String fileName = config.getReport() + "report-" + tour.getTitle() + saveTime + ".pdf";

        try {
            newReport.save(fileName);
            newReport.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    //CREATE PDF DOC AND RETURN IT
    @Override
    public PDDocument createTourReport(Tour tour, ArrayList<TourLog> tourLogs) {

        TourService tourService = new TourServiceImpl();
        TourStatistics tourStatistics = tourService.loadTourStatistics(tour.getTourID());

        // TimeStamp
        String reportTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        // Create Document
        PDDocument newReport = new PDDocument();
        // Create Page
        PDPage currentPage = new PDPage();
        newReport.addPage(currentPage);


        try {
            // Start edit File
            PDPageContentStream write = new PDPageContentStream(newReport, currentPage);

            write.beginText();

            write.setFont(PDType1Font.COURIER_BOLD, 24);
            write.setLeading(10f);
            write.newLineAtOffset(195, 750);
            write.showText("TOUR REPORT CARD");

            write.endText();

            RouteImage currentRoutImage = tour.getRouteImage();
            // Route Image
            // PDImageXObject imageXObject = PDImageXObject.createFromFile(currentRoutImage.getFilePath(), newReport);
            PDImageXObject imageXObject = PDImageXObject.createFromByteArray(newReport, currentRoutImage.getData(), "IMG");
            write.drawImage(imageXObject, 50, 425, currentRoutImage.getWidth(), currentRoutImage.getHeight());

            // START - TEXTING
            write.beginText();

            // // SETTINGS
            write.addComment("TEST");
            write.setFont(PDType1Font.TIMES_ROMAN, 16);
            write.setLeading(15f);
            write.newLineAtOffset(25, 375);
            //
            write.newLine();
            write.showText("On this report you can view the statistical data of the tour. This Tour is titled " + tour.getTitle());
            write.newLine();
            write.showText("The starting point is " + tour.getFrom());
            write.showText(" to destination " + tour.getTo());
            write.newLine();
            write.showText("The distance is  " + tour.getDistance());
            write.showText(" km");
            write.newLine();
            write.showText("Below you can see a summary and further information of the tour.");
            //
            write.newLine();
            write.showText("Create Report Date: " + reportTime);
            //write.newLine();
            //write.showText("Tour ID: " + tour.getTourID());
            write.newLine();
            write.showText("Date: " + tour.getDate());
            write.newLine();
            write.showText("Title: " + tour.getTitle());
            write.newLine();
            write.showText("From: " + tour.getFrom());
            write.newLine();
            write.showText("To: " + tour.getTo());
            write.newLine();
            write.showText("Distance: " + tour.getDistance());
            write.newLine();
            write.showText("Transport: " + tour.getTransporter().toString());
            write.newLine();
            write.showText("Description: " + tour.getDescription());
            write.newLine();
            write.showText("Average distance: " + tourStatistics.getAvgDistance());

            for (TourLog tourlog : tourLogs) {
                //write.newLine();
                //write.showText("TourLog-ID: " + tourlog.getTourLogID());
                write.newLine();
                write.showText("Date: " + tourlog.getDate());
                write.newLine();
                write.showText("Stars: " + tourlog.getStars());
                write.newLine();
                write.showText("Level: " + tourlog.getLevel());
                write.newLine();
                write.showText("Comment: " + tourlog.getComment());
                write.newLine();
                write.newLine();
                write.newLine();
            }

            // // STOP - TEXTING
            write.endText();
            write.close();

            // // newReport.save(fileName);

            //   newReport.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newReport;
    }


}
