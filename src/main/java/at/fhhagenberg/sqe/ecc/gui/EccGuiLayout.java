package at.fhhagenberg.sqe.ecc.gui;

import java.util.ArrayList;
import java.util.List;
import at.fhhagenberg.sqe.ecc.IElevatorWrapper.DoorState;
import at.fhhagenberg.sqe.ecc.model.EccModel;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class EccGuiLayout {

	private EccModel model;

	private int elevCnt;
	private int floorCnt;

	// gui elements
	private List<TextField> calls = new ArrayList<>();
	private List<List<Button>> positions = new ArrayList<>();
	private List<List<TextField>> stops = new ArrayList<>();

	private List<TextField> loads = new ArrayList<>();
	private List<TextField> speeds = new ArrayList<>();
	private List<TextField> doors = new ArrayList<>();
	private List<Button> modes = new ArrayList<>();

	private String manualStr = "Manual";
	private String autoStr = "Automatic";

	public EccGuiLayout(EccModel eccModel) {
		model = eccModel;
		elevCnt = model.getNumberOfElevators();
		floorCnt = model.getNumberOfFloors();

		// generate gui elements
		for (int i = 0; i < floorCnt; i++) { // calls for each floor
			calls.add(new TextField());
		}
		// per elevator
		for (int i = 0; i < elevCnt; i++) {
			loads.add(new TextField());
			speeds.add(new TextField());
			doors.add(new TextField());

			// button for mode with event handler
			var btn = new Button(manualStr);
			final Integer elev = Integer.valueOf(i);
			btn.setOnAction(event -> {
				if (btn.getText().equals(manualStr)) {
					btn.setText(autoStr);
					btn.getStyleClass().clear();
					btn.getStyleClass().add("darkGrayStyle");
					// disable manual target selection
					for (var pos : positions.get(elev)) {
						pos.setDisable(true);
						pos.setOpacity(1);
					}
					// TODO: start automatic mode
				} else {
					btn.setText(manualStr);
					btn.getStyleClass().clear();
					btn.getStyleClass().add("lightGrayStyle");
					// enable manual target selection
					for (var pos : positions.get(elev)) {
						pos.setDisable(false);
					}
					// TODO: stop automatic mode
				}
			});
			modes.add(btn);

		}
		// per elevator and floor
		for (int i = 0; i < elevCnt; i++) {
			positions.add(new ArrayList<>());
			stops.add(new ArrayList<>());
			for (int j = 0; j < floorCnt; j++) {
				stops.get(i).add(new TextField());

				var btn = new Button();
				// event handler for each button
				final Integer elev = Integer.valueOf(i);
				final Integer floor = Integer.valueOf(j);
				btn.setOnAction(event -> {
					model.getElevator(elev).setTargetFloor(floor);
				});
				positions.get(i).add(btn);
			}
		}

		// binding gui elements with model

		// speeds, loads and door-states can be bound directly to the text fields
		for (int i = 0; i < elevCnt; i++) {
			speeds.get(i).textProperty().bind(model.getElevator(i).speedProperty().asString());
			loads.get(i).textProperty().bind(model.getElevator(i).currentPassengerWeightProperty().asString());
			doors.get(i).textProperty().bind(model.getElevator(i).doorStateProperty().asString());

			// indirect binding via custom changed listeners
			model.getElevator(i).currentFloorProperty()
					.addListener((observable, oldValue, newValue) -> positionHandler());
			model.getElevator(i).targetFloorProperty()
					.addListener((observable, oldValue, newValue) -> positionHandler());
			model.getElevator(i).doorStateProperty().addListener((observable, oldValue, newValue) -> doorHandler());

			for (int f = 0; f < floorCnt; f++) {
				model.getElevator(i).buttonPressedProperty(f)
						.addListener((observable, oldValue, newValue) -> stopHandler());
			}
		}
		for (int i = 0; i < floorCnt; i++) {
			// indirect binding via custom changed listeners
			model.getFloor(i).downButtonPressedProperty()
					.addListener((observable, oldValue, newValue) -> callHandler());
			model.getFloor(i).upButtonPressedProperty().addListener((observable, oldValue, newValue) -> callHandler());
		}

	}

	private void positionHandler() {
		for (int i = 0; i < elevCnt; i++) {
			int currentFloor = model.getElevator(i).getCurrentFloor();
			int nextFloor = model.getElevator(i).getTargetFloor();

			for (int j = 0; j < floorCnt; j++) {
				// reset
				positions.get(i).get(j).getStyleClass().clear();
				if ((floorCnt - j) % 2 == 0) // white background for even rows
				{
					positions.get(i).get(j).getStyleClass().add("defaultStyle");
				} else // gray background for odd rows
				{
					positions.get(i).get(j).getStyleClass().add("lightGrayStyle");
				}
			}
			positions.get(i).get(nextFloor).getStyleClass().clear();
			positions.get(i).get(currentFloor).getStyleClass().clear();
			positions.get(i).get(nextFloor).getStyleClass().add("lightGreenStyle");
			if (nextFloor > currentFloor) // arrow up
			{
				positions.get(i).get(currentFloor).getStyleClass().add("arrowUpStyle");
			} else if (nextFloor < currentFloor) // arrow down
			{
				positions.get(i).get(currentFloor).getStyleClass().add("arrowDownStyle");
			} else {
				positions.get(i).get(currentFloor).getStyleClass().add("greenStyle");
			}
		}
	}

	private void stopHandler() {
		for (int i = 0; i < elevCnt; i++) {
			for (int j = 0; j < floorCnt; j++) {
				stops.get(i).get(j).getStyleClass().clear();

				if (model.getElevator(i).isButtonPressed(j)) {
					stops.get(i).get(j).getStyleClass().add("redStyle");
				} else if ((floorCnt - j) % 2 == 0) // white background for even rows
				{
					stops.get(i).get(j).getStyleClass().add("defaultStyle");
				} else // gray background for even rows
				{
					stops.get(i).get(j).getStyleClass().add("lightGrayStyle");
				}
			}
		}
	}

	private void doorHandler() {
		for (int i = 0; i < elevCnt; i++) {
			DoorState state = model.getElevator(i).getDoorState();

			doors.get(i).getStyleClass().clear();
			switch (state) {
			case OPEN:
				doors.get(i).getStyleClass().add("greenStyle");
				break;
			case OPENING:
				doors.get(i).getStyleClass().add("lightGreenStyle");
				break;
			case CLOSED:
				doors.get(i).getStyleClass().add("redStyle");
				break;
			case CLOSING:
				doors.get(i).getStyleClass().add("lightGreenStyle");
				break;
			default:
				break;
			}
		}
	}

	private void callHandler() {
		for (int i = 0; i < floorCnt; i++) {
			var up = model.getFloor(i).isUpButtonPressed();
			var down = model.getFloor(i).isDownButtonPressed();
			calls.get(i).getStyleClass().clear();
			if ((floorCnt - i) % 2 == 0) // white background for even rows
			{
				if (up && down) {
					calls.get(i).getStyleClass().add("smallArrowUpDownStyle");
				} else if (up) {
					calls.get(i).getStyleClass().add("smallArrowUpStyle");
				} else if (down) {
					calls.get(i).getStyleClass().add("smallArrowDownStyle");
				} else {
					calls.get(i).getStyleClass().add("defaultStyle");
				}
			} else // gray background for odd rows
			{
				if (up && down) {
					calls.get(i).getStyleClass().add("smallArrowUpDownGrayStyle");
				} else if (up) {
					calls.get(i).getStyleClass().add("smallArrowUpGrayStyle");
				} else if (down) {
					calls.get(i).getStyleClass().add("smallArrowDownGrayStyle");
				} else {
					calls.get(i).getStyleClass().add("lightGrayStyle");
				}
			}
		}
	}

	public Scene getScene() {
		Scene scene = new Scene(new Group());
		scene.getStylesheets().add("/stylesheet.css");

		GridPane grid = new GridPane();

		// generate upper part
		// ---------------------------------------------------
		// add headings for floors and calls
		var floorsHeading = new TextField("Floors");
		floorsHeading.getStyleClass().clear();
		floorsHeading.getStyleClass().add("lightGrayStyle");
		floorsHeading.setEditable(false);
		var nrHeading = new TextField("Nr.");
		nrHeading.getStyleClass().clear();
		nrHeading.getStyleClass().add("defaultStyle");
		nrHeading.setEditable(false);
		var callHeading = new TextField("Call");
		callHeading.getStyleClass().clear();
		callHeading.getStyleClass().add("defaultStyle");
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
		for (int i = 0; i < elevCnt; i++) {
			var elevHeading = new TextField("Elevator " + i);
			elevHeading.getStyleClass().clear();
			elevHeading.getStyleClass().add("lightGrayStyle");
			elevHeading.setEditable(false);
			var posHeading = new TextField("Position");
			posHeading.getStyleClass().clear();
			posHeading.getStyleClass().add("defaultStyle");
			posHeading.setEditable(false);
			var stopHeading = new TextField("Stop");
			stopHeading.getStyleClass().clear();
			stopHeading.getStyleClass().add("defaultStyle");
			stopHeading.setEditable(false);

			int colIdx = i * 2 + 2;
			grid.add(elevHeading, colIdx, 0);
			GridPane.setColumnSpan(elevHeading, 2);
			grid.add(posHeading, colIdx, 1);
			grid.add(stopHeading, colIdx + 1, 1);

			// add column constraints to set width
			grid.getColumnConstraints().addAll(wideConstraint, mediumConstraint);
		}

		// add row for each floor
		String[] styleArr = { "defaultStyle", "lightGrayStyle" };
		for (int i = floorCnt - 1; i >= 0; i--) {
			int colIdx = 0;
			int rowIdx = floorCnt - i + 2;
			var floorNr = new TextField("" + i);
			floorNr.getStyleClass().clear();
			floorNr.getStyleClass().add(styleArr[rowIdx % 2]);
			floorNr.setEditable(false);

			grid.add(floorNr, colIdx++, rowIdx);
			calls.get(i).getStyleClass().clear();
			calls.get(i).getStyleClass().add(styleArr[rowIdx % 2]);
			calls.get(i).setEditable(false);
			calls.get(i).setId("calls" + i);
			grid.add(calls.get(i), colIdx++, rowIdx);

			for (int j = 0; j < elevCnt; j++) {
				positions.get(j).get(i).getStyleClass().add(styleArr[rowIdx % 2]);
				positions.get(j).get(i).setPrefWidth(100);
				positions.get(j).get(i).setId("position_e" + j + "_f" + i);
				grid.add(positions.get(j).get(i), colIdx++, rowIdx);

				stops.get(j).get(i).getStyleClass().add(styleArr[rowIdx % 2]);
				stops.get(j).get(i).setEditable(false);
				stops.get(j).get(i).setId("stop_e" + j + "_f" + i);
				grid.add(stops.get(j).get(i), colIdx++, rowIdx);
			}
		}

		// generate lower part
		// ---------------------------------------------------

		// add headings for details
		var loadHeading = new TextField("Load[kg]");
		var speedHeading = new TextField("Speed[m/s]");
		var doorsHeading = new TextField("Doors");
		var modeHeading = new TextField("Mode");

		int rowIdx = floorCnt + 3;

		loadHeading.getStyleClass().clear();
		loadHeading.getStyleClass().add(styleArr[rowIdx % 2]);
		loadHeading.setEditable(false);
		grid.add(loadHeading, 0, rowIdx++);
		GridPane.setColumnSpan(loadHeading, 2);

		speedHeading.getStyleClass().clear();
		speedHeading.getStyleClass().add(styleArr[rowIdx % 2]);
		speedHeading.setEditable(false);
		grid.add(speedHeading, 0, rowIdx++);
		GridPane.setColumnSpan(speedHeading, 2);

		doorsHeading.getStyleClass().clear();
		doorsHeading.getStyleClass().add(styleArr[rowIdx % 2]);
		doorsHeading.setEditable(false);
		grid.add(doorsHeading, 0, rowIdx++);
		GridPane.setColumnSpan(doorsHeading, 2);

		modeHeading.getStyleClass().clear();
		modeHeading.getStyleClass().add(styleArr[rowIdx % 2]);
		modeHeading.setEditable(false);
		grid.add(modeHeading, 0, rowIdx);
		GridPane.setColumnSpan(modeHeading, 2);

		// add details for each elevator
		for (int i = 0; i < elevCnt; i++) {
			int colIdx = 2 * i + 2;
			rowIdx = floorCnt + 3;

			loads.get(i).getStyleClass().add(styleArr[rowIdx % 2]);
			loads.get(i).setId("load" + i);
			grid.add(loads.get(i), colIdx, rowIdx++);
			GridPane.setColumnSpan(loads.get(i), 2);

			speeds.get(i).getStyleClass().add(styleArr[rowIdx % 2]);
			speeds.get(i).setId("speed" + i);
			grid.add(speeds.get(i), colIdx, rowIdx++);
			GridPane.setColumnSpan(speeds.get(i), 2);

			doors.get(i).getStyleClass().add(styleArr[rowIdx % 2]);
			doors.get(i).setId("door" + i);
			grid.add(doors.get(i), colIdx, rowIdx++);
			GridPane.setColumnSpan(doors.get(i), 2);

			modes.get(i).getStyleClass().add("lightGrayStyle");
			modes.get(i).setPrefWidth(165);
			modes.get(i).setId("mode" + i);
			grid.add(modes.get(i), colIdx, rowIdx);
			GridPane.setColumnSpan(modes.get(i), 2);
		}
		// refresh to show initial state
		positionHandler();
		stopHandler();
		callHandler();
		doorHandler();

		grid.setPadding(new Insets(10, 0, 0, 10));
		((Group) scene.getRoot()).getChildren().add(grid);

		return scene;
	}
}
