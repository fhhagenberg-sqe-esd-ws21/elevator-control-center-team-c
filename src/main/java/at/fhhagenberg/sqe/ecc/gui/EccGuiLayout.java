package at.fhhagenberg.sqe.ecc.gui;

import java.util.ArrayList;
import java.util.List;

import at.fhhagenberg.sqe.ecc.model.EccModel;
import at.fhhagenberg.sqe.ecc.model.Elevator;
import at.fhhagenberg.sqe.ecc.model.Floor;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class EccGuiLayout {
	

	 private EccModel model;
     
     private int elevCnt;
     private int floorCnt;
     
     // gui elements
     private List<TextField> calls = new ArrayList<TextField>();
     private List<List<TextField>> positions = new ArrayList<List<TextField>>();
     private List<List<TextField>> stops = new ArrayList<List<TextField>>();

     private List<TextField> loads = new ArrayList<TextField>();
     private List<TextField> speeds = new ArrayList<TextField>();
     private List<TextField> doors = new ArrayList<TextField>();
     private List<ToggleButton> modes = new ArrayList<ToggleButton>();
     
     // style sheet
     private String defaultStyle = "-fx-background-color: #FFFFFF; -fx-border-radius: 0; -fx-font: 14pt \"Calibri\"; -fx-alignment: CENTER; -fx-border-style: solid;";
     private String lightGrayStyle = "-fx-background-color: #D0CECE; -fx-border-radius: 0; -fx-font: 14pt 'Calibri'; -fx-alignment: CENTER; -fx-border-style: solid;";
     private String greenStyle = "-fx-background-color: #548235; -fx-border-radius: 0; -fx-font: 14pt 'Calibri'; -fx-alignment: CENTER; -fx-border-style: solid;";
     private String lightGreenStyle = "-fx-background-color: #C6E0B4; -fx-border-radius: 0; -fx-font: 14pt 'Calibri'; -fx-alignment: CENTER; -fx-border-style: solid;";
     private String redStyle = "-fx-background-color: #C00000; -fx-border-radius: 0; -fx-font: 14pt 'Calibri'; -fx-alignment: CENTER; -fx-border-style: solid;";
     private String arrowUpStyle = "-fx-background-image: url(\"big_arrow_up.png\"); -fx-background-color: #C6E0B4; -fx-border-radius: 0; -fx-font: 14pt 'Calibri'; -fx-alignment: CENTER; -fx-border-style: solid;";
     private String arrowDownStyle = "-fx-background-image: url(\"big_arrow_down.png\"); -fx-background-color: #C6E0B4; -fx-border-radius: 0; -fx-font: 14pt 'Calibri'; -fx-alignment: CENTER; -fx-border-style: solid;";
     private String smallArrowUpStyle = "-fx-background-image: url(\"small_arrow_up.png\"); -fx-background-color: #C6E0B4; -fx-border-radius: 0; -fx-font: 14pt 'Calibri'; -fx-alignment: CENTER; -fx-border-style: solid;";
     private String smallArrowDownStyle = "-fx-background-image: url(\"small_arrow_down.png\"); -fx-background-color: #C6E0B4; -fx-border-radius: 0; -fx-font: 14pt 'Calibri'; -fx-alignment: CENTER; -fx-border-style: solid;";
     private String smallArrowUpDownStyle = "-fx-background-image: url(\"small_arrow_up_down.png\"); -fx-background-color: #C6E0B4; -fx-border-radius: 0; -fx-font: 14pt 'Calibri'; -fx-alignment: CENTER; -fx-border-style: solid;";
     
     
	
     
     public EccGuiLayout()
     {
    	 
    	elevCnt = 3;
     	floorCnt = 10;
     	
     	// init model
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
        model = new EccModel(elevators, floors);
        
        // change some values
        for(int i = 0; i < floorCnt; i++)
        {
        	model.getFloor(i).setDownButtonPressed(i % 2 == 0);
        	model.getFloor(i).setUpButtonPressed(i % 3 == 0);
        }
        for(int i = 0; i < elevCnt; i++)
        {
        	model.getElevator(i).setSpeed(i);
        	model.getElevator(i).setTargetFloor(floorCnt-i-1);
        	model.getElevator(i).setButtonPressed((i+1)%floorCnt, true);
        }
     	
        
        
        
    	 // generate gui elements
        
    	 // per floor
 		for(int i = 0; i < floorCnt; i++)
 		{
 			calls.add(new TextField());
 		}
 		// per elevator
 		for(int i = 0; i < elevCnt; i++)
 		{
 			loads.add(new TextField("kg"));
 			speeds.add(new TextField("m/s"));
 			doors.add(new TextField("Open"));
 			modes.add(new ToggleButton("Automatic"));
 			
 			if(i == 1)
 			{
 	 			modes.get(i).setDisable(true);
 			}
 		}
 		// per elevator and floor
 		for(int i = 0; i < elevCnt; i++)
 		{
 			positions.add(new ArrayList<TextField>());
 			stops.add(new ArrayList<TextField>());
 			for(int j = 0; j < floorCnt; j++)
 	 		{
 				positions.get(i).add(new TextField());
 				stops.get(i).add(new TextField());
 	 		}
 			
 		}
 		
 		// TODO: binding
 		//var testData = new EccGuiData(elevCnt);
	 	//speeds.get(1).textProperty().bind(testData.mTestProperty().asString());
 		
 		
     }
     
	 public Scene getScene()
	 {
		 
		Scene scene = new Scene(new Group());
		//scene.getStylesheets().add("stylesheet.css");
		    
		GridPane grid = new GridPane();
		
		// generate upper part
		//---------------------------------------------------
		// add headings for floors and calls
		var floorsHeading = new TextField("Floors");
		floorsHeading.setStyle(lightGrayStyle);
		var nrHeading = new TextField("Nr.");
		nrHeading.setStyle(defaultStyle);
		var callHeading = new TextField("Call");
		callHeading.setStyle(defaultStyle);
		
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
			var posHeading = new TextField("Position");
			posHeading.setStyle(defaultStyle);
			var stopHeading = new TextField("Stop");
			stopHeading.setStyle(defaultStyle);
			
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
				stops.get(j).get(i).setStyle(styleArr[rowIdx%2]);
				grid.add(stops.get(j).get(i), colIdx++, rowIdx);
			}
		}
		
		
		// generate lower part
		//---------------------------------------------------
		
		// add headings for details
		var loadHeading = new TextField("Load");
		var speedHeading = new TextField("Speed");
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
		
		// change some cells to test how it looks
		if(floorCnt > 9 && elevCnt > 2)
		{
			positions.get(0).get(9).setStyle(greenStyle);
			positions.get(0).get(8).setStyle(lightGreenStyle);
			positions.get(0).get(8).setText("Next");
			stops.get(0).get(8).setStyle(redStyle);
			stops.get(0).get(0).setStyle(redStyle);
			
			positions.get(1).get(6).setStyle(arrowDownStyle);
			positions.get(1).get(4).setStyle(lightGreenStyle);
			positions.get(1).get(4).setText("Next");
			stops.get(1).get(7).setStyle(redStyle);
			stops.get(1).get(4).setStyle(redStyle);
			
			positions.get(2).get(2).setStyle(arrowUpStyle);
			positions.get(2).get(5).setStyle(lightGreenStyle);
			positions.get(2).get(5).setText("Next");
			stops.get(2).get(5).setStyle(redStyle);
			stops.get(2).get(0).setStyle(redStyle);
			
			doors.get(0).setStyle(greenStyle);
			doors.get(1).setStyle(redStyle);
			doors.get(2).setStyle(lightGreenStyle);
			
			calls.get(8).setStyle(smallArrowDownStyle);
			calls.get(3).setStyle(smallArrowUpDownStyle);
			calls.get(1).setStyle(smallArrowUpStyle);
			calls.get(9).getStyleClass().add("test");
		}
		
		
		final VBox vbox = new VBox();
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(grid);		
		((Group) scene.getRoot()).getChildren().addAll(vbox);
		
		return scene;
	 }

	
}
