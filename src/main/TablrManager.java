package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//Manage tables and their contents
public class TablrManager {
	private List<Table> tables;
	private List<TablrManagerListener> listeners;

	public TablrManager() {
		setTables(new ArrayList<Table>());
		listeners = new ArrayList<TablrManagerListener>();
		this.fireContentsChanged();
	}

	public void addListener(TablrManagerListener listener) {
		listeners.add(listener);
	}

	public void removeListener(TablrManagerListener listener) {
		listeners.remove(listener);
	}

	private void fireContentsChanged() {
		for (TablrManagerListener listener : listeners) {
			listener.contentsChanged();
		}
	}

	public void addTable() {
		String uniqueName = generateUniqueName();
		Table newTable = new Table(uniqueName);
		tables.add(newTable);
		fireContentsChanged();
	}

	public void removeTable(Table table) {
		// TODO
	}

	public List<Table> getTables() {
		return tables;
	}
	
	public List<String> getTableNames(){
		return tables.stream().map(t -> t.getName()).collect(Collectors.toList());
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	private String generateUniqueName() {
		int n = 0;
		while (getTableNames().contains("Table" + n)) {
			n++;
		}
		return "Table" + n;
	}

}
