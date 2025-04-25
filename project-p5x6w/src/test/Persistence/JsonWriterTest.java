package persistence;

import model.Location;
import model.Recommendation;
import model.Rating;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Test for JasonWriter
//Part of the code from sample code (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo) 
public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyRecommendation() {
        try {
            Recommendation r = new Recommendation();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRecommendation.json");
            writer.open();
            writer.write(r);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyRecommendation.json");
            r = reader.read();
            assertNull(r.getAlreadyList());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } 
    }

    @Test
    void testWriterEmptyRating() {
        try {
            Rating r = new Rating();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRating.json");
            writer.open();
            writer.writeRating(r);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyRating.json");
            r = reader.check();
            assertEquals(0, r.getRating());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } 
    }

    @Test
    void testWriterGeneralRecommendation() {
        try {
            Recommendation r = new Recommendation();
            r.visitedLocation(new Location("Taipei Dome", "Taipei", "Landmark", 0));
            r.visitedLocation(new Location("Taipei 101", "Taipei", "Landmark", 250));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralRecommendation.json");
            writer.open();
            writer.write(r);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralRecommendation.json");
            r = reader.read(); 
            List<String> location = r.getAlreadyList();
            assertEquals(2, location.size());
            assertTrue(location.contains("Taipei Dome"));
            assertTrue(location.contains("Taipei 101"));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralRating() {
        try {
            Rating r = new Rating();
            r.changeRating(10);
            r.changeRating(5);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralRating.json");
            writer.open();
            writer.writeRating(r);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralRating.json");
            r = reader.check();
            assertEquals(7, r.getRating());
            
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
