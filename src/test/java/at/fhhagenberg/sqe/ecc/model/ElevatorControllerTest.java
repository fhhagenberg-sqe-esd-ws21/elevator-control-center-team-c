package at.fhhagenberg.sqe.ecc.model;

import at.fhhagenberg.sqe.ecc.ElevatorController;
import at.fhhagenberg.sqe.ecc.IElevatorController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sqelevator.IElevator;

import java.rmi.Remote;
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
    void testGetAccelConversion() throws RemoteException {
        when(mockedIElevator.getElevatorAccel(0)).thenReturn(1);
        assertEquals(1*factorFeetToMeter, elevatorController.getElevatorAccel(0));
    }

    @Test
    void testGetPositionConversion() throws RemoteException {
        when(mockedIElevator.getElevatorPosition(0)).thenReturn(1);
        assertEquals(1*factorFeetToMeter, elevatorController.getElevatorPosition(0));
    }

    @Test
    void testGetSpeedConversion() throws RemoteException {
        when(mockedIElevator.getElevatorSpeed(0)).thenReturn(1);
        assertEquals(1*factorFeetToMeter, elevatorController.getElevatorSpeed(0));
    }

    @Test
    void testGetWeightConversion() throws RemoteException {
        when(mockedIElevator.getElevatorWeight(0)).thenReturn(1);
        assertEquals(1*factorPoundToKg, elevatorController.getElevatorWeight(0));
    }

    @Test
    void testGetFloorHeightConversion() throws RemoteException {
        when(mockedIElevator.getFloorHeight()).thenReturn(1);
        assertEquals(1*factorFeetToMeter, elevatorController.getFloorHeight());
    }

    @Test
    void testGetDirectionUP() throws RemoteException {
        when(mockedIElevator.getCommittedDirection(0)).thenReturn(ELEVATOR_DIRECTION_UP);
        assertEquals(IElevatorController.CommittedDirection.UP, elevatorController.getCommittedDirection(0));
    }

    @Test
    void testGetDirectionDOWN() throws RemoteException{
        when(mockedIElevator.getCommittedDirection(0)).thenReturn(ELEVATOR_DIRECTION_DOWN);
        assertEquals(IElevatorController.CommittedDirection.DOWN, elevatorController.getCommittedDirection(0));
    }

    @Test
    void testGetDirectionUNCOMMITED() throws RemoteException{
        when(mockedIElevator.getCommittedDirection(0)).thenReturn(ELEVATOR_DIRECTION_UNCOMMITTED);
        assertEquals(IElevatorController.CommittedDirection.UNCOMMITED, elevatorController.getCommittedDirection(0));
    }

    @Test
    void testSetDirectionUP() throws RemoteException {
        elevatorController.setCommittedDirection(0, IElevatorController.CommittedDirection.UP);
        verify(mockedIElevator).setCommittedDirection(0, ELEVATOR_DIRECTION_UP);
    }

    @Test
    void testSetDirectionDOWN() throws RemoteException{
        elevatorController.setCommittedDirection(0, IElevatorController.CommittedDirection.DOWN);
        verify(mockedIElevator).setCommittedDirection(0, ELEVATOR_DIRECTION_DOWN);
    }

    @Test
    void testSetDirectionUNCOMMITED() throws RemoteException{
        elevatorController.setCommittedDirection(0, IElevatorController.CommittedDirection.UNCOMMITED);
        verify(mockedIElevator).setCommittedDirection(0, ELEVATOR_DIRECTION_UNCOMMITTED);
    }

    @Test
    void testGetDoorStateOPEN() throws RemoteException {
        when(mockedIElevator.getElevatorDoorStatus(0)).thenReturn(ELEVATOR_DOORS_OPEN);
        assertEquals(IElevatorController.DoorState.OPEN, elevatorController.getElevatorDoorStatus(0));
    }

    @Test
    void testGetDoorStateCLOSED() throws RemoteException{
        when(mockedIElevator.getElevatorDoorStatus(0)).thenReturn(ELEVATOR_DOORS_CLOSED);
        assertEquals(IElevatorController.DoorState.CLOSED, elevatorController.getElevatorDoorStatus(0));
    }

    @Test
    void testGetDoorStateOPENING() throws RemoteException{
        when(mockedIElevator.getElevatorDoorStatus(0)).thenReturn(ELEVATOR_DOORS_OPENING);
        assertEquals(IElevatorController.DoorState.OPENING, elevatorController.getElevatorDoorStatus(0));
    }

    @Test
    void testGetDoorStateCLOSING() throws RemoteException{
        when(mockedIElevator.getElevatorDoorStatus(0)).thenReturn(ELEVATOR_DOORS_CLOSING);
        assertEquals(IElevatorController.DoorState.CLOSING, elevatorController.getElevatorDoorStatus(0));
    }
}
