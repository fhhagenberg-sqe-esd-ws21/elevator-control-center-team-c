package at.fhhagenberg.sqe.ecc;

import at.fhhagenberg.sqe.ecc.IElevatorWrapper.CommittedDirection;
import at.fhhagenberg.sqe.ecc.model.EccModel;
import at.fhhagenberg.sqe.ecc.model.Elevator;
import at.fhhagenberg.sqe.ecc.model.Floor;
import javafx.beans.property.SimpleBooleanProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sqelevator.IElevator;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EccControllerTest {



    @Mock
    IElevatorWrapper wrapper;

    TestableEccController controller;

    @BeforeEach
    void setup() {
        controller = new TestableEccController();
    }

    @Test
    void testConnected() {
        controller.setConnected(true);
        assertTrue(controller.isConnected());
    }

    @Test
    void testConnectedProperty() {
        var connected = new SimpleBooleanProperty(true);

        connected.bind(controller.connectedProperty());
        controller.setConnected(false);

        assertFalse(connected.get());
    }

    @Test
    void testGetSetModel() {
        assertNull(controller.getModel());

        var model = new EccModel(new ArrayList<>(), new ArrayList<>());
        controller.setModel(model);
        assertEquals(model, controller.getModel());
    }

    @Test
    void testUpdatePeriod() {
        assertEquals(500, controller.getUpdatePeriod());

        controller.setUpdatePeriod(4711);
        assertEquals(4711, controller.getUpdatePeriod());
    }

    @Test
    void testCreateModel() {
        when(wrapper.getElevatorNum()).thenReturn(2);
        when(wrapper.getFloorNum()).thenReturn(3);
        when(wrapper.getElevatorCapacity(0)).thenReturn(47);
        when(wrapper.getElevatorCapacity(1)).thenReturn(11);
        controller.setWrapper(wrapper);

        var model = controller.createModel();

        assertEquals(2, model.getNumberOfElevators());
        assertEquals(3, model.getNumberOfFloors());
        assertEquals(47, model.getElevator(0).getMaxPassengers());
        assertEquals(11, model.getElevator(1).getMaxPassengers());
    }

    @Test
    void testCreateMethod_withoutWrapper() {
        controller.setModel(new EccModel(new ArrayList<>(), new ArrayList<>()));
        assertThrows(IllegalStateException.class, () -> controller.createModel());
    }
    
    @Test
    void testSetTargetFloor() {
        var elev = new Elevator(2, 3);
        controller.setModel(new EccModel(Stream.of(elev).collect(Collectors.toList()),
                Stream.of(new Floor(), new Floor()).collect(Collectors.toList())));
        controller.setWrapper(wrapper);

        controller.setTargetFloor(0, 1);

        assertEquals(1, elev.getTargetFloor());
        verify(wrapper).setTarget(0, 1);
    }

    @Test
    void testSetTargetFloor_withoutWrapper() {
        controller.setModel(new EccModel(new ArrayList<>(), new ArrayList<>()));
        assertThrows(IllegalStateException.class, () -> controller.setTargetFloor(0, 1));
    }

    @Test
    void testSetTargetFloor_withoutModel() {
        controller.setWrapper(wrapper);
        assertThrows(IllegalStateException.class, () -> controller.setTargetFloor(0, 1));
    }

    @Test
    void testSetCommittedDirection() {
        var elev = new Elevator(2, 3);
        controller.setModel(new EccModel(Stream.of(elev).collect(Collectors.toList()),
                Stream.of(new Floor(), new Floor()).collect(Collectors.toList())));
        controller.setWrapper(wrapper);

        controller.setCommittedDirection(0, CommittedDirection.UP);

        assertEquals(CommittedDirection.UP, elev.getDirection());
        verify(wrapper).setCommittedDirection(0, CommittedDirection.UP);
    }

    @Test
    void testSetCommittedDirection_withoutWrapper() {
        controller.setModel(new EccModel(new ArrayList<>(), new ArrayList<>()));
        assertThrows(IllegalStateException.class, () -> controller.setCommittedDirection(0, CommittedDirection.UNCOMMITTED));
    }

    @Test
    void testSetCommittedDirection_withoutModel() {
        controller.setWrapper(wrapper);
        assertThrows(IllegalStateException.class, () -> controller.setCommittedDirection(0, CommittedDirection.UNCOMMITTED));
    }
}
