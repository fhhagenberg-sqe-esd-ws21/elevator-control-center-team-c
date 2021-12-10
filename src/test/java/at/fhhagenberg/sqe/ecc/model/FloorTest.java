package at.fhhagenberg.sqe.ecc.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FloorTest {

    Floor floor = new Floor();

    @Test
    void testUpButtonPressed()
    {
        floor.setUpButtonPressed(true);

        assertTrue(floor.isUpButtonPressed());
    }

    @Test
    void testDownButtonPressed()
    {
        floor.setDownButtonPressed(false);

        assertFalse(floor.isDownButtonPressed());
    }
}
