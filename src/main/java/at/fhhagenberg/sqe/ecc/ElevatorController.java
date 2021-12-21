package at.fhhagenberg.sqe.ecc;
import sqelevator.IElevator;

import java.rmi.RemoteException;


public class ElevatorController implements IElevatorController {

    private final IElevator elevatorCenter;
    private static final double factorFeetToMeter = 0.3048;
    private static final double factorPoundToKg = 0.45359237;

    public ElevatorController(IElevator e) {
        elevatorCenter = e;
    }

    public CommittedDirection getCommittedDirection(int elevatorNumber) {
        CommittedDirection dir;
        try {
            switch (elevatorCenter.getCommittedDirection(elevatorNumber)){
                case IElevator.ELEVATOR_DIRECTION_UP:
                    dir = CommittedDirection.UP;
                    break;
                case IElevator.ELEVATOR_DIRECTION_DOWN:
                    dir = CommittedDirection.DOWN;
                    break;
                default:
                    dir = CommittedDirection.UNCOMMITED;
                    break;
            }
        }
        catch (RemoteException ex) {
            throw(new RuntimeException("Error in getCommittedDirection: " + ex.getMessage()));
        }
        return dir;
    }

    public double getElevatorAccel(int elevatorNumber) {
        try {
            // convert feet/sec^2 into meter/sec^2
            return (elevatorCenter.getElevatorAccel(elevatorNumber) * factorFeetToMeter);
        }
        catch (RemoteException ex) {
            throw(new RuntimeException("Error in getElevatorAccel: " + ex.getMessage()));
        }
    }

    public boolean getElevatorButton(int elevatorNumber, int floor) {
        try{
            return elevatorCenter.getElevatorButton(elevatorNumber, floor);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getElevatorButton: " + ex.getMessage()));
        }
    }


    public DoorState getElevatorDoorStatus(int elevatorNumber) {
        DoorState state;
        try{
            switch (elevatorCenter.getElevatorDoorStatus(elevatorNumber)){
                case IElevator.ELEVATOR_DOORS_OPEN:
                    state = DoorState.OPEN;
                    break;
                case IElevator.ELEVATOR_DOORS_OPENING:
                    state = DoorState.OPENING;
                    break;
                case IElevator.ELEVATOR_DOORS_CLOSING:
                    state = DoorState.CLOSING;
                    break;
                default:
                    state = DoorState.CLOSED;
                    break;
            }
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getElevatorDoorStatus: " + ex.getMessage()));
        }
        return state;
    }

    public int getElevatorFloor(int elevatorNumber) {
        try{
            return elevatorCenter.getElevatorFloor(elevatorNumber);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getElevatorFloor: " + ex.getMessage()));
        }
    }

    public int getElevatorNum() {
        try{
            return elevatorCenter.getElevatorNum();
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getElevatorNum: " + ex.getMessage()));
        }
    }

    public double getElevatorPosition(int elevatorNumber) {
        try{
            // convert feet into meters
            return (elevatorCenter.getElevatorPosition(elevatorNumber) * factorFeetToMeter);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getElevatorPosition: " + ex.getMessage()));
        }
    }

    public double getElevatorSpeed(int elevatorNumber) {
        try{
            // convert feet/sec into meter/sec
            return (elevatorCenter.getElevatorSpeed(elevatorNumber) * factorFeetToMeter);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getElevatorSpeed: " + ex.getMessage()));
        }
    }

    public double getElevatorWeight(int elevatorNumber) {
        try{
            // convert lbs to kg
            return (elevatorCenter.getElevatorWeight(elevatorNumber) * factorPoundToKg);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getElevatorWeight: " + ex.getMessage()));
        }
    }

    public int getElevatorCapacity(int elevatorNumber) {
        try{
            return elevatorCenter.getElevatorCapacity(elevatorNumber);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getElevatorCapacity: " + ex.getMessage()));
        }
    }

    public boolean getFloorButtonDown(int floor) {
        try{
            return elevatorCenter.getFloorButtonDown(floor);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getFloorButtonDown: " + ex.getMessage()));
        }
    }

    public boolean getFloorButtonUp(int floor) {
        try{
            return elevatorCenter.getFloorButtonUp(floor);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getFloorButtonUp: " + ex.getMessage()));
        }
    }

    public double getFloorHeight() {
        try{
            // convert feet to meter
            return (elevatorCenter.getFloorHeight() * factorFeetToMeter);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getFloorHeight: " + ex.getMessage()));
        }
    }

    public int getFloorNum() {
        try{
            return elevatorCenter.getFloorNum();
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getFloorNum: " + ex.getMessage()));
        }
    }

    public boolean getServicesFloors(int elevatorNumber, int floor) {
        try{
            return elevatorCenter.getServicesFloors(elevatorNumber, floor);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getServicesFloors: " + ex.getMessage()));
        }
    }

    public int getTarget(int elevatorNumber) {
        try{
            return elevatorCenter.getTarget(elevatorNumber);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getTarget: " + ex.getMessage()));
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
            throw(new RuntimeException("Error in setCommittedDirection: " + ex.getMessage()));
        }
    }


    public void setServicesFloors(int elevatorNumber, int floor, boolean service) {
        try{
            elevatorCenter.setServicesFloors(elevatorNumber, floor, service);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in setServicesFloors: " + ex.getMessage()));
        }
    }

    public void setTarget(int elevatorNumber, int target) {
        try{
            elevatorCenter.setTarget(elevatorNumber, target);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in setTarget: " + ex.getMessage()));
        }
    }

    public long getClockTick() {
        try{
            return elevatorCenter.getClockTick();
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getClockTick: " + ex.getMessage()));
        }
    }
}