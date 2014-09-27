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

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.encog.util.file.FileUtil;

public class ProjectTreeRenderer extends DefaultTreeCellRenderer {
	
	private Icon iconFolder;
	private Icon iconFileCSV;
	private Icon iconFileEG;
	private Icon iconFileEGB;
	private Icon iconFileEGA;
	private Icon iconFileTxt;
	private Icon iconFileUnknown;

	public static ImageIcon getImageIcon(String name) {
		   return new ImageIcon(ClassLoader.getSystemResource(name));
		   }
	

	
	public ProjectTreeRenderer() {
		iconFolder = getImageIcon("org/encog/workbench/data/icons/tree/tree-folder.png");
		iconFileCSV = getImageIcon("org/encog/workbench/data/icons/tree/tree-leaf-csv.png");
		iconFileEG = getImageIcon("org/encog/workbench/data/icons/tree/tree-leaf-ml.png");
		iconFileEGB = getImageIcon("org/encog/workbench/data/icons/tree/tree-leaf-egb.png");
		iconFileEGA = getImageIcon("org/encog/workbench/data/icons/tree/tree-leaf-ega.png");
		iconFileTxt = getImageIcon("org/encog/workbench/data/icons/tree/tree-leaf-txt.png");
		iconFileUnknown = getImageIcon("org/encog/workbench/data/icons/tree/tree-leaf.png");		
	}

		public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		
		Component result = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);
		
		if( value instanceof ProjectEGFile ) {
			((JLabel)result).setIcon(this.iconFileEG);
		} else if( value instanceof ProjectParent || value instanceof ProjectDirectory ) {
			((JLabel)result).setIcon(this.iconFolder);
		} else if( value instanceof ProjectFile ) {
			String ext = FileUtil.getFileExt(((ProjectFile)value).getFile());
			if( ext.equalsIgnoreCase("txt") || ext.equalsIgnoreCase("html") ) {
				((JLabel)result).setIcon(this.iconFileTxt);
			} else if( ext.equalsIgnoreCase("csv")  ) {
				((JLabel)result).setIcon(this.iconFileCSV);
			} else if( ext.equalsIgnoreCase("EGB")  ) {
				((JLabel)result).setIcon(this.iconFileEGB);
			} else if( ext.equalsIgnoreCase("EGA")  ) {
				((JLabel)result).setIcon(this.iconFileEGA);
			} else {
				((JLabel)result).setIcon(this.iconFileUnknown);
			}
		} else {
			((JLabel)result).setIcon(this.iconFileUnknown);
		}
		
		return result;
	}

}
