package org.encog.workbench.frames.document;

import java.awt.event.ActionEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.dialogs.EditEncogObjectProperties;
import org.encog.workbench.process.Training;

public class EncogMenus {
	public static final String FILE_NEW = "New";
	public static final String FILE_CLOSE = "Close";
	public static final String FILE_OPEN = "Open...";
	public static final String FILE_SAVE = "Save";
	public static final String FILE_SAVE_AS = "Save As...";
	public static final String FILE_REVERT = "Revert";
	public static final String FILE_QUIT = "Quit...";
	public static final String FILE_IMPORT = "Import CSV...";

	public static final String EDIT_CUT = "Cut";
	public static final String EDIT_COPY = "Copy";
	public static final String EDIT_PASTE = "Paste";
	public static final String EDIT_CONFIG = "Config...";

	public static final String OBJECTS_CREATE = "Create Object...";
	public static final String OBJECTS_DELETE = "Delete Object...";

	public static final String TRAIN_RESILIENT_PROPAGATION = "Train Resilient Propagation...";
	public static final String TRAIN_BACKPROPAGATION = "Train Back Propagation...";
	public static final String TRAIN_MANHATTAN_PROPAGATION = "Train Manhattan Update Rule Prop...";
	public static final String TRAIN_SIMULATED_ANNEALING = "Train Simulated Annealing...";
	public static final String TRAIN_GENETIC = "Train Genetically...";
	public static final String TRAIN_HOPFIELD = "Train Hopfield Layers...";
	public static final String TRAIN_SOM = "Train SOM Layers...";

	public static final String TOOLS_CODE = "Generate Code...";
	public static final String TOOLS_CHAT = "Chat with Encog NLP...";
	public static final String TOOLS_BROWSE = "Browse Web Data...";
	
	public static final String HELP_ABOUT = "About Encog Workbench...";
	
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenu menuEdit;
	private JMenu menuObjects;
	private JMenu menuTrain;
	private JMenu menuHelp;
	private JMenu menuTools;
	
	private EncogDocumentFrame owner;
	
	public EncogMenus(EncogDocumentFrame owner) {
		this.owner = owner;
	}
	
	void initMenuBar() {
		// menu bar
		this.menuBar = new JMenuBar();
		this.menuFile = new JMenu("File");
		this.menuFile.add(owner.addItem(this.menuFile, EncogMenus.FILE_NEW,
				'n'));
		this.menuFile.add(owner.addItem(this.menuFile, EncogMenus.FILE_OPEN,
				'o'));
		this.menuFile.addSeparator();
		this.menuFile.add(owner.addItem(this.menuFile, EncogMenus.FILE_CLOSE,
				'c'));
		this.menuFile.addSeparator();
		this.menuFile.add(owner.addItem(this.menuFile,
				EncogMenus.FILE_SAVE, 's'));
		this.menuFile.add(owner.addItem(this.menuFile,
				EncogMenus.FILE_SAVE_AS, 'a'));
		this.menuFile.add(owner.addItem(this.menuFile,
				EncogMenus.FILE_REVERT, 'r'));
		this.menuFile.addSeparator();
		this.menuFile.add(owner.addItem(this.menuFile,
				EncogMenus.FILE_IMPORT, 'i'));
		this.menuFile.addSeparator();
		this.menuFile.add(owner.addItem(this.menuFile, EncogMenus.FILE_QUIT,
				'q'));
		this.menuFile.addActionListener(this.owner);
		this.menuBar.add(this.menuFile);

		this.menuEdit = new JMenu("Edit");
		this.menuEdit.add(owner.addItem(this.menuEdit, EncogMenus.EDIT_CUT,
				'x'));
		this.menuEdit.add(owner.addItem(this.menuEdit, EncogMenus.EDIT_COPY,
				'c'));
		this.menuEdit.add(owner.addItem(this.menuEdit, EncogMenus.EDIT_PASTE,
				'v'));
		this.menuEdit.addSeparator();
		this.menuEdit.add(owner.addItem(this.menuEdit, EncogMenus.EDIT_CONFIG,
		'f'));
		this.menuBar.add(this.menuEdit);
		
		this.menuObjects = new JMenu("Objects");
		this.menuObjects.add(owner.addItem(this.menuObjects,
				EncogMenus.OBJECTS_CREATE, 'c'));
		this.menuObjects.add(owner.addItem(this.menuObjects,
				EncogMenus.OBJECTS_DELETE, 'd'));
		this.menuBar.add(this.menuObjects);

		this.menuTrain = new JMenu("Train");
		
		this.menuTrain.add(owner.addItem(this.menuObjects,
				EncogMenus.TRAIN_RESILIENT_PROPAGATION, 'r'));
		this.menuTrain.add(owner.addItem(this.menuObjects,
				EncogMenus.TRAIN_MANHATTAN_PROPAGATION, 'm'));
		owner.addItem(this.menuTrain, EncogMenus.TRAIN_BACKPROPAGATION, 'b');
		this.menuTrain.addSeparator();
		this.menuTrain.add(owner.addItem(this.menuObjects,
				EncogMenus.TRAIN_SIMULATED_ANNEALING, 'a'));
		this.menuTrain.add(owner.addItem(this.menuObjects,
				EncogMenus.TRAIN_GENETIC, 'g'));
		this.menuTrain.addSeparator();
		this.menuTrain.add(owner.addItem(this.menuObjects,
				EncogMenus.TRAIN_HOPFIELD, 'h'));
		this.menuTrain.add(owner.addItem(this.menuObjects,
				EncogMenus.TRAIN_SOM, 's'));

		this.menuBar.add(this.menuTrain);

		this.menuTools = new JMenu("Tools");
		owner.addItem(this.menuTools, EncogMenus.TOOLS_CODE, 'g');
		owner.addItem(this.menuTools, EncogMenus.TOOLS_CHAT, 'c');
		owner.addItem(this.menuTools, EncogMenus.TOOLS_BROWSE, 'b');
		this.menuBar.add(this.menuTools);
		
		this.menuHelp = new JMenu("Help");
		this.menuHelp.add(owner.addItem(this.menuHelp, EncogMenus.HELP_ABOUT, 'a'));
		this.menuBar.add(this.menuHelp);

		owner.setJMenuBar(this.menuBar);
	}

