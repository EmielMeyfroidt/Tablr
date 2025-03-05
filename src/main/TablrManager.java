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
		tables = new ArrayList<Table>();
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
		// TODO
	}

	public void removeTable(Table table) {
		// TODO
	}

}
