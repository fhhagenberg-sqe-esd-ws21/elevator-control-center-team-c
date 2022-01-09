package at.fhhagenberg.sqe.ecc.model;

import at.fhhagenberg.sqe.ecc.IElevatorWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EccModelFactoryTest {

    @Mock
    private IElevatorWrapper controller;

    @Test
    void testCreateModel() {
        when(controller.getElevatorNum()).thenReturn(2);
        when(controller.getFloorNum()).thenReturn(2);
        when(controller.getElevatorCapacity(0)).thenReturn(4);
        when(controller.getElevatorCapacity(1)).thenReturn(5);
        var factory = new EccModelFactory(controller);

        var model = factory.createModel();

        // Check number of floors
        assertEquals(2, model.getNumberOfElevators());
        assertEquals(2, model.getNumberOfFloors());

        // Check amount of max passengers
        assertEquals(4, model.getElevator(0).getMaxPassengers());
        assertEquals(5, model.getElevator(1).getMaxPassengers());
    }
}
