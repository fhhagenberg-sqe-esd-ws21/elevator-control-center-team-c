package at.fhhagenberg.sqe.ecc.model;

import at.fhhagenberg.sqe.ecc.IElevatorController;
import at.fhhagenberg.sqe.ecc.IElevatorController.CommittedDirection;
import at.fhhagenberg.sqe.ecc.IElevatorController.DoorState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EccModelFactoryTest {

    @Mock
    private IElevatorController controller;

    @Test
    void testCreateModel() {
        when(controller.getElevatorNum()).thenReturn(1);
        when(controller.getFloorNum()).thenReturn(2);
        when(controller.getCommittedDirection(0)).thenReturn(CommittedDirection.DOWN);
        when(controller.getElevatorAccel(0)).thenReturn(2.0);
        when(controller.getElevatorButton(0,0)).thenReturn(false);
        when(controller.getElevatorButton(0,1)).thenReturn(true);
        when(controller.getElevatorDoorStatus(0)).thenReturn(DoorState.OPEN);
        when(controller.getElevatorFloor(0)).thenReturn(0);
        when(controller.getElevatorPosition(0)).thenReturn(1.0);
        when(controller.getElevatorSpeed(0)).thenReturn(3.0);
        when(controller.getElevatorWeight(0)).thenReturn(140.3);
        when(controller.getElevatorCapacity(0)).thenReturn(4);
        when(controller.getTarget(0)).thenReturn(1);
        when(controller.getFloorButtonDown(0)).thenReturn(false);
        when(controller.getFloorButtonUp(0)).thenReturn(true);
        when(controller.getFloorButtonDown(1)).thenReturn(true);
        when(controller.getFloorButtonUp(1)).thenReturn(false);
        var factory = new EccModelFactory(controller);

        var model = factory.CreateModel();

        assertNotNull(model);

        verify(controller).getElevatorNum();
        verify(controller).getFloorNum();
        verify(controller).getCommittedDirection(0);
        verify(controller).getElevatorAccel(0);
        verify(controller).getElevatorButton(0,0);
        verify(controller).getElevatorButton(0,1);
        verify(controller).getElevatorDoorStatus(0);
        verify(controller).getElevatorFloor(0);
        verify(controller).getElevatorPosition(0);
        verify(controller).getElevatorSpeed(0);
        verify(controller).getElevatorWeight(0);
        verify(controller).getElevatorCapacity(0);
        verify(controller).getTarget(0);
        verify(controller).getFloorButtonDown(0);
        verify(controller).getFloorButtonUp(0);
        verify(controller).getFloorButtonDown(1);
        verify(controller).getFloorButtonUp(1);

        assertEquals(1, model.getNumberOfElevators());
        assertEquals(2, model.getNumberOfFloors());
        assertEquals(CommittedDirection.DOWN, model.getElevator(0).getDirection());
        assertEquals(2.0, model.getElevator(0).getAcceleration());
        assertFalse(model.getElevator(0).isButtonPressed(0));
        assertTrue(model.getElevator(0).isButtonPressed(1));
        assertEquals(DoorState.OPEN, model.getElevator(0).getDoorState());
        assertEquals(0, model.getElevator(0).getCurrentFloor());
        assertEquals(1.0, model.getElevator(0).getPosition());
        assertEquals(3.0, model.getElevator(0).getSpeed());
        assertEquals(140.3, model.getElevator(0).getCurrentPassengerWeight());
        assertEquals(4, model.getElevator(0).getMaxPassengers());
        assertEquals(1, model.getElevator(0).getTargetFloor());
        assertFalse(model.getFloor(0).isDownButtonPressed());
        assertTrue(model.getFloor(0).isUpButtonPressed());
        assertTrue(model.getFloor(1).isDownButtonPressed());
        assertFalse(model.getFloor(1).isUpButtonPressed());
    }
}
