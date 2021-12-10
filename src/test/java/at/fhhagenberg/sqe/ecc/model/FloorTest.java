package at.fhhagenberg.sqe.ecc.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FloorTest {

    Floor floor = new Floor();

    @Test
    void testUpButtonPressed()
    {
        floor.setUpButtonPressed(true);
        assertTrue(floor.isUpButtonPressed());
        floor.setUpButtonPressed(false);
        assertFalse(floor.isUpButtonPressed());
    }

    @Test
    void testDownButtonPressed()
    {
        floor.setDownButtonPressed(true);
        assertTrue(floor.isDownButtonPressed());
        floor.setDownButtonPressed(false);
        assertFalse(floor.isDownButtonPressed());
    }
}
