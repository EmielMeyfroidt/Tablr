package main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DesignMode extends AbstractMode {

	private Table table;
	public DesignMode(TablrManager mgr, Table table) {
		super(mgr);
		this.table = table;
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> getPaintData() {
		return table.getColumns().stream().map(x -> x.getName()).collect(Collectors.toList());
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

}
