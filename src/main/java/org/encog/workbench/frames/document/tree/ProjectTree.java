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
package org.encog.workbench.frames.document.tree;

import java.awt.BorderLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.encog.util.file.FileUtil;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.frames.document.EncogDocumentFrame;
import org.encog.workbench.util.MouseUtil;

public class ProjectTree extends JPanel implements MouseListener, KeyListener,
		DropTargetListener {
	private JTree tree;
	private final EncogCollectionModel collectionModel;
	private EncogDocumentFrame doc;
	private DropTarget dt;

	public ProjectTree(EncogDocumentFrame doc) {
		this.doc = doc;
		// setup the contents list
		this.collectionModel = new EncogCollectionModel();
		this.tree = new JTree(this.collectionModel);
		// this.tree.setRootVisible(false);
		this.tree.addMouseListener(this);
		this.tree.addKeyListener(this);
		this.tree.setCellRenderer(new ProjectTreeRenderer());

		final JScrollPane scrollPane = new JScrollPane(this.tree);
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);

		this.tree.updateUI();
		dt = new DropTarget(this, this);

	}

	public List<ProjectItem> getSelectedValue() {

		List<ProjectItem> result = new ArrayList<ProjectItem>();
		TreePath[] path = this.tree.getSelectionPaths();

		if (path == null || path.length == 0)
			return null;

		for (int i = 0; i < path.length; i++) {
			Object obj = path[i].getLastPathComponent();
			if (obj instanceof ProjectItem)
				result.add((ProjectItem) obj);
		}

		return result;
	}

	public void rightMouseClicked(final MouseEvent e, final Object item) {
		if( EncogWorkBench.getInstance().getMainWindow().getTabManager().notWithModalTabOpen() )
			return;
		
		this.doc.getPopupMenus().rightMouseClicked(e, item);
	}

	public void mouseClicked(MouseEvent e) {
		if( EncogWorkBench.getInstance().getMainWindow().getTabManager().notWithModalTabOpen() )
			return;
		
		TreePath path = this.tree.getSelectionPath();

		// see if something should be selected because of right-click
		if (MouseUtil.isRightClick(e)) {
			TreePath[] currentPaths = this.tree.getSelectionPaths();

			TreePath rightPath = this.tree.getClosestPathForLocation(e.getX(),
					e.getY());
			boolean included = false;

			if (currentPaths != null) {
				for (TreePath t : currentPaths) {
					if (t.equals(rightPath))
						included = true;
				}
			}
			if (!included)
				this.tree.setSelectionPath(path = rightPath);
		}

		if (path != null) {
			Object obj = path.getLastPathComponent();

			if (obj instanceof ProjectFile) {
				if (MouseUtil.isRightClick(e)) {
					rightMouseClicked(e, obj);
				} else if (e.getClickCount() == 2) {
					ProjectFile pf = (ProjectFile) obj;
					this.doc.openFile(pf);
				}
			} else if (obj instanceof ProjectParent && e.getClickCount() == 2) {
				File p = this.collectionModel.getPath().getParentFile();
				EncogWorkBench.getInstance().getMainWindow()
						.changeDirectory(p);
			} else if (obj instanceof ProjectDirectory
					&& e.getClickCount() == 2) {
				File p = new File(this.collectionModel.getPath(),
						obj.toString());
				EncogWorkBench.getInstance().getMainWindow()
						.changeDirectory(p);
			} else {
				if (MouseUtil.isRightClick(e)) {
					rightMouseClicked(e, obj);
				}
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void refresh() {
		this.collectionModel.invalidate();

	}

	public void refresh(File path) {
		if( path!=null ) {
			this.collectionModel.invalidate(path);
			EncogWorkBench.getInstance().getMainWindow().redraw();
		}
	}

	public File getPath() {
		return this.collectionModel.getPath();

	}

	public String[] listEGFiles() {
		return this.collectionModel.listEGFiles();
	}

	public ProjectFile findTreeFile(String filename) {
		return this.collectionModel.findTreeFile(filename);
	}

	public EncogCollectionModel getModel() {
		return this.collectionModel;

	}

	public void dragEnter(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void dragExit(DropTargetEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void dragOver(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void drop(DropTargetDropEvent dtde) {
		if( EncogWorkBench.getInstance().getMainWindow().getTabManager().notWithModalTabOpen() )
			return;
		
		try {
			if( EncogWorkBench.getInstance().getProjectDirectory()==null ) {
				EncogWorkBench.displayError("Error", "Open a project before using drag and drop.");
				return;
			}
			
			if( !EncogWorkBench.askQuestion("Drag and Drop", "Copy the file(s) to the project?") )
			{
				return;
			}
			
			Transferable tr = dtde.getTransferable();
			DataFlavor[] flavors = tr.getTransferDataFlavors();
			for (int i = 0; i < flavors.length; i++) {
				if (flavors[i].isFlavorJavaFileListType()) {
					dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
					java.util.List list = (java.util.List) tr
							.getTransferData(flavors[i]);
										
					for (int j = 0; j < list.size(); j++) {
						File sourceFile = new File(list.get(j).toString());
						
						if( sourceFile.isDirectory() ) {
							EncogWorkBench.displayError("Drag and Drop", "Can't drop folder.");
							return;
						}
						
						String name = sourceFile.getName();
						File targetFile = new File(EncogWorkBench.getInstance().getProjectDirectory(),name);
						FileUtil.copy(sourceFile, targetFile);
					}

					// If we made it this far, everything worked.
					dtde.dropComplete(true);
					EncogWorkBench.getInstance().refresh();
					return;
				}
			}
		} catch (Exception e) {			
			EncogWorkBench.displayError("Drag and Drop", e);			
		}
		
		dtde.rejectDrop();

	}

	public void dropActionChanged(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {
		if( EncogWorkBench.getInstance().getMainWindow().getTabManager().notWithModalTabOpen() )
			return;
		
		if( e.getKeyChar()==KeyEvent.VK_DELETE) {
			EncogWorkBench.getInstance().getMainWindow().getOperations().performDelete();
		}
		
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public ProjectFile findTreeFile(File egaFile) {
		return this.collectionModel.findTreeFile(egaFile.getName());
	}

	public ProjectFile findFirstEGA() {
		return this.collectionModel.findFirstEGA();
	}

}
