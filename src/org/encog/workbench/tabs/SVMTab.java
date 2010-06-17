package org.encog.workbench.tabs;

import java.awt.FontMetrics;
import java.awt.Graphics;

import org.encog.Encog;
import org.encog.neural.networks.svm.SVMNetwork;
import org.encog.persist.EncogPersistedObject;
import org.encog.util.Format;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.util.EncogFonts;

public class SVMTab extends EncogCommonTab {

	private SVMNetwork network;
	
	public SVMTab(EncogPersistedObject encogObject) {
		super(encogObject);
		this.network = (SVMNetwork)encogObject;
	}
	
	/**
	 * Paint the panel.
	 * @param g The graphics object to use.
	 */
	public void paint(final Graphics g) {
		super.paint(g);
		final FontMetrics fm = g.getFontMetrics();
		g.setFont(EncogFonts.getInstance().getTitleFont());
		int y = fm.getHeight();
		g.setFont(EncogFonts.getInstance().getTitleFont());
		g.drawString("Support Vector Machine", 0, y);
		y += g.getFontMetrics().getHeight();
		y += g.getFontMetrics().getHeight();

		g.setFont(EncogFonts.getInstance().getHeadFont());
		g.drawString("Input Count:", 10, y);
		g.setFont(EncogFonts.getInstance().getBodyFont());
		g.drawString(Format.formatInteger(this.network.getInputCount()), 150, y);
		y += g.getFontMetrics().getHeight();
		
		g.setFont(EncogFonts.getInstance().getHeadFont());
		g.drawString("Output Count:", 10, y);
		g.setFont(EncogFonts.getInstance().getBodyFont());
		g.drawString(Format.formatInteger(this.network.getOutputCount()), 150, y);
		y += g.getFontMetrics().getHeight();		
		

	}

}
