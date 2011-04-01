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
