/*
 * Encog(tm) Workbench v2.4
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
 */

package org.encog.workbench.dialogs.common;

import java.awt.Frame;
import java.awt.Window;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class EncogPropertiesDialog extends EncogCommonDialog {

	private List<PropertiesField> nonTabbedproperties = new ArrayList<PropertiesField>();
	private List<PropertiesField> allProperties = new ArrayList<PropertiesField>();
	private List<String> tabs = new ArrayList<String>();
	private Map<String,List<PropertiesField>> tabMaps = new HashMap<String,List<PropertiesField>>();
	private List<PropertiesField> currentTab; 
	private JTabbedPane tabPane;
	
	public EncogPropertiesDialog(Frame owner) {
		super(owner);
	}
	
	public EncogPropertiesDialog(JDialog owner) {
		super(owner);
	}
	
	public void render()
	{
		if( this.tabs.size()==0 )
			renderNonTab();
		else
			renderTab();
	}
	
	public void renderTab()
	{
		this.tabPane = new JTabbedPane();
		this.getContentPane().add(this.tabPane);
		
		for(String tabName: this.tabs )
		{
			JPanel tab = new JPanel();
			this.tabPane.addTab(tabName, tab);
			List<PropertiesField> tabProperties = this.tabMaps.get(tabName);
			renderProperties(tab,tabProperties);
		}
	}
	
	public void renderNonTab()
	{
		JPanel contents = this.getBodyPanel();
		renderProperties(contents, this.nonTabbedproperties);
	}
	
	public void renderProperties(JPanel target, List<PropertiesField> properties)
	{
		int y = 0;
		int maxLabelWidth = 0;
		int dialogWidth = getWidth();
		int labelHeight=0;
		
		target.setLayout(null);
		
		// create the labels
		
		for(PropertiesField field: properties)
		{
			JLabel label = field.createLabel();
			maxLabelWidth = Math.max(maxLabelWidth, label.getWidth());
		}
		
		y=0;
		// create the text fields
		for(PropertiesField field: properties)
		{
			y = field.createField(target, maxLabelWidth+30, y, dialogWidth-maxLabelWidth-50 );
		}
	}
	
	public void beginTab(String tabName)
	{
		this.currentTab = new ArrayList<PropertiesField>();
		this.tabMaps.put(tabName,this.currentTab);
		this.tabs.add(tabName);
	}

	@Override
	public void collectFields() throws ValidationException {
		for(PropertiesField field: this.allProperties)
		{
			field.collect();
		}
	}

	@Override
	public void setFields() {
		// nothing to do here
		
	}
	
	public void addProperty(PropertiesField field) {
		if( this.currentTab==null ) {
			this.nonTabbedproperties.add(field);
			field.setOwner(this);
		}
		else {
			this.currentTab.add(field);
			field.setOwner(this);
		}
		
		this.allProperties.add(field);
	}

}
