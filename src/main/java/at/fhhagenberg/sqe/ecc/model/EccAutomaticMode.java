package at.fhhagenberg.sqe.ecc.model;

import at.fhhagenberg.sqe.ecc.IElevatorWrapper.CommittedDirection;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EccAutomaticMode {

    private final EccModel model;
    private final BooleanProperty automaticModeRunning = new SimpleBooleanProperty();
    private ScheduledThreadPoolExecutor scheduledExecutor;

    public BooleanProperty getAutomaticModeRunningProperty(){
        return automaticModeRunning;
    }

    public void setAutomaticModeRunning(boolean run){ automaticModeRunning.setValue(run);}

    public Boolean getAutomaticModeRunning(){ return automaticModeRunning.getValue(); }

    EccAutomaticMode(EccModel model){
        this.model = model;
    }

    public void StopAutomaticMode(){
        automaticModeRunning.setValue(false);
    }
    public void StartAutomaticMode(){ automaticModeRunning.setValue(true); }

    // get next stop request in current direction
    // if no stop in current direction, returns next stop in other direction
    // if there is no stop request at all, the current floor is returned
    // if the current direction is uncommitted, returns the nearest stop to current floor
    // else the current floor is returned
    public int GetNextStopRequest(int elevNum){
        Elevator currElev = model.getElevator(elevNum);
        CommittedDirection dir = currElev.getDirection();
        int maxFloors = currElev.getNumOfFloors();
        int startFloor = currElev.getCurrentFloor();
        int nextStopAbove = -1;
        int nextStopBelow = -1;

        // check next stop above current floor
        for(int i = startFloor; i < maxFloors; i++){
            if(currElev.isButtonPressed(i)){
                nextStopAbove = i;
                break;
            }
        }
        // check next stop below current floor
        for(int i = startFloor; i >= 0; i--){
            if(currElev.isButtonPressed(i)){
                nextStopBelow = i;
                break;
            }
        }

        // always prefer first stop in current direction, else the nearest
        // if no stop request is done in either direction, stay at current floor
        switch (dir){
            case UP:
                if     (nextStopAbove > -1){ return nextStopAbove; }
                else if(nextStopBelow > -1){ return nextStopBelow; }
                else return startFloor;
            case DOWN:
                if     (nextStopBelow > -1){ return nextStopBelow; }
                else if(nextStopAbove > -1){ return nextStopAbove; }
                else return startFloor;
            case UNCOMMITTED:
                // if uncommitted: choose the nearest stop
                int diffAbove = nextStopAbove - startFloor;
                int diffBelow = startFloor - nextStopBelow;
                if     (diffAbove > diffBelow){ return nextStopBelow; }
                else if(diffAbove < diffBelow){ return nextStopAbove; }
                else{ return startFloor; }
            default:
                return startFloor;
        }
    }

    // get next floor call request in current direction
    // if no call in current direction, returns next call in other direction
    // if there is no call request at all, the current floor is returned
    // if the current direction is uncommitted, returns the nearest call to current floor
    // else the current floor is returned
    public int GetNextCallRequest(int elevNum){
        Elevator currElev = model.getElevator(elevNum);
        CommittedDirection dir = currElev.getDirection();
        int maxFloors = model.getNumberOfFloors();
        int startFloor = currElev.getCurrentFloor();
        int nextCallAbove = -1;
        int nextCallBelow = -1;

        // check next call above current floor
        // don't care about direction, can be got with the found index later on
        for(int i = startFloor; i < maxFloors; i++){
            Floor checkFloor = model.getFloor(i);
            if(checkFloor.isUpButtonPressed() || checkFloor.isDownButtonPressed()){
                nextCallAbove = i;
                break;
            }
        }
        // check next stop below current floor
        for(int i = startFloor; i >= 0; i--){
            Floor checkFloor = model.getFloor(i);
            if(checkFloor.isUpButtonPressed() || checkFloor.isDownButtonPressed()){
                nextCallBelow = i;
                break;
            }
        }

        // always prefer first call in current direction, else the nearest
        // if no call request is done in either direction, stay at current floor
        switch (dir){
            case UP:
                if     (nextCallAbove > -1){ return nextCallAbove; }
                else if(nextCallBelow > -1){ return nextCallBelow; }
                else return startFloor;
            case DOWN:
                if     (nextCallBelow > -1){ return nextCallBelow; }
                else if(nextCallAbove > -1){ return nextCallAbove; }
                else return startFloor;
            case UNCOMMITTED:
                // if uncommitted: choose the nearest stop
                int diffAbove = nextCallAbove - startFloor;
                int diffBelow = startFloor - nextCallBelow;
                if     (diffAbove > diffBelow){ return nextCallBelow; }
                else if(diffAbove < diffBelow){ return nextCallAbove; }
                else{ return startFloor; }
            default:
                return startFloor;
        }
    }

    private void RunAutomatic(){
        int numElevators = model.getNumberOfElevators();
        if(automaticModeRunning.getValue()){
            for(int i = 0; i < numElevators; i++){
                Elevator currElev = model.getElevator(i);
                int currFloor = currElev.getCurrentFloor();
                CommittedDirection currDir = currElev.getDirection();

                // first find out next stop request (from elevator)/call request (floors)
                int nextStop = GetNextStopRequest(i);
                int nextCall = GetNextCallRequest(i);

                // neither stop nor call was pressed: stay
                if(nextStop == currFloor && nextCall == currFloor){
                    currElev.setTargetFloor(currFloor);
                    currElev.setDirection(CommittedDirection.UNCOMMITTED);
                }
                // no stop request: directly set next call as target
                else if(nextStop == currFloor){
                    currElev.setTargetFloor(nextCall);
                    if(nextCall < currFloor){currElev.setDirection(CommittedDirection.DOWN);}
                    else{currElev.setDirection(CommittedDirection.UP);}
                }
                // no call request: directly set next stop as target
                else if(nextCall == currFloor){
                    currElev.setTargetFloor(nextStop);
                    if(nextStop < currFloor){currElev.setDirection(CommittedDirection.DOWN);}
                    else{currElev.setDirection(CommittedDirection.UP);}
                }
                // stop and call were pressed: go to the nearest stop/call
                else{
                    // if the stop request is nearer than call, set this as next target.
                    // if the stop request is further than call, set call as next target
                    // always check direction (if up and on upmost floor: set dir down etc.)
                    switch (currDir){
                        case UP:
                            // check if stop is above currentFloor - prioritize that
                            if(nextStop > currFloor){
                                currElev.setTargetFloor(nextStop);
                            }
                            // next stop is below - first check if a call would be above
                            else{
                                if(nextCall > currFloor){
                                    currElev.setTargetFloor(nextCall);
                                }
                                else{
                                    // elevator definitely needs to go down in this case
                                    currElev.setDirection(CommittedDirection.DOWN);

                                    // check through differences if stop or call is nearer
                                    int diffStop = currFloor - nextStop;
                                    int diffCall = currFloor - nextCall;

                                    if (diffStop < diffCall){
                                        currElev.setTargetFloor(nextStop);
                                    }
                                    else{
                                        currElev.setTargetFloor(nextCall);
                                    }
                                }
                            }
                            break;
                        case DOWN:
                            // check if stop is below currentFloor - prioritize that
                            if(nextStop < currFloor){
                                currElev.setTargetFloor(nextStop);
                            }
                            // next stop is above - first check if a call would be below
                            else{
                                if(nextCall < currFloor){
                                    currElev.setTargetFloor(nextCall);
                                }
                                else{
                                    // elevator definitely needs to go up in this case
                                    currElev.setDirection(CommittedDirection.UP);

                                    // check through differences if stop or call is nearer
                                    int diffStop = nextStop - currFloor;
                                    int diffCall = nextCall - currFloor;

                                    if (diffStop < diffCall){
                                        currElev.setTargetFloor(nextStop);
                                    }
                                    else{
                                        currElev.setTargetFloor(nextCall);
                                    }
                                }
                            }
                            break;
                        default:
                            currElev.setTargetFloor(currFloor);
                            currElev.setDirection(CommittedDirection.UNCOMMITTED);
                            break;
                    }
                }
            }
        }
    }

    public ScheduledFuture<?> Run(){
        ScheduledThreadPoolExecutor scheduledExecutor;
        long updatePeriod = 100;

        scheduledExecutor = new ScheduledThreadPoolExecutor(1);
        return scheduledExecutor.scheduleAtFixedRate(this::RunAutomatic, updatePeriod, updatePeriod, TimeUnit.MILLISECONDS);

    }
}
