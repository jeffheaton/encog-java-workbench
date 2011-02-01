package org.encog.workbench.frames.document.tree;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.frames.document.EncogDocumentFrame;
import org.encog.workbench.util.MouseUtil;

public class ProjectTree extends JPanel implements MouseListener {
	private JTree tree;
	private final EncogCollectionModel collectionModel;
	private EncogDocumentFrame doc;
	
	public ProjectTree(EncogDocumentFrame doc)	
	{
		this.doc = doc;
		// setup the contents list
		this.collectionModel = new EncogCollectionModel();
		this.tree = new JTree(this.collectionModel);
		//this.tree.setRootVisible(false);
		this.tree.addMouseListener(this);

		final JScrollPane scrollPane = new JScrollPane(this.tree);
		this.setLayout(new BorderLayout());
		this.add(scrollPane,BorderLayout.CENTER);
		
		this.tree.updateUI();
		

	}
	
	public List<ProjectItem> getSelectedValue() {
		
		List<ProjectItem> result = new ArrayList<ProjectItem>();
		TreePath[] path = this.tree.getSelectionPaths();
		
		if( path==null || path.length==0 )
			return null;
				
		for(int i=0;i<path.length;i++)
		{
			Object obj = path[i].getLastPathComponent();
			if( obj instanceof ProjectItem )
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
			if( currentPaths==null )
				return;
			TreePath rightPath = this.tree.getClosestPathForLocation(e.getX(), e.getY());
			boolean included = false;
			for(TreePath t: currentPaths) {
				if( t.equals(rightPath) )
					included = true;
			}
			if(!included)
				this.tree.setSelectionPath(path = rightPath);
		}
		

		if (path != null) {
			Object obj = path.getLastPathComponent();
			
			if( obj instanceof ProjectFile ) {
				if (MouseUtil.isRightClick(e)) {
					rightMouseClicked(e, obj);
				} else if (e.getClickCount() == 2) {
					ProjectFile pf = (ProjectFile)obj; 
					this.doc.openFile(pf.getFile());
				}
			} else if( obj instanceof ProjectEGItem ) {
				if (e.getClickCount() == 2) {
					ProjectEGItem egItem = (ProjectEGItem)obj;
					EncogWorkBench.getInstance().getMainWindow().open(egItem.getObj());
				} else if (MouseUtil.isRightClick(e)) {
					EncogWorkBench.getInstance().getMainWindow().getPopupMenus().rightMouseClicked(e, obj);
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

	public void refresh(String path) {
		this.collectionModel.invalidate(path);
		EncogWorkBench.getInstance().getMainWindow().redraw();		
	}

	public String getPath() {
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

}
