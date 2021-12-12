package at.fhhagenberg.sqe.ecc.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ElevatorConstructorTest {
    @Test
    void testLegalParameters() {
        assertDoesNotThrow(() -> new Elevator(1, 1));
    }

    @Test
    void testIllegalParameters() {
        assertThrows(IllegalArgumentException.class, () -> new Elevator(0, 1));
        assertThrows(IllegalArgumentException.class, () -> new Elevator(1, 0));
    }
}
