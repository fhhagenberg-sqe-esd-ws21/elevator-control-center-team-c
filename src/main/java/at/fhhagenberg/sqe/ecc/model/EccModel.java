package at.fhhagenberg.sqe.ecc.model;

import java.util.ArrayList;
import java.util.List;

public class EccModel {
    private final List<Elevator> elevators;
    private final List<Floor> floors;

    public EccModel(List<Elevator> elevators, List<Floor> floors)
    {
        this.elevators = elevators;
        this.floors = floors;
    }

    public EccModel(EccModel other) {
        elevators = new ArrayList<>();
        for (var elev : other.elevators) {
            elevators.add(new Elevator(elev));
        }

        floors = new ArrayList<>();
        for (var floor : floors) {
            floors.add(new Floor(floor));
        }
    }

    public Elevator getElevator(int elevatorNumber)
    {
        return elevators.get(elevatorNumber);
    }

    public Floor getFloor(int floor)
    {
        return floors.get(floor);
    }

    public int getNumberOfElevators()
    {
        return elevators.size();
    }

    public int getNumberOfFloors() {
        return floors.size();
    }
}
