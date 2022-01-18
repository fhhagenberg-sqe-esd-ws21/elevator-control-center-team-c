package at.fhhagenberg.sqe.ecc.model;

import javafx.beans.property.SimpleBooleanProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EccAutomaticModelTest {

    private EccAutomaticMode automaticMode;

    @BeforeEach
    void Setup(){
        Elevator elev = new Elevator(3, 1);
        Floor floor = new Floor();
        EccModel model = new EccModel(Stream.of(elev).collect(Collectors.toList()), Stream.of(floor).collect(Collectors.toList()));
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
    void testRunAutomatic(){
        automaticMode.setAutomaticModeRunning(true);
        automaticMode.RunAutomatic();
        automaticMode.setAutomaticModeRunning(false);
    }
}
