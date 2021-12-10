package at.fhhagenberg.sqe.ecc.model;

import at.fhhagenberg.sqe.ecc.IElevatorController;

import java.util.Timer;
import java.util.TimerTask;

public class EccModelUpdater {

    IElevatorController controller;
    EccModel model;
    Timer updateTimer;
    boolean updateRunning = false;

    public EccModelUpdater(IElevatorController controller, EccModel model)
    {
        this.controller = controller;
        this.model = model;

        updateTimer = new Timer();

    }

    public void start()
    {
        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateModel();
            }
        }, 0, 500);
    }

    public void stop()
    {
        updateTimer.cancel();
    }

    private void updateModel()
    {
        updateRunning = true;

        for (int e = 0; e < model.getNumberOfElevators(); e++) {
            var elev = model.getElevator(e);

            elev.setDirection(controller.getCommittedDirection(e));
            elev.setAcceleration(controller.getElevatorAccel(e));
            elev.setDoorState(controller.getElevatorDoorStatus(e));
            elev.setCurrentFloor(controller.getElevatorFloor(e));
            elev.setPosition(controller.getElevatorPosition(e));
            elev.setSpeed(controller.getElevatorSpeed(e));
            elev.setCurrentPassengerWeight(controller.getElevatorWeight(e));
            elev.setMaxPassengers(controller.getElevatorCapacity(e));
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

        updateRunning = false;
    }
}
