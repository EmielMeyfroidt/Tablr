package main;

/**
 * Interface representing a list of AbstractViews, providing methods to manage and interact
 * with the views it contains.
 */
public interface ViewList {
	/**
	 * Opens the specified view by integrating it into the current view list or interface.
	 *
	 * @param view The AbstractView instance to be opened.
	 */
	void openView(AbstractView view);

	/**
	 * Closes the specified view by removing it from the current view list or interface.
	 *
	 * @param view The AbstractView instance to be closed.
	 */
	void closeView(AbstractView view);

	/**
	 * Replaces an existing view with a new view in the view list.
	 *
	 * @param oldView The existing AbstractView instance that is to be replaced.
	 * @param newView The new AbstractView instance that will replace the old view.
	 */
	void substituteView(AbstractView oldView, AbstractView newView);

	/**
	 * Moves the specified view to a new location by the increment defined by x and y.
	 *
	 * @param view The AbstractView instance to move.
	 * @param x    horizontal increment.
	 * @param y    verical increment.
	 */
	void moveViewLocation(AbstractView view, int x, int y);

}
