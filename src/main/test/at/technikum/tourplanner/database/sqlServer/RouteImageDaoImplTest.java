package at.technikum.tourplanner.database.sqlServer;

import at.technikum.tourplanner.database.dao.RouteImageDao;
import at.technikum.tourplanner.models.RouteImage;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("RouteImageDaoImplTest")
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
        RouteImage image = initImage();
        assertNotNull(image);
        assertTrue(imageDao.delete(image.getImageID()));
        assertNull(imageDao.getItemById(image.getImageID()));
        image = imageDao.insert(image);
        assertNotNull(image);
        assertTrue(imageDao.delete(image.getImageID()));
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
        image = imageDao.update(image);
         assertTrue(imageDao.delete(image.getImageID()));
         assertNull(imageDao.getItemById(image.getImageID()));
    }


}