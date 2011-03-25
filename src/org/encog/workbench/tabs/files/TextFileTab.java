package org.encog.workbench.tabs.files;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.frames.document.tree.ProjectFile;
import org.encog.workbench.tabs.files.text.BasicTextTab;
import org.encog.workbench.util.EncogFonts;
import org.encog.workbench.util.FileUtil;

public class TextFileTab extends BasicTextTab {
		
	public TextFileTab(ProjectFile file) {
		super(file);
	
	}	
}
