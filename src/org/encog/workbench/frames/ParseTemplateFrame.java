package org.encog.workbench.frames;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.encog.neural.persist.EncogPersistedCollection;
import org.encog.parse.Parse;
import org.encog.parse.ParseTemplate;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.frames.manager.EncogCommonFrame;
import org.encog.workbench.models.ParseTableModel;
import org.encog.parse.recognize.Recognize;
import org.encog.parse.recognize.RecognizeElement;

public class ParseTemplateFrame extends EncogCommonFrame implements
		TreeSelectionListener {

	private ParseTemplate data;
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
	private ParseTableModel tableModel;

	public ParseTemplateFrame(ParseTemplate data) {
		setSize(400, 400);
		setTitle("Parser Template");
		this.data = data;
		Container content = this.getContentPane();
		content.setLayout(new BorderLayout());
		this.tableModel = new ParseTableModel();
		this.table = new JTable(this.tableModel);
		this.root = new DefaultMutableTreeNode("Template");
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
		generateTree();
	}

	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnNewRecognizer) {
			Recognize r = new Recognize("New");
			this.data.addRecognizer(r);
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

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	private void generateTree() {
		this.root.removeAllChildren();
		for (Recognize r : this.data.getRecognizers()) {
			DefaultMutableTreeNode n = new DefaultMutableTreeNode(r);
			this.root.add(n);
			generateRecognizerNode(r, n);
		}

		treeModel.reload();
		tree.invalidate();
		pack();
	}

	private void generateRecognizerNode(Recognize r,
			DefaultMutableTreeNode parent) {
		for (RecognizeElement d : r.getPattern()) {
			DefaultMutableTreeNode n = new DefaultMutableTreeNode(d);
			parent.add(n);
		}
	}

	public void valueChanged(TreeSelectionEvent e) {
		TreePath path = this.tree.getSelectionPath();
		if (path != null) {
			DefaultMutableTreeNode n = (DefaultMutableTreeNode) path
					.getLastPathComponent();
			this.tableModel.setData(n.getUserObject());
		}
	}

}
