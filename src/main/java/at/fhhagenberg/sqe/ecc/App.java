package at.fhhagenberg.sqe.ecc;

import java.util.ArrayList;

import at.fhhagenberg.sqe.ecc.IElevatorWrapper.DoorState;
import at.fhhagenberg.sqe.ecc.gui.EccGuiLayout;
import at.fhhagenberg.sqe.ecc.model.EccModel;
import at.fhhagenberg.sqe.ecc.model.Elevator;
import at.fhhagenberg.sqe.ecc.model.Floor;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class App extends Application{

	protected EccController controller;

	public App()
	{
		controller = new EccController();
	}

	@Override
	public void start(Stage stage) {

		controller.connect();
		var model = controller.createModel();
		controller.setModel(model);
		controller.scheduleModelUpdater();

		var layout = new EccGuiLayout(model, controller);

		stage.setTitle("Elevator Control Center");
	    stage.getIcons().add(new Image("icon.bmp"));
		stage.setScene(layout.getScene());
		stage.setOnCloseRequest(event -> controller.shutdownScheduler());
	    stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
