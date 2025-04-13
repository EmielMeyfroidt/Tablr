package main;

import java.awt.*;
import java.util.ArrayList;

/**
 * Manages the set of open AbstractViews in TablrApp
 * Only one Manager per TablrApp
 * Initializes and keeps TablrManager and LayoutManager
 */
public class ViewManager implements ViewList {
	private paintListener paintListener;
	private int defaultWidth = 1000;
	private int defaultHeight = 1000;
	private int newWindowOffset = 100;
	private ArrayList<MetaView> metaViews;
	private TablrManager tablrManager;
	private LayoutInfo layoutInfo;


	class MetaView {
		int x;
		int y;
		int width;
		int height;
		AbstractView view;

		MetaView(AbstractView view, int x, int y) {
			this.view = view;
			this.x = x;
			this.y = y;
			this.width = defaultWidth;
			this.height = defaultHeight;
		}
	}

	public void addListener(paintListener paintListener) {
		this.paintListener = paintListener;
	}


	public ViewManager() {
		metaViews = new ArrayList<MetaView>();
		tablrManager = new TablrManager();
		layoutInfo = new LayoutInfo();
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

	private MetaView getMetaView(AbstractView view) {
		return metaViews.stream().filter(v -> v.view == view).findFirst().orElse(null);
	}

	private void setActiveWindow(AbstractView view) {
		metaViews.remove(getMetaView(view));
		metaViews.add(getMetaView(view));
	}

	private Window getActiveWindow() {
//		return metaViews.getLast();
		return null;
	}

	public void addView(AbstractView newView) {
	}

	public void removeView(AbstractView view) {
	}

	public String getTitle() {
		return getActiveWindow().view.getTitle();
	}

	public void paint(Graphics g) {
		getActiveWindow().view.paint(g);
	}

	public void handleDoubleClick(int x, int y) {
	}

	public void handleSingleClick(int x, int y) {
	}

	public void handleEscape() {
		getActiveWindow().view.handleEscape();
	}

	public void handleCtrlEnter() {
		getActiveWindow().view.handleCtrlEnter();
	}

	public void handleEnter() {
		getActiveWindow().view.handleEnter();
	}

	public void handleBackSpace() {
		getActiveWindow().view.handleBackSpace();
	}

	public void handleDelete() {
		getActiveWindow().view.handleDelete();
	}

	public void handleCharTyped(char keyChar) {
		getActiveWindow().view.handleCharTyped(keyChar);
	}

}
