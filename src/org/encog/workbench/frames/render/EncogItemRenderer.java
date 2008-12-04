package org.encog.workbench.frames.render;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Network;
import org.encog.neural.persist.EncogPersistedObject;

public class EncogItemRenderer extends JPanel implements ListCellRenderer {
    /**
	 * 
	 */
	private static final long serialVersionUID = 987233162876263335L;

    private EncogPersistedObject encogObject;
    private boolean selected;
    private Font titleFont;
    private Font regularFont;
    private ImageIcon iconNeuralNet;
    private ImageIcon iconTrainingSet;

    public EncogItemRenderer()
    {
    	this.iconNeuralNet = new ImageIcon(this.getClass().getResource("/resource/iconNeuralNet.png"));
    	this.iconTrainingSet = new ImageIcon(this.getClass().getResource("/resource/iconTrain.png"));
    	this.titleFont = new Font("sansserif",Font.BOLD,12);
    	this.regularFont = new Font("serif",0,12);
    }
    
   
    public Component getListCellRendererComponent(
      JList list,
      Object value,            // value to display
      int index,               // cell index
      boolean isSelected,      // is the cell selected
      boolean cellHasFocus)    // the list and the cell have the focus
    {
        this.setEncogObject((EncogPersistedObject)value);
        this.setSelected(isSelected);
        return this;
    }

	/**
	 * @return the encogObject
	 */
	public EncogPersistedObject getEncogObject() {
		return encogObject;
	}

	/**
	 * @param encogObject the encogObject to set
	 */
	public void setEncogObject(EncogPersistedObject encogObject) {
		this.encogObject = encogObject;
	} 
	
	
	
	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void paint(Graphics g)
	{
		int width = this.getWidth();
		int height = this.getHeight();
		
		if( this.selected )
		{
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0,0, width, height);
		}
		else
		{
			g.setColor(Color.WHITE);
			g.fillRect(0,0, width, height);
		}
		
		g.setColor(Color.GRAY);
		g.drawRect(0, 0, width-1, height-1);
		
		FontMetrics titleMetrics = g.getFontMetrics(this.titleFont);
		FontMetrics regularMetrics = g.getFontMetrics(this.regularFont);
		
		int y = titleMetrics.getHeight();
		
		if( this.getEncogObject() instanceof Network )
		{
			this.iconNeuralNet.paintIcon(this, g, 4, 4);
			g.setFont(this.titleFont);
			g.setColor(Color.BLACK);
			g.drawString("Neural Network", 70, y);
			y+=titleMetrics.getHeight();
			g.setFont(this.regularFont);
			g.drawString(this.getEncogObject().getDescription()
					+"("+this.getEncogObject().getName()
					+")", 70, y);
			y+=regularMetrics.getHeight();
			BasicNetwork network = (BasicNetwork)this.getEncogObject();
			g.drawString("Layers: " + network.getLayers().size(), 70, y);
		}
		else if( this.getEncogObject() instanceof NeuralDataSet )
		{
			this.iconTrainingSet.paintIcon(this, g, 4, 4);	
			g.setFont(this.titleFont);
			g.setColor(Color.BLACK);
			g.drawString("Training Data", 70, y);
			y+=titleMetrics.getHeight();
			g.setFont(this.regularFont);
			g.drawString(this.getEncogObject().getDescription()
					+"("+this.getEncogObject().getName()
					+")", 70, y);
			y+=regularMetrics.getHeight();
			NeuralDataSet data = (NeuralDataSet)this.getEncogObject();
			g.drawString("Ideal Size: " + data.getIdealSize()+","+
					"Input Size: " + data.getInputSize(), 70, y);

		}
	}
}
