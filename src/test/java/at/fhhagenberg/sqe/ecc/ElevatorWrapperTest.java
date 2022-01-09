package at.fhhagenberg.sqe.ecc;

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
public class ElevatorWrapperTest {

    @Mock
    private IElevator mockedIElevator;

    private ElevatorWrapper elevatorWrapper;

    private final double factorFeetToMeter = 0.3048;

    @BeforeEach
    void Setup(){
        elevatorWrapper = new ElevatorWrapper(mockedIElevator);
    }

    @Test
    void testGetAccelConversion() throws RemoteException {
        when(mockedIElevator.getElevatorAccel(0)).thenReturn(1);
        assertEquals(1*factorFeetToMeter, elevatorWrapper.getElevatorAccel(0));
    }

    @Test
    void testGetPositionConversion() throws RemoteException {
        when(mockedIElevator.getElevatorPosition(0)).thenReturn(1);
        assertEquals(1*factorFeetToMeter, elevatorWrapper.getElevatorPosition(0));
    }

    @Test
    void testGetSpeedConversion() throws RemoteException {
        when(mockedIElevator.getElevatorSpeed(0)).thenReturn(1);
        assertEquals(1*factorFeetToMeter, elevatorWrapper.getElevatorSpeed(0));
    }

    @Test
    void testGetWeightConversion() throws RemoteException {
        when(mockedIElevator.getElevatorWeight(0)).thenReturn(1);
        double factorPoundToKg = 0.45359237;
        assertEquals(1* factorPoundToKg, elevatorWrapper.getElevatorWeight(0));
    }

    @Test
    void testGetFloorHeightConversion() throws RemoteException {
        when(mockedIElevator.getFloorHeight()).thenReturn(1);
        assertEquals(1*factorFeetToMeter, elevatorWrapper.getFloorHeight());
    }

    @Test
    void testGetDirectionUP() throws RemoteException {
        when(mockedIElevator.getCommittedDirection(0)).thenReturn(ELEVATOR_DIRECTION_UP);
        assertEquals(IElevatorWrapper.CommittedDirection.UP, elevatorWrapper.getCommittedDirection(0));
    }

    @Test
    void testGetDirectionDOWN() throws RemoteException{
        when(mockedIElevator.getCommittedDirection(0)).thenReturn(ELEVATOR_DIRECTION_DOWN);
        assertEquals(IElevatorWrapper.CommittedDirection.DOWN, elevatorWrapper.getCommittedDirection(0));
    }

    @Test
    void testGetDirectionUNCOMMITED() throws RemoteException{
        when(mockedIElevator.getCommittedDirection(0)).thenReturn(ELEVATOR_DIRECTION_UNCOMMITTED);
        assertEquals(IElevatorWrapper.CommittedDirection.UNCOMMITTED, elevatorWrapper.getCommittedDirection(0));
    }

    @Test
    void testSetDirectionUP() throws RemoteException {
        elevatorWrapper.setCommittedDirection(0, IElevatorWrapper.CommittedDirection.UP);
        verify(mockedIElevator).setCommittedDirection(0, ELEVATOR_DIRECTION_UP);
    }

    @Test
    void testSetDirectionDOWN() throws RemoteException{
        elevatorWrapper.setCommittedDirection(0, IElevatorWrapper.CommittedDirection.DOWN);
        verify(mockedIElevator).setCommittedDirection(0, ELEVATOR_DIRECTION_DOWN);
    }

    @Test
    void testSetDirectionUNCOMMITED() throws RemoteException{
        elevatorWrapper.setCommittedDirection(0, IElevatorWrapper.CommittedDirection.UNCOMMITTED);
        verify(mockedIElevator).setCommittedDirection(0, ELEVATOR_DIRECTION_UNCOMMITTED);
    }

    @Test
    void testGetDoorStateOPEN() throws RemoteException {
        when(mockedIElevator.getElevatorDoorStatus(0)).thenReturn(ELEVATOR_DOORS_OPEN);
        assertEquals(IElevatorWrapper.DoorState.OPEN, elevatorWrapper.getElevatorDoorStatus(0));
    }

    @Test
    void testGetDoorStateCLOSED() throws RemoteException{
        when(mockedIElevator.getElevatorDoorStatus(0)).thenReturn(ELEVATOR_DOORS_CLOSED);
        assertEquals(IElevatorWrapper.DoorState.CLOSED, elevatorWrapper.getElevatorDoorStatus(0));
    }

    @Test
    void testGetDoorStateOPENING() throws RemoteException{
        when(mockedIElevator.getElevatorDoorStatus(0)).thenReturn(ELEVATOR_DOORS_OPENING);
        assertEquals(IElevatorWrapper.DoorState.OPENING, elevatorWrapper.getElevatorDoorStatus(0));
    }

