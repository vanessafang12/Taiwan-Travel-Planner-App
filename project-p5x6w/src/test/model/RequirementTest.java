package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Test Requirement Class
public class RequirementTest {

    private Requirement requirement;

    @BeforeEach
    void runBefore() {
        requirement = new Requirement("Taipei", "Park", 0);
    }

    @Test
    void testConstructor() {
        assertEquals("Taipei", requirement.getCity());
        assertEquals("Park", requirement.getType());
        assertEquals(0, requirement.getSpedning());
    }

    @Test
    void testGetCity() {
        assertEquals("Taipei", requirement.getCity());
    }

    @Test
    void testGetType() {
        assertEquals("Park", requirement.getType());
    }

    @Test
    void testGetSpending() {
        assertEquals(0, requirement.getSpedning());
    }
}
