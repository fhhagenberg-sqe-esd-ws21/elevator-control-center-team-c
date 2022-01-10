package at.fhhagenberg.sqe.ecc;

import at.fhhagenberg.sqe.ecc.model.EccModel;
import at.fhhagenberg.sqe.ecc.model.EccModelFactory;
import at.fhhagenberg.sqe.ecc.model.EccModelUpdater;
import sqelevator.IElevator;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EccController {

    private EccModel model;
    private ElevatorWrapper wrapper;
    private EccModelUpdater updater;

    public boolean init()
    {
        try {
            connect();
            createModel();
            updater = new EccModelUpdater(wrapper, model);
            scheduleModelUpdater();
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

    public void reconnect() throws MalformedURLException, NotBoundException, RemoteException {
        IElevator controller = (IElevator) Naming.lookup("rmi://localhost/ElevatorSim");
        wrapper.setElevatorCenter(controller);
    }

    private void connect() throws MalformedURLException, NotBoundException, RemoteException {
        IElevator controller = (IElevator) Naming.lookup("rmi://localhost/ElevatorSim");
        wrapper = new ElevatorWrapper(controller);
    }

    private void createModel()
    {
        model = new EccModelFactory(wrapper).createModel();
    }

    private void scheduleModelUpdater()
    {
        ScheduledThreadPoolExecutor scheduledExecutor = new ScheduledThreadPoolExecutor(1);
        scheduledExecutor.scheduleAtFixedRate(() -> updater.updateModel(), 500, 500, TimeUnit.MILLISECONDS);
    }
}
