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

public class ProjectTree extends JPanel implements MouseListener,
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
		this.doc.getPopupMenus().rightMouseClicked(e, item);
	}

	public void mouseClicked(MouseEvent e) {
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
		this.collectionModel.invalidate(path);
		EncogWorkBench.getInstance().getMainWindow().redraw();
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
						File sourceFile = new File(list.get(i).toString());
						
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

}