    @Test
    void testGetDoorStateCLOSING() throws RemoteException{
        when(mockedIElevator.getElevatorDoorStatus(0)).thenReturn(ELEVATOR_DOORS_CLOSING);
        assertEquals(IElevatorWrapper.DoorState.CLOSING, elevatorWrapper.getElevatorDoorStatus(0));
    }


    // additional tests for code coverage

    @Test
    void testGetElevatorButton() throws RemoteException {
        when(mockedIElevator.getElevatorButton(0, 1)).thenReturn(true);
        when(mockedIElevator.getElevatorButton(0, 2)).thenReturn(false);

        assertTrue(elevatorWrapper.getElevatorButton(0, 1));
        assertFalse(elevatorWrapper.getElevatorButton(0, 2));
    }

    @Test
    void testGetCurrentFloor() throws RemoteException{
        when(mockedIElevator.getElevatorFloor(0)).thenReturn(0);
        when(mockedIElevator.getElevatorFloor(1)).thenReturn(2);

        assertEquals(0, elevatorWrapper.getElevatorFloor(0));
        assertEquals(2, elevatorWrapper.getElevatorFloor(1));
    }

    @Test
    void testGetNumberOfElevators() throws RemoteException{
        when(mockedIElevator.getElevatorNum()).thenReturn(2);
        assertEquals(2, elevatorWrapper.getElevatorNum());
    }

    @Test
    void testGetCapacity() throws RemoteException{
        when(mockedIElevator.getElevatorCapacity(0)).thenReturn(2);
        when(mockedIElevator.getElevatorCapacity(1)).thenReturn(0);

        assertEquals(2, elevatorWrapper.getElevatorCapacity(0));
        assertEquals(0, elevatorWrapper.getElevatorCapacity(1));
    }

    @Test
    void testGetFloorButtonDown() throws RemoteException{
        when(mockedIElevator.getFloorButtonDown(0)).thenReturn(true);
        when(mockedIElevator.getFloorButtonDown(1)).thenReturn(false);

        assertTrue(elevatorWrapper.getFloorButtonDown(0));
        assertFalse(elevatorWrapper.getFloorButtonDown(1));
    }

    @Test
    void testGetFloorButtonUp() throws RemoteException{
        when(mockedIElevator.getFloorButtonUp(0)).thenReturn(true);
        when(mockedIElevator.getFloorButtonUp(1)).thenReturn(false);

        assertTrue(elevatorWrapper.getFloorButtonUp(0));
        assertFalse(elevatorWrapper.getFloorButtonUp(1));
    }

    @Test
    void testGetNumberOfFloors() throws RemoteException{
        when(mockedIElevator.getFloorNum()).thenReturn(0);
        assertEquals(0, elevatorWrapper.getFloorNum());
    }

    @Test
    void testGetServicesFloor() throws RemoteException{
        when(mockedIElevator.getServicesFloors(0, 0)).thenReturn(true);
        when(mockedIElevator.getServicesFloors(0, 2)).thenReturn(false);

        assertTrue(elevatorWrapper.getServicesFloors(0,0));
        assertFalse(elevatorWrapper.getServicesFloors(0,2));
    }

    @Test
    void testGetTarget() throws RemoteException{
        when(mockedIElevator.getTarget(0)).thenReturn(0);
        assertEquals(0, elevatorWrapper.getTarget(0));
    }

    @Test
    void testSetServicesFloor() throws RemoteException{
        elevatorWrapper.setServicesFloors(0, 2, true);
        verify(mockedIElevator).setServicesFloors(0, 2, true);
    }

    @Test
    void testSetTarget() throws RemoteException{
        elevatorWrapper.setTarget(0, 2);
        verify(mockedIElevator).setTarget(0, 2);
    }

    @Test
    void testGetClockTick() throws RemoteException{
        when(mockedIElevator.getClockTick()).thenReturn(10L);
        assertEquals(10L, elevatorWrapper.getClockTick());
    }


    // also test catching exceptions

    @Test
    void testGetCommittedDirectionException() throws RemoteException {
        when(mockedIElevator.getCommittedDirection(0)).thenThrow(new RemoteException("Trigger Exception"));
        assertThrows(RuntimeException.class, () -> elevatorWrapper.getCommittedDirection(0));
    }

    @Test
    void testGetElevatorAccelException() throws RemoteException {
        when(mockedIElevator.getElevatorAccel(0)).thenThrow(new RemoteException("Trigger Exception"));
        assertThrows(RuntimeException.class, () -> elevatorWrapper.getElevatorAccel(0));
    }

    @Test
    void testGetElevatorButtonException() throws RemoteException {
        when(mockedIElevator.getElevatorButton(0, 0)).thenThrow(new RemoteException("Trigger Exception"));
        assertThrows(RuntimeException.class, () -> elevatorWrapper.getElevatorButton(0, 0));
    }

