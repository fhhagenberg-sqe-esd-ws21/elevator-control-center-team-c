package at.fhhagenberg.sqe.ecc.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
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

    public EccModel(EccModel other) {
        var elevatorList = new ArrayList<Elevator>();
        for (var elev : other.elevators.get()) {
            elevatorList.add(new Elevator(elev));
        }

        this.elevators.set(elevatorList);

        var floorList = new ArrayList<Floor>();
        for (var floor : other.floors.get()) {
            floorList.add(new Floor(floor));
        }

        this.floors.set(floorList);
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
