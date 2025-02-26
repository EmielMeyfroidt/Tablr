package main;

import java.util.ArrayList;
import java.util.List;

public class EditNameMode extends AbstractMode {

	private AbstractMode underlyingMode;
	private int elementNumber;
	public EditNameMode(TablrManager mgr, AbstractMode underlyingMode, int ElementNumber) {
		super(mgr);
		this.underlyingMode = underlyingMode;
		this.elementNumber = ElementNumber;
		// TODO Auto-generated constructor stub
	}

	public AbstractMode getUnderlyingMode() {
		return underlyingMode;
	}
	
	@Override
	public List<String> getPaintData() {
		List<String> underlyingData = underlyingMode.getPaintData();
		List<String> returnList = new ArrayList<String>();
		for (int i = 0; i<underlyingData.size(); i++) {
			if (i == elementNumber) {
				returnList.add((underlyingData.get(i) + "|"));
			}
			else {
				returnList.add(underlyingData.get(i));
			}
		}
		return returnList;
	}

	@Override
	public void handleDoubleClick(int elementNumber) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEscape() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleSingleClick(int elementNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleBackSpace() {
		String s = this.getMgr().getTables().get(elementNumber).getName();
		s = s.substring(0, s.length() - 1);
		
	}

}
