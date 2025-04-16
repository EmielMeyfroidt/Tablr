package main;

public interface ViewList {
	void openView(AbstractView view);


	void closeView(AbstractView view);

	void substituteView(AbstractView oldView, AbstractView newView);

	/**
	 * Move the spatial Location of view by x y
	 *
	 * @param view
	 * @param x
	 * @param y
	 */
	void moveViewLocation(AbstractView view, int x, int y);

}
