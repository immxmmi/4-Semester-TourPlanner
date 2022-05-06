package at.technikum.tourplanner.business.report;

import at.technikum.tourplanner.business.config.ConfigurationManager;
import at.technikum.tourplanner.database.fileServer.FileAccess;
import at.technikum.tourplanner.database.fileServer.FileAccessImpl;
import at.technikum.tourplanner.models.RouteImage;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.TourLog;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReportImpl implements Report {

    ConfigurationManager config = new ConfigurationManager();

    @Override
    public void createTourReport(Tour tour, ArrayList<TourLog> tourLogs) {

        String saveTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss"));
        // Create Document
        PDDocument newReport = saveTourReport(tour, tourLogs);
        String fileName = config.getReport() + "report-" + tour.getTitle() + saveTime + ".pdf";

        try {
            newReport.save(fileName);
            newReport.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public PDDocument saveTourReport(Tour tour, ArrayList<TourLog> tourLogs) {

        FileAccess fileAccess = new FileAccessImpl();
        fileAccess.createFolder("report");


        // TimeStamp
        String reportTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        String saveTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss"));


        // Create Document
        PDDocument newReport = new PDDocument();
        // Create Page
        PDPage currentPage = new PDPage();
        newReport.addPage(currentPage);


        try {
            // Start edit File
            PDPageContentStream write = new PDPageContentStream(newReport, currentPage);

            RouteImage currentRoutImage = tour.getRouteImage();
            // Route Image
            PDImageXObject imageXObject = PDImageXObject.createFromFile(currentRoutImage.getFilePath(), newReport);

            write.drawImage(imageXObject, 50, 100, currentRoutImage.getWidth(), currentRoutImage.getHeight());


            // START - TEXTING
            write.beginText();

            // SETTINGS
            write.addComment("TEST");
            write.setFont(PDType1Font.COURIER_BOLD, 13);
            write.setLeading(10f);
            write.newLineAtOffset(25, 700);



            write.newLine();
            write.showText("Tour-Report: " + reportTime);
            write.newLine();
            write.showText("Tour Tour ID: " + tour.getTourID());
            write.newLine();
            write.showText("Tour Name: " + tour.getTitle());
            write.newLine();
            write.showText("Tour To: " + tour.getTo());
            write.newLine();
            write.showText("Tour From: " + tour.getFrom());
            write.newLine();
            write.showText("Tour Distance: " + tour.getDistance());
            write.newLine();
            write.showText("Tour Transport: " + tour.getTransporter().toString());
            write.newLine();
            write.newLine();


            for (TourLog tourlog : tourLogs ){
                write.newLine();
                write.showText("TourLog-ID: " + tourlog.getTourLogID());
                write.newLine();
                write.showText("TourLog-DATE: " + tourlog.getDate());
                write.newLine();
                write.showText("TourLog-STARS: " + tourlog.getStars());
                write.newLine();
                write.showText("TourLog-LEVEL: " + tourlog.getLevel());
                write.newLine();
                write.showText("TourLog-COMMENT: " + tourlog.getComment());
                write.newLine();
                write.newLine();
                write.newLine();
            }

            // STOP - TEXTING
            write.endText();
            write.close();


            // newReport.save(fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return newReport;
    }

}
