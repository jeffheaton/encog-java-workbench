package org.encog.workbench.tabs.files;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.encog.neural.data.TextData;
import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.util.EncogFonts;
import org.encog.workbench.util.FileUtil;

public class ImageFileTab extends BasicFileTab {
	
	private BufferedImage image;
	
	public ImageFileTab(File file) {
		super(file);
		
		try {
		    this.image = ImageIO.read(file);
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
