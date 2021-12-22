package at.fhhagenberg.sqe.ecc;

import at.fhhagenberg.sqe.ecc.model.EccModel;
import at.fhhagenberg.sqe.ecc.model.EccModelFactory;
import at.fhhagenberg.sqe.ecc.model.EccModelUpdater;
import javafx.scene.Parent;
import sqelevator.IElevator;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EccController {

    private EccLayout view;
    private EccModel model;
    private IElevatorController wrapper;
    private EccModelUpdater updater;
    ScheduledThreadPoolExecutor scheduledExecutor;

    public boolean init()
    {
        try {
            IElevator controller = (IElevator) Naming.lookup("rmi://localhost/ElevatorSim");
            wrapper = new ElevatorController(controller);
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

    public void createModel()
    {
        model = new EccModelFactory(wrapper).createModel();
    }

    public Parent createView()
    {
        view = new EccLayout(model);
        return view;
    }

    public void scheduleModelUpdater()
    {
        scheduledExecutor = new ScheduledThreadPoolExecutor(1);
        scheduledExecutor.scheduleAtFixedRate(() -> updater.updateModel(), 500, 500, TimeUnit.MILLISECONDS);
    }
}
