package main;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import canvaswindow.CanvasWindow;

public class MyCanvasWindow extends CanvasWindow {

	private PaintStrategy paintStrategy;
	private final TablrManagerListener tablrManagerListener = () -> {
		repaint();
	};
	private final ChangeModeListener changeModeListener = (AbstractView view, PaintStrategy strategy) -> {
		changeMode(view, strategy);
	};
	private static Timer clickTimer = new Timer(); // Shared timer
	private static final int DOUBLE_CLICK_DELAY = 200; // Delay in milliseconds

	private AbstractView view;

	public MyCanvasWindow(String title, AbstractView view) {
		super(title);
		this.view = view;
		view.addListener(changeModeListener);
		
	}

	private void changeMode(AbstractView view, PaintStrategy strategy) {
		setPaintStrategy(strategy);
		this.view = view;
		this.repaint();
	}

	public void setPaintStrategy(PaintStrategy strategy) {
		this.paintStrategy = strategy;
	}
	@Override
	protected void paint(Graphics g) {
		super.setTitle(paintStrategy.getTitle());
		paintStrategy.paint(g, view.getPaintData(), view.getStepX(), view.getStepY());
	}

	@Override
	protected void handleMouseEvent(int id, int x, int y, int clickCount) {
		if (id == java.awt.event.MouseEvent.MOUSE_CLICKED) {
			clickTimer.cancel();
			clickTimer = new Timer();

			if (clickCount == 2) {
				Runnable doubleClickListener = new Runnable() {

					@Override
					public void run() {

						view.handleDoubleClick(x, y);
					}

				};
				doubleClickListener.run();
			} else {
				clickTimer.schedule(new TimerTask() {

					@Override
					public void run() {
						Runnable singleClickListener = new Runnable() {

							@Override
							public void run() {
								view.handleSingleClick(x, y);
							}

						};
						singleClickListener.run();
					}

				}, DOUBLE_CLICK_DELAY);
			}
		}
	}

	@Override
	protected void handleKeyEvent(int id, int keyCode, char keyChar, int modifiers) {
		if (id != KeyEvent.KEY_PRESSED) {
			return; // Only handle key press events
		}

		Runnable action = null;

		switch (keyCode) {
		case KeyEvent.VK_ESCAPE:
			action = () -> view.handleEscape();
			break;

		case KeyEvent.VK_ENTER:
			if (modifiers == KeyEvent.CTRL_DOWN_MASK) {
				// Ctrl + Enter detected
				action = () -> view.handleCtrlEnter();
			} else {
				// Normal Enter detected
				action = () -> view.handleEnter();
			}
			break;

		case KeyEvent.VK_BACK_SPACE:
			action = () -> view.handleBackSpace();
			break;

		case KeyEvent.VK_DELETE:
			action = () -> view.handleDelete();
			break;

		default:
			if (Character.isDefined(keyChar)) {
				// Handle character input
				action = () -> view.handleCharTyped(keyChar);
			}
			break;
		}

		// Execute the corresponding action if one was set
		if (action != null) {
			action.run();
		}
	}

	public TablrManagerListener getTablrManagerListener() {
		return tablrManagerListener;
	}

}
