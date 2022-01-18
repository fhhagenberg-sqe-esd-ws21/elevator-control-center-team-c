package at.fhhagenberg.sqe.ecc.model;

import at.fhhagenberg.sqe.ecc.IElevatorWrapper.CommittedDirection;
import at.fhhagenberg.sqe.ecc.IElevatorWrapper.DoorState;
import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.List;

public class Elevator {

    // permanent parameters
    private final int maxPassengers;
    private final int floors;

    // dynamic parameters
    private final ObjectProperty<CommittedDirection> direction = new SimpleObjectProperty<>(CommittedDirection.UNCOMMITTED);
    private final DoubleProperty speed = new SimpleDoubleProperty();
    private final DoubleProperty acceleration = new SimpleDoubleProperty();
    private final ObjectProperty<DoorState> doorState = new SimpleObjectProperty<>(DoorState.CLOSED);
    private final IntegerProperty currentFloor = new SimpleIntegerProperty();
    private final DoubleProperty position = new SimpleDoubleProperty();
    private final DoubleProperty currentPassengerWeight = new SimpleDoubleProperty();
    private final IntegerProperty targetFloor = new SimpleIntegerProperty();
    private final ObjectProperty<List<BooleanProperty>> buttonsPressed = new SimpleObjectProperty<>();


    // Setters and Getters (property based)

    public CommittedDirection getDirection() {
        return direction.get();
    }
    public void setDirection(CommittedDirection newDirection) {
        this.direction.set(newDirection);
    }
    public ObjectProperty<CommittedDirection> directionProperty() {
        return direction;
    }

    public double getSpeed() {
        return speed.get();
    }
    public void setSpeed(double newSpeed) {
        this.speed.set(newSpeed);
    }
    public DoubleProperty speedProperty(){ return speed; }

    public double getAcceleration() {
        return acceleration.get();
    }
    public void setAcceleration(double newAcceleration) { this.acceleration.set(newAcceleration); }
    public DoubleProperty accelerationProperty(){ return acceleration; }

    public DoorState getDoorState() {
        return doorState.get();
    }
    public void setDoorState(DoorState newDoorState) { this.doorState.set(newDoorState); }
    public ObjectProperty<DoorState> doorStateProperty() {
        return doorState;
    }

    public int getCurrentFloor() {
        return currentFloor.get();
    }
    public void setCurrentFloor(int newCurrentFloor) {
        if (newCurrentFloor < 0 || newCurrentFloor >= floors) {
            throw new IllegalArgumentException("newCurrentFloor must be a positive number lower than floors");
        }
        this.currentFloor.set(newCurrentFloor);
    }
    public IntegerProperty currentFloorProperty() { return currentFloor; }

    public double getPosition() { return position.get(); }
    public void setPosition(double newPosition) {
        if (newPosition < 0) {
            throw new IllegalArgumentException("newPosition must be greater than or equal to 0");
        }
        this.position.set(newPosition);
    }
    public DoubleProperty positionProperty() { return position; }

    public double getCurrentPassengerWeight() { return currentPassengerWeight.get(); }
    public void setCurrentPassengerWeight(double newCurrentPassengerWeight) {
        if (newCurrentPassengerWeight < 0) {
            throw new IllegalArgumentException("newCurrentPassengerWeight must be greater than or equal to 0");
        }
        this.currentPassengerWeight.set(newCurrentPassengerWeight);
    }
    public DoubleProperty currentPassengerWeightProperty() { return currentPassengerWeight; }

    public int getTargetFloor() { return targetFloor.get(); }
    public void setTargetFloor(int newTargetFloor) {
        if (newTargetFloor < 0 || newTargetFloor >= floors) {
            throw new IllegalArgumentException("newTargetFloor must be a positive number lower than floors");
        }
        this.targetFloor.set(newTargetFloor);
    }
    public IntegerProperty targetFloorProperty() { return targetFloor; }

    public boolean isButtonPressed(int floor) { return buttonsPressed.get().get(floor).get(); }
    public void setButtonPressed(int floor, boolean value) { buttonsPressed.get().get(floor).set(value); }
    public BooleanProperty buttonPressedProperty(int floor) { return buttonsPressed.get().get(floor); }
    public ObjectProperty<List<BooleanProperty>> buttonsPressedProperty() { return buttonsPressed; }


    // constructor

    public Elevator(int floors, int maxPassengers)
    {
        if (floors <= 0) {
            throw new IllegalArgumentException("currentFloor must be a positive number");
        }
        if (maxPassengers <= 0) {
            throw new IllegalArgumentException("maxPassengers must be greater than or equal to 0");
        }

        this.floors = floors;
        this.maxPassengers = maxPassengers;

        buttonsPressed.set(new ArrayList<>(floors));
        for (int i = 0; i < floors; i++) {
            buttonsPressed.get().add(new SimpleBooleanProperty());
        }
    }

    public Elevator(Elevator other) {
        maxPassengers = other.maxPassengers;
        floors = other.floors;

        direction.set(other.direction.get());
        speed.set(other.speed.get());
        acceleration.set(other.acceleration.get());
        doorState.set(other.doorState.get());
        currentFloor.set(other.currentFloor.get());
        position.set(other.position.get());
        currentPassengerWeight.set(other.currentPassengerWeight.get());
        targetFloor.set(other.targetFloor.get());

        var buttonList = new ArrayList<BooleanProperty>(floors);
        buttonList.addAll(other.buttonsPressed.get());
        this.buttonsPressed.set(buttonList);
    }

    // other getters/setters

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public int getNumOfFloors(){
        return floors;
    }
}
