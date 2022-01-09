package at.fhhagenberg.sqe.ecc;

import at.fhhagenberg.sqe.ecc.gui.EccGuiLayout;

import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	 
	@Override
	public void start(Stage stage) {
		stage.setTitle("Elevator Control Center");
		stage.setWidth(700);
	    stage.setHeight(700);
		var layout = new EccGuiLayout();
		stage.setScene(layout.getScene());
	    stage.show();
	}

}
