package at.fhhagenberg.sqe.ecc.model;

import at.fhhagenberg.sqe.ecc.IElevatorWrapper.CommittedDirection;
import at.fhhagenberg.sqe.ecc.IElevatorWrapper.DoorState;

import java.util.ArrayList;
import java.util.List;

public class Elevator {
    // permanent parameters
    private final int maxPassengers;
    private final int floors;

    // dynamic parameters
    private CommittedDirection direction;
    private double speed;
    private double acceleration;
    private DoorState doorState;
    private int currentFloor;
    private double position;
    private double currentPassengerWeight;
    private int targetFloor;
    private final List<Boolean> buttonPressed;

    public Elevator(int floors, int maxPassengers)
    {
        if (floors <= 0) {
            throw new IllegalArgumentException("newCurrentFloor must be a positive number");
        }
        if (maxPassengers <= 0) {
            throw new IllegalArgumentException("newMaxPassengers must be greater than or equal to 0");
        }

        this.floors = floors;
        this.maxPassengers = maxPassengers;

        buttonPressed = new ArrayList<>(floors);
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

    public void setSpeed(double newSpeed) {
        this.speed = newSpeed;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double newAcceleration) {
        this.acceleration = newAcceleration;
    }

    public DoorState getDoorState() {
        return doorState;
    }

    public void setDoorState(DoorState newDoorState) {
        this.doorState = newDoorState;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int newCurrentFloor) {
        if (newCurrentFloor < 0 || newCurrentFloor >= floors) {
            throw new IllegalArgumentException("newCurrentFloor must be a positive number lower than floors");
        }
        this.currentFloor = newCurrentFloor;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double newPosition) {
        if (newPosition < 0) {
            throw new IllegalArgumentException("newPosition must be greater than or equal to 0");
        }

        this.position = newPosition;
    }

    public double getCurrentPassengerWeight() {
        return currentPassengerWeight;
    }

    public void setCurrentPassengerWeight(double newCurrentPassengerWeight) {
        if (newCurrentPassengerWeight < 0) {
            throw new IllegalArgumentException("newCurrentPassengerWeight must be greater than or equal to 0");
        }

        this.currentPassengerWeight = newCurrentPassengerWeight;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public int getTargetFloor() {
        return targetFloor;
    }

    public void setTargetFloor(int newTargetFloor) {
        if (newTargetFloor < 0 || newTargetFloor >= floors) {
            throw new IllegalArgumentException("newTargetFloor must be a positive number lower than floors");
        }
        this.targetFloor = newTargetFloor;
    }

    public CommittedDirection getDirection() {
        return direction;
    }

    public void setDirection(CommittedDirection newDirection) {
        this.direction = newDirection;
    }
}
