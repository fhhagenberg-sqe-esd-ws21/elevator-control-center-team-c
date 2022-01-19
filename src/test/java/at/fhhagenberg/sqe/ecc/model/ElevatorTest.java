package at.fhhagenberg.sqe.ecc.model;

import at.fhhagenberg.sqe.ecc.IElevatorWrapper.CommittedDirection;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    void testDirectionProperty() {
        var directionProp = new SimpleObjectProperty<CommittedDirection>();
        directionProp.bind(elev.directionProperty());

        elev.setDirection(CommittedDirection.UP);
        assertEquals(CommittedDirection.UP, directionProp.get());
    }

    @Test
    void testSpeedProperty(){
        var speedProp = new SimpleDoubleProperty();
        speedProp.bind(elev.speedProperty());

        elev.setSpeed(0.1);
        assertEquals(0.1, speedProp.get());
    }

    @Test
    void testAccelProperty(){
        var accelProp = new SimpleDoubleProperty();
        accelProp.bind(elev.accelerationProperty());

        elev.setAcceleration(0.1);
        assertEquals(0.1, accelProp.get());
    }

    @Test
    void testDoorStateProperty(){
        var doorProp = new SimpleObjectProperty<DoorState>();
        doorProp.bind(elev.doorStateProperty());

        elev.setDoorState(DoorState.OPENING);
        assertEquals(DoorState.OPENING, doorProp.get());
    }

    @Test
    void testCurrentFloorProperty(){
        var currFloorProp = new SimpleIntegerProperty();
        currFloorProp.bind(elev.currentFloorProperty());

        elev.setCurrentFloor(2);
        assertEquals(2, currFloorProp.get());
    }

    @Test
    void testPositionProperty(){
        var positionProp = new SimpleDoubleProperty();
        positionProp.bind(elev.positionProperty());

        elev.setPosition(3.7);
        assertEquals(3.7, positionProp.get());
    }

    @Test
    void testCurrentPassengerWeightProperty(){
        var currPassengerProp = new SimpleDoubleProperty();
        currPassengerProp.bind(elev.currentPassengerWeightProperty());

        elev.setCurrentPassengerWeight(30.1);
        assertEquals(30.1, currPassengerProp.get());
    }

    @Test
    void testTargetFloorProperty(){
        var targetFloorProp = new SimpleIntegerProperty();
        targetFloorProp.bind(elev.targetFloorProperty());

        elev.setTargetFloor(0);
        assertEquals(0, targetFloorProp.get());
    }

    @Test
    void testButtonsPressed(){
        var buttonsProp = new SimpleObjectProperty<List<BooleanProperty>>();
        buttonsProp.bind(elev.buttonsPressedProperty());

        elev.setButtonPressed(1, true);
        assertTrue(buttonsProp.get().get(1).get());
    }
}
