package at.fhhagenberg.sqe.ecc.model;
import sqelevator.IElevator;

import java.rmi.RemoteException;


public class ElevatorController {

    private final IElevator elevatorCenter;

    public ElevatorController(IElevator e) {
        elevatorCenter = e;
    }

    public int getCommittedDirection(int elevatorNumber) {
        try {
            return elevatorCenter.getCommittedDirection(elevatorNumber);
        }
        catch (RemoteException ex) {
            throw(new RuntimeException("Error in getCommittedDirection: " + ex.getMessage()));
        }
    }

    public int getElevatorAccel(int elevatorNumber) {
        try {
            return elevatorCenter.getElevatorAccel(elevatorNumber);
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


    public int getElevatorDoorStatus(int elevatorNumber) {
        try{
            return elevatorCenter.getElevatorDoorStatus(elevatorNumber);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getElevatorDoorStatus: " + ex.getMessage()));
        }
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

    public int getElevatorPosition(int elevatorNumber) {
        try{
            return elevatorCenter.getElevatorPosition(elevatorNumber);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getElevatorPosition: " + ex.getMessage()));
        }
    }

    public int getElevatorSpeed(int elevatorNumber) {
        try{
            return elevatorCenter.getElevatorSpeed(elevatorNumber);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in getElevatorSpeed: " + ex.getMessage()));
        }
    }

    public int getElevatorWeight(int elevatorNumber) {
        try{
            return elevatorCenter.getElevatorWeight(elevatorNumber);
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

    public int getFloorHeight() {
        try{
            return elevatorCenter.getFloorHeight();
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

    public void setCommittedDirection(int elevatorNumber, int direction) {
        try{
            elevatorCenter.setCommittedDirection(elevatorNumber, direction);
        }
        catch(RemoteException ex){
            throw(new RuntimeException("Error in setCommitedDirection: " + ex.getMessage()));
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