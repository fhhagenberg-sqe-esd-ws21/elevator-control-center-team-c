package at.fhhagenberg.sqe.ecc.model;

import at.fhhagenberg.sqe.ecc.ElevatorController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sqelevator.IElevator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class ElevatorControllerTest {

    @Mock
    private IElevator mockedIElevator = mock(IElevator.class);

    private ElevatorController elevatorController;

    @BeforeEach
    void Setup(){
        assertNotNull(mockedIElevator);
        elevatorController = new ElevatorController(mockedIElevator);
        assertNotNull(elevatorController);
    }

    @Test
    void testConversionGetElevatorAccel(){

    }

    @Test
    void testConversionGetElevatorPosition(){

    }

    @Test
    void testConversionGetElevatorSpeed(){

    }

    @Test
    void testConversionGetElevatorWeight(){

    }

    @Test
    void testConversionGetFloorHeight(){

    }

    @Test
    void testEnumeratedGetCommittedDirection(){

    }

    @Test
    void testEnumeratedSetCommittedDirection(){

    }

    @Test
    void testEnumeratedGetElevatorDoorStatus(){

    }
}
