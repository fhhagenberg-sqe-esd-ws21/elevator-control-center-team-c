package at.fhhagenberg.sqe.ecc.model;

import at.fhhagenberg.sqe.ecc.IElevatorWrapper;
import at.fhhagenberg.sqe.ecc.IElevatorWrapper.CommittedDirection;
import at.fhhagenberg.sqe.ecc.IElevatorWrapper.DoorState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EccModelUpdaterTest {

    @Mock
    private IElevatorWrapper controller;

    private Elevator elev;
    private Floor floor;

    @BeforeEach
    void setup() {
        elev = new Elevator(1, 3);
        floor = new Floor();
    }

    @Test
    void testUpdate() {
        when(controller.getCommittedDirection(0)).thenReturn(CommittedDirection.DOWN, CommittedDirection.UP);
        when(controller.getElevatorAccel(0)).thenReturn(2.0, 3.0);
        when(controller.getElevatorButton(0,0)).thenReturn(false, true);
        when(controller.getElevatorDoorStatus(0)).thenReturn(DoorState.OPEN, DoorState.CLOSING);
        when(controller.getElevatorFloor(0)).thenReturn(0, 0);
        when(controller.getElevatorPosition(0)).thenReturn(1.0, 0.5);
        when(controller.getElevatorSpeed(0)).thenReturn(3.0, 4.0);
        when(controller.getElevatorWeight(0)).thenReturn(140.3, 140.4);
        when(controller.getTarget(0)).thenReturn(0, 0);
        when(controller.getFloorButtonDown(0)).thenReturn(false, false);
        when(controller.getFloorButtonUp(0)).thenReturn(true, false);

        var model = new EccModel(Stream.of(elev).collect(Collectors.toList()), Stream.of(floor).collect(Collectors.toList()));
        var updater = new SynchronousEccModelUpdater(controller, model);

        updater.updateModel();

        assertEquals(CommittedDirection.DOWN, elev.getDirection());
        assertEquals(2.0, elev.getAcceleration());
        assertFalse(elev.isButtonPressed(0));
        assertEquals(DoorState.OPEN, elev.getDoorState());
        assertEquals(0, elev.getCurrentFloor());
        assertEquals(1.0, elev.getPosition());
        assertEquals(3.0, elev.getSpeed());
        assertEquals(140.3, elev.getCurrentPassengerWeight());
        assertEquals(0, elev.getTargetFloor());

        assertFalse(floor.isDownButtonPressed());
        assertTrue(floor.isUpButtonPressed());
    }

    // A model without elevators is possible but pointless

    @Test
    void testUpdate_withoutElevators() {
        when(controller.getFloorButtonDown(0)).thenReturn(false, false);
        when(controller.getFloorButtonUp(0)).thenReturn(true, false);

        var model = new EccModel(new ArrayList<>(), Stream.of(floor).collect(Collectors.toList()));
        var updater = new SynchronousEccModelUpdater(controller, model);

        updater.updateModel();

        assertFalse(floor.isDownButtonPressed());
        assertTrue(floor.isUpButtonPressed());
    }

    @Test
    void testUpdate_withoutFloors() {
        when(controller.getFloorButtonDown(0)).thenReturn(false, false);
        when(controller.getFloorButtonUp(0)).thenReturn(true, false);

        var model = new EccModel(new ArrayList<>(), Stream.of(floor).collect(Collectors.toList()));
        var updater = new SynchronousEccModelUpdater(controller, model);

        updater.updateModel();

        assertFalse(floor.isDownButtonPressed());
        assertTrue(floor.isUpButtonPressed());
    }
}
