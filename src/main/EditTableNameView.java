package main;

import java.awt.Graphics;

public class EditTableNameView extends AbstractView {

	private TablesView underlyingMode;
	private String name;
	private final String originalName;

	public EditTableNameView(TablrManager mgr, TablesView underlyingMode, String name) {
		super(mgr);
		this.underlyingMode = underlyingMode;
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
		// TODO: check for validity
		fireModeChanged(underlyingMode);
	}

	@Override
	public void handleEscape() {
		getMgr().changeName(name, originalName);
		fireModeChanged(underlyingMode);

	}

	@Override
	public void handleBackSpace() {
		try {
			getMgr().changeName(name, name.substring(0, name.length() - 1));
			name = name.substring(0, name.length() - 1);
		} catch (Exception e) {
			getMgr().changeName(name, "");
			name = "";
		}

	}

	@Override
	public void handleCtrlEnter() {
		// nothing should happen
	}

	@Override
	public void handleEnter() {
		// TODO: check for validity
		fireModeChanged(underlyingMode);
	}

	@Override
	public void handleDelete() {
		// nothing should happen
	}

	@Override
	public void handleCharTyped(char keyChar) {
		getMgr().changeName(name, name + keyChar);
		name += keyChar;

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
