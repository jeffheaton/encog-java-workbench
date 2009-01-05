package org.encog.workbench.editor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.util.Collection;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.encog.neural.persist.EncogPersistedCollection;
import org.encog.parse.Parse;
import org.encog.parse.ParseTemplate;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.WorkBenchError;
import org.encog.workbench.frames.manager.EncogCommonFrame;
import org.encog.workbench.util.MouseUtil;
import org.encog.parse.recognize.Recognize;
import org.encog.parse.recognize.RecognizeElement;

public class ParseTemplateFrame extends EncogCommonFrame implements
		TreeSelectionListener {

	private Object data;
	private JTable table;
	private JTree tree;
	private JScrollPane scrollTree;
	private JScrollPane scrollTable;
	private DefaultTreeModel treeModel;
	private DefaultMutableTreeNode root;
	private JPanel buttons;
	private JButton btnProperties;
	private JButton btnNewRecognizer;
	private JButton btnLoadDefault;
	private PropertyCollection properties;

	public ParseTemplateFrame(Object data) {
		setSize(400, 400);
		setTitle("Parser Template");
		this.data = data;
		this.properties = new PropertyCollection(data, this.table);
		Container content = this.getContentPane();
		content.setLayout(new BorderLayout());
		this.table = new JTable(this.properties.getModel());
		this.root = new DefaultMutableTreeNode(data);
		this.treeModel = new DefaultTreeModel(this.root);
		this.tree = new JTree(this.treeModel);
		this.scrollTable = new JScrollPane(this.table);
		this.scrollTree = new JScrollPane(this.tree);
		this.buttons = new JPanel();
		content.add(this.buttons, BorderLayout.NORTH);
		content.add(this.scrollTree, BorderLayout.WEST);
		content.add(this.scrollTable, BorderLayout.EAST);
		this.btnProperties = new JButton("Properties");
		this.btnNewRecognizer = new JButton("New Recognizer");
		this.btnLoadDefault = new JButton("Reset to Default Template");
		this.buttons.add(this.btnProperties);
		this.buttons.add(this.btnNewRecognizer);
		this.buttons.add(this.btnLoadDefault);
		this.buttons.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.btnProperties.addActionListener(this);
		this.btnNewRecognizer.addActionListener(this);
		this.btnLoadDefault.addActionListener(this);
		this.tree.addTreeSelectionListener(this);

		this.table.setDefaultEditor(Boolean.class, new DefaultCellEditor(
				new JComboBox(PropertyCollection.booleanValues)));

		generateTree();
	}

	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnNewRecognizer) {
			Recognize r = new Recognize("New");
			//this.data.addRecognizer(r);
			generateTree();
		} else if (e.getSource() == this.btnLoadDefault) {
			if (EncogWorkBench
					.askQuestion(
							"Are you sure?",
							"Would you like to reset this parse template to the\ndefault Encog template? This will discard all changes.")) {
				EncogPersistedCollection encog = new EncogPersistedCollection();
				encog.loadResource(Parse.RESOURCE_NAME);
				this.data = (ParseTemplate) encog
						.find(Parse.RESOURCE_ITEM_NAME);
				generateTree();
			}
		}

	}

	private void generateTree() {
		try {
			this.root.removeAllChildren();

			Field fields[] = this.data.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);
				Class c = field.getClass();
				Object value = field.get(this.data);
				if (value instanceof Collection) {
					DefaultMutableTreeNode n = new DefaultMutableTreeNode(field
							.getName());
					this.root.add(n);
					generateTreeCollection(n, (Collection)value);
				}
			}

			treeModel.reload();
			tree.invalidate();
			pack();
		} catch (IllegalAccessException e) {
			throw new WorkBenchError(e);
		}
	}

	private void generateTreeCollection(DefaultMutableTreeNode parentNode,
			Collection parentObject) {
		for (Object obj : parentObject) {

			DefaultMutableTreeNode n = new DefaultMutableTreeNode(obj);
			parentNode.add(n);
		}
	}

	public void valueChanged(TreeSelectionEvent e) {
		TreePath path = this.tree.getSelectionPath();
		if (path != null) {
			DefaultMutableTreeNode n = (DefaultMutableTreeNode) path
					.getLastPathComponent();
			Object obj = n.getUserObject();
			
			// if its a String, then it is just a label
			if( obj instanceof String )
				this.properties.setData(null);
			else
				this.properties.setData(obj);
		}
	}
	
	public void mouseClicked(final MouseEvent e) {
/*
		final int index = this.contents.locationToIndex(e.getPoint());
		final ListModel dlm = this.contents.getModel();
		Object item = null;
		if (index != -1) {
			item = dlm.getElementAt(index);
		}
		this.contents.ensureIndexIsVisible(index);
		this.contents.setSelectedIndex(index);

		if (MouseUtil.isRightClick(e)) {
			rightMouseClicked(e, item);
		}

		if (e.getClickCount() == 2) {

			openItem(item);
		}*/
	}

}
