/**
 * 
 */
package main;

import java.awt.Graphics;

/**
 * 
 */
public class EditDefaultValueView extends AbstractView {

	private DesignView underlyingView;
	private String table;
	private String column;
	private final String originalValue;
	private String newValue;
	
	/**
	 * @param mgr
	 * @param table 
	 * @param string 
	 * @param designView 
	 */
	public EditDefaultValueView(TablrManager mgr, DesignView underlyingView, String column, String table) {
		super(mgr);
		this.underlyingView = underlyingView;
		this.table = table;
		this.column = column;
		this.originalValue = mgr.getDefaultValue(table, column).toString();
		this.newValue = mgr.getDefaultValue(table, column).toString();
		this.setChangeModeListeners(underlyingView.getChangeModeListeners());
	}

	@Override
	public String getTitle() {
		return "Editing default value of " + column + ".";
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
		fireModeChanged(underlyingView);
	}

	@Override
	public void handleEscape() {
		getMgr().setDefaultValue(table, column, originalValue);
		fireModeChanged(underlyingView);
	}

	@Override
	public void handleBackSpace() {
		try {
			getMgr().setDefaultValue(table, column, newValue.substring(0, newValue.length() - 1));;
			newValue = newValue.substring(0, newValue.length() - 1);
		} catch (Exception e) {
			getMgr().setDefaultValue(table, column, "");
			newValue = "";
		}

	}

	@Override
	public void handleCtrlEnter() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEnter() {
		fireModeChanged(underlyingView);
	}

	@Override
	public void handleDelete() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleCharTyped(char keyChar) {
		getMgr().setDefaultValue(table, column, newValue.toString() + keyChar);
		newValue += keyChar;
	}

}
