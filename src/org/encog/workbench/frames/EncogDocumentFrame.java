package org.encog.workbench.frames;

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
import javax.swing.JToolBar;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

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
import org.encog.workbench.dialogs.training.backpropagation.InputBackpropagation;
import org.encog.workbench.frames.visualize.NetworkVisualizeFrame;
import org.encog.workbench.models.EncogListModel;
import org.encog.workbench.training.RunAnneal;
import org.encog.workbench.training.RunGenetic;
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

	public static final String OBJECTS_CREATE = "Create Object...";
	public static final String OBJECTS_DELETE = "Delete Object...";

	public static final String TRAIN_BACKPROPAGATION = "Train Backpropagation...";
	public static final String TRAIN_SIMULATED_ANNEALING = "Train Simulated Annealing...";
	public static final String TRAIN_GENETIC = "Train Genetically...";

	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenu menuObjects;
	private JMenu menuTrain;
	private JToolBar toolBar;
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

	private List<JFrame> subwindows = new ArrayList<JFrame>();

	private EncogListModel encogListModel;
	public static final ExtensionFilter ENCOG_FILTER = new ExtensionFilter(
			"Encog Files", ".eg");
	public static final ExtensionFilter CSV_FILTER = new ExtensionFilter(
			"CSV Files", ".csv");
	public static final String WINDOW_TITLE = "Encog Workbench 1.0";

	public EncogDocumentFrame() {
		this.addWindowListener(this);
		EncogWorkBench.getInstance().setCurrentFile(
				new EncogPersistedCollection());
		this.encogListModel = new EncogListModel(EncogWorkBench.getInstance()
				.getCurrentFile());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4161616483326975155L;

	public void windowClosed(WindowEvent e) {
		System.exit(0);

	}

	public void windowOpened(WindowEvent e) {

		this.setSize(640, 480);

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

		this.menuObjects = new JMenu("Objects");
		this.menuObjects.add(addItem(this.menuObjects,
				EncogDocumentFrame.OBJECTS_CREATE, 'c'));
		this.menuObjects.add(addItem(this.menuObjects,
				EncogDocumentFrame.OBJECTS_DELETE, 'd'));
		this.menuBar.add(this.menuObjects);

		this.menuTrain = new JMenu("Train");
		this.menuTrain.add(addItem(this.menuObjects,
				EncogDocumentFrame.TRAIN_BACKPROPAGATION, 'b'));
		this.menuTrain.add(addItem(this.menuObjects,
				EncogDocumentFrame.TRAIN_SIMULATED_ANNEALING, 'a'));
		this.menuTrain.add(addItem(this.menuObjects,
				EncogDocumentFrame.TRAIN_GENETIC, 'g'));
		this.menuBar.add(this.menuTrain);

		this.setJMenuBar(this.menuBar);

		// setup the contents list
		this.contents = new JList(this.encogListModel);
		this.contents.setCellRenderer(new EncogItemRenderer());
		this.contents.setFixedCellHeight(72);
		this.contents.addMouseListener(this);

		JScrollPane scrollPane = new JScrollPane(this.contents);

		this.getContentPane().add(scrollPane);
		redraw();

		// build network popup menu
		this.popupNetwork = new JPopupMenu();
		this.popupNetworkDelete = addItem(this.popupNetwork, "Delete", 'd');
		this.popupNetworkOpen = addItem(this.popupNetwork, "Open", 'o');
		this.popupNetworkProperties = addItem(this.popupNetwork, "Properties",
				'p');
		this.popupNetworkVisualize = addItem(this.popupNetwork, "Visualize", 'v');
		this.popupNetworkQuery = addItem(this.popupNetwork, "Query", 'q');

		this.popupData = new JPopupMenu();
		this.popupDataDelete = addItem(this.popupData, "Delete", 'd');
		this.popupDataOpen = addItem(this.popupData, "Open", 'o');
		this.popupDataProperties = addItem(this.popupData, "Properties", 'p');
		this.popupDataImport = addItem(this.popupData, "Import...", 'i');
		this.popupDataExport = addItem(this.popupData, "Export...", 'e');
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals(EncogDocumentFrame.FILE_OPEN))
			performFileOpen();
		else if (event.getActionCommand().equals(EncogDocumentFrame.FILE_SAVE))
			performFileSave();
		else if (event.getActionCommand().equals(
				EncogDocumentFrame.FILE_SAVE_AS))
			performFileSaveAs();
		else if (event.getActionCommand()
				.equals(EncogDocumentFrame.FILE_IMPORT))
			performImport(null);
		else if (event.getActionCommand().equals(EncogDocumentFrame.FILE_QUIT))
			System.exit(0);
		else if (event.getActionCommand().equals(
				EncogDocumentFrame.OBJECTS_CREATE))
			performObjectsCreate();
		else if (event.getActionCommand().equals(
				EncogDocumentFrame.OBJECTS_DELETE))
			performObjectsDelete();
		else if (event.getActionCommand().equals(EncogDocumentFrame.FILE_CLOSE))
			performFileClose();
		else if (event.getActionCommand().equals(EncogDocumentFrame.FILE_NEW))
			performFileClose();
		else if (event.getActionCommand().equals(
				EncogDocumentFrame.TRAIN_BACKPROPAGATION)) {
			InputBackpropagation dialog = new InputBackpropagation(new javax.swing.JFrame());
			dialog.show(true);

		} else if (event.getActionCommand().equals(
				EncogDocumentFrame.TRAIN_GENETIC)) {
			RunGenetic train = new RunGenetic();
			train.begin();
		} else if (event.getActionCommand().equals(
				EncogDocumentFrame.TRAIN_SIMULATED_ANNEALING)) {
			RunAnneal train = new RunAnneal();
			train.begin();
		}

		if (event.getSource() == this.popupNetworkDelete) {
			performObjectsDelete();
		}
			else if (event.getSource() == this.popupNetworkQuery) {
				performNetworkQuery();				
		} else if( event.getSource() == this.popupNetworkVisualize ) {
			performNetworkVisualize();
		}
			else if (event.getSource() == this.popupNetworkOpen) {
			openItem(this.contents.getSelectedValue());
		} else if (event.getSource() == this.popupNetworkProperties) {
			EditEncogObjectProperties dialog = new EditEncogObjectProperties(
					this, (EncogPersistedObject) this.contents
							.getSelectedValue());
			dialog.process();
		} else if (event.getSource() == this.popupDataDelete) {
			performObjectsDelete();
		} else if (event.getSource() == this.popupDataOpen) {
			openItem(this.contents.getSelectedValue());
		} else if (event.getSource() == this.popupDataProperties) {
			EditEncogObjectProperties dialog = new EditEncogObjectProperties(
					this, (EncogPersistedObject) this.contents
							.getSelectedValue());
			dialog.process();
		} else if (event.getSource() == this.popupDataImport) {
			performImport(this.contents.getSelectedValue());
		} else if (event.getSource() == this.popupDataExport) {
			performExport(this.contents.getSelectedValue());
		}
	}

	private void performFileOpen() {
		try {
			if (!checkSave()) {
				return;
			}

			JFileChooser fc = new JFileChooser();
			fc.addChoosableFileFilter(EncogDocumentFrame.ENCOG_FILTER);
			int result = fc.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {				
				EncogWorkBench.load(fc.getSelectedFile().getAbsolutePath());
			}
		} catch (EncogError e) {
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
		} catch (EncogError e) {
			JOptionPane.showMessageDialog(this, e.getMessage(),
					"Can't Open File", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void performFileSaveAs() {
		try {
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(EncogDocumentFrame.ENCOG_FILTER);
			int result = fc.showSaveDialog(this);

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
					int response = JOptionPane.showConfirmDialog(null,
							"Overwrite existing file?", "Confirm Overwrite",
							JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.CANCEL_OPTION)
						return;
				}

				EncogWorkBench.save(file.getAbsolutePath());
			}
		} catch (EncogError e) {
			JOptionPane.showMessageDialog(this, e.getMessage(),
					"Can't Save File", JOptionPane.ERROR_MESSAGE);
		}

	}

	public void performFileClose() {
		if (!checkSave())
			return;
		EncogWorkBench.getInstance().close();
	}

	public void performObjectsCreate() {

		SelectItem itemTraining, itemNetwork;
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(itemTraining = new SelectItem("Training Data"));
		list.add(itemNetwork = new SelectItem("Neural Network"));
		SelectDialog dialog = new SelectDialog(this, list);
		SelectItem result = dialog.process();

		if (result == itemNetwork) {
			BasicNetwork network = new BasicNetwork();
			network.addLayer(new FeedforwardLayer(2));
			network.addLayer(new FeedforwardLayer(3));
			network.addLayer(new FeedforwardLayer(1));
			network.reset();
			network.setName("network-" + (networkCount++));
			network.setDescription("A neural network");
			EncogWorkBench.getInstance().getCurrentFile().add(network);
			EncogWorkBench.getInstance().getMainWindow().redraw();
		} else if (result == itemTraining) {
			BasicNeuralDataSet trainingData = new BasicNeuralDataSet(
					NeuralConst.XOR_INPUT, NeuralConst.XOR_IDEAL);

			trainingData.setName("data-" + (trainingCount++));
			trainingData.setDescription("Training data");
			EncogWorkBench.getInstance().getCurrentFile().add(trainingData);
			EncogWorkBench.getInstance().getMainWindow().redraw();
		}
	}

	public void performObjectsDelete() {
		Object object = this.contents.getSelectedValue();
		if (object != null) {
			if (JOptionPane.showConfirmDialog(this,
					"Are you sure you want to delete this object?", "Warning",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				EncogWorkBench.getInstance().getCurrentFile().getList().remove(
						object);
				EncogWorkBench.getInstance().getMainWindow().redraw();
			}
		}
	}

	private boolean checkSave() {
		String currentFileName = EncogWorkBench.getInstance()
				.getCurrentFileName();

		if (currentFileName != null
				|| EncogWorkBench.getInstance().getCurrentFile().getList()
						.size() > 0) {
			String f = currentFileName != null ? currentFileName : "Untitled";
			int response = JOptionPane.showConfirmDialog(null, "Save " + f
					+ ", first?", "Save", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				performFileSave();
				return true;
			} else if (response == JOptionPane.NO_OPTION) {
				return true;
			} else
				return false;

		}

		return true;
	}

	public void redraw() {

		// set the title properly
		if (EncogWorkBench.getInstance().getCurrentFileName() == null) {
			this.setTitle(EncogDocumentFrame.WINDOW_TITLE + " : Untitled");
		} else {
			this.setTitle(EncogDocumentFrame.WINDOW_TITLE + " : "
					+ EncogWorkBench.getInstance().getCurrentFileName());
		}

		// redraw the list
		this.encogListModel.invalidate();
	}

	public void rightMouseClicked(MouseEvent e, Object item) {
		if (item instanceof Network) {
			this.popupNetwork.show(e.getComponent(), e.getX(), e.getY());
		} else if (item instanceof NeuralDataSet) {
			this.popupData.show(e.getComponent(), e.getX(), e.getY());
		}

	}

	protected void openItem(Object item) {
		if (item instanceof NeuralDataSet) {
			JFrame frame = findSubWindow((EncogPersistedObject) item);
			if (frame == null) {
				frame = new TrainingDataFrame((BasicNeuralDataSet) item);
				frame.setVisible(true);
				this.subwindows.add(frame);
			} else {
				frame.toFront();
			}
		} else if (item instanceof Network) {
			JFrame frame = findSubWindow((EncogPersistedObject) item);
			if (frame == null) {
				frame = new NetworkFrame((BasicNetwork) item);
				frame.setVisible(true);
				this.subwindows.add(frame);
			} else {
				frame.toFront();
			}
		}
	}

	public JFrame findSubWindow(EncogPersistedObject object) {
		for (JFrame frame : this.subwindows) {
			if (frame instanceof TrainingDataFrame) {
				TrainingDataFrame t = (TrainingDataFrame) frame;
				if (t.getData() == object)
					return frame;
			} else if (frame instanceof NetworkFrame) {
				NetworkFrame n = (NetworkFrame) frame;
				if (n.getData() == object)
					return frame;
			}
		}
		return null;
	}

	public void closeSubWindow(JFrame sub) {
		this.subwindows.remove(sub);
		redraw();
		sub.setVisible(false);
		sub.dispose();
	}

	private void performImport(Object obj) {

		JFileChooser fc = new JFileChooser();
		fc.addChoosableFileFilter(EncogDocumentFrame.CSV_FILTER);
		int result = fc.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			BasicNeuralDataSet set = (BasicNeuralDataSet) obj;
			boolean clear = false;
			boolean addit = false;

			if (obj != null) {
				int o = JOptionPane
						.showConfirmDialog(
								this,
								"Do you wish to delete information already in this result set?",
								"Delete", JOptionPane.YES_NO_CANCEL_OPTION);



				if (o == JOptionPane.CANCEL_OPTION)
					return;
				else if (o == JOptionPane.YES_OPTION) {
					clear = true;
				}
			}
			else
			{
				CreateDataSet dialog = new CreateDataSet(this);
				if( !dialog.process() )
				{
					return;
				}
				
				set = new BasicNeuralDataSet();
				NeuralData input = null;
				NeuralData ideal = null;
				input = new BasicNeuralData(dialog.getInputSize());
				if( dialog.getIdealSize()>0 )
				{
					ideal = new BasicNeuralData(dialog.getIdealSize());
				}
				set.add(input,ideal);
				set.setName(dialog.getName());
				set.setDescription(dialog.getDescription());
				clear = true;
				addit = true;
			}

			try {
				ImportExportUtility.importCSV(set, fc.getSelectedFile()
						.toString(), clear);
				if(addit)
				{
					EncogWorkBench.getInstance().getCurrentFile().add(set);
					EncogWorkBench.getInstance().getMainWindow().redraw();
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage(),
						"Can't Import File", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void performExport(Object obj) {
		if (obj instanceof BasicNeuralDataSet) {
			SelectItem itemCSV, itemXML;
			List<SelectItem> list = new ArrayList<SelectItem>();
			list.add(itemCSV = new SelectItem("Export CSV"));
			list.add(itemXML = new SelectItem("Export XML (EG format)"));
			SelectDialog dialog = new SelectDialog(this, list);
			SelectItem result = dialog.process();

			if (result == null)
				return;

			JFileChooser fc = new JFileChooser();

			if (result == itemCSV)
				fc.setFileFilter(EncogDocumentFrame.CSV_FILTER);
			else
				fc.setFileFilter(EncogDocumentFrame.ENCOG_FILTER);

			int r = fc.showSaveDialog(this);

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
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(),
						"Can't Export File", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void performNetworkQuery()
	{
		Object item = this.contents.getSelectedValue();
		
		JFrame frame = findSubWindow((EncogPersistedObject) item);
		if (frame == null) {
			frame = new NetworkQueryFrame((BasicNetwork) item);
			frame.setVisible(true);
			this.subwindows.add(frame);
		} else {
			frame.toFront();
		}
	}
	
	public void performNetworkVisualize()
	{
		Object item = this.contents.getSelectedValue();
		
		JFrame frame = findSubWindow((EncogPersistedObject) item);
		if (frame == null) {
			frame = new NetworkVisualizeFrame((BasicNetwork) item);
			frame.setVisible(true);
			this.subwindows.add(frame);
		} else {
			frame.toFront();
		}
	}
}
