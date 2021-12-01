package at.fhhagenberg.sqe.ecc;

import at.fhhagenberg.sqe.ecc.IElevatorController.CommittedDirection;
import at.fhhagenberg.sqe.ecc.IElevatorController.DoorState;

import java.util.List;

public class Elevator {
    private CommittedDirection direction;
    private double speed;
    private double acceleration;
    private List<Boolean> buttons;
    private DoorState doorState;
    private int currentFloor;
    private double position;
    private double currentPassengerWeight;
    private int maxPassengers;
    private int targetFloor;
}
