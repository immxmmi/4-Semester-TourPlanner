package at.technikum.tourplanner.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationManagerImplTest {

    ConfigurationManager config = new ConfigurationManagerImpl();
    @Test
    void getConfigPropertyValue() {
        assertTrue(config.getVersion().equals("1.0V"));
        assertFalse(config.getVersion().equals("t"));
    }


    @Test
    void getVersion() {

        assertNotNull(config.getVersion());
    }

    @Test
    void testGetDbUrl() {
        assertNotNull(config.getDbUrl());
    }

    @Test
    void testGetDbDatabase() {
        assertNotNull(config.getDbDatabase());
    }

    @Test
    void testGetDbUsername() {
        assertNotNull(config.getDbUsername());
    }

    @Test
    void testGetDbPassword() {
        assertNotNull(config.getDbPassword());
    }

    @Test
    void testGetDbPort() {
        assertNotNull(config.getDbPort());
    }

    @Test
    void testGetMainPage() {
        assertNotNull(config.getMainPage());
    }

    @Test
    void testGetErrorPage() {
        assertNotNull(config.getErrorPage());
    }

    @Test
    void testGetMapQuestID() {
        assertNotNull(config.getMapQuestID());
    }

    @Test
    void testGetImage() {
        assertNotNull(config.getImage());
    }

    @Test
    void getImageHeight() {
        assertNotNull(config.getImageHeight());
    }

    @Test
    void getImageWidth() {
        assertNotNull(config.getImageWidth());
    }

    @Test
    void getCreateTourHeight() {
        assertNotNull(config.getEditTourHeight());
    }

    @Test
    void getCreateTourWidth() {
        assertNotNull(config.getCreateTourWidth());
    }

    @Test
    void getEditTourWidth() {
        assertNotNull(config.getEditTourWidth());
    }

    @Test
    void getEditTourHeight() {
        assertNotNull(config.getEditTourHeight());
    }

    @Test
    void getEditTourLogWidth() {
        assertNotNull(config.getEditTourLogWidth());
    }

    @Test
    void getEditTourLogHeight() {
        assertNotNull(config.getEditTourHeight());
    }

    @Test
    void testGetRoot() {
        assertNotNull(config.getRoot());
    }

    @Test
    void testGetReport() {
        assertNotNull(config.getReport());
    }

    @Test
    void getShowTourHeight() {
        assertNotNull(config.getShowTourHeight());
    }

    @Test
    void getShowTourWidth() {
        assertNotNull(config.getShowTourWidth());
    }

    @Test
    void getListTourHeight() {
        assertNotNull(config.getListTourHeight());
    }

    @Test
    void getListTourWidth() {
        assertNotNull(config.getListTourWidth());
    }

    @Test
    void getErrorWidth() {
        assertNotNull(config.getErrorWidth());
    }

    @Test
    void testGetStageWidth() {
        assertNotNull(config.getStageWidth());
    }

    @Test
    void testGetStageHeight() {
        assertNotNull(config.getStageHeight());
    }
}