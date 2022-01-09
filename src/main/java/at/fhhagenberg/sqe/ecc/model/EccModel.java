package at.fhhagenberg.sqe.ecc.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;

public class EccModel {
    private final ObjectProperty<List<Elevator>> elevators = new SimpleObjectProperty<>();
    private final ObjectProperty<List<Floor>> floors = new SimpleObjectProperty<>();

    public ObjectProperty<List<Elevator>> getElevatorsProperty() { return elevators; }
    public ObjectProperty<List<Floor>> getFloorsProperty() { return floors; }

    public EccModel(List<Elevator> elevators, List<Floor> floors)
    {
        this.elevators.set(elevators);
        this.floors.set(floors);
    }

    public Elevator getElevator(int elevatorNumber) { return elevators.get().get(elevatorNumber); }

    public Floor getFloor(int floor)
    {
        return floors.get().get(floor);
    }

    public int getNumberOfElevators()
    {
        return elevators.get().size();
    }

    public int getNumberOfFloors() { return floors.get().size(); }
}
