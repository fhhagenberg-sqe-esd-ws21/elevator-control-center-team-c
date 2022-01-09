package at.fhhagenberg.sqe.ecc.model;

import at.fhhagenberg.sqe.ecc.IElevatorWrapper;

public class EccModelUpdater {

    IElevatorWrapper controller;
    EccModel model;

    public EccModelUpdater(IElevatorWrapper controller, EccModel model)
    {
        this.controller = controller;
        this.model = model;
    }

    public void updateModel()
    {
        for (int e = 0; e < model.getNumberOfElevators(); e++) {
            var elev = model.getElevator(e);

            elev.setDirection(controller.getCommittedDirection(e));
            elev.setAcceleration(controller.getElevatorAccel(e));
            elev.setDoorState(controller.getElevatorDoorStatus(e));
            elev.setCurrentFloor(controller.getElevatorFloor(e));
            elev.setPosition(controller.getElevatorPosition(e));
            elev.setSpeed(controller.getElevatorSpeed(e));
            elev.setCurrentPassengerWeight(controller.getElevatorWeight(e));
            elev.setTargetFloor(controller.getTarget(e));

            for (int f = 0; f < model.getNumberOfFloors(); f++) {
                elev.setButtonPressed(f, controller.getElevatorButton(e, f));
            }
        }

        for (int f = 0; f < model.getNumberOfFloors(); f++) {
            var floor = model.getFloor(f);

            floor.setDownButtonPressed(controller.getFloorButtonDown(f));
            floor.setUpButtonPressed(controller.getFloorButtonUp(f));
        }
    }
}
