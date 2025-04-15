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
		view.handleDoubleClick(x, y);
		//TODO translate point
	}

	/**
	 * @param x The x-coordinate for the mouse click.
	 * @param y The y-coordinate for the mouse click.
	 */
	@Override
	public void handleSingleClick(int x, int y) {
		if (y > titleOffset) {
			view.handleSingleClick(x, y);
			//TODO translate point
		} else if (x > closeButtonX) {
			closeView(view);
		}


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
	 * Renders the Window on the bounds of g, then translates and bounds g before invoking paint on the underlying view
	 *
	 * @param g The Graphics context used for painting.
	 */
	@Override
	public void paint(Graphics g) {
		Rectangle bounds = g.getClipBounds();
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, bounds.width, titleOffset);

		g.setColor(Color.red);
		this.closeButtonX = bounds.width - closeButtonWidth;
		g.fillRect(this.closeButtonX, 0, bounds.width, titleOffset);

		g.setColor(Color.black);
		g.drawRect(0, titleOffset, bounds.width - 2, bounds.height - 12);
		g.translate(0, titleOffset);
		this.view.paint(g);
	}

}
