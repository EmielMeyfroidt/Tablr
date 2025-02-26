package main;

import java.util.List;

public class TablesMode extends AbstractMode {

	public TablesMode(TablrManager mgr) {
		super(mgr);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> getPaintData() {
		return getMgr().getTables().stream().map(t -> t.getName()).toList();
	}

	@Override
	public void handleDoubleClick(int elementNumber) {
		if (elementNumber >= this.getMgr().getTables().size()) {
			this.getMgr().addTable();
		}
		else {
			DesignMode newMode = new DesignMode(this.getMgr(), this.getMgr().getTables().get(elementNumber));
			this.getMgr().setMode(newMode);
			System.out.println("NOW IN DESIGN MODE FOR TABLE: " + elementNumber);
		}
	}

	@Override
	public void handleEscape() {
		// nothing should happen
	}

	@Override
	public void handleSingleClick(int elementNumber) {
		if (elementNumber >= this.getMgr().getTables().size()) {
			//nothing
		}
		else {
			this.getMgr().startEditNameMode(elementNumber);
		}
		
	}

	@Override
	public void handleBackSpace() {
		// TODO Auto-generated method stub
		
	}

}
