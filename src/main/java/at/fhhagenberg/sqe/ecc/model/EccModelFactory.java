package at.fhhagenberg.sqe.ecc.model;

import at.fhhagenberg.sqe.ecc.IElevatorController;
import at.fhhagenberg.sqe.ecc.model.EccModel;
import at.fhhagenberg.sqe.ecc.model.Elevator;
import at.fhhagenberg.sqe.ecc.model.Floor;

import java.util.Vector;

public class EccModelFactory {
    private final IElevatorController controller;

    public EccModelFactory(IElevatorController controller) {
        this.controller = controller;
    }

    public EccModel CreateModel()
    {
        var nElevators = controller.getElevatorNum();
        var nFloors = controller.getFloorNum();

        var elevators = new Vector<Elevator>(nElevators);
        for (int e = 0; e < nElevators; e++) {
            var elevator = new Elevator(nFloors);

            elevator.setDirection(controller.getCommittedDirection(e));
            elevator.setSpeed(controller.getElevatorSpeed(e));
            elevator.setAcceleration(controller.getElevatorAccel(e));
            elevator.setDoorState(controller.getElevatorDoorStatus(e));
            elevator.setCurrentFloor(controller.getElevatorFloor(e));
            elevator.setPosition(controller.getElevatorPosition(e));
            elevator.setCurrentPassengerWeight(controller.getElevatorWeight(e));
            elevator.setMaxPassengers(controller.getElevatorCapacity(e));
            elevator.setTargetFloor(controller.getTarget(e));

            for (int f = 0; f < nFloors; f++) {
                elevator.setButtonPressed(f, controller.getElevatorButton(e, f));
            }

            elevators.add(elevator);
        }
        var floors = new Vector<Floor>(nFloors);
        for (int f = 0; f < nFloors; f++) {
            var floor = new Floor();

            floor.setUpButtonPressed(controller.getFloorButtonUp(f));
            floor.setDownButtonPressed(controller.getFloorButtonDown(f));

            floors.add(floor);
        }

        var model = new EccModel(elevators, floors);
        return model;
    }
}
