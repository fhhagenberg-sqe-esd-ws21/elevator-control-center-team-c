package at.fhhagenberg.sqe.ecc;

import at.fhhagenberg.sqe.ecc.model.EccModel;
import at.fhhagenberg.sqe.ecc.model.EccModelFactory;
import at.fhhagenberg.sqe.ecc.model.EccModelUpdater;
import sqelevator.IElevator;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EccController {

    protected IElevatorWrapper wrapper;
    protected EccModelUpdater updater;
    ScheduledThreadPoolExecutor scheduledExecutor;

    private long updatePeriod = 500;

    public long getUpdatePeriod() {
        return updatePeriod;
    }

    public void setUpdatePeriod(long updatePeriod) {
        this.updatePeriod = updatePeriod;
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
            throw new IllegalStateException("Not connected: wrapper is not set.");
        }

        return new EccModelFactory(wrapper).createModel();
    }

    protected void createUpdater(EccModel model) {
        updater = new EccModelUpdater(wrapper, model);
    }

    public ScheduledFuture<?> scheduleModelUpdater(EccModel model) {
        if (wrapper == null) {
            throw new IllegalStateException("Not connected: wrapper is not set.");
        }

        createUpdater(model);
        scheduledExecutor = new ScheduledThreadPoolExecutor(1);
        return scheduledExecutor.scheduleAtFixedRate(() -> updater.updateModel(), updatePeriod, updatePeriod, TimeUnit.MILLISECONDS);
    }
}
