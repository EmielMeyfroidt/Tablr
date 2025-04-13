package main;

//import javax.swing.*;

/**
 * TablrApp is the main entry point for the Tablr application.
 * It initializes the application components.
 */
public class TablrApp {
	/**
	 * The main method serves as the entry point for the Tablr application.
	 *
	 * @param args execution arguments. not used.
	 */
	public static void main(String[] args) {
		ViewManager viewManager = new ViewManager();

		java.awt.EventQueue.invokeLater(() -> {
			MyCanvasWindow window = new MyCanvasWindow("My Canvas Window", viewManager);
			viewManager.addListener(window.getTablrManagerListener());
			window.show();
		});
	}
}
