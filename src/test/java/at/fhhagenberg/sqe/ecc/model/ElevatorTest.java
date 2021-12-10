package at.fhhagenberg.sqe.ecc.model;

import at.fhhagenberg.sqe.ecc.IElevatorController;
import at.fhhagenberg.sqe.ecc.IElevatorController.CommittedDirection;
import org.junit.jupiter.api.Test;

import static at.fhhagenberg.sqe.ecc.IElevatorController.DoorState;
import static org.junit.jupiter.api.Assertions.*;

public class ElevatorTest {

    Elevator elev = new Elevator(3);

    @Test
    void testDirection()
    {
        elev.setDirection(CommittedDirection.UNCOMMITED);
        assertEquals(CommittedDirection.UNCOMMITED, elev.getDirection());

        elev.setDirection(CommittedDirection.UP);
        assertEquals(CommittedDirection.UP, elev.getDirection());
    }

    @Test
    void testSpeed()
    {
        elev.setSpeed(4.5);
        assertEquals(4.5, elev.getSpeed());

        elev.setSpeed(-66);
        assertEquals(-66, elev.getSpeed());
    }

    @Test
    void testAcceleration()
    {
        elev.setAcceleration(27.5);
        assertEquals(27.5, elev.getAcceleration());

        elev.setAcceleration(-33.4);
        assertEquals(-33.4, elev.getAcceleration());
    }

    @Test
    void testDoorState()
    {
        elev.setDoorState(DoorState.OPENING);
        assertEquals(DoorState.OPENING, elev.getDoorState());

        elev.setDoorState(DoorState.CLOSING);
        assertEquals(DoorState.CLOSING, elev.getDoorState());
    }

    @Test
    void testCurrentFloor()
    {
        elev.setCurrentFloor(2);
        assertEquals(2, elev.getCurrentFloor());

        elev.setCurrentFloor(0);
        assertEquals(0, elev.getCurrentFloor());
    }

    @Test
    void testPosition()
    {
        elev.setPosition(55.25);
        assertEquals(55.25, elev.getPosition());
    }

    @Test
    void testCurrentPassengerWeight()
    {
        elev.setCurrentPassengerWeight(47.11);
        assertEquals(47.11, elev.getCurrentPassengerWeight());
    }

    @Test
    void testMaxPassengers()
    {
        elev.setMaxPassengers(8);
        assertEquals(8, elev.getMaxPassengers());
    }

    @Test
    void testTargetFloor()
    {
        elev.setTargetFloor(3);
        assertEquals(3, elev.getTargetFloor());
    }

    @Test
    void testButtonPressed()
    {
        elev.setButtonPressed(0, true);
        assertTrue(elev.isButtonPressed(0));
        elev.setButtonPressed(1, false);
        assertFalse(elev.isButtonPressed(1));
        elev.setButtonPressed(2, true);
        assertTrue(elev.isButtonPressed(2));

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> elev.setButtonPressed(-1, false));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> elev.isButtonPressed(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> elev.setButtonPressed(3, true));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> elev.isButtonPressed(3));
    }
}
