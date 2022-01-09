package at.fhhagenberg.sqe.ecc;
import sqelevator.IElevator;

import java.rmi.RemoteException;


public class ElevatorController implements IElevatorController {

    private final IElevator elevatorCenter;
    private static final double FACTOR_FEET_TO_METER = 0.3048;
    private static final double FACTOR_POUND_TO_KG = 0.45359237;

    public ElevatorController(IElevator e) {
        elevatorCenter = e;
    }

    public CommittedDirection getCommittedDirection(int elevatorNumber) {
        try {
            switch (elevatorCenter.getCommittedDirection(elevatorNumber)){
                case IElevator.ELEVATOR_DIRECTION_UP:
                    return CommittedDirection.UP;
                case IElevator.ELEVATOR_DIRECTION_DOWN:
                    return CommittedDirection.DOWN;
                case IElevator.ELEVATOR_DIRECTION_UNCOMMITTED:
                    return CommittedDirection.UNCOMMITTED;
                default:
                    throw(new RuntimeException("getCommittedDirection returned unknown IElevator state"));
            }
        }
        catch (RemoteException ex) {
            throw(new RuntimeException("Error in getCommittedDirection: " + ex.getMessage(), ex.getCause()));
        }
    }

    public double getElevatorAccel(int elevatorNumber) {
        try {
            // convert feet/sec^2 into meter/sec^2
            return (elevatorCenter.getElevatorAccel(elevatorNumber) * FACTOR_FEET_TO_METER);
        }
        catch (RemoteException ex) {
            throw(new RuntimeException("Error in getElevatorAccel: " + ex.getMessage(), ex.getCause()));
        }
    }

    public boolean getElevatorButton(int elevatorNumber, int floor) {
        try{
            return elevatorCenter.getElevatorButton(elevatorNumber, floor);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getElevatorButton: " + ex.getMessage(), ex.getCause()));
        }
    }


    public DoorState getElevatorDoorStatus(int elevatorNumber) {
        try{
            switch (elevatorCenter.getElevatorDoorStatus(elevatorNumber)){
                case IElevator.ELEVATOR_DOORS_OPEN:
                    return DoorState.OPEN;
                case IElevator.ELEVATOR_DOORS_OPENING:
                    return DoorState.OPENING;
                case IElevator.ELEVATOR_DOORS_CLOSING:
                    return DoorState.CLOSING;
                case IElevator.ELEVATOR_DOORS_CLOSED:
                    return DoorState.CLOSED;
                default:
                    throw(new RuntimeException("getElevatorDoorStatus returned unknown IElevator state"));
            }
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getElevatorDoorStatus: " + ex.getMessage(), ex.getCause()));
        }
    }

    public int getElevatorFloor(int elevatorNumber) {
        try{
            return elevatorCenter.getElevatorFloor(elevatorNumber);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getElevatorFloor: " + ex.getMessage(), ex.getCause()));
        }
    }

    public int getElevatorNum() {
        try{
            return elevatorCenter.getElevatorNum();
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getElevatorNum: " + ex.getMessage(), ex.getCause()));
        }
    }

    public double getElevatorPosition(int elevatorNumber) {
        try{
            // convert feet into meters
            return (elevatorCenter.getElevatorPosition(elevatorNumber) * FACTOR_FEET_TO_METER);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getElevatorPosition: " + ex.getMessage(), ex.getCause()));
        }
    }

    public double getElevatorSpeed(int elevatorNumber) {
        try{
            // convert feet/sec into meter/sec
            return (elevatorCenter.getElevatorSpeed(elevatorNumber) * FACTOR_FEET_TO_METER);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getElevatorSpeed: " + ex.getMessage(), ex.getCause()));
        }
    }

    public double getElevatorWeight(int elevatorNumber) {
        try{
            // convert lbs to kg
            return (elevatorCenter.getElevatorWeight(elevatorNumber) * FACTOR_POUND_TO_KG);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getElevatorWeight: " + ex.getMessage(), ex.getCause()));
        }
    }

    public int getElevatorCapacity(int elevatorNumber) {
        try{
            return elevatorCenter.getElevatorCapacity(elevatorNumber);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getElevatorCapacity: " + ex.getMessage(), ex.getCause()));
        }
    }

    public boolean getFloorButtonDown(int floor) {
        try{
            return elevatorCenter.getFloorButtonDown(floor);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getFloorButtonDown: " + ex.getMessage(), ex.getCause()));
        }
    }

    public boolean getFloorButtonUp(int floor) {
        try{
            return elevatorCenter.getFloorButtonUp(floor);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getFloorButtonUp: " + ex.getMessage(), ex.getCause()));
        }
    }

    public double getFloorHeight() {
        try{
            // convert feet to meter
            return (elevatorCenter.getFloorHeight() * FACTOR_FEET_TO_METER);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getFloorHeight: " + ex.getMessage(), ex.getCause()));
        }
    }

    public int getFloorNum() {
        try{
            return elevatorCenter.getFloorNum();
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getFloorNum: " + ex.getMessage(), ex.getCause()));
        }
    }

    public boolean getServicesFloors(int elevatorNumber, int floor) {
        try{
            return elevatorCenter.getServicesFloors(elevatorNumber, floor);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getServicesFloors: " + ex.getMessage(), ex.getCause()));
        }
    }

    public int getTarget(int elevatorNumber) {
        try{
            return elevatorCenter.getTarget(elevatorNumber);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getTarget: " + ex.getMessage(), ex.getCause()));
        }
    }

    public long getClockTick() {
        try{
            return elevatorCenter.getClockTick();
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getClockTick: " + ex.getMessage(), ex.getCause()));
        }
    }

    public void setCommittedDirection(int elevatorNumber, CommittedDirection direction) {
        try{
            if(direction == CommittedDirection.UP){
                elevatorCenter.setCommittedDirection(elevatorNumber, IElevator.ELEVATOR_DIRECTION_UP);
            }
            else if(direction == CommittedDirection.DOWN){
                elevatorCenter.setCommittedDirection(elevatorNumber, IElevator.ELEVATOR_DIRECTION_DOWN);}
            else{
                elevatorCenter.setCommittedDirection(elevatorNumber, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
            }
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in setCommittedDirection: " + ex.getMessage(), ex.getCause()));
        }
    }


    public void setServicesFloors(int elevatorNumber, int floor, boolean service) {
        try{
            elevatorCenter.setServicesFloors(elevatorNumber, floor, service);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in setServicesFloors: " + ex.getMessage(), ex.getCause()));
        }
    }

    public void setTarget(int elevatorNumber, int target) {
        try{
            elevatorCenter.setTarget(elevatorNumber, target);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in setTarget: " + ex.getMessage(), ex.getCause()));
        }
    }
}