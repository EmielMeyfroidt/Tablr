package main;

import java.util.List;

public class TablesMode extends AbstractMode {

	public TablesMode(TablrManager mgr) {
		super(mgr);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> getPaintData() {
		// TODO Auto-generated method stub
		return getMgr().getTables().stream().map(t -> t.getName()).toList();
	}

}
