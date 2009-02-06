/*
 * Encog Workbench v1.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of 
 * individual contributors.
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
 */
package org.encog.workbench;

import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JOptionPane;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.TransformerHandler;

import org.encog.neural.persist.EncogPersistedCollection;
import org.encog.neural.persist.persistors.generic.Object2XML;
import org.encog.util.XMLUtil;
import org.encog.workbench.config.EncogWorkBenchConfig;
import org.encog.workbench.frames.document.EncogDocumentFrame;
import org.xml.sax.SAXException;

/**
 * Main class for the Encog Workbench.  The main method in this class
 * starts up the application.  This is a singleton.
 * @author jheaton
 *
 */
public class EncogWorkBench {

	public final static String CONFIG_FILENAME = "EncogWorkbench.conf";
	
	/**
	 * The singleton instance.
	 */
	private static EncogWorkBench instance;

	/**
	 * The main window.
	 */
	private EncogDocumentFrame mainWindow;

	/**
	 * The current file being edited.
	 */
	private EncogPersistedCollection currentFile;
	
	/**
	 * Config info for the workbench.
	 */
	private EncogWorkBenchConfig config;

	/**
	 * The current filename being edited.
	 */
	private String currentFileName;
	
	public EncogWorkBench()
	{
		this.config = new EncogWorkBenchConfig();
	}

	/**
	 * Display a dialog box to ask a question.
	 * @param title The title of the dialog box.
	 * @param question The question to ask.
	 * @return True if the user answered yes.
	 */
	public static boolean askQuestion(final String title, final String question) {
		return JOptionPane.showConfirmDialog(null,
				question, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}

	/**
	 * Display an error dialog.
	 * @param title The title of the error dialog.
	 * @param message The message to display.
	 */
	public static void displayError(final String title, final String message) {
		JOptionPane.showMessageDialog(null, message, title,
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Display a message dialog.
	 * @param title The title of the message dialog.
	 * @param message The message to be displayed.
	 */
	public static void displayMessage(final String title, final String message) {
		JOptionPane.showMessageDialog(null, message, title,
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Determine which top-level frame has the focus.
	 * @return The frame that has the focus.
	 */
	public static Frame getCurrentFocus() {
		final Frame[] frames = Frame.getFrames();
		for (int i = 0; i < frames.length; i++) {
			if (frames[i].hasFocus()) {
				return frames[i];
			}
		}
		return null;
	}

	/**
	 * Get the singleton instance.
	 * @return The instance of the application.
	 */
	public static EncogWorkBench getInstance() {
		if (EncogWorkBench.instance == null) {
			EncogWorkBench.instance = new EncogWorkBench();
		}

		return EncogWorkBench.instance;
	}

	public static void load(final String filename) {
		getInstance().setCurrentFileName(filename);
		getInstance().getCurrentFile().clear();
		getInstance().getCurrentFile().load(filename);
		getInstance().getMainWindow().redraw();
	}

	public static void save(final String filename) {
		getInstance().setCurrentFileName(filename);
		getInstance().getCurrentFile().save(filename);
		getInstance().getMainWindow().redraw();
	}

	/**
	 * Close the current file.
	 */
	public void close() {
		this.currentFile.clear();
		this.currentFileName = null;
		this.mainWindow.redraw();
	}

	/**
	 * @return the currentFile
	 */
	public EncogPersistedCollection getCurrentFile() {
		return this.currentFile;
	}

	/**
	 * @return the currentFileName
	 */
	public String getCurrentFileName() {
		return this.currentFileName;
	}

	/**
	 * @return the mainWindow
	 */
	public EncogDocumentFrame getMainWindow() {
		return this.mainWindow;
	}

	/**
	 * @param currentFile
	 *            the currentFile to set
	 */
	public void setCurrentFile(final EncogPersistedCollection currentFile) {
		this.currentFile = currentFile;
	}

	/**
	 * @param currentFileName
	 *            the currentFileName to set
	 */
	public void setCurrentFileName(final String currentFileName) {
		this.currentFileName = currentFileName;
	}

	/**
	 * @param mainWindow
	 *            the mainWindow to set
	 */
	public void setMainWindow(final EncogDocumentFrame mainWindow) {
		this.mainWindow = mainWindow;
	}

	/**
	 * The main entry point into the program.  To support opening documents
	 * by double clicking their file, the first parameter specifies a file
	 * to open.
	 * @param args The first argument specifies an option file to open.
	 */
	public static void main(final String args[]) {
		final EncogWorkBench workBench = EncogWorkBench.getInstance();
		workBench.setMainWindow(new EncogDocumentFrame());

		if (args.length > 0) {
			EncogWorkBench.load(args[0]);
		}
		try
		{
			loadConfig();
			workBench.getMainWindow().setVisible(true);
			saveConfig();
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
	}

	private static void saveConfig() throws TransformerConfigurationException, IOException, SAXException {
		String home = System.getProperty("user.home");
		File file = new File(home,CONFIG_FILENAME);
		OutputStream os = new FileOutputStream(file);
		final TransformerHandler hd = XMLUtil.saveXML(os);
		hd.startDocument();
		Object2XML conv = new Object2XML();
		conv.save(EncogWorkBench.getInstance().getConfig(), hd);
		hd.endDocument();
		os.close();
		
	}

	private static void loadConfig() {
		String home = System.getProperty("user.home");
		File file = new File(home,CONFIG_FILENAME);
		
	}

	public static String displayInput(String prompt) {
		return JOptionPane.showInputDialog(null, 
				prompt,"");
	}
	
	public EncogWorkBenchConfig getConfig()
	{
		return this.config;
	}

}
