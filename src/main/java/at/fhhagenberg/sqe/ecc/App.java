package at.fhhagenberg.sqe.ecc;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import at.fhhagenberg.sqe.ecc.gui.EccGuiLayout;
import at.fhhagenberg.sqe.ecc.model.EccModel;
import at.fhhagenberg.sqe.ecc.model.EccModelFactory;
import at.fhhagenberg.sqe.ecc.model.Elevator;
import at.fhhagenberg.sqe.ecc.model.Floor;
import javafx.application.Application;
import javafx.stage.Stage;
import sqelevator.IElevator;


public class App extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	 
	@Override
	public void start(Stage stage) {
		stage.setTitle("Elevator Control Center");
		stage.setWidth(700);
	    stage.setHeight(700);
	    
	    // init model
	    var elevCnt = 3;
     	var floorCnt = 10;
     	var elevators = new ArrayList<Elevator>();
    	for(int i = 0; i < elevCnt; i++)
        {
    		elevators.add(new Elevator(floorCnt, 7));
        }
        var floors = new ArrayList<Floor>();
        for(int i = 0; i < floorCnt; i++)
        {
        	floors.add(new Floor());
        }
        var model = new EccModel(elevators, floors);
        
		// init gui with model
		var layout = new EccGuiLayout(model);
		stage.setScene(layout.getScene());
	    stage.show();
	    stage.close();
        
        
	}

}
