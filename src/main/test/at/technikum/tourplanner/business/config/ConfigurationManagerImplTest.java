package at.technikum.tourplanner.business.config;

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
    void getDbUrl() {
        assertNotNull(config.getDbUrl());
    }

    @Test
    void getDbDatabase() {
        assertNotNull(config.getDbDatabase());
    }

    @Test
    void getDbUsername() {
        assertNotNull(config.getDbUsername());
    }

    @Test
    void getDbPassword() {
        assertNotNull(config.getDbPassword());
    }

    @Test
    void getDbPort() {
        assertNotNull(config.getDbPort());
    }

    @Test
    void getStageWidth() {
        assertNotNull(config.getStageWidth());
    }

    @Test
    void getStageHeight() {
        assertNotNull(config.getStageHeight());
    }

    @Test
    void getMainPage() {
        assertNotNull(config.getMainPage());
    }

    @Test
    void getErrorPage(){
    assertNotNull(config.getErrorPage());
    }

    @Test
    void getMapQuestID() {
        assertNotNull(config.getMapQuestID());
    }

    @Test
    void getRoot() {
        assertNotNull(config.getRoot());
    }

    @Test
    void getImage() {
        assertNotNull(config.getImage());
    }

    @Test
    void getReport() {
        assertNotNull(config.getReport());
    }
}