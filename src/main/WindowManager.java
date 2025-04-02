package main;

import java.awt.*;
import java.util.ArrayList;

public class WindowManager {
	private paintListener paintListener;
	private int defaultWidth = 1000;
	private int defaultHeight = 1000;
	private int newWindowOffset = 100;
	private ArrayList<Window> windows;

	public void addListener(paintListener paintListener) {
		this.paintListener = paintListener;
	}

	private class Window {
		int startx;
		int starty;
		int width;
		int height;
		AbstractView view;

		record Coordinates(int x, int y) {
		}

		Coordinates translatePoint(Coordinates coordinates) {
			return new Coordinates(coordinates.x - startx, coordinates.y - starty);
		}

		boolean containsPoint(Coordinates coordinates) {
			return coordinates.x >= startx &&
					coordinates.x <= startx + width &&
					coordinates.y >= starty &&
					coordinates.y <= starty + height;
		}

		Window(int startx, int starty, int width, int height, AbstractView view) {
		}
	}

	private final WindowListener windowListener = new WindowListener() {
		@Override
		public void openWindow(AbstractView view) {
			addView(view);
			System.out.println("Window opened: " + view);
		}

		@Override
		public void closeWindow(AbstractView view) {
			removeView(view);
			System.out.println("Window closed: " + view);
		}
	};

	public WindowListener getWindowListener() {
		return windowListener;
	}

	public WindowManager(AbstractView view) {
		windows = new ArrayList<>();
		windows.add(new Window(newWindowOffset, newWindowOffset, defaultWidth, defaultHeight, view));
		setActiveWindow(windows.get(0));
	}

	private void setActiveWindow(Window Window) {
		windows.remove(windows.stream().filter(v -> v == Window));
		windows.add(Window);
	}

	private Window getActiveWindow() {
		return windows.getLast();
	}

	public void addView(AbstractView newView) {
		Window newWindow = new Window(getActiveWindow().startx + getActiveWindow().width + newWindowOffset, newWindowOffset, defaultWidth, defaultHeight, newView);
		windows.add(newWindow);
		setActiveWindow(newWindow);
	}

	public void removeView(AbstractView view) {
		if (getActiveWindow().view == view) {
			if (windows.size() == 1) {
				System.out.println("not allowed to close last window!");
				return;
			}
			setActiveWindow(windows.get(windows.size() - 2));
		}
		windows.stream().filter(v -> v.view == view).findFirst().ifPresent(windows::remove);
	}

	public String getTitle() {
		return getActiveWindow().view.getTitle();
	}

	public void paint(Graphics g) {
		getActiveWindow().view.paint(g);
	}

	public void handleDoubleClick(int x, int y) {
		if (getActiveWindow().containsPoint(new Window.Coordinates(x, y))) {
			Window.Coordinates translatedCoordinates = getActiveWindow().translatePoint(new Window.Coordinates(x, y));
			getActiveWindow().view.handleDoubleClick(translatedCoordinates.x, translatedCoordinates.y);
		} else {
			try {
				setActiveWindow(findView(new Window.Coordinates(x, y)));
			} catch (IllegalArgumentException e) {
				getActiveWindow().view.handleDoubleClickOutside();
			}
			paintListener.contentsChanged();
		}
	}

	private Window findView(Window.Coordinates coordinates) {
		for (Window v : windows) {
			if (v.containsPoint(coordinates)) {
				return v;
			}
		}
		throw new IllegalArgumentException("No view found at coordinates: " + coordinates);
	}

	public void handleSingleClick(int x, int y) {
		if (getActiveWindow().containsPoint(new Window.Coordinates(x, y))) {
			Window.Coordinates translatedCoordinates = getActiveWindow().translatePoint(new Window.Coordinates(x, y));

			getActiveWindow().view.handleSingleClick(translatedCoordinates.x, translatedCoordinates.y);
		} else {
			try {
				setActiveWindow(findView(new Window.Coordinates(x, y)));
			} catch (IllegalArgumentException e) {
				getActiveWindow().view.handleSingleClickOutside();
			}
			paintListener.contentsChanged();
		}
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
