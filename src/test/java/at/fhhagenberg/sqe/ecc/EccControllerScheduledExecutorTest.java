package at.fhhagenberg.sqe.ecc;

import at.fhhagenberg.sqe.ecc.IElevatorWrapper.DoorState;
import at.fhhagenberg.sqe.ecc.model.EccModel;
import at.fhhagenberg.sqe.ecc.model.Elevator;
import at.fhhagenberg.sqe.ecc.model.Floor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sqelevator.IElevator;

import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EccControllerScheduledExecutorTest {

    @Mock
    IElevator ielev;
    @Mock
    IElevatorWrapper wrapper;

    TestableEccController controller;
    EccModel model;

    @BeforeEach
    void setup() {
        controller = new TestableEccController();

        Elevator elev;
        Floor floor;
        elev = new Elevator(1, 3);
        floor = new Floor();
        model = new EccModel(Stream.of(elev).collect(Collectors.toList()), Stream.of(floor).collect(Collectors.toList()));
    }

    @Test
    void reconnectTest() throws RemoteException {
        controller.setIElevator(ielev);
        controller.setModel(model);
        controller.setUpdatePeriod(100);
        when(ielev.getCommittedDirection(0)).thenThrow(RemoteException.class);

        controller.connect();
        controller.scheduleModelUpdater();

        await().until(() -> !controller.isConnected());
        await().until(() -> controller.isConnected());
    }

    @Test
    void testScheduleModelUpdate_waitForOneUpdate() {
        when(wrapper.getElevatorDoorStatus(0)).thenReturn(DoorState.OPENING);

        controller.setModel(model);
        controller.setWrapper(wrapper);
        controller.setUpdatePeriod(100);

        controller.scheduleModelUpdater();
        await().pollDelay(50, TimeUnit.MILLISECONDS)
                .atMost(200, TimeUnit.MILLISECONDS)
                .until(() -> model.getElevator(0).getDoorState(), equalTo(DoorState.OPENING));

        assertEquals(DoorState.OPENING, model.getElevator(0).getDoorState());
    }

    @Test
    void testScheduleModelUpdater_withoutWrapper() {
        controller.setModel(model);
        assertThrows(IllegalStateException.class, () -> controller.scheduleModelUpdater());
    }

    @Test
    void testScheduleModelUpdater_withoutModel() {
        controller.setWrapper(wrapper);
        assertThrows(IllegalStateException.class, () -> controller.scheduleModelUpdater());
    }

    @Test
    void testShutdownScheduler() throws InterruptedException {
        controller.setModel(model);
        controller.setWrapper(wrapper);
        controller.setUpdatePeriod(100);

        controller.scheduleModelUpdater();
        controller.shutdownScheduler();

        // Check if the value stays the same
        Thread.sleep(110);
        assertEquals(DoorState.CLOSED, model.getElevator(0).getDoorState());
    }
}
