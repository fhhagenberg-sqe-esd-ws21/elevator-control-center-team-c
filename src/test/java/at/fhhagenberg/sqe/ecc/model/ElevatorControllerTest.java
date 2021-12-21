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
        double factorPoundToKg = 0.45359237;
        assertEquals(1* factorPoundToKg, elevatorController.getElevatorWeight(0));
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


    // additional tests for code coverage

    @Test
    void testGetElevatorButton() throws RemoteException {
        when(mockedIElevator.getElevatorButton(0, 1)).thenReturn(true);
        when(mockedIElevator.getElevatorButton(0, 2)).thenReturn(false);

        assertTrue(elevatorController.getElevatorButton(0, 1));
        assertFalse(elevatorController.getElevatorButton(0, 2));
    }

    @Test
    void testGetCurrentFloor() throws RemoteException{
        when(mockedIElevator.getElevatorFloor(0)).thenReturn(0);
        when(mockedIElevator.getElevatorFloor(1)).thenReturn(2);

        assertEquals(0, elevatorController.getElevatorFloor(0));
        assertEquals(2, elevatorController.getElevatorFloor(1));
    }

    @Test
    void testGetNumberOfElevators() throws RemoteException{
        when(mockedIElevator.getElevatorNum()).thenReturn(2);
        assertEquals(2, elevatorController.getElevatorNum());
    }

    @Test
    void testGetCapacity() throws RemoteException{
        when(mockedIElevator.getElevatorCapacity(0)).thenReturn(2);
        when(mockedIElevator.getElevatorCapacity(1)).thenReturn(0);

        assertEquals(2, elevatorController.getElevatorCapacity(0));
        assertEquals(0, elevatorController.getElevatorCapacity(1));
    }

    @Test
    void testGetFloorButtonDown() throws RemoteException{
        when(mockedIElevator.getFloorButtonDown(0)).thenReturn(true);
        when(mockedIElevator.getFloorButtonDown(1)).thenReturn(false);

        assertTrue(elevatorController.getFloorButtonDown(0));
        assertFalse(elevatorController.getFloorButtonDown(1));
    }

    @Test
    void testGetFloorButtonUp() throws RemoteException{
        when(mockedIElevator.getFloorButtonUp(0)).thenReturn(true);
        when(mockedIElevator.getFloorButtonUp(1)).thenReturn(false);

        assertTrue(elevatorController.getFloorButtonUp(0));
        assertFalse(elevatorController.getFloorButtonUp(1));
    }

    @Test
    void testGetNumberOfFloors() throws RemoteException{
        when(mockedIElevator.getFloorNum()).thenReturn(0);
        assertEquals(0, elevatorController.getFloorNum());
    }

    @Test
    void testGetServicesFloor() throws RemoteException{
        when(mockedIElevator.getServicesFloors(0, 0)).thenReturn(true);
        when(mockedIElevator.getServicesFloors(0, 2)).thenReturn(false);

        assertTrue(elevatorController.getServicesFloors(0,0));
        assertFalse(elevatorController.getServicesFloors(0,2));
    }

    @Test
    void testGetTarget() throws RemoteException{
        when(mockedIElevator.getTarget(0)).thenReturn(0);
        assertEquals(0, elevatorController.getTarget(0));
    }

    @Test
    void testSetServicesFloor() throws RemoteException{
        elevatorController.setServicesFloors(0, 2, true);
        verify(mockedIElevator).setServicesFloors(0, 2, true);
    }

    @Test
    void testSetTarget() throws RemoteException{
        elevatorController.setTarget(0, 2);
        verify(mockedIElevator).setTarget(0, 2);
    }

    @Test
    void testGetClockTick() throws RemoteException{
        when(mockedIElevator.getClockTick()).thenReturn(10L);
        assertEquals(10L, elevatorController.getClockTick());
    }
}



