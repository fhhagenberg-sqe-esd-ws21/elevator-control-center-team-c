package at.fhhagenberg.sqe.ecc.model;

import at.fhhagenberg.sqe.ecc.IElevatorWrapper.CommittedDirection;
import org.junit.jupiter.api.Test;

import static at.fhhagenberg.sqe.ecc.IElevatorWrapper.DoorState;
import static org.junit.jupiter.api.Assertions.*;

class ElevatorTest {

    Elevator elev = new Elevator(3, 5);

    @Test
    void testDirection()
    {
        elev.setDirection(CommittedDirection.UNCOMMITTED);
        assertEquals(CommittedDirection.UNCOMMITTED, elev.getDirection());

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
        elev.setCurrentFloor(0);
        assertEquals(0, elev.getCurrentFloor());
        elev.setCurrentFloor(2);
        assertEquals(2, elev.getCurrentFloor());

        assertThrows(IllegalArgumentException.class, () -> elev.setCurrentFloor(-1));
        assertThrows(IllegalArgumentException.class, () -> elev.setCurrentFloor(3));
    }

    @Test
    void testPosition()
    {
        elev.setPosition(0);
        assertEquals(0, elev.getPosition());
        elev.setPosition(55.25);
        assertEquals(55.25, elev.getPosition());

        assertThrows(IllegalArgumentException.class, () -> elev.setPosition(-0.001));
    }

    @Test
    void testCurrentPassengerWeight()
    {
        elev.setCurrentPassengerWeight(0);
        assertEquals(0, elev.getCurrentPassengerWeight());
        elev.setCurrentPassengerWeight(47.11);
        assertEquals(47.11, elev.getCurrentPassengerWeight());

        assertThrows(IllegalArgumentException.class, () -> elev.setCurrentPassengerWeight(-0.001));
    }

    @Test
    void testMaxPassengers()
    {
        assertEquals(5, elev.getMaxPassengers());
    }

    @Test
    void testNumOfFloors(){ assertEquals(3, elev.getNumOfFloors()); }

    @Test
    void testTargetFloor()
    {
        elev.setTargetFloor(0);
        assertEquals(0, elev.getTargetFloor());
        elev.setTargetFloor(2);
        assertEquals(2, elev.getTargetFloor());

        assertThrows(IllegalArgumentException.class, () -> elev.setTargetFloor(-1));
        assertThrows(IllegalArgumentException.class, () -> elev.setTargetFloor(3));
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
    }


    // for code coverage

    @Test
    void testDirectionProperty(){ assertNotNull(elev.directionProperty());}

    @Test
    void testSpeedProperty(){ assertNotNull(elev.speedProperty());}

    @Test
    void testAccelProperty(){ assertNotNull(elev.accelerationProperty());}

    @Test
    void testDoorStateProperty(){ assertNotNull(elev.doorStateProperty());}

    @Test
    void testCurrentFloorProperty(){ assertNotNull(elev.currentFloorProperty());}

    @Test
    void testPositionProperty(){ assertNotNull(elev.positionProperty());}

    @Test
    void testCurrentPassengerWeightProperty(){ assertNotNull(elev.currentPassengerWeightProperty());}

    @Test
    void testTargetFloorProperty(){ assertNotNull(elev.targetFloorProperty());}

    @Test
    void testButtonsPressed(){ assertNotNull(elev.buttonsPressedProperty());}
}
