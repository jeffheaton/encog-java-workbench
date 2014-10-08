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
package org.encog.workbench;

import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JOptionPane;

import org.encog.Encog;
import org.encog.EncogError;
import org.encog.mathutil.error.ErrorCalculation;
import org.encog.ml.MLMethod;
import org.encog.ml.data.MLDataSet;
import org.encog.neural.neat.NEATPopulation;
import org.encog.persist.EncogDirectoryPersistence;
import org.encog.util.concurrency.MultiThreadable;
import org.encog.util.obj.ReflectionUtil;
import org.encog.workbench.config.EncogWorkBenchConfig;
import org.encog.workbench.dialogs.error.ErrorDialog;
import org.encog.workbench.frames.document.EncogDocumentFrame;
import org.encog.workbench.frames.document.EncogOutputPanel;
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.frames.document.tree.ProjectFile;
import org.encog.workbench.frames.document.tree.ProjectItem;
import org.encog.workbench.frames.document.tree.ProjectTraining;
import org.encog.workbench.util.FileUtil;
import org.encog.workbench.util.WorkbenchLogHandler;

/**
 * Main class for the Encog Workbench. The main method in this class starts up
 * the application. This is a singleton.
 * 
 * @author jheaton
 * 
 */
public class EncogWorkBench implements Runnable {

	/**
	 * The name of the config file.
	 */
	public final static String CONFIG_FILENAME = ".EncogWorkbench.conf";
	public final static String VERSION = "3.3";
	public static final String COPYRIGHT = "Copyright 2014 by Heaton Research, Inc.";

	/**
	 * The singleton instance.
	 */
	private static EncogWorkBench instance;
	
	/**
	 * The main window.
	 */
	private EncogDocumentFrame mainWindow;

	/**
	 * Config info for the workbench.
	 */
	private EncogWorkBenchConfig config;

	private WorkbenchLogHandler logHandler;

	/**
	 * The current filename being edited.
	 */
	private String currentFileName;

	public EncogWorkBench() {
		this.config = new EncogWorkBenchConfig();
		this.logHandler = new WorkbenchLogHandler();
	}

