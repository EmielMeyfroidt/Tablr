package main;

import java.util.List;

public class RowsMode extends AbstractMode {

	public RowsMode(TablrManager mgr) {
		super(mgr);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> getPaintData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleDoubleClick(int elementNumber) {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleEscape() {
		TablesMode newMode = new TablesMode(this.getMgr());
		this.getMgr().setMode(newMode);
	}

	@Override
	public void handleSingleClick(int elementNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleBackSpace() {
		// TODO Auto-generated method stub
		
	}


}
