package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Test Location Class
public class LocationTest {
    
    private Location location;
    
    @BeforeEach
    void runBefore() {
        location = new Location("Test","Taipei", "Landmark", 30);
    }

    @Test
    void testGetCity() {
        assertEquals("Taipei", location.getCity());
    }

    @Test
    void testGetType() {
        assertEquals("Landmark", location.getType());
    }

    @Test
    void testGetName() {
        assertEquals("Test", location.getName());
    } 

    @Test
    void testGetSpending() {
        assertEquals(30, location.getSpending());
    }
}
