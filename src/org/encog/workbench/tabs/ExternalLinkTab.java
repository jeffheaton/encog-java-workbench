package org.encog.workbench.tabs;

import java.awt.FontMetrics;
import java.awt.Graphics;

import org.encog.mathutil.libsvm.svm_model;
import org.encog.neural.data.external.ExternalDataSource;
import org.encog.persist.EncogPersistedObject;
import org.encog.util.Format;
import org.encog.workbench.util.EncogFonts;

public class ExternalLinkTab extends EncogCommonTab {

	private ExternalDataSource object;
	
	public ExternalLinkTab(EncogPersistedObject encogObject) {
		super(encogObject);
		this.object = (ExternalDataSource)encogObject;		
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
		g.drawString("External Data Source", 0, y);
		y += g.getFontMetrics().getHeight();
		y += g.getFontMetrics().getHeight();

		g.setFont(EncogFonts.getInstance().getHeadFont());
		g.drawString("Type:", 10, y);
		g.setFont(EncogFonts.getInstance().getBodyFont());
		g.drawString(this.object.getType(), 150, y);
		y += g.getFontMetrics().getHeight();
		
		g.setFont(EncogFonts.getInstance().getHeadFont());
		g.drawString("File:", 10, y);
		g.setFont(EncogFonts.getInstance().getBodyFont());
		g.drawString(this.object.getLink(), 150, y);
		y += g.getFontMetrics().getHeight();
		
		g.setFont(EncogFonts.getInstance().getHeadFont());
		g.drawString("Input Count:", 10, y);
		g.setFont(EncogFonts.getInstance().getBodyFont());
		g.drawString(""+this.object.getInputCount(), 150, y);
		y += g.getFontMetrics().getHeight();
		
		g.setFont(EncogFonts.getInstance().getHeadFont());
		g.drawString("Ideal Count:", 10, y);
		g.setFont(EncogFonts.getInstance().getBodyFont());
		g.drawString(""+this.object.getIdealCount(), 150, y);
		y += g.getFontMetrics().getHeight();
		
	}


}
