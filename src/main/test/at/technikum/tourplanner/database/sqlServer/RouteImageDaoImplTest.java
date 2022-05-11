package at.technikum.tourplanner.database.sqlServer;

import at.technikum.tourplanner.database.dao.RouteImageDao;
import at.technikum.tourplanner.models.RouteImage;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

class RouteImageDaoImplTest {

        RouteImageDao imageDao = new RouteImageDaoImpl();

    @Before
    private RouteImage initImage(){

        return RouteImage.builder()
                .imageID("test")
                .width(500)
                .height(500)
                .zoom(11)
                .from("TEST1")
                .to("TEST2")
                .downloadURL("www.TEST")
                .local(false)
                .filePath("/test/test")
                .build();
    }

    @Test
    void getItemById() {
        RouteImage image = initImage();
        assertNotNull(image);
        assertTrue(imageDao.delete(image.getImageID()));
        image = imageDao.insert(image);
        assertNotNull(image);
        assertNotNull(imageDao.getItemById(image.getImageID()));
        assertTrue(imageDao.delete(image.getImageID()));
        assertNull(imageDao.getItemById(image.getImageID()));
    }

    @Test
    void insert() {
    }

    @Test
    void update() {
        RouteImage image = initImage();
        assertNotNull(image);
        assertTrue(imageDao.delete(image.getImageID()));
        image = imageDao.insert(image);
        image = imageDao.getItemById(image.getImageID());
        assertNotNull(image);
        image.setFrom("Wien");
        //assertTrue(image.getFrom().equals("TEST1"));
        image = imageDao.update(image);
        // assertFalse(image.getFrom().equals("TEST1"));
        // assertTrue(image.getFrom().equals("Wien"));

         assertTrue(imageDao.delete(image.getImageID()));
         assertNull(imageDao.getItemById(image.getImageID()));
    }

    @Test
    void buildClass() {
    }

    @Test
    void updateImageData() {
    }
}