    @Test
    void testGetElevatorDoorStatusException() throws RemoteException {
        when(mockedIElevator.getElevatorDoorStatus(0)).thenThrow(new RemoteException("Trigger Exception"));
        assertThrows(RuntimeException.class, () -> elevatorWrapper.getElevatorDoorStatus(0));
    }

    @Test
    void testGetElevatorFloorException() throws RemoteException {
        when(mockedIElevator.getElevatorFloor(0)).thenThrow(new RemoteException("Trigger Exception"));
        assertThrows(RuntimeException.class, () -> elevatorWrapper.getElevatorFloor(0));
    }

    @Test
    void testGetElevatorNumException() throws RemoteException {
        when(mockedIElevator.getElevatorNum()).thenThrow(new RemoteException("Trigger Exception"));
        assertThrows(RuntimeException.class, () -> elevatorWrapper.getElevatorNum());
    }

    @Test
    void testGetElevatorPositionException() throws RemoteException {
        when(mockedIElevator.getElevatorPosition(0)).thenThrow(new RemoteException("Trigger Exception"));
        assertThrows(RuntimeException.class, () -> elevatorWrapper.getElevatorPosition(0));
    }

    @Test
    void testGetElevatorSpeedException() throws RemoteException {
        when(mockedIElevator.getElevatorSpeed(0)).thenThrow(new RemoteException("Trigger Exception"));
        assertThrows(RuntimeException.class, () -> elevatorWrapper.getElevatorSpeed(0));
    }

    @Test
    void testGetElevatorWeightException() throws RemoteException {
        when(mockedIElevator.getElevatorWeight(0)).thenThrow(new RemoteException("Trigger Exception"));
        assertThrows(RuntimeException.class, () -> elevatorWrapper.getElevatorWeight(0));
    }

    @Test
    void testGetElevatorCapacityException() throws RemoteException {
        when(mockedIElevator.getElevatorCapacity(0)).thenThrow(new RemoteException("Trigger Exception"));
        assertThrows(RuntimeException.class, () -> elevatorWrapper.getElevatorCapacity(0));
    }

    @Test
    void testGetFloorButtonDownException() throws RemoteException {
        when(mockedIElevator.getFloorButtonDown(0)).thenThrow(new RemoteException("Trigger Exception"));
        assertThrows(RuntimeException.class, () -> elevatorWrapper.getFloorButtonDown(0));
    }

    @Test
    void testGetFloorButtonUpException() throws RemoteException {
        when(mockedIElevator.getFloorButtonUp(0)).thenThrow(new RemoteException("Trigger Exception"));
        assertThrows(RuntimeException.class, () -> elevatorWrapper.getFloorButtonUp(0));
    }

    @Test
    void testGetFloorHeightException() throws RemoteException {
        when(mockedIElevator.getFloorHeight()).thenThrow(new RemoteException("Trigger Exception"));
        assertThrows(RuntimeException.class, () -> elevatorWrapper.getFloorHeight());
    }

    @Test
    void testGetFloorNumException() throws RemoteException {
        when(mockedIElevator.getFloorNum()).thenThrow(new RemoteException("Trigger Exception"));
        assertThrows(RuntimeException.class, () -> elevatorWrapper.getFloorNum());
    }

    @Test
    void testGetServicesFloorsException() throws RemoteException {
        when(mockedIElevator.getServicesFloors(0, 0)).thenThrow(new RemoteException("Trigger Exception"));
        assertThrows(RuntimeException.class, () -> elevatorWrapper.getServicesFloors(0, 0));
    }

    @Test
    void testGetTargetException() throws RemoteException {
        when(mockedIElevator.getTarget(0)).thenThrow(new RemoteException("Trigger Exception"));
        assertThrows(RuntimeException.class, () -> elevatorWrapper.getTarget(0));
    }

    @Test
    void testGetClockTickException() throws RemoteException {
        when(mockedIElevator.getClockTick()).thenThrow(new RemoteException("Trigger Exception"));
        assertThrows(RuntimeException.class, () -> elevatorWrapper.getClockTick());
    }


    // also test exception throwing on setters

    @Test
    void testSetDirectionException() throws RemoteException {
        doThrow(RemoteException.class).when(mockedIElevator).setCommittedDirection(-1, ELEVATOR_DIRECTION_UP);
        assertThrows(RuntimeException.class, () -> elevatorWrapper.setCommittedDirection(-1, IElevatorWrapper.CommittedDirection.UP));
    }

    @Test
    void testSetServicesFloorException() throws RemoteException {
        doThrow(RemoteException.class).when(mockedIElevator).setServicesFloors(0, -1, true);
        assertThrows(RuntimeException.class, () -> elevatorWrapper.setServicesFloors(0, -1, true));
    }

    @Test
    void testSetTargetException() throws RemoteException {
        doThrow(RemoteException.class).when(mockedIElevator).setTarget(0, -1);
        assertThrows(RuntimeException.class, () -> elevatorWrapper.setTarget(0, -1));
    }

}



