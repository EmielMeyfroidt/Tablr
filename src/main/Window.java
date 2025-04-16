package main;

import java.awt.*;

class Window extends AbstractView implements ViewList {
	private AbstractView view;
	private int titleOffset = 10;
	private int closeButtonWidth = 10;
	private int closeButtonX = 0;

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
		if (y > titleOffset) {
			view.handleDoubleClick(x, y - titleOffset);
		} else {
			//nothing
		}

		//TODO translate point
	}

	/**
	 * @param x The x-coordinate for the mouse click.
	 * @param y The y-coordinate for the mouse click.
	 */
	@Override
	public void handleSingleClick(int x, int y) {
		if (y > titleOffset) {
			view.handleSingleClick(x, y - titleOffset);
		} else {
			//TODO drag Window
		}

	}

	@Override
	public void handleMouseDrag(int startX, int startY, int endX, int endY) {
		if (startY > titleOffset) {
			view.handleMouseDrag(startX, startY, endX, endY);
		} else {
			getViewList().moveViewLocation(this, endX - startX, endY - startY);
		}
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
		view.handleCharTyped(keyChar);
	}


	/**
	 * @param view
	 */
	@Override
	public void openView(AbstractView view) {
		getViewList().openView(view);
	}

	/**
	 * @param view
	 */
	@Override
	public void closeView(AbstractView view) {
		getViewList().closeView(this);
	}

	/**
	 * @param oldView
	 * @param newView
	 */
	@Override
	public void substituteView(AbstractView oldView, AbstractView newView) {
		this.view = newView;
	}

	/**
	 * view is stationary in window, nothing happens
	 *
	 * @param view
	 * @param x
	 * @param y
	 */
	@Override
	public void moveViewLocation(AbstractView view, int x, int y) {
		//do nothing
	}

	/**
	 * Renders the Window on the bounds of g, then translates and bounds g before invoking paint on the underlying view
	 *
	 * @param g The Graphics context used for painting.
	 */
	@Override
	public void paint(Graphics g) {
		Rectangle bounds = g.getClipBounds();

		//clear window
		g.clearRect(0, 0, bounds.width, bounds.height);

		//paint title
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, bounds.width, titleOffset);

		g.setColor(Color.red);
		this.closeButtonX = bounds.width - closeButtonWidth;
		g.fillRect(this.closeButtonX, 0, bounds.width, titleOffset);

		//paint window border
		g.setColor(Color.black);
		g.drawRect(0, titleOffset, bounds.width - 2, bounds.height - 12);
		g.translate(0, titleOffset);

		//crop g and pass to underlying view
		this.view.paint(g.create(0, 0, bounds.width, bounds.height - titleOffset));
	}

}
