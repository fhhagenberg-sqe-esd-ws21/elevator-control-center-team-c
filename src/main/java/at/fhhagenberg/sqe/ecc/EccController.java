package at.fhhagenberg.sqe.ecc;

import at.fhhagenberg.sqe.ecc.IElevatorWrapper.CommittedDirection;
import at.fhhagenberg.sqe.ecc.model.EccModel;
import at.fhhagenberg.sqe.ecc.model.EccModelFactory;
import at.fhhagenberg.sqe.ecc.model.EccModelUpdater;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Alert;
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
    ScheduledFuture<?> updateTaskFuture;
    ScheduledFuture<?> reconnectTaskFuture;
    private long updatePeriod = 500;
    private static final long RECONNECT_PERIOD = 1000;

    private final BooleanProperty connected = new SimpleBooleanProperty();

    public boolean isConnected() {
        return connected.get();
    }

    public BooleanProperty connectedProperty() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected.set(connected);
    }


    public EccController() {
        var executor = new ScheduledThreadPoolExecutor(1);
        executor.setRemoveOnCancelPolicy(true);
        scheduledExecutor = executor;
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

        for (int e = 0; e < model.getNumberOfElevators(); e++) {
            final var elevNum = e;
            model.getElevator(e).currentFloorProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.intValue() == 0) {
                    setCommittedDirection(elevNum, CommittedDirection.UP);
                }
                else if (newValue.intValue() == model.getNumberOfFloors()-1) {
                    setCommittedDirection(elevNum, CommittedDirection.DOWN);
                }
            });
        }
    }

    protected IElevator establishConnection() throws MalformedURLException, NotBoundException, RemoteException {
        return (IElevator) Naming.lookup("rmi://localhost/ElevatorSim");
    }

    public void connect() {
        try {
            var controller = establishConnection();
            wrapper = new ElevatorWrapper(controller);
            setConnected(true);
        } catch (NotBoundException e) {
            new Alert(Alert.AlertType.ERROR, "Remote server not started. " + e.getMessage()).showAndWait();
        } catch (MalformedURLException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid URL: " + e.getMessage()).showAndWait();
        } catch (RemoteException e) {
            new Alert(Alert.AlertType.ERROR, "Some remote exception on connecting: " + e.getMessage()).showAndWait();
        }
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
        if (updater == null) {
            if (wrapper == null) {
                throw new IllegalStateException(TEXT_NOT_CONNECTED);
            }
            if (model == null) {
                throw new IllegalStateException(TEXT_MODEL_NOT_SET);
            }

            createUpdater();
        }

        updateTaskFuture = scheduledExecutor.scheduleAtFixedRate(this::updateModel, 0, updatePeriod, TimeUnit.MILLISECONDS);
    }

    private void updateModel()
    {
        try {
            updater.updateModel();
        }
        catch (RuntimeException ex) {
            setConnected(false);
            updateTaskFuture.cancel(false);
            reconnectTaskFuture = scheduledExecutor.scheduleAtFixedRate(this::reconnect, 0, RECONNECT_PERIOD, TimeUnit.MILLISECONDS);
        }
    }

    private void reconnect()
    {
        try {
            var controller = establishConnection();
            wrapper.setElevatorCenter(controller);

            reconnectTaskFuture.cancel(false);
            updateTaskFuture = scheduledExecutor.scheduleAtFixedRate(this::updateModel, 0, updatePeriod, TimeUnit.MILLISECONDS);
            setConnected(true);
        } catch (NotBoundException | MalformedURLException | RemoteException ignored) {
            // do nothing, try again next time
        }
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

    public void setCommittedDirection(int elevNum, CommittedDirection direction) {
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
