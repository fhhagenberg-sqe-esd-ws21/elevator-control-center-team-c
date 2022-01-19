package at.fhhagenberg.sqe.ecc;

import at.fhhagenberg.sqe.ecc.EccController;
import at.fhhagenberg.sqe.ecc.IElevatorWrapper;
import at.fhhagenberg.sqe.ecc.model.SynchronousEccModelUpdater;
import sqelevator.IElevator;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class TestableEccController extends EccController {

    IElevator ielev;

    void setIElevator(IElevator e) {
        ielev = e;
    }

    public void setWrapper(IElevatorWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    protected IElevator establishConnection() throws MalformedURLException, NotBoundException, RemoteException {
        return ielev;
    }

    @Override
    protected void createUpdater() {
        updater = new SynchronousEccModelUpdater(wrapper, model);
    }
}
