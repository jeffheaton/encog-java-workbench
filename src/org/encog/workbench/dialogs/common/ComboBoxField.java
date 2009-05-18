package org.encog.workbench.dialogs.common;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ComboBoxField extends PropertiesField {
	
	private List<?> list;

	public ComboBoxField(String name, String label, boolean required, List<?> list) {
		super(name, label, required);
		this.list = list;
	}

	@Override
	public void collect() throws ValidationException {
		// TODO Auto-generated method stub
		
	}
	
	public int createField(JPanel panel, int x, int y,int width)
	{
		JComboBox combo = new JComboBox(this.list.toArray());
		combo.setLocation(x, y);
		combo.setSize(combo.getPreferredSize());
		combo.setSize(width,combo.getHeight());
		JLabel label = createLabel();
		label.setLocation(label.getX(), y);
		panel.add(label);
		panel.add(combo);
		
		setField(combo);
		return y+combo.getHeight();
	}
	
	public Object getSelectedValue()
	{
		return ((JComboBox)this.getField()).getSelectedItem();
	}

}
