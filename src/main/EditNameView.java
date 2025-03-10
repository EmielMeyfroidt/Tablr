package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class EditNameView extends AbstractView {

	private AbstractView underlyingMode;
	private Nameable element;
	final String originalName;
	
	public EditNameView(TablrManager mgr, AbstractView underlyingMode, Nameable element) {
		super(mgr);
		this.underlyingMode = underlyingMode;
		this.element = element;
		this.originalName = element.getName();
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
		element.setName(originalName);
		fireModeChanged(underlyingMode);
	}

	@Override
	public void handleBackSpace() {
		try {
			element.setName(element.getName().substring(0, element.getName().length() - 1));
		} catch (Exception e) {
			element.setName("");
		}
		
		fireModeChanged(this);
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
		element.setName(element.getName() + keyChar);
		fireModeChanged(this);
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
