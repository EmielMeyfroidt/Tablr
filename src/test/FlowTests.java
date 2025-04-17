package test;

import canvaswindow.CanvasWindow;
import main.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class FlowTests {

	private void replaySession(String sessionFile) throws InterruptedException {

		ViewManager view = new ViewManager();
		java.awt.EventQueue.invokeLater(() -> {
			MyCanvasWindow window = new MyCanvasWindow("My Canvas Window", view);
			view.addListener(window.getTablrManagerListener());
			window.show();

			CanvasWindow.replayRecording(sessionFile, window);

		});
		Thread.sleep(200000);
	}

	@Test
	public void test() throws InterruptedException {
		replaySession("SessionRecordings/test");
	}

}

