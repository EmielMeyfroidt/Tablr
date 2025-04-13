package main;

import java.awt.*;

class Window extends AbstractView implements ViewList {
	AbstractView view;

	Window(AbstractView view, TablrManager mgr, LayoutInfo layoutInfo, ViewList viewList) {
		super(mgr, layoutInfo, viewList);
		view.setViewList(this);
		this.view = view;
	}


	/**
	 * @return
	 */
	@Override
	public String getTitle() {
		return view.getTitle();
	}

	/**
	 * @param x The x-coordinate for the mouse click.
	 * @param y The y-coordinate for the mouse click.
	 */
	@Override
	public void handleDoubleClick(int x, int y) {
		view.handleDoubleClick(x, y);
		//TODO translate point
	}

	/**
	 * @param x The x-coordinate for the mouse click.
	 * @param y The y-coordinate for the mouse click.
	 */
	@Override
	public void handleSingleClick(int x, int y) {
		view.handleSingleClick(x, y);
		//TODO translate point
		//TODO detect click on close button

	}

	/**
	 *
	 */
	@Override
	public void handleEscape() {
		view.handleEscape();

	}

	/**
	 *
	 */
	@Override
	public void handleBackSpace() {
		view.handleBackSpace();
	}

	/**
	 *
	 */
	@Override
	public void handleCtrlEnter() {
		view.handleCtrlEnter();
	}

	/**
	 *
	 */
	@Override
	public void handleEnter() {
		view.handleEnter();
	}

	/**
	 *
	 */
	@Override
	public void handleDelete() {
		view.handleDelete();
	}

	/**
	 * @param keyChar The character that was typed.
	 */
	@Override
	public void handleCharTyped(char keyChar) {

	}

	@Override
	public void paint(Graphics g) {
		// TODO Paint Window and Translate g
		super.paint(g);
	}

	/**
	 * @param view
	 */
	@Override
	public void openView(AbstractView view) {

	}

	/**
	 * @param view
	 */
	@Override
	public void closeView(AbstractView view) {

	}

	/**
	 * @param oldView
	 * @param newView
	 */
	@Override
	public void substituteView(AbstractView oldView, AbstractView newView) {

	}
}
