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
package org.encog.workbench.dialogs.error;

import java.awt.Frame;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.encog.Encog;
import org.encog.ml.data.MLDataSet;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.EncogCommonDialog;
import org.encog.workbench.dialogs.common.ValidationException;
import org.encog.workbench.frames.document.tree.ProjectFile;
import org.encog.workbench.util.EncogFonts;

public class ErrorDialog extends EncogCommonDialog {

	private final static String NEW_LINE = System.getProperty("line.separator");
	private final JTextArea text;
	private final JScrollPane scroll;
	
	public ErrorDialog(Frame owner, Throwable t, ProjectFile network, MLDataSet dataset) {
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
		
		Locale defaultLocale = Locale.getDefault();
	    message.append("ISO3 Country: " + defaultLocale.getISO3Country()+ NEW_LINE);
	    message.append("Display Country: " + defaultLocale.getDisplayCountry() + NEW_LINE);
		message.append("Radix: " + DecimalFormatSymbols.getInstance().getDecimalSeparator() + NEW_LINE);
		message.append("Grouping: " + DecimalFormatSymbols.getInstance().getGroupingSeparator() + NEW_LINE);

		
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
	
	public static void handleError(Throwable t, ProjectFile network, MLDataSet training)
	{
		t.printStackTrace();
		ErrorDialog dialog = new ErrorDialog(EncogWorkBench.getInstance().getMainWindow(),t,network,training);
		dialog.process();
		EncogWorkBench.getInstance().getMainWindow().endWait();
	}

}
