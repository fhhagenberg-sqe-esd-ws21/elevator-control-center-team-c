package at.fhhagenberg.sqe.ecc;

public interface IElevatorWrapper {
    enum DoorState {
        OPEN,
        CLOSED,
        OPENING,
        CLOSING
    }

    enum CommittedDirection {
        UP,
        DOWN,
        UNCOMMITTED
    }

    /**
     * Retrieves the committed direction of the specified elevator (up / down / uncommitted).
     * @param elevatorNumber - elevator number whose committed direction is being retrieved
     * @return the current direction of the specified elevator
     */
    CommittedDirection getCommittedDirection(int elevatorNumber);

    /**
     * Provides the current acceleration of the specified elevator in meter per sec^2.
     * @param elevatorNumber - elevator number whose acceleration is being retrieved
     * @return returns the acceleration of the indicated elevator where positive speed is acceleration and negative is deceleration
     */
    double getElevatorAccel(int elevatorNumber);

    /**
     * Provides the status of a floor request button on a specified elevator (on/off).
     * @param elevatorNumber - elevator number whose button status is being retrieved
     * @param floor - floor number button being checked on the selected elevator
     * @return returns boolean to indicate if floor button on the elevator is active (true) or not (false)
     */
    boolean getElevatorButton(int elevatorNumber, int floor);

    /**
     * Provides the current status of the doors of the specified elevator (open/closed).
     * @param elevatorNumber - elevator number whose door status is being retrieved
     * @return returns the door status of the indicated elevator
     */
    DoorState getElevatorDoorStatus(int elevatorNumber);

    /**
     * Provides the current location of the specified elevator to the nearest floor
     * @param elevatorNumber - elevator number whose location is being retrieved
     * @return returns the floor number of the floor closest to the indicated elevator
     */
    int getElevatorFloor(int elevatorNumber);

    /**
     * Retrieves the number of elevators in the building.
     * @return total number of elevators
     */
    int getElevatorNum();

    /**
     * Provides the current location of the specified elevator in meter from the bottom of the building.
     * @param elevatorNumber  - elevator number whose location is being retrieved
     * @return returns the location in meter of the indicated elevator from the bottom of the building
     */
    double getElevatorPosition(int elevatorNumber);

    /**
     * Provides the current speed of the specified elevator in meter per sec.
     * @param elevatorNumber - elevator number whose speed is being retrieved
     * @return returns the speed of the indicated elevator where positive speed is up and negative is down
     */
    double getElevatorSpeed(int elevatorNumber);

    /**
     * Retrieves the weight of passengers on the specified elevator.
     * @param elevatorNumber - elevator number whose service is being retrieved
     * @return total weight of all passengers in kg
     */
    double getElevatorWeight(int elevatorNumber);

    /**
     * Retrieves the maximum number of passengers that can fit on the specified elevator.
     * @param elevatorNumber - elevator number whose service is being retrieved
     * @return number of passengers
     */
    int getElevatorCapacity(int elevatorNumber);

    /**
     * Provides the status of the Down button on specified floor (on/off).
     * @param floor - floor number whose Down button status is being retrieved
     * @return returns boolean to indicate if button is active (true) or not (false)
     */
    boolean getFloorButtonDown(int floor);

    /**
     * Provides the status of the Up button on specified floor (on/off).
     * @param floor - floor number whose Up button status is being retrieved
     * @return returns boolean to indicate if button is active (true) or not (false)
     */
    boolean getFloorButtonUp(int floor);

    /**
     * Retrieves the height of the floors in the building.
     * @return floor height (meter)
     */
    double getFloorHeight();

    /**
     * Retrieves the number of floors in the building.
     * @return total number of floors
     */
    int getFloorNum();

    /**
     * Retrieves whether or not the specified elevator will service the specified floor (yes/no).
     * @param elevatorNumber elevator number whose service is being retrieved
     * @param floor floor whose service status by the specified elevator is being retrieved
     * @return service status whether the floor is serviced by the specified elevator (yes=true,no=false)
     */
    boolean getServicesFloors(int elevatorNumber, int floor);

    /**
     * Retrieves the floor target of the specified elevator.
     * @param elevatorNumber elevator number whose target floor is being retrieved
     * @return current floor target of the specified elevator
     */
    int getTarget(int elevatorNumber);

    /**
     * Sets the committed direction of the specified elevator (up / down / uncommitted).
     * @param elevatorNumber elevator number whose committed direction is being set
     * @param direction direction being set
     */
    void setCommittedDirection(int elevatorNumber, CommittedDirection direction);

    /**
     * Sets whether or not the specified elevator will service the specified floor (yes/no).
     * @param elevatorNumber elevator number whose service is being defined
     * @param floor floor whose service by the specified elevator is being set
     * @param service indicates whether the floor is serviced by the specified elevator (yes=true,no=false)
     */
    void setServicesFloors(int elevatorNumber, int floor, boolean service);

    /**
     * Sets the floor target of the specified elevator.
     * @param elevatorNumber elevator number whose target floor is being set
     * @param target floor number which the specified elevator is to target
     */
    void setTarget(int elevatorNumber, int target);

    /**
     * Retrieves the current clock tick of the elevator control system.
     * @return clock tick
     */
    long getClockTick();
}
