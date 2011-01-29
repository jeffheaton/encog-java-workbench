package org.encog.workbench.tabs.files;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.File;
import java.util.Date;

import org.encog.engine.util.Format;
import org.encog.workbench.tabs.EncogCommonTab;
import org.encog.workbench.util.EncogFonts;

public class GenericFileTab extends BasicFileTab {
	
	public GenericFileTab(File file) {
		super(file);
	}

	public void paint(final Graphics g) {
		super.paint(g);
		
		final FontMetrics fm = g.getFontMetrics();
		g.setFont(EncogFonts.getInstance().getBodyFont());
		int y = fm.getHeight();

		g.drawString( "Unknown file type.  Do not know how to display.", 0, y);
		y += g.getFontMetrics().getHeight();
		g.drawString( file.getAbsolutePath(), 0, y);
		y += g.getFontMetrics().getHeight();
		y += g.getFontMetrics().getHeight();

		g.setFont(EncogFonts.getInstance().getHeadFont());
		g.drawString("File Size:", 10, y);
		g.setFont(EncogFonts.getInstance().getBodyFont());
		g.drawString(Format.formatMemory(file.length()), 150, y);
		y += g.getFontMetrics().getHeight();
		
		g.setFont(EncogFonts.getInstance().getHeadFont());
		g.drawString("Last Modified:", 10, y);
		g.setFont(EncogFonts.getInstance().getBodyFont());
		g.drawString(new Date(file.lastModified()).toString(), 150, y);
		y += g.getFontMetrics().getHeight();

	}
	
	public File getFile() {
		return this.file;
	}

}
