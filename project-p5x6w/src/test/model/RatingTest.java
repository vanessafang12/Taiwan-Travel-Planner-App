package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Test Rating Class
public class RatingTest {

    private Rating rating;

    @BeforeEach
    void runBefore() {
        rating = new Rating();
    }

    @Test
    void testChangeRating() {
        rating.changeRating(5);
        assertEquals(5, rating.getRating());
        rating.changeRating(7);
        assertEquals(6, rating.getRating());
    }
}
