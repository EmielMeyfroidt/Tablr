package test;

import main.*;

import java.io.File;

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
		String name = "new_recording"; //change this to set output folder
		String dir = "SessionRecordings/" + name;
		String output = "SessionRecordings/" + name + "/" + name;
		File directory = new File(dir);
		if (!directory.exists()) {
			// Attempt to create the directory, including any necessary parent directories
			boolean created = directory.mkdirs();
			if (!created) {
				System.out.println("Failed to create directory: " + dir);
				return;
			}
		}


		if (directory.exists() && directory.isDirectory()) {
			// Delete all content
			boolean isEmptied = emptyDirectory(directory);
			if (isEmptied) {
				System.out.println("Directory emptied successfully: " + dir);
				ViewManager viewManager = new ViewManager();

				java.awt.EventQueue.invokeLater(() -> {
					MyCanvasWindow window = new MyCanvasWindow("My Canvas Window", viewManager);
					viewManager.addListener(window.getTablrManagerListener());
					window.recordSession(output);
					window.show();
				});
			} else {
				System.out.println("Failed to empty the directory: " + dir);
			}
		} else {
			System.out.println("Directory does not exist or is not a directory: " + dir);
		}
	}

	public static boolean emptyDirectory(File directory) {
		// List all files and directories inside the target directory
		File[] contents = directory.listFiles();
		if (contents != null) {
			for (File file : contents) {
				if (file.isDirectory()) {
					// Recursively delete subdirectories and their contents
					emptyDirectory(file);
				}
				if (!file.delete()) {
					System.out.println("Failed to delete " + file.getAbsolutePath());
					return false;
				}
			}
		}
		return true;
	}

}
