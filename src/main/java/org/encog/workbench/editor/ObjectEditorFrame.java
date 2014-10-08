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
package org.encog.workbench.editor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.encog.workbench.WorkBenchError;
import org.encog.workbench.frames.EncogCommonFrame;
import org.encog.workbench.util.MouseUtil;

public class ObjectEditorFrame extends EncogCommonFrame implements
		TreeSelectionListener {

	private Object data;
	private JTable table;
	private JTree tree;
	private JScrollPane scrollTree;
	private JScrollPane scrollTable;
	private DefaultTreeModel treeModel;
	private DefaultMutableTreeNode root;
	private PropertyCollection properties;

	private JPopupMenu popup;
	private JMenuItem popupAdd;
	private JMenuItem popupDelete;

	public ObjectEditorFrame(Object data) {
		setSize(400, 400);
		setTitle("Editing " + data.getClass().getSimpleName());
		this.data = data;
		this.properties = new PropertyCollection(data);
		Container content = this.getContentPane();
		content.setLayout(new BorderLayout());

		this.root = new DefaultMutableTreeNode(data);
		this.treeModel = new DefaultTreeModel(this.root);
		this.tree = new JTree(this.treeModel);
		this.scrollTable = new JScrollPane();
		this.scrollTree = new JScrollPane(this.tree);
		
		if( this.containsCollections(data) )
		{
			content.add(this.scrollTree, BorderLayout.WEST);
			content.add(this.scrollTable, BorderLayout.EAST);
		}
		else
		{
			content.add(this.scrollTable,BorderLayout.CENTER);
		}

		this.tree.addTreeSelectionListener(this);
		this.tree.addMouseListener(this);
		this.removeMouseListener(this);

		this.popup = new JPopupMenu();
		this.popupAdd = this.addItem(this.popup, "Add", 'a');
		this.popupDelete = this.addItem(this.popup, "Delete", 'd');

		resetTable();
		generateTree();
	}

	private boolean containsCollections(Object obj) {
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);
				Object fieldObj = field.get(obj);
				if( fieldObj instanceof Collection )
					return true;
			}
		} catch (IllegalArgumentException e) {
			throw new WorkBenchError(e);
		} catch (IllegalAccessException e) {
			throw new WorkBenchError(e);
		} finally {
		}
		return false;
	}

	private void resetTable() {
		this.table = new JTable(this.properties.getModel());
		this.table.getColumnModel().getColumn(1).setCellEditor(
				new EditorCellEditor());
		this.scrollTable.setViewportView(this.table);

	}

	public void windowOpened(WindowEvent e) {

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.popupAdd) {
			performAdd();
		} else if (e.getSource() == this.popupDelete) {
			performDelete();
		}

	}

	public void performDelete() {
		try {
			TreePath path = this.tree.getSelectionPath();

			DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
					.getLastPathComponent();

			Collection collection = null;
			Object object = null;

			if (node.getUserObject() instanceof String) {
				DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node
						.getParent();
				String fieldName = (String) node.getUserObject();
				Field field = parent.getUserObject().getClass()
						.getDeclaredField(fieldName);
				field.setAccessible(true);
				collection = (Collection) field.get(parent.getUserObject());
				object = null;
			} else {
				DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node
						.getParent();
				DefaultMutableTreeNode grandParent = (DefaultMutableTreeNode) parent
						.getParent();
				String fieldName = (String) parent.getUserObject();
				Field field = grandParent.getUserObject().getClass()
						.getDeclaredField(fieldName);
				field.setAccessible(true);
				collection = (Collection) field
						.get(grandParent.getUserObject());
				object = node.getUserObject();
			}

			if (object == null)
				collection.clear();
			else
				collection.remove(object);

			generateTree();
		} catch (SecurityException e) {
			throw new WorkBenchError(e);
		} catch (IllegalArgumentException e) {
			throw new WorkBenchError(e);
		} catch (NoSuchFieldException e) {
			throw new WorkBenchError(e);
		} catch (IllegalAccessException e) {
			throw new WorkBenchError(e);
		} finally {
		}
	}

	private void performAdd() {
		try {
			TreePath path = this.tree.getSelectionPath();
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
					.getLastPathComponent();

			if (!(node.getUserObject() instanceof String)) {
				node = (DefaultMutableTreeNode) node.getParent();
			}

			DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node
					.getParent();
			Object obj = parent.getUserObject();
			String fieldName = node.getUserObject().toString();
			Field field = obj.getClass().getDeclaredField(fieldName);

			field.setAccessible(true);
			Object fieldObject = field.get(obj);

			if (fieldObject instanceof Collection) {
				ParameterizedType gtype = (ParameterizedType) field
						.getGenericType();
				java.lang.reflect.Type[] types = gtype.getActualTypeArguments();

				Object item = ((Class) types[0]).newInstance();
				((Collection) fieldObject).add(item);
				generateTree();
			}
		} catch (SecurityException e) {
			throw new WorkBenchError(e);
		} catch (NoSuchFieldException e) {
			throw new WorkBenchError(e);
		} catch (IllegalAccessException e) {
			throw new WorkBenchError(e);
		} catch (InstantiationException e) {
			throw new WorkBenchError(e);
		}
	}

	protected void generateTree() {
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
					generateTreeCollection(n, (Collection) value);
				}
			}

			treeModel.reload();
			tree.invalidate();
			TreePath path = new TreePath(this.root);			
			tree.setSelectionPath(path);
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

			// clear the selection pointer
			resetTable();

			// if its a String, then it is just a label
			if (obj instanceof String)
				this.properties.setData(null);
			else
				this.properties.setData(obj);

		}
	}
	
	public void setData(Object data) {
		this.data = data;
		
	}

	public void mouseClicked(final MouseEvent e) {

		if (MouseUtil.isRightClick(e)) {
			TreePath path = this.tree.getPathForLocation(e.getX(), e.getY());
			Object obj = path.getLastPathComponent();

			TreeNode node = (DefaultMutableTreeNode) obj;
			TreeNode parent = node.getParent();

			if (parent != null) {
				this.tree.setSelectionPath(path);
				this.popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}
}
