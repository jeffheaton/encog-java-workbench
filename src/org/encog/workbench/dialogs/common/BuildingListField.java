package org.encog.workbench.dialogs.common;

import javax.swing.JPanel;

public class BuildingListField extends PropertiesField {

	public BuildingListField(String name, String label) {
		super(name, label, true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void collect() throws ValidationException {
		// TODO Auto-generated method stub
		
	}
	
	public int createField(JPanel panel, int x, int y,int width)
	{
		this.setField(new JPanel());
		this.getField().setLocation(x,y);
		this.getField().setSize(width,100);
		panel.add(createLabel());
		panel.add(this.getField());
		y+=100;
		return y;
	}

}
