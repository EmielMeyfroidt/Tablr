package main;
//Initialize the app
public class TablrApp {
	
	public static void main(String[] args) {
		TablrManager tablrManager = new TablrManager();
		TablesMode tablesMode = new TablesMode(tablrManager);
		tablrManager.setMode(tablesMode);
		java.awt.EventQueue.invokeLater(() -> {
			MyCanvasWindow window = new MyCanvasWindow("My Canvas Window", tablrManager);
			window.show();
			});
		}
}
