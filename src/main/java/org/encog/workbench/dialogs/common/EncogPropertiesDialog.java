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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class EncogPropertiesDialog extends EncogCommonDialog {

	private List<PropertiesField> nonTabbedproperties = new ArrayList<PropertiesField>();
	private List<PropertiesField> allProperties = new ArrayList<PropertiesField>();
	private List<String> tabs = new ArrayList<String>();
	private Map<String,List<PropertiesField>> tabMaps = new HashMap<String,List<PropertiesField>>();
	private List<PropertiesField> currentTab; 
	private JTabbedPane tabPane;
	private boolean collectCurrentTabOnly = false;
	
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
	
	public void renderProperties(JPanel contents, List<PropertiesField> properties)
	{
		int y = 0;
		int maxLabelWidth = 0;
		int dialogWidth = getWidth();
		int labelHeight=0;
		
			
		// create the labels
		
		for(PropertiesField field: properties)
		{
			JLabel label = field.createLabel();
			if( label!=null)
				maxLabelWidth = Math.max(maxLabelWidth, label.getWidth());
		}
		
		// create a scroll view
		contents.setLayout(new BorderLayout());		
		JPanel view = new JPanel();
		view.setLayout(null);
		JScrollPane scroll = new JScrollPane(view);
		contents.add(scroll,BorderLayout.CENTER);
		
		y=0;
		// create the text fields
		for(PropertiesField field: properties)
		{
			y = field.createField(view, maxLabelWidth+30, y, dialogWidth-maxLabelWidth-50 );
		}
		
		view.setPreferredSize(new Dimension(view.getWidth(),y));
	}
	
	public void beginTab(String tabName)
	{
		this.currentTab = new ArrayList<PropertiesField>();
		this.tabMaps.put(tabName,this.currentTab);
		this.tabs.add(tabName);
	}

	@Override
	public void collectFields() throws ValidationException {
		
		List<PropertiesField> list;
		
		if( this.collectCurrentTabOnly )
		{
			int index = this.tabPane.getSelectedIndex();
			String name = this.tabPane.getTitleAt(index);
			list = this.tabMaps.get(name);
		}
		else
		{
			list = this.allProperties;
		}
		
		for(PropertiesField field: list)
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

	public boolean isCollectCurrentTabOnly() {
		return collectCurrentTabOnly;
	}

	public void setCollectCurrentTabOnly(boolean collectCurrentTabOnly) {
		this.collectCurrentTabOnly = collectCurrentTabOnly;
	}
	
	public int getCurrentTab()
	{
		if( this.tabPane == null )
		{
			return -1;
		}
		else
		{
			return this.tabPane.getSelectedIndex();
		}
	}
}
