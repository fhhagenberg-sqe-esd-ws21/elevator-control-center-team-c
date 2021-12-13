package at.fhhagenberg.sqe.ecc.model;

public class Floor {
    private boolean upButtonPressed;
    private boolean downButtonPressed;

    public boolean isUpButtonPressed() {
        return upButtonPressed;
    }

    public void setUpButtonPressed(boolean upButtonPressed) {
        this.upButtonPressed = upButtonPressed;
    }

    public boolean isDownButtonPressed() {
        return downButtonPressed;
    }

    public void setDownButtonPressed(boolean downButtonPressed) {
        this.downButtonPressed = downButtonPressed;
    }
}
