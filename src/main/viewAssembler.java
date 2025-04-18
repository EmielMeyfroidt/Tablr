package main;

/**
 * Utility class for creating and assembling AbstractViews
 */
public class viewAssembler {
	/**
	 * Creates the default view for the given manager, layout information,
	 * and view list.
	 *
	 * @param manager    The TablrManager instance
	 * @param layoutInfo The LayoutInfo object that defines the layout configuration for the view.
	 * @param viewList   The ViewList instance that stores and manages the current list of views.
	 * @return An AbstractView instance.
	 */
	public static AbstractView getDefaultView(TablrManager manager, LayoutInfo layoutInfo, ViewList viewList) {
		AbstractView view = new TablesView(manager, layoutInfo, viewList);
		return new Window(view, manager, layoutInfo, viewList);
	}
}
