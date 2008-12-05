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

import javax.swing.JOptionPane;

import org.encog.neural.persist.EncogPersistedCollection;
import org.encog.workbench.frames.EncogDocumentFrame;

public class EncogWorkBench {

	private static EncogWorkBench instance;

	public static boolean askQuestion(final String title, final String question) {
		return JOptionPane.showConfirmDialog(getInstance().getMainWindow(),
				question, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}

	public static void displayError(final String title, final String message) {
		JOptionPane.showMessageDialog(null, message, title,
				JOptionPane.ERROR_MESSAGE);
	}

	public static void displayMessage(final String title, final String message) {
		JOptionPane.showMessageDialog(null, message, title,
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static Frame getCurrentFocus() {
		final Frame[] frames = Frame.getFrames();
		for (int i = 0; i < frames.length; i++) {
			if (frames[i].hasFocus()) {
				return frames[i];
			}
		}
		return null;
	}

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

	public static void main(final String args[]) {
		final EncogWorkBench workBench = EncogWorkBench.getInstance();
		workBench.setMainWindow(new EncogDocumentFrame());

		if (args.length > 0) {
			EncogWorkBench.load(args[0]);
		}

		workBench.getMainWindow().setVisible(true);
	}

	public static void save(final String filename) {
		getInstance().setCurrentFileName(filename);
		getInstance().getCurrentFile().save(filename);
		getInstance().getMainWindow().redraw();
	}

	private EncogDocumentFrame mainWindow;

	private EncogPersistedCollection currentFile;

	private String currentFileName;

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
}
