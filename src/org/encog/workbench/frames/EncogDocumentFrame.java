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
package org.encog.workbench.frames;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import org.encog.EncogError;
import org.encog.neural.data.NeuralData;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Network;
import org.encog.neural.networks.layers.FeedforwardLayer;
import org.encog.neural.persist.EncogPersistedCollection;
import org.encog.neural.persist.EncogPersistedObject;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.CreateDataSet;
import org.encog.workbench.dialogs.EditEncogObjectProperties;
import org.encog.workbench.dialogs.select.SelectDialog;
import org.encog.workbench.dialogs.select.SelectItem;
import org.encog.workbench.frames.manager.EncogCommonFrame;
import org.encog.workbench.frames.render.EncogItemRenderer;
import org.encog.workbench.frames.visualize.NetworkVisualizeFrame;
import org.encog.workbench.models.EncogListModel;
import org.encog.workbench.process.Training;
import org.encog.workbench.process.generate.CodeGeneration;
import org.encog.workbench.util.ExtensionFilter;
import org.encog.workbench.util.ImportExportUtility;
import org.encog.workbench.util.NeuralConst;

public class EncogDocumentFrame extends EncogListFrame {

	public static final String FILE_NEW = "New";
	public static final String FILE_CLOSE = "Close";
	public static final String FILE_OPEN = "Open...";
	public static final String FILE_SAVE = "Save";
	public static final String FILE_SAVE_AS = "Save As...";
	public static final String FILE_QUIT = "Quit...";
	public static final String FILE_IMPORT = "Import CSV...";

	public static final String EDIT_CUT = "Cut";
	public static final String EDIT_COPY = "Copy";
	public static final String EDIT_PASTE = "Paste";

	public static final String OBJECTS_CREATE = "Create Object...";
	public static final String OBJECTS_DELETE = "Delete Object...";

	public static final String TRAIN_BACKPROPAGATION = "Train Backpropagation...";
	public static final String TRAIN_SIMULATED_ANNEALING = "Train Simulated Annealing...";
	public static final String TRAIN_GENETIC = "Train Genetically...";
	public static final String TRAIN_HOPFIELD = "Train Hopfield Layers...";
	public static final String TRAIN_SOM = "Train SOM Layers...";

	public static final String TOOLS_CODE = "Generate Code...";

	public static final ExtensionFilter ENCOG_FILTER = new ExtensionFilter(
			"Encog Files", ".eg");
	public static final ExtensionFilter CSV_FILTER = new ExtensionFilter(
			"CSV Files", ".csv");
	public static final String WINDOW_TITLE = "Encog Workbench 1.0";
	/**
	 * 
	 */
	private static final long serialVersionUID = -4161616483326975155L;
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenu menuEdit;
	private JMenu menuObjects;
	private JMenu menuTrain;

	private JMenu menuTools;
	private int trainingCount = 1;
	private int networkCount = 1;
	private JPopupMenu popupNetwork;
	private JMenuItem popupNetworkDelete;

	private JMenuItem popupNetworkProperties;
	private JMenuItem popupNetworkOpen;
	private JMenuItem popupNetworkVisualize;
	private JMenuItem popupNetworkQuery;
	private JPopupMenu popupData;
	private JMenuItem popupDataDelete;

	private JMenuItem popupDataProperties;
	private JMenuItem popupDataOpen;
	private JMenuItem popupDataImport;
	private JMenuItem popupDataExport;

	private final EncogListModel encogListModel;

	public EncogDocumentFrame() {
		this.setSize(640, 480);

		addWindowListener(this);
		EncogWorkBench.getInstance().setCurrentFile(
				new EncogPersistedCollection());
		this.encogListModel = new EncogListModel(EncogWorkBench.getInstance()
				.getCurrentFile());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initMenuBar();
		initContents();
		initPopup();
	}

