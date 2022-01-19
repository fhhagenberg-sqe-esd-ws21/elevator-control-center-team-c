package at.fhhagenberg.sqe.ecc.model;

import at.fhhagenberg.sqe.ecc.IElevatorWrapper;
import javafx.application.Platform;

public class EccModelUpdater {

    IElevatorWrapper controller;
    EccModel model;

    EccModel shadowModel;

    public EccModelUpdater(IElevatorWrapper controller, EccModel model)
    {
        this.controller = controller;
        this.model = model;

        shadowModel = new EccModel(model);
    }

    public void updateModel()
    {
        if (fetchNewValues()) {
            Platform.runLater(this::applyNewValues);
        }
    }

    protected boolean fetchNewValues()
    {
        for (int e = 0; e < shadowModel.getNumberOfElevators(); e++) {
            var elev = shadowModel.getElevator(e);

            elev.setDirection(controller.getCommittedDirection(e));
            elev.setAcceleration(controller.getElevatorAccel(e));
            elev.setDoorState(controller.getElevatorDoorStatus(e));
            elev.setCurrentFloor(controller.getElevatorFloor(e));
            elev.setPosition(controller.getElevatorPosition(e));
            elev.setSpeed(controller.getElevatorSpeed(e));
            elev.setCurrentPassengerWeight(controller.getElevatorWeight(e));
            elev.setTargetFloor(controller.getTarget(e));

            for (int f = 0; f < shadowModel.getNumberOfFloors(); f++) {
                elev.setButtonPressed(f, controller.getElevatorButton(e, f));
            }
        }

        for (int f = 0; f < shadowModel.getNumberOfFloors(); f++) {
            var floor = shadowModel.getFloor(f);

            floor.setDownButtonPressed(controller.getFloorButtonDown(f));
            floor.setUpButtonPressed(controller.getFloorButtonUp(f));
        }

        return true;
    }

    protected void applyNewValues()
    {
        for (int e = 0; e < model.getNumberOfElevators(); e++) {
            var elev = model.getElevator(e);
            var shadowElev = shadowModel.getElevator(e);

            elev.setDirection(shadowElev.getDirection());
            elev.setAcceleration(shadowElev.getAcceleration());
            elev.setDoorState(shadowElev.getDoorState());
            elev.setCurrentFloor(shadowElev.getCurrentFloor());
            elev.setPosition(shadowElev.getPosition());
            elev.setSpeed(shadowElev.getSpeed());
            elev.setCurrentPassengerWeight(shadowElev.getCurrentPassengerWeight());
            elev.setTargetFloor(shadowElev.getTargetFloor());

            for (int f = 0; f < model.getNumberOfFloors(); f++) {
                elev.setButtonPressed(f, shadowElev.isButtonPressed(f));
            }
        }

        for (int f = 0; f < model.getNumberOfFloors(); f++) {
            var floor = model.getFloor(f);
            var shadowFloor = shadowModel.getFloor(f);

            floor.setDownButtonPressed(shadowFloor.isDownButtonPressed());
            floor.setUpButtonPressed(shadowFloor.isUpButtonPressed());
        }
    }
}
