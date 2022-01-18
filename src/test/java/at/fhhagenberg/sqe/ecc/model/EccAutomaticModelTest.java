package at.fhhagenberg.sqe.ecc.model;

import at.fhhagenberg.sqe.ecc.IElevatorWrapper;
import at.fhhagenberg.sqe.ecc.IElevatorWrapper.CommittedDirection;
import javafx.beans.property.SimpleBooleanProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EccAutomaticModelTest {

    private EccModel model;
    private EccAutomaticMode automaticMode;

    @BeforeEach
    void Setup(){
        Elevator elev = new Elevator(3, 5);
        List<Floor> floors = new ArrayList<>();
        floors.add(new Floor());
        floors.add(new Floor());
        floors.add(new Floor());

        model = new EccModel(Stream.of(elev).collect(Collectors.toList()), floors);
        automaticMode = new EccAutomaticMode(model);
    }

    @Test
    void TestGetAutomaticModeRunningProperty(){
        var runningProp = new SimpleBooleanProperty();
        runningProp.bind(automaticMode.getAutomaticModeRunningProperty());

        automaticMode.setAutomaticModeRunning(true);
        assertTrue(runningProp.getValue());
        assertTrue(automaticMode.getAutomaticModeRunning());
    }

    @Test
    void testStartRunStop() throws InterruptedException {
        model.getElevator(0).setCurrentFloor(0);
        model.getElevator(0).setDirection(CommittedDirection.UP);
        model.getElevator(0).setButtonPressed(2, true);

        automaticMode.StartAutomaticMode();
        assertTrue(automaticMode.getAutomaticModeRunning());
        // unnecessary
        var future = automaticMode.Run();
        Thread.sleep(future.getDelay(TimeUnit.MILLISECONDS) + 10);

        automaticMode.StopAutomaticMode();
        assertFalse(automaticMode.getAutomaticModeRunning());
        assertEquals(2, model.getElevator(0).getTargetFloor());
    }

    @Test
    void testRunWithoutStarting() throws ExecutionException, InterruptedException, RemoteException {
        model.getElevator(0).setCurrentFloor(0);
        model.getElevator(0).setDirection(CommittedDirection.UP);
        model.getElevator(0).setButtonPressed(2, true);

        var future = automaticMode.Run();
        Thread.sleep(future.getDelay(TimeUnit.MILLISECONDS) + 10);

        assertEquals(0, model.getElevator(0).getTargetFloor());
    }

    @Test
    void testGetNextStopRequestStayingUP(){
        model.getElevator(0).setCurrentFloor(0);
        model.getElevator(0).setDirection(CommittedDirection.UP);
        model.getElevator(0).setButtonPressed(2, true);

        assertEquals(2, automaticMode.GetNextStopRequest(0));
    }

    @Test
    void testGetNextStopRequestGoingDOWN(){
        model.getElevator(0).setCurrentFloor(1);
        model.getElevator(0).setDirection(CommittedDirection.UP);
        model.getElevator(0).setButtonPressed(0, true);

        assertEquals(0, automaticMode.GetNextStopRequest(0));
        assertEquals(0, model.getElevator(0).getTargetFloor());
        assertEquals(CommittedDirection.DOWN, model.getElevator(0).getDirection());
    }


    @Test
    void testGetNextCallRequestUP(){
        model.getElevator(0).setCurrentFloor(0);
        model.getElevator(0).setDirection(CommittedDirection.UP);
        model.getFloor(1).setUpButtonPressed(true);

        assertEquals(1, automaticMode.GetNextCallRequest(0));
    }

    @Test
    void testGetNextCallRequestGoingDOWN(){
        model.getElevator(0).setCurrentFloor(2);
        model.getElevator(0).setDirection(CommittedDirection.UP);
        model.getFloor(1).setUpButtonPressed(true);

        assertEquals(1, automaticMode.GetNextStopRequest(0));
        assertEquals(1, model.getElevator(0).getTargetFloor());
        assertEquals(CommittedDirection.DOWN, model.getElevator(0).getDirection());
    }

    @Test
    void testGetNextMixedStartingUNCOMMITTEDChoosingCALLGoingDOWN(){
        model.getElevator(0).setDirection(CommittedDirection.UNCOMMITTED);
        model.getElevator(0).setCurrentFloor(2);
        model.getFloor(1).setUpButtonPressed(true);
        model.getElevator(0).setButtonPressed(0, true);

        assertEquals(1, model.getElevator(0).getTargetFloor());
        assertEquals(CommittedDirection.DOWN, model.getElevator(0).getDirection());
    }

    @Test
    void testGetNextMixedStartingUNCOMMITTEDChoosingSTOPGoingDOWN(){
        model.getElevator(0).setDirection(CommittedDirection.UNCOMMITTED);
        model.getElevator(0).setCurrentFloor(1);
        model.getFloor(2).setUpButtonPressed(true);
        model.getElevator(0).setButtonPressed(0, true);

        assertEquals(0, model.getElevator(0).getTargetFloor());
        assertEquals(CommittedDirection.DOWN, model.getElevator(0).getDirection());
    }

    @Test
    void testGetNextMixedStartingUNCOMMITTEDChoosingSTOPGoingUP(){
        model.getElevator(0).setDirection(CommittedDirection.UNCOMMITTED);
        model.getElevator(0).setCurrentFloor(1);
        model.getFloor(0).setUpButtonPressed(true);
        model.getElevator(2).setButtonPressed(0, true);

        assertEquals(2, model.getElevator(0).getTargetFloor());
        assertEquals(CommittedDirection.UP, model.getElevator(0).getDirection());
    }
}
