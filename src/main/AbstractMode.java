package main;

import java.util.List;

public abstract class AbstractMode {
	
	private TablrManager mgr;
	
	public AbstractMode (TablrManager mgr) {
		this.setMgr(mgr);
	}
	
	public abstract List<String> getPaintData();

	public TablrManager getMgr() {
		return mgr;
	}

	public void setMgr(TablrManager mgr) {
		this.mgr = mgr;
	}
}
