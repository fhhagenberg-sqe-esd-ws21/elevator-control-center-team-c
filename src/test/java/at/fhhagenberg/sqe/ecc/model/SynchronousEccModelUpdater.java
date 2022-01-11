package at.fhhagenberg.sqe.ecc.model;

import at.fhhagenberg.sqe.ecc.IElevatorWrapper;
import javafx.application.Platform;

/**
 * EccModelUpdater is asynchronous, because of Platform.runLater(). Which is actually used to update the model form the
 * GUI thread. This class overrides updateModel() and implements it without Platform.runLater().
 */
public class SynchronousEccModelUpdater extends EccModelUpdater {

    public SynchronousEccModelUpdater(IElevatorWrapper controller, EccModel model) {
        super(controller, model);
    }

    @Override
    public void updateModel() {
        if (fetchNewValues()) {
            this.applyNewValues();
        }
    }
}
