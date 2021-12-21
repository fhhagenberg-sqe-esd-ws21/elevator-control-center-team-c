package at.fhhagenberg.sqe.ecc.model;

import at.fhhagenberg.sqe.ecc.IElevatorController;

import java.util.ArrayList;

public class EccModelFactory {
    private final IElevatorController controller;

    public EccModelFactory(IElevatorController controller) {
        this.controller = controller;
    }

    public EccModel createModel()
    {
        var nElevators = controller.getElevatorNum();
        var nFloors = controller.getFloorNum();

        var elevators = new ArrayList<Elevator>(nElevators);
        for (int e = 0; e < nElevators; e++) {
            var elevator = new Elevator(nFloors, controller.getElevatorCapacity(e));

            elevators.add(elevator);
        }
        var floors = new ArrayList<Floor>(nFloors);
        for (int f = 0; f < nFloors; f++) {
            var floor = new Floor();

            floors.add(floor);
        }

        return new EccModel(elevators, floors);
    }
}
