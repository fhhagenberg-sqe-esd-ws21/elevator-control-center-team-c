package at.fhhagenberg.sqe.ecc.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import at.fhhagenberg.sqe.ecc.IElevatorWrapper.DoorState;
import at.fhhagenberg.sqe.ecc.model.EccModel;
import at.fhhagenberg.sqe.ecc.model.Elevator;
import at.fhhagenberg.sqe.ecc.model.Floor;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
		for (int i = 0; i < elevCnt; i++) {
			var elev = new Elevator(floorCnt, 10);
			elev.setDoorState(DoorState.CLOSED);
			elevators.add(elev);
		}
		var floors = new ArrayList<Floor>();
		for (int i = 0; i < floorCnt; i++) {
			floors.add(new Floor());
		}
		model = new EccModel(elevators, floors);

		// init gui with model
		layout = new EccGuiLayout(model);
		var scene = layout.getScene();
		stage.setScene(scene);
		stage.show();
	}

	@Test
	void testCalls(FxRobot robot) {
		model.getFloor(0).setUpButtonPressed(true);
		model.getFloor(1).setUpButtonPressed(true);

		TextField calls0 = robot.lookup("#calls0").<TextField>query();
		TextField calls1 = robot.lookup("#calls1").<TextField>query();

		WaitForAsyncUtils.waitForFxEvents();

		assertTrue(calls0.getStyleClass().contains("smallArrowUpGrayStyle"));
		assertTrue(calls1.getStyleClass().contains("smallArrowUpStyle"));

		model.getFloor(0).setDownButtonPressed(true);
		model.getFloor(1).setDownButtonPressed(true);

		WaitForAsyncUtils.waitForFxEvents();

		assertTrue(calls0.getStyleClass().contains("smallArrowUpDownGrayStyle"));
		assertTrue(calls1.getStyleClass().contains("smallArrowUpDownStyle"));

		model.getFloor(0).setUpButtonPressed(false);
		model.getFloor(1).setUpButtonPressed(false);

		WaitForAsyncUtils.waitForFxEvents();

		assertTrue(calls0.getStyleClass().contains("smallArrowDownGrayStyle"));
		assertTrue(calls1.getStyleClass().contains("smallArrowDownStyle"));
	}

	@Test
	void testPositions(FxRobot robot) {
		model.getElevator(0).setCurrentFloor(1);
		model.getElevator(1).setCurrentFloor(1);
		model.getElevator(0).setTargetFloor(0);

		Button position0_1 = robot.lookup("#position_e0_f1").<Button>query();
		Button position1_1 = robot.lookup("#position_e1_f1").<Button>query();
		Button position0_0 = robot.lookup("#position_e0_f0").<Button>query();
		Button position1_2 = robot.lookup("#position_e1_f2").<Button>query();

		Platform.runLater(() -> position1_2.fire());
		WaitForAsyncUtils.waitForFxEvents();

		assertTrue(position0_0.getStyleClass().contains("lightGreenStyle"));
		assertTrue(position1_2.getStyleClass().contains("lightGreenStyle"));
		assertTrue(position0_1.getStyleClass().contains("arrowDownStyle"));
		assertTrue(position1_1.getStyleClass().contains("arrowUpStyle"));
	}

	@Test
	void testStops(FxRobot robot) {
		model.getElevator(0).setButtonPressed(1, true);
		model.getElevator(1).setButtonPressed(2, true);
		model.getElevator(2).setButtonPressed(0, true);

		TextField stop_e0_f1 = robot.lookup("#stop_e0_f1").<TextField>query();
		TextField stop_e1_f2 = robot.lookup("#stop_e1_f2").<TextField>query();
		TextField stop_e2_f0 = robot.lookup("#stop_e2_f0").<TextField>query();

		WaitForAsyncUtils.waitForFxEvents();

		assertTrue(stop_e0_f1.getStyleClass().contains("redStyle"));
		assertTrue(stop_e1_f2.getStyleClass().contains("redStyle"));
		assertTrue(stop_e2_f0.getStyleClass().contains("redStyle"));
	}

	@Test
	void testLoads(FxRobot robot) {
		model.getElevator(0).setCurrentPassengerWeight(1.1);
		model.getElevator(1).setCurrentPassengerWeight(2.2);
		model.getElevator(2).setCurrentPassengerWeight(3.3);

		TextField load0 = robot.lookup("#load0").<TextField>query();
		TextField load1 = robot.lookup("#load1").<TextField>query();
		TextField load2 = robot.lookup("#load2").<TextField>query();

		WaitForAsyncUtils.waitForFxEvents();

		assertEquals("1.1", load0.getText());
		assertEquals("2.2", load1.getText());
		assertEquals("3.3", load2.getText());
	}

	@Test
	void testSpeeds(FxRobot robot) {
		model.getElevator(0).setSpeed(1.1);
		model.getElevator(1).setSpeed(2.2);
		model.getElevator(2).setSpeed(3.3);

		TextField speed0 = robot.lookup("#speed0").<TextField>query();
		TextField speed1 = robot.lookup("#speed1").<TextField>query();
		TextField speed2 = robot.lookup("#speed2").<TextField>query();

		WaitForAsyncUtils.waitForFxEvents();

		assertEquals("1.1", speed0.getText());
		assertEquals("2.2", speed1.getText());
		assertEquals("3.3", speed2.getText());
	}

	@Test
	void testDoors(FxRobot robot) {
		model.getElevator(0).setDoorState(DoorState.OPEN);
		model.getElevator(1).setDoorState(DoorState.OPENING);
		model.getElevator(2).setDoorState(DoorState.CLOSED);

		TextField door0 = robot.lookup("#door0").<TextField>query();
		TextField door1 = robot.lookup("#door1").<TextField>query();
		TextField door2 = robot.lookup("#door2").<TextField>query();

		WaitForAsyncUtils.waitForFxEvents();

		assertEquals("OPEN", door0.getText());
		assertEquals("OPENING", door1.getText());
		assertEquals("CLOSED", door2.getText());
		assertTrue(door0.getStyleClass().contains("greenStyle"));
		assertTrue(door1.getStyleClass().contains("lightGreenStyle"));
		assertTrue(door2.getStyleClass().contains("redStyle"));

		model.getElevator(0).setDoorState(DoorState.CLOSING);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals("CLOSING", door0.getText());
		assertTrue(door0.getStyleClass().contains("lightGreenStyle"));
	}

	@Test
	void testModes(FxRobot robot) {
		Button mode0 = robot.lookup("#mode0").<Button>query();

		Platform.runLater(() -> mode0.fire());
		WaitForAsyncUtils.waitForFxEvents();

		assertEquals("Automatic", mode0.getText());
		assertTrue(mode0.getStyleClass().contains("darkGrayStyle"));

		Platform.runLater(() -> mode0.fire());
		WaitForAsyncUtils.waitForFxEvents();

		assertEquals("Manual", mode0.getText());
		assertTrue(mode0.getStyleClass().contains("lightGrayStyle"));
	}
}