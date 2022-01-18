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

	public static void main(String[] args) {
		launch(args);
	}
	 
	@Override
	public void start(Stage stage) {
		
	    // init model
	    var elevCnt = 8;
     	var floorCnt = 12;
     	var elevators = new ArrayList<Elevator>();
    	for(int i = 0; i < elevCnt; i++)
        {
    		var elev = new Elevator(floorCnt, 10);
    		elev.setDoorState(DoorState.CLOSED);
    		elev.setButtonPressed(floorCnt-1, true);
    		elevators.add(elev);
        }
        var floors = new ArrayList<Floor>();
        for(int i = 0; i < floorCnt; i++)
        {
        	var floor = new Floor();
        	floor.setDownButtonPressed(true);
        	floor.setUpButtonPressed(true);
        	floors.add(floor);
        }
        var model = new EccModel(elevators, floors);
        
        
		// init gui with model
		var layout = new EccGuiLayout(model);
		
		stage.setTitle("Elevator Control Center");
		stage.setWidth(1500);
	    stage.setHeight(800);
	    stage.getIcons().add(new Image("icon.bmp"));
		stage.setScene(layout.getScene());
	    stage.show();
	}

}
