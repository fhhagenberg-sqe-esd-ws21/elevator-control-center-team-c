package at.fhhagenberg.sqe.ecc.model;

import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class EccModelTest {

    @Test
    void testGetNumberOfElevators()
    {
        var elevators = new Vector<Elevator>();
        elevators.add(new Elevator(2, 3));
        elevators.add(new Elevator(2, 4));
        var floors = new Vector<Floor>();

        var model = new EccModel(elevators, floors);

        assertEquals(2, model.getNumberOfElevators());
    }

    @Test
    void testGetNumberOfFloors()
    {
        var elevators = new Vector<Elevator>();
        var floors = new Vector<Floor>();
        floors.add(new Floor());
        floors.add(new Floor());
        floors.add(new Floor());

        var model = new EccModel(elevators, floors);

        assertEquals(3, model.getNumberOfFloors());
    }

    @Test
    void testGetElevator()
    {
        var elevators = new Vector<Elevator>();
        elevators.add(new Elevator(2, 3));
        elevators.add(new Elevator(2, 4));
        var floors = new Vector<Floor>();

        var model = new EccModel(elevators, floors);

        assertNotNull(model.getElevator(0));
        assertNotNull(model.getElevator(1));

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> model.getElevator(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> model.getElevator(2));
    }

    @Test
    void testGetFloor()
    {
        var elevators = new Vector<Elevator>();
        var floors = new Vector<Floor>();
        floors.add(new Floor());
        floors.add(new Floor());

        var model = new EccModel(elevators, floors);

        assertNotNull(model.getFloor(0));
        assertNotNull(model.getFloor(1));

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> model.getFloor(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> model.getFloor(2));
    }
}
