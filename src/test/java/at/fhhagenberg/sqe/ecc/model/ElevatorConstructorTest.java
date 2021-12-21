package at.fhhagenberg.sqe.ecc.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorConstructorTest {
    @Test
    void testLegalParameters() {
        Elevator elev = new Elevator(2, 3);

        assertEquals(3, elev.getMaxPassengers());

        assertThrows(IndexOutOfBoundsException.class, () -> elev.isButtonPressed(-1));
        assertDoesNotThrow(() -> elev.isButtonPressed(0));
        assertDoesNotThrow(() -> elev.isButtonPressed(1));
        assertThrows(IndexOutOfBoundsException.class, () -> elev.isButtonPressed(2));

        assertThrows(IndexOutOfBoundsException.class, () -> elev.setButtonPressed(-1, true));
        assertDoesNotThrow(() -> elev.setButtonPressed(0, true));
        assertDoesNotThrow(() -> elev.setButtonPressed(1, true));
        assertThrows(IndexOutOfBoundsException.class, () -> elev.setButtonPressed(2, true));
    }

    @Test
    void testIllegalParameters() {
        assertThrows(IllegalArgumentException.class, () -> new Elevator(0, 1));
        assertThrows(IllegalArgumentException.class, () -> new Elevator(1, 0));
    }
}
