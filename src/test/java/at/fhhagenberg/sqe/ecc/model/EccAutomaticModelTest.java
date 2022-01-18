package at.fhhagenberg.sqe.ecc.model;

import at.fhhagenberg.sqe.ecc.IElevatorWrapper.CommittedDirection;
import javafx.beans.property.SimpleBooleanProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

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
    }

    /*
    @Test
    void testStartAutomaticMode(){
        model.getElevator(0).setDirection(CommittedDirection.UNCOMMITTED);
        automaticMode.StartAutomaticMode();
        assertTrue(automaticMode.getAutomaticModeRunning());

        automaticMode.StopAutomaticMode();
        assertFalse(automaticMode.getAutomaticModeRunning());

        assertEquals(2, model.getElevator(0).getTargetFloor());
    }
    */

    @Test
    void testGetNextStopRequest(){
        model.getElevator(0).setCurrentFloor(0);
        model.getElevator(0).setDirection(CommittedDirection.UP);
        model.getElevator(0).setButtonPressed(2, true);

        assertEquals(2, automaticMode.GetNextStopRequest(0));
    }

    @Test
    void testGetNextCallRequest(){
        model.getElevator(0).setCurrentFloor(0);
        model.getElevator(0).setDirection(CommittedDirection.UP);
        model.getFloor(1).setUpButtonPressed(true);

        assertEquals(1, automaticMode.GetNextCallRequest(0));
    }
}
