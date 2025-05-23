package main;

import java.awt.*;

/**
 * Represents a resizable and draggable window in a graphical user interface.
 * The Window acts as a wrapper for an underlying AbstractView and adds
 * functionality such as title bar rendering and close button functionality.
 */
class Window extends AbstractView implements ViewList {
	private AbstractView view;
	private final int titleOffset = 10;
	private final int closeButtonWidth = 10;
	private int closeButtonX = 0;

	Window(AbstractView view, TablrManager mgr, LayoutInfo layoutInfo, ViewList viewList) {
		super(mgr, layoutInfo, viewList);
		view.setViewList(this);
		this.view = view;
	}


	/**
	 * @return underlying views title
	 */
	@Override
	public String getTitle() {
		return view.getTitle();
	}

	/**
	 * If the click is not on the title bar forward to underlying view
	 *
	 * @param x The x-coordinate for the mouse click.
	 * @param y The y-coordinate for the mouse click.
	 */
	@Override
	public void handleDoubleClick(int x, int y) {
		if (y > titleOffset) {
			view.handleDoubleClick(x, y - titleOffset);
		}
	}

	/**
	 * If the click is not on the title bar forward to underlying view
	 * if the click is in the title bar and on the  close button, close this view in getViewList().
	 *
	 * @param x The x-coordinate for the mouse click.
	 * @param y The y-coordinate for the mouse click.
	 */
	@Override
	public void handleSingleClick(int x, int y) {
		if (y > titleOffset) {
			view.handleSingleClick(x, y - titleOffset);
		} else if (x >= closeButtonX && x <= closeButtonX + closeButtonWidth) {
			getViewList().closeView(this);
		}

	}

	/**
	 * Handles the dragging of the mouse within the window. If the drag starts on the title bar,
	 * it moves the window position; otherwise, it forwards the drag event to the underlying view.
	 *
	 * @param startX The x-coordinate where the drag starts.
	 * @param startY The y-coordinate where the drag starts.
	 * @param endX   The x-coordinate where the drag ends.
	 * @param endY   The y-coordinate where the drag ends.
	 */
	@Override
	public void handleMouseDrag(int startX, int startY, int endX, int endY) {
		if (startY > titleOffset) {
			view.handleMouseDrag(startX, startY, endX, endY);
		} else {
			getViewList().moveViewLocation(this, endX - startX, endY - startY);
		}
	}

	/**
	 * Forwards the handleEscape() event to the underlying view.
	 */
	@Override
	public void handleEscape() {
		view.handleEscape();

	}

	/**
	 * Forwards the handleBackSpace() event to the underlying view.
	 */
	@Override
	public void handleBackSpace() {
		view.handleBackSpace();
	}

	/**
	 * Forwards the handleCtrlEnter() event to the underlying view.
	 */
	@Override
	public void handleCtrlEnter() {
		view.handleCtrlEnter();
	}

	/**
	 * Forwards the handleEnter() event to the underlying view.
	 */
	@Override
	public void handleEnter() {
		view.handleEnter();
	}

	/**
	 * Forwards the handleDelete() event to the underlying view.
	 */
	@Override
	public void handleDelete() {
		view.handleDelete();
	}

	/**
	 * Forwards the handleCharTyped() event to the underlying view.
	 *
	 * @param keyChar The character that was typed.
	 */
	@Override
	public void handleCharTyped(char keyChar) {
		view.handleCharTyped(keyChar);
	}

	/**
	 * this method is invoked if view is killed and should be disposed.
	 * Returns true if this object is also irrelevant as a result.
	 *
	 * @param view that is to be disposed
	 * @return True if this object is to be disposed also
	 */
	@Override
	public boolean handleDeadView(AbstractView view) {
		boolean closeEmptyWindow = false;
		if (this.view == view || this.view.handleDeadView(view)) {
			this.view = null;
			return closeEmptyWindow;
		}
		return false;
	}

	/**
	 * wraps view in a window and adds it to getviewlist()
	 *
	 * @param view view to be opened
	 */
	@Override
	public void openView(AbstractView view) {
		getViewList().openView(new Window(view, getMgr(), getLayoutInfo(), getViewList()));
	}

	/**
	 * removes view from getviewlist()
	 *
	 * @param view view to be removed
	 */
	@Override
	public void closeView(AbstractView view) {
		getViewList().closeView(view);
	}

	/**
	 * substitutes view in getViewList()
	 *
	 * @param oldView The existing AbstractView instance that is to be replaced.
	 * @param newView The new AbstractView instance that will replace the old view.
	 */
	@Override
	public void substituteView(AbstractView oldView, AbstractView newView) {
		this.view = newView;
	}

	/**
	 * moves the underlying view.
	 * has no effect in Window.
	 *
	 * @param view The AbstractView instance to move.
	 * @param x    horizontal increment.
	 * @param y    verical increment.
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
		if (view == null) {
			getViewList().closeView(this);
		}
		Rectangle bounds = g.getClipBounds();

		//clear window
		g.clearRect(0, 0, bounds.width, bounds.height);
		g.setColor(Color.white);
		g.fillRect(0, 0, bounds.width, bounds.height);
		//paint title
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, bounds.width, titleOffset);
		g.setColor(Color.black);


		g.setColor(Color.red);
		this.closeButtonX = bounds.width - closeButtonWidth;
		g.fillRect(this.closeButtonX, 0, bounds.width, titleOffset);

		//paint window border
		g.setColor(Color.black);
		g.drawRect(0, titleOffset, bounds.width - 2, bounds.height - 12);


		if (view != null) {
			g.drawString(view.getTitle(), 10, titleOffset);
			g.translate(0, titleOffset);
			//crop g and pass to underlying view
			this.view.paint(g.create(0, 0, bounds.width, bounds.height - titleOffset));
		}

	}

}
