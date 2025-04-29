package main;

import java.awt.*;
import java.util.ArrayList;

/**
 * Manages the set of open AbstractViews in TablrApp
 */
public class ViewManager implements ViewList {
	private paintListener paintListener;
	private final ArrayList<MetaView> metaViews;
	private final TablrManager tablrManager;
	private final LayoutInfo layoutInfo;
	private final int newWindowOffset = 10;


	/**
	 * Represents a meta view that encapsulates information about an {@link AbstractView}
	 * and its position and dimensions within a graphical user interface.
	 */
	class MetaView {
		int x;
		int y;
		int width;
		int height;
		AbstractView view;

		private final int defaultWidth = 300;
		private final int defaultHeight = 300;

		MetaView(AbstractView view, int x, int y) {
			this.view = view;
			this.x = x;
			this.y = y;
			this.width = defaultWidth;
			this.height = defaultHeight;
		}

		/**
		 * Translates the given x-coordinate by subtracting the x-coordinate of this MetaView.
		 *
		 * @param x The x-coordinate to be translated.
		 * @return The translated x-coordinate, which is the difference between the provided x
		 * coordinate and the x-coordinate of this MetaView.
		 */
		int translateX(int x) {
			return x - this.x;
		}

		/**
		 * Translates the given y-coordinate by subtracting the y-coordinate of this MetaView.
		 *
		 * @param y The y-coordinate to be translated.
		 * @return The translated y-coordinate, which is the difference between the provided y-coordinate
		 * and the y-coordinate of this MetaView.
		 */
		int translateY(int y) {
			return y - this.y;
		}
	}

	/**
	 * Registers a paintListener to be notified of change in UI Context and the need to re invoke paint()
	 *
	 * @param paintListener the paintListener instance to be registered
	 */
	public void addListener(paintListener paintListener) {
		this.paintListener = paintListener;
	}


	public ViewManager() {
		metaViews = new ArrayList<MetaView>();
		tablrManager = new TablrManager();
		layoutInfo = new LayoutInfo();
	}

	public ViewManager(TablrManager tablrManager) {
		metaViews = new ArrayList<MetaView>();
		this.tablrManager = tablrManager;
		layoutInfo = new LayoutInfo();
	}

	/**
	 * Opens a new view and adds it to the list of managed MetaViews. Positions the new view
	 * at an offset relative to the currently active view.
	 * Notifies the paint listener of the update to ensure the UI is refreshed.
	 *
	 * @param view The AbstractView instance to be added to the list of views managed by this class.
	 * @post view = getActiveView()
	 */
	@Override
	public void openView(AbstractView view) {
		int offset = newWindowOffset;
		if (hasActiveView())
			offset = getActiveView().x + 10;
		metaViews.add(new MetaView(view, offset, offset));
		paintListener.contentsChanged();
	}

	/**
	 * Closes the specified view and removes it from the list of managed views.
	 * Also invokes handleDeadView(view) on each view.
	 * Notifies the paint listener of the update to ensure the UI is refreshed.
	 *
	 * @param view The AbstractView instance to be closed and removed.
	 */
	@Override
	public void closeView(AbstractView view) {
		metaViews.remove(getMetaView(view));
		for (MetaView metaView : metaViews) {
			metaView.view.handleDeadView(view);
		}
		paintListener.contentsChanged();
	}

	/**
	 * Substitutes an existing view with a new view in the list of managed views.
	 * Notifies the paint listener
	 * to refresh the UI.
	 *
	 * @param oldView The existing AbstractView instance to be replaced.
	 * @param newView The new AbstractView instance to replace the old view.
	 */
	@Override
	public void substituteView(AbstractView oldView, AbstractView newView) {
		getMetaView(oldView).view = newView;
		paintListener.contentsChanged();

	}

	/**
	 * Updates the location of the specified view by modifying its x and y coordinates
	 * based on the provided offset values. The paint listener is notified to refresh the UI
	 * to reflect the location change.
	 *
	 * @param view The AbstractView instance whose location is to be updated.
	 * @param x    The horizontal offset by which to move the view's location.
	 * @param y    The vertical offset by which to move the view's location.
	 */
	@Override
	public void moveViewLocation(AbstractView view, int x, int y) {
		getMetaView(view).x += x;
		getMetaView(view).y += y;
		paintListener.contentsChanged();
	}

	private MetaView getMetaView(AbstractView view) {
		return metaViews.stream().filter(v -> v.view == view).findFirst().orElse(null);
	}

	private void setActiveView(MetaView view) {
		if (metaViews.contains(view))
			metaViews.remove(view);
		metaViews.add(view);
	}

	private boolean hasActiveView() {
		return getActiveView() != null;
	}

	private MetaView getActiveView() {
		try {
			return metaViews.getLast();
		} catch (Exception noSuchElementException) {
			return null;
		}
	}

	/**
	 * Retrieves the title of the view.
	 *
	 * @return the title of the view as a String. The default title is "Tablr".
	 */
	public String getTitle() {
		return "Tablr";
	}

	private MetaView getViewClicked(int x, int y) {
		for (int i = metaViews.size() - 1; i >= 0; i--) {
			MetaView metaView = metaViews.get(i);
			if (x >= metaView.x && x <= metaView.x + metaView.width &&
					y >= metaView.y && y <= metaView.y + metaView.height) {
				return metaView;
			}
		}
		return null;
	}

