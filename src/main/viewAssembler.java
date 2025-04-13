package main;

public class viewAssembler {
	public static AbstractView getDefaultView(TablrManager manager, LayoutInfo layoutInfo, ViewList viewList) {
		AbstractView view = new TablesView(manager, layoutInfo, viewList);
		Window window = new Window(view, manager, layoutInfo, viewList);
		return new Window(view, manager, layoutInfo, viewList);
	}
}
