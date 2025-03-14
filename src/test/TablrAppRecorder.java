package test;

import main.AbstractView;
import main.MyCanvasWindow;
import main.TablesView;
import main.TablrManager;

/**
 * TablrApp is the main entry point for the Tablr application.
 * It initializes the application components and sets up the main window for the user interface.
 */
public class TablrAppRecorder {
    /**
	 * The main method serves as the entry point for the Tablr application.
	 *
	 * @param args execution arguments. not used.
	 */
	public static void main(String[] args) {
		String output = "SessionRecordings/" + "edit_tableName";
		TablrManager tablrManager = new TablrManager();
		AbstractView view = new TablesView(tablrManager);
		java.awt.EventQueue.invokeLater(() -> {
			MyCanvasWindow window = new MyCanvasWindow("My Canvas Window", view);
			tablrManager.addListener(window.getTablrManagerListener());
			window.recordSession(output);
			window.show();
		});
	}
}
