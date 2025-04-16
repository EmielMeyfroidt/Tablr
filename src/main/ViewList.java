package main;

public interface ViewList {
	public void openView(AbstractView view);

	public void closeView(AbstractView view);

	public void substituteView(AbstractView oldView, AbstractView newView);

	/**
	 * Move the spatial Location of view by x y
	 *
	 * @param view
	 * @param x
	 * @param y
	 */
	void moveViewLocation(AbstractView view, int x, int y);
}
