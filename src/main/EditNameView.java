package main;

import java.awt.Graphics;

public class EditNameView extends AbstractView {

	private AbstractView underlyingMode;
	private String name;
	private String nameTable;
	private final String originalName;
	
	public EditNameView(TablrManager mgr, AbstractView underlyingMode, String name, String nameTable) {
		super(mgr);
		this.underlyingMode = underlyingMode;
		this.nameTable = nameTable;
		this.name = name;
		this.originalName = name;
		this.setChangeModeListeners(underlyingMode.getChangeModeListeners());
	}

	

	@Override
	public void handleDoubleClick(int x, int y) {
		// nothing should happen
		
	}

	@Override
	public void handleSingleClick(int x, int y) {
		//TODO: check for validity
		fireModeChanged(underlyingMode);
	}

	@Override
	public void handleEscape() {
		if (underlyingMode instanceof TablesView) {
			getMgr().changeName(name, originalName);
			fireModeChanged(underlyingMode);
		} else if (underlyingMode instanceof DesignView) {
			getMgr().changeNameColumn(nameTable, name, originalName);
			fireModeChanged(underlyingMode);
		}
	}

	@Override
	public void handleBackSpace() {
		if (underlyingMode instanceof TablesView) {
			try {
				getMgr().changeName(name, name.substring(0, name.length() - 1));
				name = name.substring(0, name.length() - 1);
			} catch (Exception e) {
				getMgr().changeName(name, "");
				name = "";
			}
		} else if (underlyingMode instanceof DesignView) {
			try {

				getMgr().changeNameColumn(nameTable, name, name.substring(0, name.length() - 1));
				name = name.substring(0, name.length() - 1);
			} catch (Exception e) {
				getMgr().changeNameColumn(nameTable,name , "");
				name = "";
			}
		}

	}

	@Override
	public void handleCtrlEnter() {
		// nothing should happen
	}

	@Override
	public void handleEnter() {
		//TODO: check for validity
		fireModeChanged(underlyingMode);
	}

	@Override
	public void handleDelete() {
		// nothing should happen
	}

	@Override
	public void handleCharTyped(char keyChar) {
		if (underlyingMode instanceof TablesView) {
			getMgr().changeName(name, name + keyChar);
			name += keyChar;
		} else if (underlyingMode instanceof DesignView) {
			getMgr().changeNameColumn(nameTable, name, name + keyChar);
			name += keyChar;
		}
	}

	@Override
	public String getTitle() {
		return "Editing name " + originalName + ".";
	}

	@Override
	public void paint(Graphics g) {
		underlyingMode.paint(g);
	}


}