	/**
	 * Display a dialog box to ask a question.
	 * 
	 * @param title
	 *            The title of the dialog box.
	 * @param question
	 *            The question to ask.
	 * @return True if the user answered yes.
	 */
	public static boolean askQuestion(final String title, final String question) {
		return JOptionPane.showConfirmDialog(null, question, title,
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}

	/**
	 * Display an error dialog.
	 * 
	 * @param title
	 *            The title of the error dialog.
	 * @param message
	 *            The message to display.
	 */
	public static void displayError(final String title, final String message) {
		JOptionPane.showMessageDialog(null, message, title,
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Display a message dialog.
	 * 
	 * @param title
	 *            The title of the message dialog.
	 * @param message
	 *            The message to be displayed.
	 */
	public static void displayMessage(final String title, final String message) {
		JOptionPane.showMessageDialog(null, message, title,
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Determine which top-level frame has the focus.
	 * 
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
	 * 
	 * @return The instance of the application.
	 */
	public static EncogWorkBench getInstance() {
		if (EncogWorkBench.instance == null) {
			EncogWorkBench.instance = new EncogWorkBench();
		}

		return EncogWorkBench.instance;
	}

	/**
	 * @return the mainWindow
	 */
	public EncogDocumentFrame getMainWindow() {
		return this.mainWindow;
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

	public static String displayInput(String prompt) {
		return JOptionPane.showInputDialog(null, prompt, "");
	}

	public EncogWorkBenchConfig getConfig() {
		return this.config;
	}

	public static boolean displayQuery(String title, String message) {
		int result = JOptionPane.showConfirmDialog(null, message, title,
				JOptionPane.YES_NO_OPTION);
		EncogWorkBench.getInstance().getMainWindow().endWait();
		return result == JOptionPane.YES_OPTION;
	}

	public static void displayError(String title, Throwable t,
			ProjectFile network, MLDataSet set) {
		if (t instanceof EncogError) {
			displayError(
					title,
					"An error occured while performing this operation:\n"
							+ t.toString());
			t.printStackTrace();
		} else if (t instanceof OutOfMemoryError) {
			displayError(title, "Not enough memory to do that.");
			t.printStackTrace();
		} else
			ErrorDialog.handleError(t, network, set);
	}

	public static void displayError(String title, Throwable t) {
		displayError(title, t, null, null);
		EncogWorkBench.getInstance().getMainWindow().endWait();
	}

	public void run() {
		for (;;) {
			try {
				Thread.sleep(300000);
			} catch (InterruptedException e) {
				return;
			}
			// background();
		}
	}

	public void init() {
		Encog.getInstance().registerPlugin(new WorkbenchLogging());
		EncogWorkBench.getInstance().getConfig().loadConfig();

		if (EncogWorkBench.getInstance().getConfig().isUseOpenCL()) {
			EncogWorkBench.initCL();
		}

		ErrorCalculation.setMode(EncogWorkBench.getInstance().getConfig()
				.getErrorCalculation());

		Thread thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * The main entry point into the program. To support opening documents by
	 * double clicking their file, the first parameter specifies a file to open.
	 * 
	 * @param args
	 *            The first argument specifies an option file to open.
	 */
	public static void main(final String args[]) {
		Encog.getInstance();

		if (Encog.isOSX()) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("apple.awt.brushMetalLook", "true");
			System.setProperty(
					"com.apple.mrj.application.apple.menu.about.name",
					"Encog Workbench");
			System.setProperty("apple.eawt.quitStrategy", "CLOSE_ALL_WINDOWS");
		}

		final EncogWorkBench workBench = EncogWorkBench.getInstance();
		workBench.setMainWindow(new EncogDocumentFrame());

		workBench.init();

		try {
			workBench.getMainWindow().setVisible(true);
		} catch (Throwable t) {
			EncogWorkBench.displayError("Internal error", t.getMessage());
			t.printStackTrace();
		}
	}

	public static void initCL() {
		try {
			// Encog.getInstance().initCL();
		} catch (Throwable t) {
			EncogWorkBench
					.displayError(
							"Error",
							"Can't init OpenCL.\n  Make sure you have a compatible graphics card,\n and the drivers have  been installed.");
			EncogWorkBench.displayError("Error", t);
			EncogWorkBench.getInstance().getConfig().setUseOpenCL(false);
			EncogWorkBench.getInstance().getConfig().saveConfig();
		}

	}

	public void clearOutput() {
		this.getMainWindow().getOutputPane().clear();
	}

	public void output(String str) {
		EncogOutputPanel pane = this.getMainWindow().getOutputPane();
		if (pane != null) {
			pane.output(str);
		}
	}

	public void outputLine(String str) {
		EncogOutputPanel pane = this.getMainWindow().getOutputPane();
		if (pane != null) {
			pane.outputLine(str);
		}
	}

	/**
	 * @return the logHandler
	 */
	public WorkbenchLogHandler getLogHandler() {
		return logHandler;
	}

	public File getEncogFolders() {
		if (config.getProjectRoot() == null
				|| config.getProjectRoot().trim().length() == 0) {
			return EncogWorkBenchConfig.getDefaultProjectRoot();
		} else {
			return new File(config.getProjectRoot());
		}
	}

	public List<ProjectTraining> getTrainingData() {

		List<ProjectTraining> result = new ArrayList<ProjectTraining>();

		for (ProjectItem item : this.getMainWindow().getTree().getModel()
				.getData()) {
			if (item instanceof ProjectTraining)
				result.add((ProjectTraining) item);
		}

		return result;
	}

	public List<ProjectFile> getAnalystFiles() {

		List<ProjectFile> result = new ArrayList<ProjectFile>();

		for (ProjectItem item : this.getMainWindow().getTree().getModel()
				.getData()) {

			if (item instanceof ProjectFile) {
				ProjectFile pf = (ProjectFile) item;
				if (pf.getExtension().equalsIgnoreCase("ega")) {
					result.add((ProjectFile) item);
				}
			}
		}

		return result;
	}

	public File getProjectDirectory() {
		return this.getMainWindow().getTree().getModel().getPath();
	}

	public void refresh() {
		this.getMainWindow().getTree().refresh();

	}

	public void save(File path, Object network) {
		EncogDirectoryPersistence.saveObject(path, network);
		refresh();
	}

	public EncogDirectoryPersistence getProject() {
		return this.getMainWindow().getTree().getModel().getProjectDirectory();
	}

	public List<ProjectEGFile> getMLMethods(boolean includePop) {
		List<ProjectEGFile> result = new ArrayList<ProjectEGFile>();

		for (ProjectItem item : this.getMainWindow().getTree().getModel()
				.getData()) {
			if (item instanceof ProjectEGFile) {
				ProjectEGFile item2 = (ProjectEGFile) item;
				Class<?> clazz = ReflectionUtil.resolveEncogClass(item2
						.getEncogType());
				if (clazz == null) {
					continue;
				}
				if (MLMethod.class.isAssignableFrom(clazz)) {
					result.add(item2);
				} else if (NEATPopulation.class.isAssignableFrom(clazz)
						&& includePop) {
					result.add(item2);
				}
			}
		}

		return result;
	}

	public void setupThreads(Object obj) {
		if (obj instanceof MultiThreadable) {
			MultiThreadable threadable = (MultiThreadable) obj;
			int threads = this.config.getThreadCount();
			threadable.setThreadCount(threads);
		}
	}

	public List<ProjectEGFile> getNEATPopulations() {
		List<ProjectEGFile> result = new ArrayList<ProjectEGFile>();

		for (ProjectItem item : this.getMainWindow().getTree().getModel()
				.getData()) {
			if (item instanceof ProjectEGFile) {
				ProjectEGFile item2 = (ProjectEGFile) item;
				Class<?> clazz = ReflectionUtil.resolveEncogClass(item2
						.getEncogType());
				if (NEATPopulation.class.isAssignableFrom(clazz)) {
					result.add(item2);
				}
			}
		}

		return result;
	}

	public static String displayInput(String prompt, String str) {
		return JOptionPane.showInputDialog(null, prompt, str);
	}
	
	public List<ProjectTraining> getTrainingData(final String sortFirst) {
        List<ProjectTraining> list = getTrainingData();

        Collections.sort(list, new Comparator<ProjectTraining>() {
            @Override
            public int compare(ProjectTraining p1, ProjectTraining p2) {
                boolean isFirst1 = FileUtil.getFileName(p1.getFile()).endsWith(sortFirst);
                boolean isFirst2 = FileUtil.getFileName(p2.getFile()).endsWith(sortFirst);

                if (isFirst1 && !isFirst2) return -1;
                if (isFirst2 && !isFirst1) return 1;

                return p1.getName().compareTo(p2.getName());
            }
        });

        return list;
    }
}
