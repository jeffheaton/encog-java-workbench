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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.encog.persist.EncogDirectoryPersistence;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.util.FileUtil;

public class EncogCollectionModel implements TreeModel {

	private EncogDirectoryPersistence projectDirectory;
	private List<ProjectItem> files = new ArrayList<ProjectItem>();
	private List<TreeModelListener> listeners = new ArrayList<TreeModelListener>();

	public EncogCollectionModel(File path) {
		invalidate(path);
	}

	public EncogCollectionModel() {
	}

	public Object getRoot() {
		if (projectDirectory == null)
			return null;
		else
			return projectDirectory.getParent();
	}

	public Object getChild(Object parent, int index) {
		if (parent == projectDirectory.getParent()) {
			return this.files.get(index);
		} else {
			return null;
		}
	}

	public int getChildCount(Object parent) {
		if (parent == projectDirectory.getParent()) {
			return this.files.size();
		} else {
			return 0;
		}
	}

	public boolean isLeaf(Object node) {
		if (node == projectDirectory.getParent()) {
			return false;
		} else {
			if (node instanceof ProjectDirectory) {
				return false;
			} else if (node instanceof ProjectEGFile) {
				return true;
			} else if (node instanceof ProjectFile) {
				return true;
			}
			return true;
		}
	}

	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub

	}

	public int getIndexOfChild(Object parent, Object child) {
		if (parent == projectDirectory.getParent()) {
			return this.files.indexOf(child);
		} else {
			return -1;
		}
	}

	public void addTreeModelListener(TreeModelListener l) {
		this.listeners.add(l);
	}

	public void removeTreeModelListener(TreeModelListener l) {
		this.listeners.remove(l);
	}

	public void invalidate(File path) {
		
		if( path==null )
			return;

		try {
			EncogWorkBench.getInstance().getMainWindow().beginWait();
			this.files.clear();

			if (path == null)
				return;

			this.projectDirectory = new EncogDirectoryPersistence(path);

			// sort
			TreeSet<File> folderList = new TreeSet<File>();
			TreeSet<File> fileList = new TreeSet<File>();
			
			if (path.listFiles() != null) {
				for (File entry : path.listFiles()) {
					if (!entry.isHidden()) {
						if (entry.isDirectory())
							folderList.add(entry);
						else
							fileList.add(entry);
					}

				}
			}

			// build list
			this.files.clear();

			if (path.getParent() != null) {
				this.files.add(new ProjectParent(path.getParentFile()));
			}

			for (File entry : folderList) {
				this.files.add(new ProjectDirectory(entry));
			}

			for (File entry : fileList) {
				String ext = FileUtil.getFileExt(entry);

				if (ext.equalsIgnoreCase("egb")) {
					this.files.add(new ProjectTraining(entry));
				} else if (ext.equalsIgnoreCase("eg")) {
					try {
						this.files.add(new ProjectEGFile(entry));
					} catch (Throwable t) {
						this.files.add(new ProjectFile(entry, true));
					}
				} else {
					this.files.add(new ProjectFile(entry));
				}
			}

			// notify
			Object[] p = new Object[1];
			p[0] = this.projectDirectory.getParent();
			TreeModelEvent e = new TreeModelEvent(this, p);
			for (TreeModelListener l : this.listeners) {
				l.treeStructureChanged(e);
			}
		} finally {

			EncogWorkBench.getInstance().getMainWindow().endWait();
		}
	}

	public void invalidate() {
		invalidate(getPath());
	}

	public File getPath() {
		if (this.projectDirectory == null)
			return null;
		else
			return this.projectDirectory.getParent();
	}

	public String[] listEGFiles() {
		List<String> files = new ArrayList<String>();

		for (ProjectItem item : this.files) {
			if (item instanceof ProjectEGFile) {
				files.add(((ProjectEGFile) item).getFile().getName());
			}
		}

		String[] result = new String[files.size()];
		files.toArray(result);

		return result;
	}

	public ProjectFile findTreeFile(String filename) {

		for (ProjectItem item : this.files) {
			if (item instanceof ProjectFile) {
				if (((ProjectFile) item).getFile().getName().equalsIgnoreCase(
						filename)) {
					return (ProjectFile) item;
				}
			}
		}

		return null;
	}

	public List<ProjectItem> getData() {
		return this.files;

	}

	/**
	 * @return the projectDirectory
	 */
	public EncogDirectoryPersistence getProjectDirectory() {
		return projectDirectory;
	}

	public ProjectFile findFirstEGA() {
		for (ProjectItem item : this.files) {
			if (item instanceof ProjectFile) {
				File file = ((ProjectFile) item).getFile();
				String ext = FileUtil.getFileExt(file);
				if (ext.equalsIgnoreCase("ega")) {
					return (ProjectFile)item;
				}
			}
		}
		
		return null;
	}

}
