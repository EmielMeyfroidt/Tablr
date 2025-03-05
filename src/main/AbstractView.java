package main;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractView {

	private TablrManager mgr;
	private List<ChangeModeListener> changeModeListeners = new ArrayList<ChangeModeListener>();
	
	public AbstractView(TablrManager mgr) {
		this.mgr = mgr;
	}

	public abstract List<String> getPaintData();

	public abstract void handleDoubleClick(int elementNumber);

	public abstract void handleSingleClick(int elementNumber);

	public abstract void handleEscape();

	public abstract void handleBackSpace();

	public abstract Object handleCtrlEnter();

	public abstract Object handleEnter();

	public abstract Object handleDelete();

	public abstract Object handleCharTyped(char keyChar);

	public void addListener(ChangeModeListener c) {
		changeModeListeners.add(c);
	}

	public void fireModeChanged() {
		for (ChangeModeListener listener : changeModeListeners) {
			listener.modeChanged();
		}
	}

}
