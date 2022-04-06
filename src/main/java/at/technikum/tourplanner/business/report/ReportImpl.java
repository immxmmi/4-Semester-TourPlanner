package at.technikum.tourplanner.business.report;

import at.technikum.tourplanner.business.config.ConfigurationManager;
import at.technikum.tourplanner.models.RouteImage;
import at.technikum.tourplanner.models.Tour;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;
import java.time.LocalDateTime;

public class ReportImpl implements Report{

    @Override
    public void createTourReport(Tour tour){

        // TimeStamp
        LocalDateTime time = LocalDateTime.now();
        // Create Document
        PDDocument newReport = new PDDocument();
        // Create Page
        PDPage currentPage = new PDPage();
        newReport.addPage(currentPage);


        try {
            // Start edit File
            PDPageContentStream write = new PDPageContentStream(newReport,currentPage);

            RouteImage currentRoutImage = tour.getRouteImage();
            // Route Image
            PDImageXObject imageXObject = PDImageXObject.createFromFile(currentRoutImage.getFilePath(),newReport);

            write.drawImage(imageXObject,50,50,currentRoutImage.getHeight(), currentRoutImage.getWidth());


            // SETTINGS
            write.addComment("TEST");
            write.setFont(PDType1Font.COURIER_BOLD,11);
            // START - TEXTING
            write.beginText();
            write.newLine();
            write.showText("Tour-Report: " + time);
            write.newLine();
            write.showText("Tour Name: " + tour.getTitle());
            write.newLine();

            // STOP - TEXTING
            write.endText();
            write.close();

            newReport.save(ConfigurationManager.getConfigPropertyValue("root")+"report-"+tour.getTitle()+time+".pdf");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
