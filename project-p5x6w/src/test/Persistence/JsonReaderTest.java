package persistence;

import model.Recommendation;
import model.Rating;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Test for JsonReader
//Part of the code from sample code (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo) 
public class JsonReaderTest {

    //Code from Sample
    //Test if there is no existing file 
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read(); 
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyRecommendation() { 
        JsonReader reader = new JsonReader("./data/testReaderEmptyRecommendation.json");
        try {
            Recommendation r = reader.read();
            assertNull(r.getAlreadyList());
        } catch (IOException e) {
            fail("Read Fail");
        }
    }

    @Test
    void testReaderNoVisitedLocations() { 
        JsonReader reader = new JsonReader("./data/testReaderNoVisitedLocations.json");
        try {
            Recommendation r = reader.read();
            assertNull(r.getAlreadyList());
        } catch (IOException e) {
            fail("Read Fail");
        }
    }
 
    @Test 
    void testReaderEmptyRating() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyRating.json");
        try {
            Rating r = reader.check();
            assertEquals(0, r.getRating());
        } catch (IOException e) {
            fail("Read Fail");
        }
    }

    @Test
    void testReaderGeneralRecommendation() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralRecommendation.json");
        try {  
            Recommendation r = new Recommendation();
            r = reader.read(); 
            List<String> location = r.getAlreadyList(); 
            assertTrue(location.contains("Taipei 101"));
            assertTrue(location.contains("Raohe Night Market"));
            assertEquals(2, location.size());
        } catch (IOException e) { 
            fail("Couldn't read from file");
        }
    } 

    @Test
    void testReaderGeneralRating() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralRating.json");
        try {  
            Rating r = reader.check(); 
            assertEquals(9, r.getRating());
        } catch (IOException e) { 
            fail("Couldn't read from file");
        }
    } 

}
