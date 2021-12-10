package at.fhhagenberg.sqe.ecc.model;

import java.util.List;

public class EccModel {
    private List<Elevator> elevators;
    private List<Floor> floors;

    public EccModel(List<Elevator> elevators, List<Floor> floors)
    {
        this.elevators = elevators;
        this.floors = floors;
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

    public int getNumberOfFloors()
    {
        return floors.size();
    }
}