	public void actionPerformed(final ActionEvent event) {
		if (event.getActionCommand().equals(EncogMenus.FILE_OPEN)) {
			owner.getOperations().performFileOpen();
		} 
		else if (event.getActionCommand().equals(
				EncogMenus.FILE_SAVE)) {
			owner.getOperations().performFileSave();
		}
		else if (event.getActionCommand().equals(
				EncogMenus.FILE_SAVE_AS)) {
			owner.getOperations().performFileSaveAs();
		} else if (event.getActionCommand().equals(
				EncogMenus.FILE_REVERT)) {
			owner.getOperations().performFileRevert();
		} 
		else if (event.getActionCommand().equals(
				EncogMenus.FILE_IMPORT)) {
			owner.getOperations().performImport(null);
		} else if (event.getActionCommand()
				.equals(EncogMenus.FILE_QUIT)) {
			System.exit(0);
		} else if (event.getActionCommand().equals(
				EncogMenus.OBJECTS_CREATE)) {
			owner.getOperations().performObjectsCreate();
		} else if (event.getActionCommand().equals(
				EncogMenus.OBJECTS_DELETE)) {
			owner.getOperations().performObjectsDelete();
		} else if (event.getActionCommand().equals(
				EncogMenus.FILE_CLOSE)) {
			owner.getOperations().performFileClose();
		} else if (event.getActionCommand().equals(EncogMenus.FILE_NEW)) {
			owner.getOperations().performFileClose();
		} else if (event.getActionCommand().equals(
				EncogMenus.TRAIN_BACKPROPAGATION)) {
			Training.performBackpropagation();

		} else if (event.getActionCommand().equals(
				EncogMenus.TRAIN_RESILIENT_PROPAGATION)) {
			Training.performResilient();
		} else if (event.getActionCommand().equals(
				EncogMenus.TRAIN_MANHATTAN_PROPAGATION)) {
			Training.performManhattan();
		}

		else if (event.getActionCommand().equals(
				EncogMenus.TRAIN_GENETIC)) {
			Training.performGenetic();

		} else if (event.getActionCommand().equals(
				EncogMenus.TRAIN_HOPFIELD)) {
			Training.performHopfield();

		} else if (event.getActionCommand()
				.equals(EncogMenus.TRAIN_SOM)) {
			Training.performSOM();

		} else if (event.getActionCommand().equals(
				EncogMenus.TRAIN_SIMULATED_ANNEALING)) {
			Training.performAnneal();
		}

		if (event.getActionCommand().equals(EncogMenus.EDIT_CUT)) {
			owner.getOperations().performEditCut();
		} else if (event.getActionCommand()
				.equals(EncogMenus.EDIT_COPY)) {
			owner.getOperations().performEditCopy();
		} else if (event.getActionCommand().equals(
				EncogMenus.EDIT_PASTE)) {
			owner.getOperations().performEditPaste();
		} else if (event.getActionCommand().equals(
				EncogMenus.EDIT_CONFIG)) {
			owner.getOperations().performEditConfig();
		} else if (event.getActionCommand().equals(
				EncogMenus.HELP_ABOUT)) {
			owner.getOperations().performHelpAbout();
		} else if (event.getActionCommand().equals(
				EncogMenus.TOOLS_CHAT)) {
			owner.getOperations().performChat();
		} else if (event.getActionCommand().equals(
				EncogMenus.TOOLS_BROWSE)) {
			owner.getOperations().performBrowse();
		} 
	}
}
