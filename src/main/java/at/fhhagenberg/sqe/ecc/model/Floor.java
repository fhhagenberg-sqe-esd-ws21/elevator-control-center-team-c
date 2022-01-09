package at.fhhagenberg.sqe.ecc.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Floor {
    private final BooleanProperty upButtonPressed = new SimpleBooleanProperty();
    private final BooleanProperty downButtonPressed = new SimpleBooleanProperty();

    public boolean isUpButtonPressed() { return upButtonPressed.get(); }
    public void setUpButtonPressed(boolean upButtonPressed) {
        this.upButtonPressed.set(upButtonPressed);
    }
    public BooleanProperty upButtonPressedProperty() { return upButtonPressed; }

    public boolean isDownButtonPressed() {
        return downButtonPressed.get();
    }
    public void setDownButtonPressed(boolean downButtonPressed) { this.downButtonPressed.set(downButtonPressed); }
    public BooleanProperty downButtonPressedProperty() { return downButtonPressed; }
}
