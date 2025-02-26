package main;

import java.util.List;

public abstract class AbstractMode {
	
	private TablrManager mgr;
	
	public AbstractMode (TablrManager mgr) {
		this.setMgr(mgr);
	}
	
	public abstract List<String> getPaintData();
	public abstract void handleDoubleClick(int elementNumber);
	public abstract void handleSingleClick(int elementNumber);
	public abstract void handleEscape();
	public abstract void handleBackSpace();

	public TablrManager getMgr() {
		return mgr;
	}

	public void setMgr(TablrManager mgr) {
		this.mgr = mgr;
	}
}
