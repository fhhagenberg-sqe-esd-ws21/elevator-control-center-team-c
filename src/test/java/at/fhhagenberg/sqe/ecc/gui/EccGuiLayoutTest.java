package at.fhhagenberg.sqe.ecc.gui;


import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import at.fhhagenberg.sqe.ecc.IElevatorWrapper.DoorState;
import at.fhhagenberg.sqe.ecc.model.EccModel;
import at.fhhagenberg.sqe.ecc.model.Elevator;
import at.fhhagenberg.sqe.ecc.model.Floor;
import javafx.scene.control.Button;
import javafx.stage.Stage;


@ExtendWith(ApplicationExtension.class)
class EccGuiLayoutTest {
	
	EccGuiLayout layout;
	EccModel model;
	int elevCnt = 3;
 	int floorCnt = 3;

    @Start
    public void start(Stage stage) throws Exception {	    
	    // init model	    
     	var elevators = new ArrayList<Elevator>();
    	for(int i = 0; i < elevCnt; i++)
        {
    		var elev = new Elevator(floorCnt, 10);
    		elev.setDoorState(DoorState.CLOSED);
    		elevators.add(elev);
        }
        var floors = new ArrayList<Floor>();
        for(int i = 0; i < floorCnt; i++)
        {
        	floors.add(new Floor());
        }
        model = new EccModel(elevators, floors);
        
		// init gui with model
		layout = new EccGuiLayout(model);
		stage.setWidth(700);
	    stage.setHeight(500);
		stage.setScene(layout.getScene());
	    stage.show();
    }

    @Test
    void testForCoverage(FxRobot robot) {
		
		// change model for test coverage
		model.getElevator(0).setCurrentFloor(1);
		model.getElevator(1).setCurrentFloor(1);
		model.getElevator(2).setCurrentFloor(1);
 		model.getElevator(0).setButtonPressed(0, true);
 		model.getElevator(0).setDoorState(DoorState.CLOSED);
 		model.getElevator(0).setDoorState(DoorState.OPENING);
 		model.getElevator(0).setDoorState(DoorState.OPEN);
 		model.getElevator(0).setDoorState(DoorState.CLOSING);
 		model.getFloor(0).setDownButtonPressed(true);
 		model.getFloor(0).setUpButtonPressed(true);
 		model.getFloor(0).setDownButtonPressed(false);
 		model.getFloor(0).setUpButtonPressed(false);
 		model.getFloor(1).setDownButtonPressed(true);
 		model.getFloor(1).setUpButtonPressed(true);
 		model.getFloor(1).setDownButtonPressed(false);
 		model.getFloor(1).setUpButtonPressed(false);
 		model.getFloor(2).setDownButtonPressed(true);
 		model.getFloor(2).setUpButtonPressed(true);
 		model.getElevator(0).setButtonPressed(0, false);
 		model.getElevator(0).setButtonPressed(1, true);
 		model.getElevator(0).setSpeed(4711);
 		/*
    	robot.clickOn("#position_e0_f0");
    	robot.clickOn("#position_e1_f1");
    	robot.clickOn("#position_e2_f2");
    	robot.clickOn("#mode0");
    	//var text = robot.lookup("#mode0").queryText();
    	//assertEquals("Automatic",text);
    	FxAssert.verifyThat("#mode0", 
    			(Button btn) -> btn.getText().equals("Automatic"));
    	robot.clickOn("#mode1");
    	robot.clickOn("#mode2");
    	robot.clickOn("#mode0");
    	robot.clickOn("#mode1");
    	robot.clickOn("#mode2");
    	*/
    }
}