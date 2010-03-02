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

package org.encog.workbench.dialogs.error;

import java.awt.Frame;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.encog.Encog;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.EncogCommonDialog;
import org.encog.workbench.dialogs.common.ValidationException;
import org.encog.workbench.util.EncogFonts;

public class ErrorDialog extends EncogCommonDialog {

	private final static String NEW_LINE = System.getProperty("line.separator");
	private final JTextArea text;
	private final JScrollPane scroll;
	
	public ErrorDialog(Frame owner, Throwable t, BasicNetwork network, NeuralDataSet dataset) {
		super(owner);
		this.setSize(640, 480);
		setTitle("An Unhandled Error Occured");
		this.text = new JTextArea();
		this.text.setFont(EncogFonts.getInstance().getCodeFont());
		this.text.setEditable(false);
		this.scroll = new JScrollPane(this.text);
		getContentPane().add(this.scroll);
		
		StringBuilder message = new StringBuilder();
		message.append("We are very sorry but an unexpected error has occured."+NEW_LINE);
		message.append("Would you consider sending this information to us?"+NEW_LINE);
		message.append("No personal information will be transmitted, just what you see below."+NEW_LINE);
		message.append("This information is very useful to us to make Encog a better program."+NEW_LINE);
		message.append("----------------------------------------------------------------------"+NEW_LINE);
		if( network!=null)
		{
			message.append(network.toString()+NEW_LINE);
			message.append("----------------------------------------------------------------------"+NEW_LINE);
		}
		
		if( dataset!=null )
		{
			message.append(dataset.toString()+NEW_LINE);
			message.append("Input: " + dataset.getInputSize()+NEW_LINE );
			message.append("Output: " + dataset.getIdealSize()+NEW_LINE );
			message.append("----------------------------------------------------------------------"+NEW_LINE);	
		}
		
		message.append("Encog Version: " + Encog.getInstance().getProperties().get(Encog.ENCOG_VERSION)+NEW_LINE);
		message.append("Encog Workbench Version: " + EncogWorkBench.VERSION +NEW_LINE);
		message.append("Java Version: " + System.getProperty("java.version") +NEW_LINE);
		message.append("Java Vendor: " + System.getProperty("java.vendor") +NEW_LINE);
		message.append("OS Name: " + System.getProperty("os.name")+NEW_LINE );
		message.append("OS Arch: " + System.getProperty("os.arch")+NEW_LINE );
		message.append("OS Version: " + System.getProperty("os.version")+NEW_LINE );
		message.append("Core Count: " + Runtime.getRuntime().availableProcessors() + NEW_LINE);
		message.append("----------------------------------------------------------------------"+NEW_LINE);
		message.append(getCustomStackTrace(t));
		
		this.text.setText(message.toString());
		this.text.setSelectionStart(0);
		this.text.setSelectionEnd(0);
	}
	
	  public static String getCustomStackTrace(Throwable aThrowable) {
		    //add the class name and any message passed to constructor
		    final StringBuilder result = new StringBuilder( "Exception: " );
		    result.append(aThrowable.toString());
		    result.append(NEW_LINE);

		    //add each element of the stack trace
		    for (StackTraceElement element : aThrowable.getStackTrace() ){
		      result.append( element );
		      result.append( NEW_LINE );
		    }
		    return result.toString();
		  }


	@Override
	public void collectFields() throws ValidationException {
		// TODO Auto-generated method stub
		this.getBodyPanel();
	}

	@Override
	public void setFields() {
		// TODO Auto-generated method stub
		
	}
	
	public static void handleError(Throwable t, BasicNetwork network, NeuralDataSet training)
	{
		ErrorDialog dialog = new ErrorDialog(EncogWorkBench.getInstance().getMainWindow(),t,network,training);
		dialog.process();
	}

}
