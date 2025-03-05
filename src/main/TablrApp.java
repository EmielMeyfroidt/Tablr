package main;

//Initialize the app
public class TablrApp {

	public static void main(String[] args) {
		TablrManager tablrManager = new TablrManager();
		AbstractView view = new TablesView(tablrManager);
		java.awt.EventQueue.invokeLater(() -> {
			MyCanvasWindow window = new MyCanvasWindow("My Canvas Window", view);
			window.show();
		});
	}
}
