package org.encog.workbench.tabs.files;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.frames.document.tree.ProjectFile;

public class ImageFileTab extends BasicFileTab {
	
	private BufferedImage image;
	
	public ImageFileTab(ProjectFile file) {
		super(file);
		
		try {
		    this.image = ImageIO.read(file.getFile());
		} catch (IOException e) {
			EncogWorkBench.displayError("Error Loading Image", e);
		}
		
	}
		
	public boolean close()
	{
		return true;
	}
	
	public void paint(final Graphics g) {
		super.paint(g);
		g.drawImage(this.image, 0,0, this);
	}
	
}