	private void setViewActiveAt(int x, int y) {
		MetaView metaViewClicked = getViewClicked(x, y);
		if (metaViewClicked != getActiveView()) {
			setActiveView(metaViewClicked);
		}
	}

	/**
	 * Handles the double-click event based on the specified x and y coordinates.
	 * Activates the view located at the specified coordinates, performs a
	 * double-click action within the active view, and refreshes the UI.
	 *
	 * @param x The x-coordinate where the double-click occurred.
	 * @param y The y-coordinate where the double-click occurred.
	 */
	public void handleDoubleClick(int x, int y) {

		if (hasActiveView()) {
			setViewActiveAt(x, y);
			getActiveView().view.handleDoubleClick(
					getActiveView().translateX(x), getActiveView().translateY(y));
		}
		paintListener.contentsChanged();
	}

	/**
	 * Handles a single-click event at the specified coordinates.
	 * If there is an active view, the method activates the corresponding view at the given coordinates,
	 * translates the coordinates relative to the active view, and triggers a single-click action
	 * on that view. The UI is then refreshed by notifying the paint listener that the contents have changed.
	 *
	 * @param x The x-coordinate where the single-click occurred.
	 * @param y The y-coordinate where the single-click occurred.
	 */
	public void handleSingleClick(int x, int y) {
		if (hasActiveView()) {
			setViewActiveAt(x, y);
			getActiveView().view.handleSingleClick(
					getActiveView().translateX(x), getActiveView().translateY(y));
		}
		paintListener.contentsChanged();

	}

	/**
	 * Handles a mouse drag event by identifying the active view and triggering the corresponding
	 * mouse drag handling within that view. The method uses the starting and ending coordinates
	 * of the drag to perform the operation. It ensures that the UI is updated after handling the drag.
	 *
	 * @param startX The x-coordinate where the drag operation starts.
	 * @param startY The y-coordinate where the drag operation starts.
	 * @param endX   The x-coordinate where the drag operation ends.
	 * @param endY   The y-coordinate where the drag operation ends.
	 */
	public void handleMouseDrag(int startX, int startY, int endX, int endY) {
		if (hasActiveView()) {
			setViewActiveAt(startX, startY);
			getActiveView().view.handleMouseDrag(
					getActiveView().translateX(startX),
					getActiveView().translateY(startY),
					getActiveView().translateX(endX),
					getActiveView().translateY(endY));
		}
		paintListener.contentsChanged();
		//System.out.println(startX + ", " + startY + " to " + endX + ", " + endY);
	}

	/**
	 * invokes handleEscape() on the activeView.
	 * <p>
	 * Invokes the paintListener
	 */
	public void handleEscape() {
		if (hasActiveView())
			getActiveView().view.handleEscape();
		paintListener.contentsChanged();

	}

	/**
	 * invokes handleCtrlEnter() on the activeView.
	 * <p>
	 * Invokes the paintListener
	 */
	public void handleCtrlEnter() {
		System.out.println("ctrl enter");
		if (hasActiveView())
			getActiveView().view.handleCtrlEnter();
		paintListener.contentsChanged();

	}

	/**
	 * invokes handleEnter() on the activeView.
	 * <p>
	 * Invokes the paintListener
	 */
	public void handleEnter() {
		if (hasActiveView())
			getActiveView().view.handleEnter();
		paintListener.contentsChanged();

	}

	/**
	 * invokes handleBackSpace() on the activeView.
	 * <p>
	 * Invokes the paintListener
	 */
	public void handleBackSpace() {
		if (hasActiveView())
			getActiveView().view.handleBackSpace();
		paintListener.contentsChanged();

	}

	/**
	 * invokes handleDelete() on the activeView.
	 * <p>
	 * Invokes the paintListener
	 */
	public void handleDelete() {
		if (hasActiveView())
			getActiveView().view.handleDelete();
		paintListener.contentsChanged();

	}

	/**
	 * invokes handleCharTyped() on the activeView.
	 * <p>
	 * Invokes the paintListener
	 */
	public void handleCharTyped(char keyChar) {
		if (keyChar == '\u0014')
			// CTRL+T opens new default view
			openView(viewAssembler.getDefaultView(this.tablrManager, this.layoutInfo, this));
		else if (hasActiveView())
			getActiveView().view.handleCharTyped(keyChar);
		paintListener.contentsChanged();
	}

	/**
	 * invokes paint(g') on each view in order of last used, where g' is created from g
	 * with bounds corresponding to the dimensions of each view.
	 *
	 * @param g The Graphics object used for drawing the views.
	 */
	public void paint(Graphics g) {
		for (MetaView metaView : metaViews)
			metaView.view.paint(
					g.create(metaView.x, metaView.y, metaView.width, metaView.height));
	}


	/**
	 * @return
	 */
	public void handleCtrlZ() {
		if (hasActiveView()) {
			getActiveView().view.handleCtrlZ();
		}
		paintListener.contentsChanged();
	}


	/**
	 * @return
	 */
	public void handleCtrlShiftZ() {
		if (hasActiveView()) {
			getActiveView().view.handleCtrlShiftZ();
		}
		paintListener.contentsChanged();
	}

}
