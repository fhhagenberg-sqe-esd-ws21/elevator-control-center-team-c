package at.fhhagenberg.sqe.ecc.gui;

import java.util.ArrayList;
import java.util.List;


import at.fhhagenberg.sqe.ecc.IElevatorWrapper.DoorState;
import at.fhhagenberg.sqe.ecc.model.EccModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class EccGuiLayout {
	

	 private EccModel model;
     
     private int elevCnt;
     private int floorCnt;
     
     // gui elements
     private List<TextField> calls = new ArrayList<TextField>();
     private List<List<Button>> positions = new ArrayList<List<Button>>();
     private List<List<TextField>> stops = new ArrayList<List<TextField>>();

     private List<TextField> loads = new ArrayList<TextField>();
     private List<TextField> speeds = new ArrayList<TextField>();
     private List<TextField> doors = new ArrayList<TextField>();
     private List<Button> modes = new ArrayList<Button>();
     
     // style sheet
     private String defaultStyle = "-fx-background-color: #FFFFFF; -fx-border-radius: 0; -fx-font: 14pt \"Calibri\"; -fx-alignment: CENTER; -fx-border-style: solid;";
     private String lightGrayStyle = "-fx-background-color: #D0CECE; -fx-border-radius: 0; -fx-font: 14pt 'Calibri'; -fx-alignment: CENTER; -fx-border-style: solid;";
     private String darkGrayStyle = "-fx-text-fill: white; -fx-background-color: #595959; -fx-border-radius: 0; -fx-font: 14pt 'Calibri'; -fx-alignment: CENTER; -fx-border-style: solid;";
     private String greenStyle = "-fx-text-fill: white; -fx-background-color: #548235; -fx-border-radius: 0; -fx-font: 14pt 'Calibri'; -fx-alignment: CENTER; -fx-border-style: solid;";
     private String lightGreenStyle = "-fx-background-color: #C6E0B4; -fx-border-radius: 0; -fx-font: 14pt 'Calibri'; -fx-alignment: CENTER; -fx-border-style: solid;";
     private String redStyle = "-fx-text-fill: white; -fx-background-color: #C00000; -fx-border-radius: 0; -fx-font: 14pt 'Calibri'; -fx-alignment: CENTER; -fx-border-style: solid;";
     private String arrowUpStyle = "-fx-background-image: url(\"big_arrow_up.png\"); -fx-background-color: #C6E0B4; -fx-border-radius: 0; -fx-font: 14pt 'Calibri'; -fx-alignment: CENTER; -fx-border-style: solid;";
     private String arrowDownStyle = "-fx-background-image: url(\"big_arrow_down.png\"); -fx-background-color: #C6E0B4; -fx-border-radius: 0; -fx-font: 14pt 'Calibri'; -fx-alignment: CENTER; -fx-border-style: solid;";
     private String smallArrowUpStyle = "-fx-background-image: url(\"small_arrow_up_white.png\"); -fx-background-color: #C6E0B4; -fx-border-radius: 0; -fx-font: 14pt 'Calibri'; -fx-alignment: CENTER; -fx-border-style: solid;";
     private String smallArrowDownStyle = "-fx-background-image: url(\"small_arrow_down_white.png\"); -fx-background-color: #C6E0B4; -fx-border-radius: 0; -fx-font: 14pt 'Calibri'; -fx-alignment: CENTER; -fx-border-style: solid;";
     private String smallArrowUpDownStyle = "-fx-background-image: url(\"small_arrow_up_down_white.png\"); -fx-background-color: #C6E0B4; -fx-border-radius: 0; -fx-font: 14pt 'Calibri'; -fx-alignment: CENTER; -fx-border-style: solid;";
     private String smallArrowUpGrayStyle = "-fx-background-image: url(\"small_arrow_up_gray.png\"); -fx-background-color: #C6E0B4; -fx-border-radius: 0; -fx-font: 14pt 'Calibri'; -fx-alignment: CENTER; -fx-border-style: solid;";
     private String smallArrowDownGrayStyle = "-fx-background-image: url(\"small_arrow_down_gray.png\"); -fx-background-color: #C6E0B4; -fx-border-radius: 0; -fx-font: 14pt 'Calibri'; -fx-alignment: CENTER; -fx-border-style: solid;";
     private String smallArrowUpDownGrayStyle = "-fx-background-image: url(\"small_arrow_up_down_gray.png\"); -fx-background-color: #C6E0B4; -fx-border-radius: 0; -fx-font: 14pt 'Calibri'; -fx-alignment: CENTER; -fx-border-style: solid;";
     
     
	
     
	public EccGuiLayout(EccModel eccModel)
     {
		model = eccModel;
		elevCnt = model.getNumberOfElevators();
		floorCnt = model.getNumberOfFloors();        
        
    	// generate gui elements
 		for(int i = 0; i < floorCnt; i++)
 		{	// calls for each floor
 			calls.add(new TextField());
 		}
 		// per elevator
 		for(int i = 0; i < elevCnt; i++)
 		{
 			loads.add(new TextField());
 			speeds.add(new TextField());
 			doors.add(new TextField());
 			
 			// button for mode with event handler
 			var btn = new Button("Manual");
 			final Integer elev = Integer.valueOf(i);
			btn.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	 		    @Override public void handle(ActionEvent e) {
	 				if(btn.getText() == "Manual")
	 				{
	 					btn.setText("Automatic");
	 		 			btn.setStyle(darkGrayStyle);
	 		 			// disable manual target selection
	 		 			for(var pos : positions.get(elev))
	 		 			{
	 		 				pos.setDisable(true);
	 		 				pos.setOpacity(1);	 		 				
	 		 			}
	 		 			// TODO: start automatic mode	 		 				 		 			
	 				}
	 				else
	 				{
	 					btn.setText("Manual");
	 		 			btn.setStyle(defaultStyle);
	 		 			// enable manual target selection
	 		 			for(var pos : positions.get(elev))
	 		 			{
	 		 				pos.setDisable(false);		 				
	 		 			}
	 		 			//TODO: stop automatic mode	 					
	 				}
	 		    }
	 		});
 			modes.add(btn);
 			
 		}
 		// per elevator and floor
 		for(int i = 0; i < elevCnt; i++)
 		{
 			positions.add(new ArrayList<Button>());
 			stops.add(new ArrayList<TextField>());
 			for(int j = 0; j < floorCnt; j++)
 	 		{
 				stops.get(i).add(new TextField());
 				
 				var btn = new Button();
 				// event handler for each button
				final Integer elev = Integer.valueOf(i);
 		    	final Integer floor = Integer.valueOf(j);
 				btn.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
 		 		    @Override public void handle(ActionEvent e) {
 		 				model.getElevator(elev).setTargetFloor(floor);
 		 		    }
 		 		});
 				positions.get(i).add(btn);
 	 		} 			
 		}
 		
 		// binding gui elements with model
 		
 		// speeds, loads and door-states can be bound directly to the text fields
 		for(int i = 0; i < elevCnt; i++)
 		{
	 		speeds.get(i).textProperty().bind(model.getElevator(i).speedProperty().asString());
	 		loads.get(i).textProperty().bind(model.getElevator(i).currentPassengerWeightProperty().asString());
	 		doors.get(i).textProperty().bind(model.getElevator(i).doorStateProperty().asString());
	 		
	 		// indirect binding via custom changed listeners
	 		model.getElevator(i).currentFloorProperty().addListener((observable, oldValue, newValue) -> positionHandler());
	 		model.getElevator(i).targetFloorProperty().addListener((observable, oldValue, newValue) -> positionHandler());
	 		model.getElevator(i).buttonsPressedProperty().addListener((observable, oldValue, newValue) -> stopHandler());
	 		model.getElevator(i).doorStateProperty().addListener((observable, oldValue, newValue) -> doorHandler());
 		}
 		for(int i = 0; i < floorCnt; i++)
 		{
 			// indirect binding via custom changed listeners
 			model.getFloor(i).downButtonPressedProperty().addListener((observable, oldValue, newValue) -> callHandler());
 			model.getFloor(i).upButtonPressedProperty().addListener((observable, oldValue, newValue) -> callHandler());
 		}    

     }
     
     private void positionHandler()
	 {
    	 for(int i = 0; i < elevCnt; i++)
	     {
	    	 int currentFloor = model.getElevator(i).getCurrentFloor();
	    	 int nextFloor = model.getElevator(i).getTargetFloor();
	    	 
	    	 for(int j = 0; j < floorCnt; j++)
	         {
	    		 // reset
	    		 if(j%2==0) // white background for even rows
		    	 {
	    			 positions.get(i).get(j).setStyle(defaultStyle);
		    	 }
	    		 else // gray background for odd rows
	    		 {
	    			 positions.get(i).get(j).setStyle(lightGrayStyle);
	    		 }
	         }
	    	 positions.get(i).get(nextFloor).setStyle(lightGreenStyle);	 
	    	 if(nextFloor > currentFloor) // arrow up
	    	 {
	    		 positions.get(i).get(currentFloor).setStyle(arrowUpStyle);
	    	 }
	    	 else if(nextFloor < currentFloor) // arrow down
	    	 {
	    		 positions.get(i).get(currentFloor).setStyle(arrowDownStyle);
	    	 }
	    	 else
	    	 {
	    		 positions.get(i).get(currentFloor).setStyle(greenStyle);
	    	 }
	     }
     }
     private void stopHandler()
	 {
    	 for(int i = 0; i < elevCnt; i++)
	     {	    	 
	    	 for(int j = 0; j < floorCnt; j++)
	         {

		    	 if(model.getElevator(i).isButtonPressed(j))
		    	 {
		    		 stops.get(i).get(j).setStyle(redStyle);
		    	 }
		    	 else if(j%2==0) // white background for even rows
		    	 {
		    		 stops.get(i).get(j).setStyle(defaultStyle);
		    	 }
		    	 else // gray background for even rows
		    	 {
		    		 stops.get(i).get(j).setStyle(lightGrayStyle);
		    	 }
	         }   	 
	     }
	 }
     private void doorHandler()
	 {
    	 for(int i = 0; i < elevCnt; i++)
    	 {
    		 DoorState state = model.getElevator(i).getDoorState();
    		 switch(state)
    		 {
				 case OPEN:
					 doors.get(i).setStyle(greenStyle);
					 break;
				 case OPENING:
					 doors.get(i).setStyle(lightGreenStyle);
					 break;
				 case CLOSED:
					 doors.get(i).setStyle(redStyle);
					 break;
				 case CLOSING:
					 doors.get(i).setStyle(greenStyle);
					 break;
				 default:
					break;    			 
    		 }
    	 }
    	 
	 }
     private void callHandler()
	 {
    	 for(int i = 0; i < floorCnt; i++)
    	 {
    		 var up = model.getFloor(i).isUpButtonPressed();
    		 var down = model.getFloor(i).isDownButtonPressed();
    		 if(i%2==0) // white background for even rows
    		 {
	    		 if(up && down)
	    		 {
	    			 calls.get(i).setStyle(smallArrowUpDownStyle);
	    		 }
	    		 else if(up)
	    		 {
	    			 calls.get(i).setStyle(smallArrowUpStyle);
	    		 }
	    		 else if(down)
	    		 {
	    			 calls.get(i).setStyle(smallArrowDownStyle);    			 
	    		 }
	    		 else
	    		 {
	    			 calls.get(i).setStyle(defaultStyle);    			 
	    		 }
    		 }
    		 else // gray background for odd rows
    		 {
	    		 if(up && down)
	    		 {
	    			 calls.get(i).setStyle(smallArrowUpDownGrayStyle);
	    		 }
	    		 else if(up)
	    		 {
	    			 calls.get(i).setStyle(smallArrowUpGrayStyle);
	    		 }
	    		 else if(down)
	    		 {
	    			 calls.get(i).setStyle(smallArrowDownGrayStyle);    			 
	    		 }
	    		 else
	    		 {
	    			 calls.get(i).setStyle(lightGrayStyle);    			 
	    		 }
    		 }
    	 }
	 }
	 public Scene getScene()
	 {		 
		Scene scene = new Scene(new Group());
		    
		GridPane grid = new GridPane();
		
		// generate upper part
		//---------------------------------------------------
		// add headings for floors and calls
		var floorsHeading = new TextField("Floors");
		floorsHeading.setStyle(lightGrayStyle);
		floorsHeading.setEditable(false);
		var nrHeading = new TextField("Nr.");
		nrHeading.setStyle(defaultStyle);
		nrHeading.setEditable(false);
		var callHeading = new TextField("Call");
		callHeading.setStyle(defaultStyle);
		callHeading.setEditable(false);
		
		grid.add(floorsHeading, 0, 0);
		GridPane.setColumnSpan(floorsHeading, 2);
		grid.add(nrHeading, 0, 1);
		grid.add(callHeading, 1, 1);
		

		// add column constraints to set width
		var narrowConstraint = new ColumnConstraints(55);
		var mediumConstraint = new ColumnConstraints(65);
		var wideConstraint = new ColumnConstraints(100);
		grid.getColumnConstraints().addAll(narrowConstraint, narrowConstraint);
		
		// add headings for each elevator
		for(int i = 0; i < elevCnt; i++)
		{
			var elevHeading = new TextField("Elevator " + i);
			elevHeading.setStyle(lightGrayStyle);
			elevHeading.setEditable(false);
			var posHeading = new TextField("Position");
			posHeading.setStyle(defaultStyle);
			posHeading.setEditable(false);
			var stopHeading = new TextField("Stop");
			stopHeading.setStyle(defaultStyle);
			stopHeading.setEditable(false);
			
			int colIdx = i*2+2;
			grid.add(elevHeading, colIdx, 0);
			GridPane.setColumnSpan(elevHeading, 2);
			grid.add(posHeading, colIdx, 1);
			grid.add(stopHeading, colIdx+1, 1);
			
			// add column constraints to set width
			grid.getColumnConstraints().addAll(wideConstraint, mediumConstraint);
		}
		
		// add row for each floor
		String styleArr[] = {defaultStyle, lightGrayStyle};
		for(int i = floorCnt-1; i >=0; i--)
		{
			int colIdx = 0;
			int rowIdx = floorCnt - i + 2;
			var floorNr = new TextField("" + i);
			floorNr.setStyle(styleArr[rowIdx%2]);
			
			grid.add(floorNr, colIdx++, rowIdx);
			calls.get(i).setStyle(styleArr[rowIdx%2]);
			grid.add(calls.get(i), colIdx++, rowIdx);
			
			for(int j = 0; j < elevCnt; j++)
			{
				positions.get(j).get(i).setStyle(styleArr[rowIdx%2]);
				grid.add(positions.get(j).get(i), colIdx++, rowIdx);
				positions.get(j).get(i).setPrefWidth(100);
				
				stops.get(j).get(i).setStyle(styleArr[rowIdx%2]);
				grid.add(stops.get(j).get(i), colIdx++, rowIdx);
			}
		}
		
		
		// generate lower part
		//---------------------------------------------------
		
		// add headings for details
		var loadHeading = new TextField("Load[kg]");
		var speedHeading = new TextField("Speed[m/s]");
		var doorsHeading = new TextField("Doors");
		var modeHeading = new TextField("Mode");
		
		int rowIdx = floorCnt+3;
		
		loadHeading.setStyle(styleArr[rowIdx%2]);		
		grid.add(loadHeading, 0, rowIdx++);
		GridPane.setColumnSpan(loadHeading, 2);
		
		speedHeading.setStyle(styleArr[rowIdx%2]);
		grid.add(speedHeading, 0, rowIdx++);
		GridPane.setColumnSpan(speedHeading, 2);
		
		doorsHeading.setStyle(styleArr[rowIdx%2]);
		grid.add(doorsHeading, 0, rowIdx++);
		GridPane.setColumnSpan(doorsHeading, 2);
		
		modeHeading.setStyle(styleArr[rowIdx%2]);
		grid.add(modeHeading, 0, rowIdx);
		GridPane.setColumnSpan(modeHeading, 2);
		
		
		// add details for each elevator
		for(int i = 0; i < elevCnt; i++)
		{
			int colIdx = 2*i+2;
			rowIdx = floorCnt+3;
			

			loads.get(i).setStyle(styleArr[rowIdx%2]);
			grid.add(loads.get(i), colIdx, rowIdx++);
			GridPane.setColumnSpan(loads.get(i), 2);

			speeds.get(i).setStyle(styleArr[rowIdx%2]);
			grid.add(speeds.get(i), colIdx, rowIdx++);
			GridPane.setColumnSpan(speeds.get(i), 2);

			doors.get(i).setStyle(styleArr[rowIdx%2]);
			grid.add(doors.get(i), colIdx, rowIdx++);
			GridPane.setColumnSpan(doors.get(i), 2);

			modes.get(i).setStyle(styleArr[rowIdx%2]);
			grid.add(modes.get(i), colIdx, rowIdx);
			GridPane.setColumnSpan(modes.get(i), 2);
			modes.get(i).setPrefWidth(165);
		}
		
		// change model for test coverage
		model.getElevator(0).setCurrentFloor(0);
    	model.getElevator(0).setTargetFloor(0);
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
 		model.getElevator(0).setButtonPressed(0, false);
 		model.getElevator(0).setButtonPressed(1, true);
 		model.getElevator(0).setSpeed(4711);
 		positions.get(0).get(0).fire();
 		positions.get(0).get(1).fire();
 		modes.get(0).fire();
 		modes.get(0).fire();

		
		final VBox vbox = new VBox();
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(grid);		
		((Group) scene.getRoot()).getChildren().addAll(vbox);
		
		return scene;
	 }
}
