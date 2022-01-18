package at.fhhagenberg.sqe.ecc;

import at.fhhagenberg.sqe.ecc.EccController;
import at.fhhagenberg.sqe.ecc.IElevatorWrapper;
import at.fhhagenberg.sqe.ecc.model.SynchronousEccModelUpdater;

public class TestableEccController extends EccController {
    public void setWrapper(IElevatorWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    protected void createUpdater() {
        updater = new SynchronousEccModelUpdater(wrapper, model);
    }
}
