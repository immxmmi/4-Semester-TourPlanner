package at.technikum.tourplanner.business;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationManagerTest {

    @Test
    void getConfigPropertyValue() {
        assertTrue(ConfigurationManager.getConfigPropertyValue("version").equals("1.0V"));
        assertFalse(ConfigurationManager.getConfigPropertyValue("version").equals("t"));
    }

}