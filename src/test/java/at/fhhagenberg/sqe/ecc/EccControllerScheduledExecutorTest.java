package at.fhhagenberg.sqe.ecc;

import at.fhhagenberg.sqe.ecc.model.EccModel;
import at.fhhagenberg.sqe.ecc.model.Elevator;
import at.fhhagenberg.sqe.ecc.model.Floor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EccControllerScheduledExecutorTest {

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
    void testScheduleModelUpdate_waitForOneUpdate() {
        when(wrapper.getElevatorDoorStatus(0)).thenReturn(IElevatorWrapper.DoorState.OPENING);

        controller.setModel(model);
        controller.setWrapper(wrapper);
        controller.setUpdatePeriod(100);

        controller.scheduleModelUpdater();
        await().pollDelay(50, TimeUnit.MILLISECONDS)
                .atMost(200, TimeUnit.MILLISECONDS)
                .until(() -> model.getElevator(0).getDoorState(), equalTo(IElevatorWrapper.DoorState.OPENING));

        assertEquals(IElevatorWrapper.DoorState.OPENING, model.getElevator(0).getDoorState());
    }

    @Test
    void testScheduleModelUpdate_waitForTwoUpdates() {
        when(wrapper.getElevatorDoorStatus(0)).thenReturn(IElevatorWrapper.DoorState.OPENING, IElevatorWrapper.DoorState.CLOSING);

        controller.setModel(model);
        controller.setWrapper(wrapper);
        controller.setUpdatePeriod(100);

        controller.scheduleModelUpdater();

        await().pollDelay(50, TimeUnit.MILLISECONDS)
                .atMost(200, TimeUnit.MILLISECONDS)
                .until(() -> model.getElevator(0).getDoorState(), equalTo(IElevatorWrapper.DoorState.OPENING));
        await().pollDelay(50, TimeUnit.MILLISECONDS)
                .atMost(200, TimeUnit.MILLISECONDS)
                .until(() -> model.getElevator(0).getDoorState(), equalTo(IElevatorWrapper.DoorState.CLOSING));
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
        when(wrapper.getElevatorDoorStatus(0)).thenReturn(IElevatorWrapper.DoorState.OPENING, IElevatorWrapper.DoorState.CLOSING);

        controller.setModel(model);
        controller.setWrapper(wrapper);
        controller.setUpdatePeriod(100);

        controller.scheduleModelUpdater();

        await().pollDelay(50, TimeUnit.MILLISECONDS)
                .atMost(200, TimeUnit.MILLISECONDS)
                .until(() -> model.getElevator(0).getDoorState(), equalTo(IElevatorWrapper.DoorState.OPENING));

        controller.shutdownScheduler();

        // Check if the value stays the same
        Thread.sleep(100);
        assertEquals(IElevatorWrapper.DoorState.OPENING, model.getElevator(0).getDoorState());
    }
}
