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
	private int defaultWidth = 300;
	private int defaultHeight = 300;
	private int newWindowOffset = 10;
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

		int translateX(int x) {
			return x - this.x;
		}

		int translateY(int y) {
			return y - this.y;
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
		int offset = newWindowOffset;
		if (hasActiveView())
			offset = getActiveView().x + 10;
		metaViews.add(new MetaView(view, offset, offset));
	}

	/**
	 * @param view
	 */
	@Override
	public void closeView(AbstractView view) {
		metaViews.remove(getMetaView(view));
		paintListener.contentsChanged();
	}

	/**
	 * @param oldView
	 * @param newView
	 */
	@Override
	public void substituteView(AbstractView oldView, AbstractView newView) {
		getMetaView(oldView).view = newView;
	}

	private MetaView getMetaView(AbstractView view) {
		return metaViews.stream().filter(v -> v.view == view).findFirst().orElse(null);
	}

	private void setActiveView(AbstractView view) {
		metaViews.add(getMetaView(view));
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

	public String getTitle() {
		return "Tablr";
	}


	public void handleDoubleClick(int x, int y) {
		System.out.println("double click");
		if (hasActiveView())
			getActiveView().view.handleDoubleClick(
					getActiveView().translateX(x), getActiveView().translateY(y));
		paintListener.contentsChanged();
	}

	public void handleSingleClick(int x, int y) {
		System.out.println("single click");
		if (hasActiveView())
			getActiveView().view.handleSingleClick(
					getActiveView().translateX(x), getActiveView().translateY(y));
	}

	public void handleEscape() {
		if (hasActiveView())
			getActiveView().view.handleEscape();
	}

	public void handleCtrlEnter() {
		System.out.println("ctrl enter");
		if (hasActiveView())
			getActiveView().view.handleCtrlEnter();
	}

	public void handleEnter() {
		if (hasActiveView())
			getActiveView().view.handleEnter();
	}

	public void handleBackSpace() {
		if (hasActiveView())
			getActiveView().view.handleBackSpace();
	}

	public void handleDelete() {
		if (hasActiveView())
			getActiveView().view.handleDelete();
	}

	public void handleCharTyped(char keyChar) {
		if (keyChar == '\u0014')
			// CTRL+T opens new default view
			openView(viewAssembler.getDefaultView(this.tablrManager, this.layoutInfo, this));
		else if (hasActiveView())
			getActiveView().view.handleCharTyped(keyChar);
		paintListener.contentsChanged();
	}

	public void paint(Graphics g) {
		for (MetaView metaView : metaViews)
			metaView.view.paint(
					g.create(metaView.x, metaView.y, metaView.width, metaView.height));
	}

}
