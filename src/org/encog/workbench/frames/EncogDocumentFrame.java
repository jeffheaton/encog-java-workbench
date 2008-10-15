package org.encog.workbench.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import org.encog.EncogError;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.FeedforwardLayer;
import org.encog.neural.persist.EncogPersistedCollection;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.CreateObject;
import org.encog.workbench.dialogs.UserInput;
import org.encog.workbench.models.EncogListModel;
import org.encog.workbench.training.RunAnneal;
import org.encog.workbench.training.RunGenetic;
import org.encog.workbench.util.ExtensionFilter;
import org.encog.workbench.util.NeuralConst;

public class EncogDocumentFrame extends JFrame implements WindowListener,
		ActionListener {

	public static final String FILE_NEW = "New";
	public static final String FILE_CLOSE = "Close";
	public static final String FILE_OPEN = "Open...";
	public static final String FILE_SAVE = "Save";
	public static final String FILE_SAVE_AS = "Save As...";
	public static final String FILE_QUIT = "Quit...";

	public static final String OBJECTS_CREATE = "Create Object...";
	public static final String OBJECTS_DELETE = "Delete Object...";
	
	public static final String TRAIN_BACKPROPAGATION = "Train Backpropagation...";
	public static final String TRAIN_SIMULATED_ANNEALING = "Train Simulated Annealing...";
	public static final String TRAIN_GENETIC = "Train Genetically...";

	

	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenu menuObjects;
	private JMenu menuTrain;
	private JList contents;
	private JToolBar toolBar;
	private int trainingCount = 1;
	private int networkCount = 1;

	private EncogListModel encogListModel;
	public static final ExtensionFilter ENCOG_FILTER = new ExtensionFilter(
			"Encog Files", ".eg");
	public static final String WINDOW_TITLE = "Encog Workbench 1.0";

	public EncogDocumentFrame() {
		this.addWindowListener(this);
		EncogWorkBench.getInstance().setCurrentFile(new EncogPersistedCollection());
		this.encogListModel = new EncogListModel(EncogWorkBench.getInstance().getCurrentFile());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4161616483326975155L;

	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowClosed(WindowEvent e) {
		System.exit(0);

	}

	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

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

		JScrollPane scrollPane = new JScrollPane(this.contents);

		this.getContentPane().add(scrollPane);
		redraw();
	}

	protected JMenuItem addItem(JMenu m, String s, int key) {

		JMenuItem mi = new JMenuItem(s, key);
		mi.addActionListener(this);
		m.add(mi);
		return mi;
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals(EncogDocumentFrame.FILE_OPEN))
			performFileOpen();
		else if (event.getActionCommand().equals(EncogDocumentFrame.FILE_SAVE))
			performFileSave();
		else if (event.getActionCommand().equals(
				EncogDocumentFrame.FILE_SAVE_AS))
			performFileSaveAs();
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
		else if (event.getActionCommand().equals(EncogDocumentFrame.TRAIN_BACKPROPAGATION))
		{
			UserInput dialog = new UserInput(new javax.swing.JFrame());
			dialog.show(true);
			
		}
		else if (event.getActionCommand().equals(EncogDocumentFrame.TRAIN_GENETIC))
		{
			RunGenetic train = new RunGenetic();
			train.begin();
		}
		else if (event.getActionCommand().equals(EncogDocumentFrame.TRAIN_SIMULATED_ANNEALING))
		{
			RunAnneal train = new RunAnneal();
			train.begin();
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
				EncogWorkBench.getInstance().getCurrentFile().save(EncogWorkBench.getInstance().getCurrentFileName());
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
		CreateObject co = new CreateObject(this);
		co.pack();
		co.setVisible(true);
		switch (co.getSelected()) {
		case NEURAL_NETWORK:
			BasicNetwork network = new BasicNetwork();
			network.addLayer(new FeedforwardLayer(2));
			network.addLayer(new FeedforwardLayer(3));
			network.addLayer(new FeedforwardLayer(1));
			network.reset();
			network.setName("network-" + (networkCount++));
			network.setDescription("A neural network");
			EncogWorkBench.getInstance().getCurrentFile().add(network);
			EncogWorkBench.getInstance().getMainWindow().redraw();
			break;

		case TRAINING_DATA:
			BasicNeuralDataSet trainingData = new BasicNeuralDataSet(
					NeuralConst.XOR_INPUT, NeuralConst.XOR_IDEAL);

			trainingData.setName("data-" + (trainingCount++));
			trainingData.setDescription("Training data");
			EncogWorkBench.getInstance().getCurrentFile().add(trainingData);
			EncogWorkBench.getInstance().getMainWindow().redraw();
			break;
		}
	}

	public void performObjectsDelete() {
		Object object = this.contents.getSelectedValue();
		if (object != null) {
			if (JOptionPane.showConfirmDialog(this,
					"Are you sure you want to delete this object?", "Warning",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				EncogWorkBench.getInstance().getCurrentFile().getList().remove(object);
				EncogWorkBench.getInstance().getMainWindow().redraw();
			}
		}
	}

	private boolean checkSave() {
		String currentFileName = EncogWorkBench.getInstance().getCurrentFileName();
		
		if (currentFileName != null
				|| EncogWorkBench.getInstance().getCurrentFile().getList().size() > 0) {
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
	
}
