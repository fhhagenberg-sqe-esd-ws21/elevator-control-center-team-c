package at.fhhagenberg.sqe.ecc;

import at.fhhagenberg.sqe.ecc.model.EccModel;
import at.fhhagenberg.sqe.ecc.model.EccModelFactory;
import at.fhhagenberg.sqe.ecc.model.EccModelUpdater;
import sqelevator.IElevator;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.*;

public class EccController {

    private static final String TEXT_NOT_CONNECTED = "Not connected: wrapper is not set.";
    private static final String TEXT_MODEL_NOT_SET = "Model not set";

    protected IElevatorWrapper wrapper;
    protected EccModelUpdater updater;
    protected EccModel model;
    ScheduledExecutorService scheduledExecutor;
    private long updatePeriod = 500;

    public EccController() {
        scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
    }

    public long getUpdatePeriod() {
        return updatePeriod;
    }

    public void setUpdatePeriod(long updatePeriod) {
        this.updatePeriod = updatePeriod;
    }

    public EccModel getModel() {
        return model;
    }

    public void setModel(EccModel model) {
        this.model = model;
    }

    public boolean connect() {
        try {
            IElevator controller = (IElevator) Naming.lookup("rmi://localhost/ElevatorSim");
            wrapper = new ElevatorWrapper(controller);
            return true;
        } catch (NotBoundException e) {
            System.err.println("Remote server not started. " + e.getMessage());
        } catch (MalformedURLException e) {
            System.err.println("Invalid URL: " + e.getMessage());
        } catch (RemoteException e) {
            System.err.println("Some remote exception on connecting: " + e.getMessage());
        }
        return false;
    }

    public EccModel createModel() {
        if (wrapper == null) {
            throw new IllegalStateException(TEXT_NOT_CONNECTED);
        }

        model = new EccModelFactory(wrapper).createModel();
        return model;
    }

    protected void createUpdater() {
        updater = new EccModelUpdater(wrapper, model);
    }

    public void scheduleModelUpdater() {
        if (wrapper == null) {
            throw new IllegalStateException(TEXT_NOT_CONNECTED);
        }
        if (model == null) {
            throw new IllegalStateException(TEXT_MODEL_NOT_SET);
        }

        createUpdater();
        scheduledExecutor.scheduleAtFixedRate(() -> updater.updateModel(), updatePeriod, updatePeriod, TimeUnit.MILLISECONDS);
    }

    public void shutdownScheduler() {
        scheduledExecutor.shutdown();
    }

    public void setTargetFloor(int elevNum, int targetFloor) {
        if (wrapper == null) {
            throw new IllegalStateException(TEXT_NOT_CONNECTED);
        }
        if (model == null) {
            throw new IllegalStateException(TEXT_MODEL_NOT_SET);
        }

        model.getElevator(elevNum).setTargetFloor(targetFloor);
        wrapper.setTarget(elevNum, targetFloor);
    }

    public void setCommittedDirection(int elevNum, IElevatorWrapper.CommittedDirection direction) {
        if (wrapper == null) {
            throw new IllegalStateException(TEXT_NOT_CONNECTED);
        }
        if (model == null) {
            throw new IllegalStateException(TEXT_MODEL_NOT_SET);
        }

        model.getElevator(elevNum).setDirection(direction);
        wrapper.setCommittedDirection(elevNum, direction);
    }
}