	public void actionPerformed(final ActionEvent event) {
		if (event.getActionCommand().equals(EncogDocumentFrame.FILE_OPEN)) {
			performFileOpen();
		} else if (event.getActionCommand()
				.equals(EncogDocumentFrame.FILE_SAVE)) {
			performFileSave();
		} else if (event.getActionCommand().equals(
				EncogDocumentFrame.FILE_SAVE_AS)) {
			performFileSaveAs();
		} else if (event.getActionCommand().equals(
				EncogDocumentFrame.FILE_IMPORT)) {
			performImport(null);
		} else if (event.getActionCommand()
				.equals(EncogDocumentFrame.FILE_QUIT)) {
			System.exit(0);
		} else if (event.getActionCommand().equals(
				EncogDocumentFrame.OBJECTS_CREATE)) {
			performObjectsCreate();
		} else if (event.getActionCommand().equals(
				EncogDocumentFrame.OBJECTS_DELETE)) {
			performObjectsDelete();
		} else if (event.getActionCommand().equals(
				EncogDocumentFrame.FILE_CLOSE)) {
			performFileClose();
		} else if (event.getActionCommand().equals(EncogDocumentFrame.FILE_NEW)) {
			performFileClose();
		} else if (event.getActionCommand().equals(
				EncogDocumentFrame.TRAIN_BACKPROPAGATION)) {
			Training.performBackpropagation();

		} else if (event.getActionCommand().equals(
				EncogDocumentFrame.TRAIN_GENETIC)) {
			Training.performGenetic();

		} else if (event.getActionCommand().equals(
				EncogDocumentFrame.TRAIN_HOPFIELD)) {
			Training.performHopfield();

		} else if (event.getActionCommand()
				.equals(EncogDocumentFrame.TRAIN_SOM)) {
			Training.performSOM();

		} else if (event.getActionCommand().equals(
				EncogDocumentFrame.TRAIN_SIMULATED_ANNEALING)) {
			Training.performAnneal();
		}

		if (event.getSource() == this.popupNetworkDelete) {
			performObjectsDelete();
		} else if (event.getSource() == this.popupNetworkQuery) {
			performNetworkQuery();
		} else if (event.getSource() == this.popupNetworkVisualize) {
			performNetworkVisualize();
		} else if (event.getSource() == this.popupNetworkOpen) {
			openItem(this.contents.getSelectedValue());
		} else if (event.getSource() == this.popupNetworkProperties) {
			final EditEncogObjectProperties dialog = new EditEncogObjectProperties(
					this, (EncogPersistedObject) this.contents
							.getSelectedValue());
			dialog.process();
		} else if (event.getSource() == this.popupDataDelete) {
			performObjectsDelete();
		} else if (event.getSource() == this.popupDataOpen) {
			openItem(this.contents.getSelectedValue());
		} else if (event.getSource() == this.popupDataProperties) {
			final EditEncogObjectProperties dialog = new EditEncogObjectProperties(
					this, (EncogPersistedObject) this.contents
							.getSelectedValue());
			dialog.process();
		} else if (event.getSource() == this.popupDataImport) {
			performImport(this.contents.getSelectedValue());
		} else if (event.getSource() == this.popupDataExport) {
			performExport(this.contents.getSelectedValue());
		} else if (event.getActionCommand().equals(
				EncogDocumentFrame.TOOLS_CODE)) {
			performGenerateCode();
		} else if (event.getActionCommand().equals(EncogDocumentFrame.EDIT_CUT)) {
			performEditCut();
		} else if (event.getActionCommand()
				.equals(EncogDocumentFrame.EDIT_COPY)) {
			performEditCopy();
		} else if (event.getActionCommand().equals(
				EncogDocumentFrame.EDIT_PASTE)) {
			performEditPaste();
		}
	}

	private boolean checkSave() {
		final String currentFileName = EncogWorkBench.getInstance()
				.getCurrentFileName();

		if (currentFileName != null
				|| EncogWorkBench.getInstance().getCurrentFile().getList()
						.size() > 0) {
			final String f = currentFileName != null ? currentFileName
					: "Untitled";
			final int response = JOptionPane.showConfirmDialog(null, "Save "
					+ f + ", first?", "Save", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				performFileSave();
				return true;
			} else if (response == JOptionPane.NO_OPTION) {
				return true;
			} else {
				return false;
			}

		}

		return true;
	}

	private void initContents() {
		// setup the contents list
		this.contents = new JList(this.encogListModel);
		this.contents.setCellRenderer(new EncogItemRenderer());
		this.contents.setFixedCellHeight(72);
		this.contents.addMouseListener(this);

		final JScrollPane scrollPane = new JScrollPane(this.contents);

		getContentPane().add(scrollPane);
		redraw();
	}

