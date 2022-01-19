package at.fhhagenberg.sqe.ecc;


import at.fhhagenberg.sqe.ecc.gui.EccGuiLayout;
import javafx.scene.control.TextField;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import at.fhhagenberg.sqe.ecc.IElevatorWrapper.DoorState;
import at.fhhagenberg.sqe.ecc.model.EccModel;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sqelevator.IElevator;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Disabled
@ExtendWith(MockitoExtension.class)
@ExtendWith(ApplicationExtension.class)
class GuiTests {

	@Mock
	IElevatorWrapper wrapper;
	GuiTestController controller;

    @Start
    public void start(Stage stage) throws Exception {
		when(wrapper.getElevatorNum()).thenReturn(3);
		when(wrapper.getFloorNum()).thenReturn(4);
		when(wrapper.getElevatorCapacity(0)).thenReturn(5);
		when(wrapper.getElevatorCapacity(1)).thenReturn(5);
		when(wrapper.getElevatorCapacity(2)).thenReturn(5);

		when(wrapper.getElevatorDoorStatus(0)).thenReturn(DoorState.OPEN);
		when(wrapper.getElevatorDoorStatus(1)).thenReturn(DoorState.CLOSED);
		when(wrapper.getElevatorDoorStatus(2)).thenReturn(DoorState.CLOSING);

		controller = new GuiTestController();
		controller.setWrapper(wrapper);
		controller.setConnected(true);
		new AppWithInjectedController(controller).start(stage);
    }

    @Test
    void testUpdateSpeed(FxRobot robot) {
		when(wrapper.getElevatorSpeed(0)).thenReturn(10.0);

		Awaitility.await().until(() -> controller.getModel().getElevator(0).getSpeed(), equalTo(10.0));
		FxAssert.verifyThat("#speed0", (TextField field) -> {
			var text = field.getText();
			return text.equals("10.0");
		});
    }

	@Test
	void testSetTargetFromGui(FxRobot robot) {
		robot.clickOn("#position_e1_f3");

		verify(wrapper).setTarget(1, 3);
	}

	static class GuiTestController extends EccController {
		public void setWrapper(IElevatorWrapper wrapper) {
			this.wrapper = wrapper;
		}

		@Override
		public void connect() {

		}
	}
}