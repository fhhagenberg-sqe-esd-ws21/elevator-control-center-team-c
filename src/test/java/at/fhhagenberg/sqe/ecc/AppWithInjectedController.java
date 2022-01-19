package at.fhhagenberg.sqe.ecc;

public class AppWithInjectedController extends App {
    public AppWithInjectedController(EccController cont) {
        controller = cont;
    }
}
