package main;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import canvaswindow.CanvasWindow;

/**
 * MyCanvasWindow is a subclass of CanvasWindow used to render the Tablr GUI.
 * TODO add event triggers for ctrl + T and dragging.
 */
public class MyCanvasWindow extends CanvasWindow {

	private ViewManager viewManager;
	private boolean dragging = false;
	private int lastX, lastY;

	/**
	 * Constructor for creating a MyCanvasWindow with a specified title and an
	 * associated view.
	 *
	 * @param title The title of the canvas window.
	 * @param view  The WindowManager associated with the canvas window, which
	 *              manages graphical components and interactions within the window.
	 */
	public MyCanvasWindow(String title, ViewManager view) {
		super(title);
		this.viewManager = view;

	}

	/**
	 * Changes the current view to the specified WindowManager and triggers a
	 * repaint of the canvas window.
	 *
	 * @param view The new WindowManager to be set for this canvas window, which
	 *             will manage the graphical components and interactions within the
	 *             window.
	 */
	private void changeMode(ViewManager view) {
		this.viewManager = view;
		System.out.println("painting" + view.getTitle());
		this.repaint();
	}

	/**
	 * Handles the painting logic for the canvas window. Sets the title of the
	 * canvas window to the title provided by the current view, and delegates the
	 * drawing operations to the view's paint method.
	 *
	 * @param g The Graphics object used for rendering on the canvas.
	 */
	@Override
	protected void paint(Graphics g) {
		super.setTitle(viewManager.getTitle());
		viewManager.paint(g);
	}

	/**
	 * Handles mouse events and dispatches single-click or double-click actions to
	 * the appropriate methods in the associated view. This method differentiates
	 * between single and double-clicks based on DOUBLE_CLICK_DELAY.
	 *
	 * @param id         The identifier of the mouse event.
	 * @param x          The x-coordinate of the mouse event.
	 * @param y          The y-coordinate of the mouse event.
	 * @param clickCount The number of clicks detected during the mouse event.
	 */
	@Override
	protected void handleMouseEvent(int id, int x, int y, int clickCount) {
		if (id == java.awt.event.MouseEvent.MOUSE_CLICKED) {
			if (clickCount == 2) {
//				System.out.println("double click");
				viewManager.handleDoubleClick(x, y);
			} else if (clickCount == 1) {
//				System.out.println("single click");
				viewManager.handleSingleClick(x, y);
			}
		} else if (id == java.awt.event.MouseEvent.MOUSE_PRESSED) {
			lastX = x;
			lastY = y;
			dragging = true;
		} else if (id == java.awt.event.MouseEvent.MOUSE_DRAGGED) {
			if (dragging) {
				viewManager.handleMouseDrag(lastX, lastY, x, y);
				lastX = x;
				lastY = y;
			}
		} else if (id == java.awt.event.MouseEvent.MOUSE_RELEASED) {
			if (dragging) {
				dragging = false;
			}
		}
		this.repaint();
	}

	/**
	 * Handles keyboard events by identifying the type of key press and dispatching
	 * actions to the appropriate methods in the associated view.
	 *
	 * @param id        The identifier of the key event, such as
	 *                  KeyEvent.KEY_PRESSED or KeyEvent.KEY_RELEASED.
	 * @param keyCode   The integer keycode of the key that was pressed or released,
	 *                  such as KeyEvent.VK_ENTER.
	 * @param keyChar   The character corresponding to the key event, if applicable,
	 *                  or KeyEvent.CHAR_UNDEFINED.
	 * @param modifiers The modifier keys held during the key event, such as
	 *                  KeyEvent.CTRL_DOWN_MASK.
	 */
	@Override
	protected void handleKeyEvent(int id, int keyCode, char keyChar, int modifiers) {
		if (id != KeyEvent.KEY_PRESSED) {
			return; // Only handle key press events
		}

		Runnable action = null;

		switch (keyCode) {
			case KeyEvent.VK_ESCAPE:
				action = () -> viewManager.handleEscape();
				break;

			case KeyEvent.VK_ENTER:
				if (modifiers == KeyEvent.CTRL_DOWN_MASK) {
					// Ctrl + Enter detected
					action = () -> viewManager.handleCtrlEnter();
				} else {
					// Normal Enter detected
					action = () -> viewManager.handleEnter();
				}
				break;

			case KeyEvent.VK_BACK_SPACE:
				action = () -> viewManager.handleBackSpace();
				break;

			case KeyEvent.VK_DELETE:
				action = () -> viewManager.handleDelete();
				break;

			case KeyEvent.VK_Z:
				if (modifiers == KeyEvent.CTRL_DOWN_MASK) {
					// Ctrl + Z detected
					action = () -> viewManager.handleCtrlZ();
				} else if (modifiers == 192) {
					action = () -> viewManager.handleCtrlShiftZ();
				}
				break;

			default:
				if (Character.isDefined(keyChar)) {
					// Handle character input
					action = () -> viewManager.handleCharTyped(keyChar);
				}
				break;

		}

		// Execute the corresponding action if one was set
		if (action != null) {
			action.run();
		}
		this.repaint();
	}
}
