package main;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractView {

	private final int stepX = 20;
	private final int stepY = 20;
	private TablrManager mgr;
	private List<ChangeModeListener> changeModeListeners = new ArrayList<ChangeModeListener>();
	
	public AbstractView(TablrManager mgr) {
		this.setMgr(mgr);
	}

	public abstract List<String> getPaintData();

	public abstract void handleDoubleClick(int x, int y);

	public abstract void handleSingleClick(int x, int y);

	public abstract void handleEscape();

	public abstract void handleBackSpace();

	public abstract Object handleCtrlEnter();

	public abstract Object handleEnter();

	public abstract Object handleDelete();

	public abstract Object handleCharTyped(char keyChar);

	public void addListener(ChangeModeListener c) {
		changeModeListeners.add(c);
	}

	public void fireModeChanged(AbstractView view, PaintStrategy strategy) {
		for (ChangeModeListener listener : changeModeListeners) {
			listener.modeChanged(view, strategy);
		}
	}

	public TablrManager getMgr() {
		return mgr;
	}

	public void setMgr(TablrManager mgr) {
		this.mgr = mgr;
	}
	
	public int getStepX() {
		return stepX;
	}
	public int getStepY() {
		return stepY;
	}

}
