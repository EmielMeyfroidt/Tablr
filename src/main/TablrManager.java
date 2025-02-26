package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//Manage tables and their contents
public class TablrManager {
	private List<Table> tables;
	private List<TablrManagerListener> listeners;
	private AbstractMode mode;
	
	public void startEditNameMode(int elementNumber) {
		EditNameMode editNameMode = new EditNameMode(this, mode, elementNumber);
		this.mode = editNameMode;
		System.out.println("EDIT NAME MODE");
	}
	
	public void exitEditNameMode() {
		this.mode = ((EditNameMode) this.mode).getUnderlyingMode();
	}
	
	public TablrManager() {
		tables = new ArrayList<Table>();
		listeners = new ArrayList<TablrManagerListener>();
		this.fireContentsChanged();
	}
	
	public void setMode(AbstractMode mode) {
		this.mode = mode;
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
	
	public List<Table> getTables() {
		return tables;
	}
	
	public void addTable() {
		String newName = generateUniqueName("Table");
		Table table = new Table(newName);
		tables.add(table);
		fireContentsChanged();
	}
	
	public void removeTable(Table table) {
		tables.remove(table);
		fireContentsChanged();
	}
	
	public void handleEscape() {
		System.out.println("ESCAPE");
		mode.handleEscape();
		fireContentsChanged();	
	}
	
	public void handleBackSpace() {
		System.out.println("BACKSPACE");
		mode.handleBackSpace();
		fireContentsChanged();
	}
	
	public void handleCharTyped(char keyChar) {
		System.out.println(keyChar);

		//TODO
	}
	
	public void handleEnter() {
		System.out.println("ENTER");

		//TODO
	}
	
	public void handleDelete() {
		System.out.println("DELETE");
		//TODO
	}
	
	public void handleCtrlEnter() {
		System.out.println("CTRL + ENTER");
		//TODO
	}
	
	public void handleDoubleClick(int elementNumber) {
		System.out.println("DOUBLE CLICK");
		mode.handleDoubleClick(elementNumber);
		fireContentsChanged();
	}
	
	public void handleSingleClick(int elementNumber) {
		System.out.println("SINGLE CLICK");
		mode.handleSingleClick(elementNumber);
		fireContentsChanged();
	}
	
	public List<String> getPaintData(){
		return mode.getPaintData();
	}
	
	private String generateUniqueName(String s) {
		Set<String> existingNames = tables.stream()
                .map(Table::getName)
                .collect(Collectors.toSet());
		
		int i = 0;
		String newName = s + i;
		while (existingNames.contains(newName)) {
			i++;
			newName = s + i;
		}
		return newName;
	}
	
}
