package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractView {

	private TablrManager mgr;
	private List<ChangeModeListener> changeModeListeners = new ArrayList<ChangeModeListener>();
	
	public AbstractView(TablrManager mgr) {
		this.setMgr(mgr);
	}
	
	public abstract String getTitle();
	
	public abstract void paint(Graphics g);

	public abstract void handleDoubleClick(int x, int y);

	public abstract void handleSingleClick(int x, int y);

	public abstract void handleEscape();

	public abstract void handleBackSpace();

	public abstract void handleCtrlEnter();

	public abstract void handleEnter();

	public abstract void handleDelete();

	public abstract void handleCharTyped(char keyChar);

	public TablrManager getMgr() {
		return mgr;
	}

	public void setMgr(TablrManager mgr) {
		this.mgr = mgr;
	}
	
	public void addListener(ChangeModeListener c) {
		changeModeListeners.add(c);
	}

	public void fireModeChanged(AbstractView view) {
		for (ChangeModeListener listener : changeModeListeners) {
			listener.modeChanged(view);
		}
	}

}