	private void initMenuBar() {
		// menu bar
		this.menuBar = new JMenuBar();
		this.menuFile = new JMenu("File");
		this.menuFile.add(addItem(this.menuFile, EncogDocumentFrame.FILE_NEW,
				'n'));
		this.menuFile.add(addItem(this.menuFile, EncogDocumentFrame.FILE_OPEN,
				'o'));
		this.menuFile.addSeparator();
		this.menuFile.add(addItem(this.menuFile, EncogDocumentFrame.FILE_CLOSE,
				'c'));
		this.menuFile.add(new JMenuItem("Revert", 'r'));
		this.menuFile.addSeparator();
		this.menuFile.add(addItem(this.menuFile, EncogDocumentFrame.FILE_SAVE,
				's'));
		this.menuFile.add(addItem(this.menuFile,
				EncogDocumentFrame.FILE_SAVE_AS, 'a'));
		this.menuFile.addSeparator();
		this.menuFile.add(addItem(this.menuFile,
				EncogDocumentFrame.FILE_IMPORT, 'i'));
		this.menuFile.addSeparator();
		this.menuFile.add(addItem(this.menuFile, EncogDocumentFrame.FILE_QUIT,
				'q'));
		this.menuFile.addActionListener(this);
		this.menuBar.add(this.menuFile);

		this.menuEdit = new JMenu("Edit");
		this.menuEdit.add(addItem(this.menuEdit, EncogDocumentFrame.EDIT_CUT,
				'x'));
		this.menuEdit.add(addItem(this.menuEdit, EncogDocumentFrame.EDIT_COPY,
				'c'));
		this.menuEdit.add(addItem(this.menuEdit, EncogDocumentFrame.EDIT_PASTE,
				'v'));
		this.menuBar.add(this.menuEdit);

		this.menuObjects = new JMenu("Objects");
		this.menuObjects.add(addItem(this.menuObjects,
				EncogDocumentFrame.OBJECTS_CREATE, 'c'));
		this.menuObjects.add(addItem(this.menuObjects,
				EncogDocumentFrame.OBJECTS_DELETE, 'd'));
		this.menuBar.add(this.menuObjects);

		this.menuTrain = new JMenu("Train");
		addItem(this.menuTrain, EncogDocumentFrame.TRAIN_BACKPROPAGATION, 'b');
		this.menuTrain.add(addItem(this.menuObjects,
				EncogDocumentFrame.TRAIN_SIMULATED_ANNEALING, 'a'));
		this.menuTrain.add(addItem(this.menuObjects,
				EncogDocumentFrame.TRAIN_GENETIC, 'g'));
		this.menuTrain.addSeparator();
		this.menuTrain.add(addItem(this.menuObjects,
				EncogDocumentFrame.TRAIN_HOPFIELD, 'h'));
		this.menuTrain.add(addItem(this.menuObjects,
				EncogDocumentFrame.TRAIN_SOM, 's'));

		this.menuBar.add(this.menuTrain);

		this.menuTools = new JMenu("Tools");
		addItem(this.menuTools, EncogDocumentFrame.TOOLS_CODE, 'g');
		this.menuBar.add(this.menuTools);

		setJMenuBar(this.menuBar);
	}

	private void initPopup() {
		// build network popup menu
		this.popupNetwork = new JPopupMenu();
		this.popupNetworkDelete = addItem(this.popupNetwork, "Delete", 'd');
		this.popupNetworkOpen = addItem(this.popupNetwork, "Open", 'o');
		this.popupNetworkProperties = addItem(this.popupNetwork, "Properties",
				'p');
		this.popupNetworkVisualize = addItem(this.popupNetwork, "Visualize",
				'v');
		this.popupNetworkQuery = addItem(this.popupNetwork, "Query", 'q');

		this.popupData = new JPopupMenu();
		this.popupDataDelete = addItem(this.popupData, "Delete", 'd');
		this.popupDataOpen = addItem(this.popupData, "Open", 'o');
		this.popupDataProperties = addItem(this.popupData, "Properties", 'p');
		this.popupDataImport = addItem(this.popupData, "Import...", 'i');
		this.popupDataExport = addItem(this.popupData, "Export...", 'e');
	}

	protected void openItem(final Object item) {
		if (item instanceof NeuralDataSet) {
			final BasicNeuralDataSet nds = (BasicNeuralDataSet) item;
			if (getSubwindows().checkBeforeOpen(nds, TrainingDataFrame.class)) {
				final TrainingDataFrame frame = new TrainingDataFrame(
						(BasicNeuralDataSet) item);
				frame.setVisible(true);
				getSubwindows().add(frame);
			}
		} else if (item instanceof Network) {

			final BasicNetwork net = (BasicNetwork) item;
			if (getSubwindows().checkBeforeOpen(net, TrainingDataFrame.class)) {
				final NetworkFrame frame = new NetworkFrame(net);
				frame.setVisible(true);
				getSubwindows().add(frame);
			}
		}
	}

	private void performEditCopy() {
		final Frame frame = EncogWorkBench.getCurrentFocus();
		if (frame instanceof EncogCommonFrame) {
			final EncogCommonFrame ecf = (EncogCommonFrame) frame;
			ecf.copy();
		}

	}

