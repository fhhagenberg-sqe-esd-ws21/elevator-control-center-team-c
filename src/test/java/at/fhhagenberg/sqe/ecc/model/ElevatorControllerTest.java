package at.fhhagenberg.sqe.ecc.model;

import at.fhhagenberg.sqe.ecc.ElevatorController;
import at.fhhagenberg.sqe.ecc.IElevatorController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sqelevator.IElevator;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static sqelevator.IElevator.*;

@ExtendWith(MockitoExtension.class)
public class ElevatorControllerTest {

    @Mock
    private IElevator mockedIElevator ;

    private ElevatorController elevatorController;

    private final double factorFeetToMeter = 0.3048;
    private final double factorPoundToKg = 0.45359237;

    @BeforeEach
    void Setup(){
        mockedIElevator = mock(IElevator.class);
        assertNotNull(mockedIElevator);
        elevatorController = new ElevatorController(mockedIElevator);
        assertNotNull(elevatorController);
    }

    @Test
    void testConversionGetElevatorAccel() throws RemoteException {
        when(mockedIElevator.getElevatorAccel(0)).thenReturn(0);
        assertEquals(0, elevatorController.getElevatorAccel(0));

        when(mockedIElevator.getElevatorAccel(1)).thenReturn(1);
        assertEquals(1*factorFeetToMeter, elevatorController.getElevatorAccel(1));
    }

    @Test
    void testConversionGetElevatorPosition() throws RemoteException {
        when(mockedIElevator.getElevatorPosition(0)).thenReturn(0);
        assertEquals(0, elevatorController.getElevatorPosition(0));

        when(mockedIElevator.getElevatorPosition(1)).thenReturn(1);
        assertEquals(1*factorFeetToMeter, elevatorController.getElevatorPosition(1));
    }

    @Test
    void testConversionGetElevatorSpeed() throws RemoteException {
        when(mockedIElevator.getElevatorSpeed(0)).thenReturn(0);
        assertEquals(0, elevatorController.getElevatorSpeed(0));

        when(mockedIElevator.getElevatorSpeed(1)).thenReturn(1);
        assertEquals(1*factorFeetToMeter, elevatorController.getElevatorAccel(1));
    }

    @Test
    void testConversionGetElevatorWeight() throws RemoteException {
        when(mockedIElevator.getElevatorWeight(0)).thenReturn(0);
        assertEquals(0, elevatorController.getElevatorWeight(0));

        when(mockedIElevator.getElevatorWeight(1)).thenReturn(1);
        assertEquals(1*factorPoundToKg, elevatorController.getElevatorWeight(1));
    }

    @Test
    void testConversionGetFloorHeight() throws RemoteException {
        when(mockedIElevator.getFloorHeight()).thenReturn(0);
        assertEquals(0, elevatorController.getFloorHeight());

        when(mockedIElevator.getFloorHeight()).thenReturn(1);
        assertEquals(1*factorFeetToMeter, elevatorController.getFloorHeight());
    }

    @Test
    void testEnumeratedGetCommittedDirection() throws RemoteException {
        when(mockedIElevator.getCommittedDirection(0)).thenReturn(ELEVATOR_DIRECTION_UP);
        assertEquals(IElevatorController.CommittedDirection.UP, elevatorController.getCommittedDirection(0));

        when(mockedIElevator.getCommittedDirection(1)).thenReturn(ELEVATOR_DIRECTION_DOWN);
        assertEquals(IElevatorController.CommittedDirection.DOWN, elevatorController.getCommittedDirection(1));

        when(mockedIElevator.getCommittedDirection(2)).thenReturn(ELEVATOR_DIRECTION_UNCOMMITTED);
        assertEquals(IElevatorController.CommittedDirection.UNCOMMITED, elevatorController.getCommittedDirection(2));
    }

    @Test
    void testEnumeratedSetCommittedDirection() throws RemoteException {
        doNothing().when(mockedIElevator).setCommittedDirection(0, ELEVATOR_DIRECTION_UP);
        verify(mockedIElevator).setCommittedDirection(0, ELEVATOR_DIRECTION_UP);
        elevatorController.setCommittedDirection(0, IElevatorController.CommittedDirection.UP);

        doNothing().when(mockedIElevator).setCommittedDirection(1, ELEVATOR_DIRECTION_DOWN);
        verify(mockedIElevator).setCommittedDirection(1, ELEVATOR_DIRECTION_DOWN);
        elevatorController.setCommittedDirection(1, IElevatorController.CommittedDirection.DOWN);

        doNothing().when(mockedIElevator).setCommittedDirection(2, ELEVATOR_DIRECTION_UNCOMMITTED);
        verify(mockedIElevator).setCommittedDirection(2, ELEVATOR_DIRECTION_UNCOMMITTED);
        elevatorController.setCommittedDirection(2, IElevatorController.CommittedDirection.UNCOMMITED);
    }

    @Test
    void testEnumeratedGetElevatorDoorStatus() throws RemoteException {
        when(mockedIElevator.getElevatorDoorStatus(0)).thenReturn(ELEVATOR_DOORS_OPEN);
        assertEquals(IElevatorController.DoorState.OPEN, elevatorController.getElevatorDoorStatus(0));

        when(mockedIElevator.getCommittedDirection(1)).thenReturn(ELEVATOR_DOORS_CLOSED);
        assertEquals(IElevatorController.DoorState.CLOSED, elevatorController.getElevatorDoorStatus(1));

        when(mockedIElevator.getCommittedDirection(2)).thenReturn(ELEVATOR_DOORS_OPENING);
        assertEquals(IElevatorController.DoorState.OPENING, elevatorController.getElevatorDoorStatus(2));

        when(mockedIElevator.getCommittedDirection(3)).thenReturn(ELEVATOR_DOORS_CLOSING);
        assertEquals(IElevatorController.DoorState.CLOSING, elevatorController.getElevatorDoorStatus(3));
    }
}
