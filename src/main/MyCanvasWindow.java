package main;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import canvaswindow.CanvasWindow;

public class MyCanvasWindow extends CanvasWindow {
	
	private final int STEPSIZE = 20;
	private final TablrManagerListener listener = 
			() -> {repaint();};
	private static Timer clickTimer = new Timer(); // Shared timer
    private static final int DOUBLE_CLICK_DELAY = 200; // Delay in milliseconds

	
    private TablrManager mgr;
	
	public MyCanvasWindow(String title, TablrManager mgr) {
        super(title);
        this.mgr = mgr;
        mgr.addListener(listener);
    }
	
    @Override
    protected void paint(Graphics g) {
    	List<String> paintData = mgr.getPaintData();
    	int i = STEPSIZE;
    	for (String s : paintData) {
    		g.drawString(s, STEPSIZE, i);
    		i += STEPSIZE;
    	}
    }

    @Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
    	int elementNumber = (int) Math.floor(y/STEPSIZE);
    	if (id == java.awt.event.MouseEvent.MOUSE_CLICKED) {
    		clickTimer.cancel();
    		clickTimer = new Timer();
    		
    		if (clickCount == 2) {
    			Runnable doubleClickListener = new Runnable() {

					@Override
					public void run() {
						
						mgr.handleDoubleClick(elementNumber);
					}
    				
    			};
    			doubleClickListener.run();
    		}else {
    			clickTimer.schedule(new TimerTask() {

					@Override
					public void run() {
						Runnable singleClickListener = new Runnable() {

							@Override
							public void run() {
								mgr.handleSingleClick(elementNumber);
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
            return;  // Only handle key press events
        }

        Runnable action = null;
        
        switch (keyCode) {
            case KeyEvent.VK_ESCAPE:
                action = () -> mgr.handleEscape();
                break;

            case KeyEvent.VK_ENTER:
                if (modifiers == KeyEvent.CTRL_DOWN_MASK) {
                    // Ctrl + Enter detected
                    action = () -> mgr.handleCtrlEnter();
                } else {
                    // Normal Enter detected
                    action = () -> mgr.handleEnter();
                }
                break;

            case KeyEvent.VK_BACK_SPACE:
                action = () -> mgr.handleBackSpace();
                break;

            case KeyEvent.VK_DELETE:
                action = () -> mgr.handleDelete();
                break;

            default:
                if (Character.isDefined(keyChar)) {
                    // Handle character input
                    action = () -> mgr.handleCharTyped(keyChar);
                }
                break;
        }

        // Execute the corresponding action if one was set
        if (action != null) {
            action.run();
        }
    }

}