	private void performEditCut() {
		final Frame frame = EncogWorkBench.getCurrentFocus();
		if (frame instanceof EncogCommonFrame) {
			final EncogCommonFrame ecf = (EncogCommonFrame) frame;
			ecf.cut();
		}
	}

	private void performEditPaste() {
		final Frame frame = EncogWorkBench.getCurrentFocus();
		if (frame instanceof EncogCommonFrame) {
			final EncogCommonFrame ecf = (EncogCommonFrame) frame;
			ecf.paste();
		}

	}

	public void performExport(final Object obj) {
		if (obj instanceof BasicNeuralDataSet) {
			SelectItem itemCSV, itemXML;
			final List<SelectItem> list = new ArrayList<SelectItem>();
			list.add(itemCSV = new SelectItem("Export CSV"));
			list.add(itemXML = new SelectItem("Export XML (EG format)"));
			final SelectDialog dialog = new SelectDialog(this, list);
			final SelectItem result = dialog.process();

			if (result == null) {
				return;
			}

			final JFileChooser fc = new JFileChooser();

			if (result == itemCSV) {
				fc.setFileFilter(EncogDocumentFrame.CSV_FILTER);
			} else {
				fc.setFileFilter(EncogDocumentFrame.ENCOG_FILTER);
			}

			final int r = fc.showSaveDialog(this);

			if (r != JFileChooser.APPROVE_OPTION) {
				return;
			}

			try {

				if (result == itemCSV) {
					ImportExportUtility.exportCSV((NeuralDataSet) obj, fc
							.getSelectedFile().toString());
				} else if (result == itemXML) {
					ImportExportUtility.exportXML((BasicNeuralDataSet) obj, fc
							.getSelectedFile().toString());
				}
				JOptionPane.showMessageDialog(this, "Export succssful.",
						"Complete", JOptionPane.INFORMATION_MESSAGE);
			} catch (final IOException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(),
						"Can't Export File", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void performFileClose() {
		if (!checkSave()) {
			return;
		}
		EncogWorkBench.getInstance().close();
	}

	private void performFileOpen() {
		try {
			if (!checkSave()) {
				return;
			}

			final JFileChooser fc = new JFileChooser();
			fc.addChoosableFileFilter(EncogDocumentFrame.ENCOG_FILTER);
			final int result = fc.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				EncogWorkBench.load(fc.getSelectedFile().getAbsolutePath());
			}
		} catch (final EncogError e) {
			JOptionPane.showMessageDialog(this, e.getMessage(),
					"Can't Open File", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void performFileSave() {
		try {
			if (EncogWorkBench.getInstance().getCurrentFileName() == null) {
				performFileSaveAs();
			} else {
				EncogWorkBench.getInstance().getCurrentFile().save(
						EncogWorkBench.getInstance().getCurrentFileName());
			}
		} catch (final EncogError e) {
			JOptionPane.showMessageDialog(this, e.getMessage(),
					"Can't Open File", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void performFileSaveAs() {
		try {
			final JFileChooser fc = new JFileChooser();
			fc.setFileFilter(EncogDocumentFrame.ENCOG_FILTER);
			final int result = fc.showSaveDialog(this);

			if (result == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				// no extension
				if (ExtensionFilter.getExtension(file) == null) {
					file = new File(file.getPath() + ".eg");
				}
				// wrong extension
				else if (ExtensionFilter.getExtension(file).compareTo("eg") != 0) {

					if (JOptionPane
							.showConfirmDialog(
									this,
									"Encog files are usually stored with the .eg extension. \nDo you wish to save with the name you specified?",
									"Warning", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
				}

				// file doesn't exist yet
				if (file.exists()) {
					final int response = JOptionPane.showConfirmDialog(null,
							"Overwrite existing file?", "Confirm Overwrite",
							JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.CANCEL_OPTION) {
						return;
					}
				}

				EncogWorkBench.save(file.getAbsolutePath());
			}
		} catch (final EncogError e) {
			JOptionPane.showMessageDialog(this, e.getMessage(),
					"Can't Save File", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void performGenerateCode() {
		final CodeGeneration code = new CodeGeneration();
		code.processCodeGeneration();

	}

	private void performImport(final Object obj) {

		final JFileChooser fc = new JFileChooser();
		fc.addChoosableFileFilter(EncogDocumentFrame.CSV_FILTER);
		final int result = fc.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			BasicNeuralDataSet set = (BasicNeuralDataSet) obj;
			boolean clear = false;
			boolean addit = false;

			if (obj != null) {
				final int o = JOptionPane
						.showConfirmDialog(
								this,
								"Do you wish to delete information already in this result set?",
								"Delete", JOptionPane.YES_NO_CANCEL_OPTION);

				if (o == JOptionPane.CANCEL_OPTION) {
					return;
				} else if (o == JOptionPane.YES_OPTION) {
					clear = true;
				}
			} else {
				final CreateDataSet dialog = new CreateDataSet(this);
				if (!dialog.process()) {
					return;
				}

				set = new BasicNeuralDataSet();
				NeuralData input = null;
				NeuralData ideal = null;
				input = new BasicNeuralData(dialog.getInputSize());
				if (dialog.getIdealSize() > 0) {
					ideal = new BasicNeuralData(dialog.getIdealSize());
				}
				set.add(input, ideal);
				set.setName(dialog.getName());
				set.setDescription(dialog.getDescription());
				clear = true;
				addit = true;
			}

			try {
				ImportExportUtility.importCSV(set, fc.getSelectedFile()
						.toString(), clear);
				if (addit) {
					EncogWorkBench.getInstance().getCurrentFile().add(set);
					EncogWorkBench.getInstance().getMainWindow().redraw();
				}
			} catch (final Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage(),
						"Can't Import File", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void performNetworkQuery() {
		final BasicNetwork item = (BasicNetwork) this.contents
				.getSelectedValue();

		if (getSubwindows().checkBeforeOpen(item, NetworkQueryFrame.class)) {
			final NetworkQueryFrame frame = new NetworkQueryFrame(item);
			frame.setVisible(true);
			getSubwindows().add(frame);
		}

	}

	public void performNetworkVisualize() {
		final BasicNetwork item = (BasicNetwork) this.contents
				.getSelectedValue();

		if (getSubwindows().checkBeforeOpen(item, NetworkVisualizeFrame.class)) {
			final NetworkVisualizeFrame frame = new NetworkVisualizeFrame(item);
			frame.setVisible(true);
			getSubwindows().add(frame);
		}
	}

	public void performObjectsCreate() {

		SelectItem itemTraining, itemNetwork;
		final List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(itemTraining = new SelectItem("Training Data"));
		list.add(itemNetwork = new SelectItem("Neural Network"));
		final SelectDialog dialog = new SelectDialog(this, list);
		final SelectItem result = dialog.process();

		if (result == itemNetwork) {
			final BasicNetwork network = new BasicNetwork();
			network.addLayer(new FeedforwardLayer(2));
			network.addLayer(new FeedforwardLayer(3));
			network.addLayer(new FeedforwardLayer(1));
			network.reset();
			network.setName("network-" + this.networkCount++);
			network.setDescription("A neural network");
			EncogWorkBench.getInstance().getCurrentFile().add(network);
			EncogWorkBench.getInstance().getMainWindow().redraw();
		} else if (result == itemTraining) {
			final BasicNeuralDataSet trainingData = new BasicNeuralDataSet(
					NeuralConst.XOR_INPUT, NeuralConst.XOR_IDEAL);

			trainingData.setName("data-" + this.trainingCount++);
			trainingData.setDescription("Training data");
			EncogWorkBench.getInstance().getCurrentFile().add(trainingData);
			EncogWorkBench.getInstance().getMainWindow().redraw();
		}
	}

	public void performObjectsDelete() {
		final Object object = this.contents.getSelectedValue();
		if (object != null) {
			if (getSubwindows().find((EncogPersistedObject) object) != null) {
				EncogWorkBench.displayError("Can't Delete Object",
						"This object can not be deleted while it is open.");
				return;
			}

			if (JOptionPane.showConfirmDialog(this,
					"Are you sure you want to delete this object?", "Warning",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				EncogWorkBench.getInstance().getCurrentFile().getList().remove(
						object);
				EncogWorkBench.getInstance().getMainWindow().redraw();
			}
		}
	}

	public void redraw() {

		// set the title properly
		if (EncogWorkBench.getInstance().getCurrentFileName() == null) {
			setTitle(EncogDocumentFrame.WINDOW_TITLE + " : Untitled");
		} else {
			setTitle(EncogDocumentFrame.WINDOW_TITLE + " : "
					+ EncogWorkBench.getInstance().getCurrentFileName());
		}

		// redraw the list
		this.encogListModel.invalidate();
	}

	public void rightMouseClicked(final MouseEvent e, final Object item) {
		if (item instanceof Network) {
			this.popupNetwork.show(e.getComponent(), e.getX(), e.getY());
		} else if (item instanceof NeuralDataSet) {
			this.popupData.show(e.getComponent(), e.getX(), e.getY());
		}

	}

	public void windowClosed(final WindowEvent e) {
		System.exit(0);

	}

	public void windowOpened(final WindowEvent e) {
	}
}
