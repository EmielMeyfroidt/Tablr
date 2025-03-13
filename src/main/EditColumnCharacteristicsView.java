/**
 * 
 */
package main;

import java.awt.Graphics;

/**
 * 
 */
public class EditColumnCharacteristicsView extends AbstractView {

	private DesignView underlyingView;
	private String nameTable;
	private String name;
	private String originalName;
	
	/**
	 * @param mgr
	 */
	public EditColumnCharacteristicsView(TablrManager mgr, DesignView underlyingMode, String name, String nameTable) {
		super(mgr);
		this.underlyingView = underlyingMode;
		this.nameTable = nameTable;
		this.name = name;
		this.originalName = name;
		this.setChangeModeListeners(underlyingMode.getChangeModeListeners());
	}

	@Override
	public String getTitle() {
		return "Editing column " + name + ".";
	}

	@Override
	public void paint(Graphics g) {
		underlyingView.paint(g);
		
	}

	@Override
	public void handleDoubleClick(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleSingleClick(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleEscape() {
		// TODO Auto-generated method stub
		getMgr().changeNameColumn(nameTable, name, originalName);
		fireModeChanged(underlyingView);
		
	}

	@Override
	public void handleBackSpace() {
		try {
			getMgr().changeNameColumn(nameTable, name, name.substring(0, name.length() - 1));
			name = name.substring(0, name.length() - 1);
		} catch (Exception e) {
			getMgr().changeNameColumn(nameTable, name, "");
			name = "";
		}
		
	}

	@Override
	public void handleCtrlEnter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleEnter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleDelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleCharTyped(char keyChar) {
		getMgr().changeNameColumn(nameTable, name, name + keyChar);
		name += keyChar;
	}

}
