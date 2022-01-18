package at.fhhagenberg.sqe.ecc.model;

import at.fhhagenberg.sqe.ecc.IElevatorWrapper.CommittedDirection;
import javafx.beans.property.SimpleBooleanProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Floor floor = new Floor();
        model = new EccModel(Stream.of(elev).collect(Collectors.toList()), Stream.of(floor).collect(Collectors.toList()));
        automaticMode = new EccAutomaticMode(model);
    }

    @Test
    void TestGetAutomaticModeRunningProperty(){
        var runningProp = new SimpleBooleanProperty();
        runningProp.bind(automaticMode.getAutomaticModeRunningProperty());

        automaticMode.setAutomaticModeRunning(true);
        assertTrue(runningProp.getValue());
    }

    @Test
    void testStopAutomaticMode(){
        automaticMode.StopAutomaticMode();
        assertFalse(automaticMode.getAutomaticModeRunning());
    }

    @Test
    void testStartAutomaticMode(){
        automaticMode.StartAutomaticMode();
        assertTrue(automaticMode.getAutomaticModeRunning());
    }

    @Test
    void testGetNextStopRequest(){
        model.getElevator(0).setCurrentFloor(0);
        model.getElevator(0).setDirection(CommittedDirection.UP);
        model.getElevator(0).setButtonPressed(2, true);

        assertEquals(2, automaticMode.GetNextStopRequest(0));
    }

    @Test
    void testGetNextCallRequest(){

    }

    @Test
    void testRun(){
    }
}
