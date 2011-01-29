package org.encog.workbench.frames.document.tree;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogMemoryCollection;
import org.encog.workbench.frames.document.EncogDocumentFrame;
import org.encog.workbench.tabs.GenericFileTab;
import org.encog.workbench.util.MouseUtil;

public class ProjectTree extends JPanel implements MouseListener {
	private JTree tree;
	private final EncogCollectionModel collectionModel;
	private EncogDocumentFrame doc;
	private String path = "d:\\";
	
	public ProjectTree(EncogDocumentFrame doc)	
	{
		this.doc = doc;
		// setup the contents list
		this.collectionModel = new EncogCollectionModel("c:\\");
		this.tree = new JTree(this.collectionModel);
		//this.tree.setRootVisible(false);
		this.tree.addMouseListener(this);

		final JScrollPane scrollPane = new JScrollPane(this.tree);
		this.setLayout(new BorderLayout());
		this.add(scrollPane,BorderLayout.CENTER);
		
		this.tree.updateUI();
		

	}
	
	public List<DirectoryEntry> getSelectedValue() {
		
		List<DirectoryEntry> result = new ArrayList<DirectoryEntry>();
		TreePath[] path = this.tree.getSelectionPaths();
		
		if( path==null || path.length==0 )
			return null;
				
		for(int i=0;i<path.length;i++)
		{
			Object obj = path[i].getLastPathComponent();
			//if( obj instanceof EncogCollectionEntry )
			//	result.add(((EncogCollectionEntry) obj).getEntry());
		}

		return result;
	}
	
	public void rightMouseClicked(final MouseEvent e, final Object item) {
		this.doc.getPopupMenus().rightMouseClicked(e, item);
	}

	public void mouseClicked(MouseEvent e) {
		TreePath path = this.tree.getSelectionPath();

		if (MouseUtil.isRightClick(e)) {
			path = this.tree.getClosestPathForLocation(e.getX(), e.getY());
			this.tree.setSelectionPath(path);
		}
		

		if (path != null) {
			Object obj = path.getLastPathComponent();

			if (obj instanceof ProjectDirectory) {
				ProjectDirectory dir = ((ProjectDirectory) obj);

				if (MouseUtil.isRightClick(e)) {
					//rightMouseClicked(e, item);
				}

				if (e.getClickCount() == 2) {

					this.collectionModel.invalidate(dir.getFile().toString());
				}
				
				/*DirectoryEntry item = ((EncogCollectionEntry) obj).getEntry();

				if (MouseUtil.isRightClick(e)) {
					rightMouseClicked(e, item);
				}

				if (e.getClickCount() == 2) {

					this.doc.openItem(item);
				}*/
			} else if( obj instanceof ProjectFile ) {
				if (MouseUtil.isRightClick(e)) {
					rightMouseClicked(e, obj);
				} else if (e.getClickCount() == 2) {
					ProjectFile pf = (ProjectFile)obj;
					GenericFileTab tab = new GenericFileTab(pf.getFile());
					this.doc.openFile(pf.getFile());
				}
			}
		}
	}
	
	public void invalidate(EncogMemoryCollection currentFile) {
		this.collectionModel.invalidate("c:\\");
		
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

}
