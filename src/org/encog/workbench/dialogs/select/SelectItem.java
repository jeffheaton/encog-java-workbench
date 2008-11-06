package org.encog.workbench.dialogs.select;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import org.encog.workbench.dialogs.CreateObject.CreateObjectResult;
import org.encog.workbench.util.StringConst;

public class SelectItem   {
	
	private String text;
	
	public SelectItem(String text)
	{
		this.text = text;
	}
	
	public String getText()
	{
		return this.text;
	}
		
}
