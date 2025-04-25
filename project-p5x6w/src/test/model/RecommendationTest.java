package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

//Test Recommendation Class
public class RecommendationTest {

    private Recommendation recommendation;
    private Requirement require;
    private Requirement require2;
    private Requirement require3;
    private Location location;
    private Location location2;

    @BeforeEach
    void runBefore() {
        recommendation = new Recommendation();
    }

    @Test
    void testContructor() {
        assertNull(recommendation.getAlreadyList());
    } 

    @Test
    void testCheckLocation() {
        require = new Requirement("Taipei", "Landmark", 0);
        require2 = new Requirement("Taipei", "Landmark", 500);
        assertEquals("Taipei Dome", recommendation.checkLocation(require));
        assertEquals("Taipei 101", recommendation.checkLocation(require2));
    }

    @Test
    void testCheckLocationRequirement() {
        require = new Requirement("NewTaipei", "Museum", 0);
        require2 = new Requirement("NewTaipei", "Museum", 250);
        require3 = new Requirement("NewTaipei", "Museum", 250);
        assertNull(recommendation.checkLocation(require)); 
        assertEquals("Ju Ming Museum", recommendation.checkLocation(require2)); 
        assertNull(recommendation.checkLocation(require3));
        List<String> getAlreadyList = recommendation.getAlreadyList();
        assertTrue(getAlreadyList.contains("Ju Ming Museum"));
    }

    @Test
    void testVisitedLocation() {
        location = new Location("737 Street", "Taipei", "Night Market", 150);
        location2 = new Location("737 Street", "Taipei", "Night Market", 100);
        recommendation.visitedLocation(location);
        recommendation.visitedLocation(location2);
        assertTrue(recommendation.getAlreadyList().contains("737 Street"));
        assertEquals(1, recommendation.getAlreadyList().size());
    } 

    @Test
    void testReset() {
        recommendation.reset();
        List<String> testList = recommendation.getAlreadyList();
        assertNull(testList); 
    }
}
