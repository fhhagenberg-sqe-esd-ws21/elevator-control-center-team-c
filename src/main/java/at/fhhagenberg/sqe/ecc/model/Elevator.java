package at.fhhagenberg.sqe.ecc.model;

import at.fhhagenberg.sqe.ecc.IElevatorController.CommittedDirection;
import at.fhhagenberg.sqe.ecc.IElevatorController.DoorState;

import java.util.List;
import java.util.Vector;

public class Elevator {
    private CommittedDirection direction;
    private double speed;
    private double acceleration;
    private DoorState doorState;
    private int currentFloor;
    private double position;
    private double currentPassengerWeight;
    private int maxPassengers;
    private int targetFloor;
    private List<Boolean> buttonPressed;

    private int floors;
    // TODO: check floors

    public Elevator(int floors)
    {
        this.floors = floors;

        buttonPressed = new Vector<>(floors);
        for (int i = 0; i < floors; i++) {
            buttonPressed.add(false);
        }
    }

    public boolean isButtonPressed(int floor)
    {
        return buttonPressed.get(floor);
    }

    public void setButtonPressed(int floor, boolean value)
    {
        buttonPressed.set(floor, value);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public DoorState getDoorState() {
        return doorState;
    }

    public void setDoorState(DoorState doorState) {
        this.doorState = doorState;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public double getCurrentPassengerWeight() {
        return currentPassengerWeight;
    }

    public void setCurrentPassengerWeight(double currentPassengerWeight) {
        this.currentPassengerWeight = currentPassengerWeight;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public int getTargetFloor() {
        return targetFloor;
    }

    public void setTargetFloor(int targetFloor) {
        this.targetFloor = targetFloor;
    }

    public CommittedDirection getDirection() {
        return direction;
    }

    public void setDirection(CommittedDirection direction) {
        this.direction = direction;
    }
}
