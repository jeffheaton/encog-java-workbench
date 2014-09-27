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

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InformationField extends PropertiesField {

	private String value;
	private int height;
	
	public InformationField(int height, String label) {
		super(null, label, false);
		this.height = height;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		((JTextField)this.getField()).setText(value);
	}
	
	public JLabel createLabel() 
	{
		return null;
	}
	
	public int createField(JPanel panel, int x, int y,int width)
	{
		JTextArea l = new JTextArea(this.getLabel());
		l.setEditable(false);
		l.setLineWrap(true);
		l.setBackground(this.getOwner().getBackground());
		l.setWrapStyleWord(true);
		l.setSize(new Dimension(this.getOwner().getWidth(),this.height*(l.getFontMetrics(l.getFont())).getHeight()));
		this.setLabelControl(null);
		l.setLocation(new Point(0,y));
		panel.add(l);
		
		return y+100;
	}


	@Override
	public void collect() throws ValidationException {
	}

}
