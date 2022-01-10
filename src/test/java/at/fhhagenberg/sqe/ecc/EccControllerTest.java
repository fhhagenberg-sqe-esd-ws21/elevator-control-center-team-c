package at.fhhagenberg.sqe.ecc;

import at.fhhagenberg.sqe.ecc.IElevatorWrapper.DoorState;
import at.fhhagenberg.sqe.ecc.model.EccModel;
import at.fhhagenberg.sqe.ecc.model.Elevator;
import at.fhhagenberg.sqe.ecc.model.Floor;
import at.fhhagenberg.sqe.ecc.model.SynchronousEccModelUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sqelevator.IElevator;

import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EccControllerTest {

    @Mock
    IElevatorWrapper wrapper;

    EccControllerInjectIElevator controller;

    @BeforeEach
    void setup() {
        controller = new EccControllerInjectIElevator();
    }

    @Test
    void testCreateModel() throws RemoteException {
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
        assertThrows(RuntimeException.class, controller::createModel);
    }

    @Test
    void testScheduleModelUpdate() throws ExecutionException, InterruptedException, RemoteException {
        when(wrapper.getElevatorDoorStatus(0)).thenReturn(DoorState.OPENING);

        Elevator elev;
        Floor floor;
        EccModel model;
        elev = new Elevator(1, 3);
        floor = new Floor();

        model = new EccModel(Stream.of(elev).collect(Collectors.toList()), Stream.of(floor).collect(Collectors.toList()));
        controller.setWrapper(wrapper);

        var future = controller.scheduleModelUpdater(model);
        Thread.sleep(future.getDelay(TimeUnit.MILLISECONDS) + 50);

        assertEquals(DoorState.OPENING, model.getElevator(0).getDoorState());
    }

//    @Test
//    void testScheduleModelUpdater_withoutWrapper() {
//        EccModel model;
//        assertThrows(RuntimeException.class, () -> controller.scheduleModelUpdater(model));
//    }

    static class EccControllerInjectIElevator extends EccController {
        public void setWrapper(IElevatorWrapper wrapper) {
            this.wrapper = wrapper;
        }

        @Override
        protected void createUpdater(EccModel model) {
            updater = new SynchronousEccModelUpdater(wrapper, model);
        }
    }
}
