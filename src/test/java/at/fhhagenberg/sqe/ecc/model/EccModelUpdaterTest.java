package at.fhhagenberg.sqe.ecc.model;

import at.fhhagenberg.sqe.ecc.IElevatorController;
import at.fhhagenberg.sqe.ecc.IElevatorController.CommittedDirection;
import at.fhhagenberg.sqe.ecc.IElevatorController.DoorState;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EccModelUpdaterTest {

    @Mock
    private IElevatorController controller;
    @Mock
    private EccModel model;
    @Mock
    private Elevator elev;
    @Mock
    private Floor floor;

    @BeforeEach
    void setup() {
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

        when(model.getNumberOfElevators()).thenReturn(1);
        when(model.getNumberOfFloors()).thenReturn(1);
        when(model.getElevator(0)).thenReturn(elev);
        when(model.getFloor(0)).thenReturn(floor);
    }

    @Test
    void testSingleUpdate() {
        var updater = new EccModelUpdater(controller, model);

        updater.updateModel();

        verify(model).getElevator(0);
        verify(model).getFloor(0);

        verify(elev).setDirection(CommittedDirection.DOWN);
        verify(elev).setAcceleration(2.0);
        verify(elev).setButtonPressed(0, false);
        verify(elev).setDoorState(DoorState.OPEN);
        verify(elev).setCurrentFloor(0);
        verify(elev).setPosition(1.0);
        verify(elev).setSpeed(3.0);
        verify(elev).setCurrentPassengerWeight(140.3);
        verify(elev).setTargetFloor(0);

        verify(floor).setDownButtonPressed(false);
        verify(floor).setUpButtonPressed(true);
    }
}
