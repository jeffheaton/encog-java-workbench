/*
 * Encog(tm) Workbench v3.3
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-workbench
 *
 * Copyright 2008-2014 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package org.encog.workbench.dialogs.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CheckField extends PropertiesField implements ActionListener {

	private boolean value;
	private CheckListener listener;
	
	public CheckField(String name, String label) {
		super(name, label, true);
	}
	
	public int createField(JPanel panel, int x, int y,int width)
	{
		this.setField(new JCheckBox());
		this.getField().setLocation(x, y);
		this.getField().setSize(this.getField().getPreferredSize());
		this.getField().setSize(width,this.getField().getHeight());
		JLabel label = createLabel();
		label.setLocation(label.getX(), y);
		panel.add(label);
		panel.add(this.getField());
		((JCheckBox)this.getField()).addActionListener(this);
		
		return y+this.getField().getHeight();
	}

	@Override
	public void collect() throws ValidationException {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == getField() )
			if( this.listener!=null )
		this.listener.check(this);
		
	}

	public CheckListener getListener() {
		return listener;
	}

	public void setListener(CheckListener listener) {
		
			this.listener = listener;
	}

	public boolean getValue() {
		return ((JCheckBox)this.getField()).isSelected();
	}

	public void setValue(boolean b) {
		((JCheckBox)this.getField()).setSelected(b);
		
	}
	
	
	
	

}
