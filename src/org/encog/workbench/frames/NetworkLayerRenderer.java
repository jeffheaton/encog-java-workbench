package org.encog.workbench.frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import org.encog.matrix.Matrix;
import org.encog.neural.activation.ActivationFunction;
import org.encog.neural.activation.ActivationLinear;
import org.encog.neural.activation.ActivationSigmoid;
import org.encog.neural.activation.ActivationTANH;
import org.encog.neural.networks.Layer;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.layers.FeedforwardLayer;
import org.encog.neural.networks.layers.HopfieldLayer;
import org.encog.neural.networks.layers.SOMLayer;
import org.encog.neural.persist.EncogPersistedObject;
import org.encog.util.NormalizeInput;

public class NetworkLayerRenderer extends JPanel implements ListCellRenderer {

	private Layer layer;
	private boolean selected;
	private Font titleFont;
	private Font regularFont;
	private ImageIcon iconFeedforward;
	private ImageIcon iconHopfield;
	private ImageIcon iconSOM;
	private ImageIcon iconSimple;

	public NetworkLayerRenderer() {
		
		Object obj = this.getClass().getResource(
		"/resource/iconLayerFeedforward.png");
		
		obj.getClass();
		
		this.iconFeedforward = new ImageIcon(this.getClass().getResource(
				"/resource/iconLayerFeedforward.png"));
		this.iconHopfield = new ImageIcon(this.getClass().getResource(
				"/resource/iconLayerHopfield.png"));
		this.iconSOM = new ImageIcon(this.getClass().getResource(
				"/resource/iconLayerSOM.png"));
		this.iconSimple = new ImageIcon(this.getClass().getResource(
		"/resource/iconLayerSimple.png"));
		this.titleFont = new Font("sansserif", Font.BOLD, 12);
		this.regularFont = new Font("serif", 0, 12);
	}

	public Component getListCellRendererComponent(JList list, Object value, // value
																			// to
																			// display
			int index, // cell index
			boolean isSelected, // is the cell selected
			boolean cellHasFocus) // the list and the cell have the focus
	{
		this.setLayer((Layer) value);
		this.setSelected(isSelected);
		return this;
	}
	
	private String getLayerType(Layer layer)
	{
		if( layer.isHidden())
			return "Hidden";
		else if(layer.isInput())
			return "Input";
		else if(layer.isOutput())
			return "Output";
		else
			return "Unknown";
	}
	
	private String getActivationType(ActivationFunction a)
	{
		if( a instanceof ActivationLinear )
		{
			return "Linear";
		}
		else if( a instanceof ActivationSigmoid )
		{
			return "Sigmoid";
		}
		else if( a instanceof ActivationTANH )
		{
			return "Hyperbolic Tangent (TANH)";
		}
		else 
			return "Unknown";
	}
	
	private String getNormalizationType(SOMLayer som)
	{
		if( som.getNormalizationType()==NormalizeInput.NormalizationType.MULTIPLICATIVE)
		{
			return "Multiplicative";
		}
		else if( som.getNormalizationType()==NormalizeInput.NormalizationType.Z_AXIS)
		{
			return "Z-Axis";
		}
		else
		{
			return "Unknown";
		}
	}
	
	public String getMatrix(Matrix matrix)
	{
		if( matrix==null )
			return "N/A";
		else
			return matrix.getRows() + "x" + matrix.getCols()+ " (rows x cols)";
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
		
		if( this.getLayer() instanceof FeedforwardLayer )
		{
			FeedforwardLayer ff = (FeedforwardLayer)this.getLayer();
			this.iconFeedforward.paintIcon(this, g, 4, 4);
			g.setFont(this.titleFont);
			g.setColor(Color.BLACK);
			g.drawString("Feedforward Layer ("+this.getLayerType(ff)+")", 70, y);
			y+=titleMetrics.getHeight();
			
			g.setFont(this.regularFont);
			
			if( shouldDisplayName(ff) )
			{
				g.drawString(ff.getDescription()
						+"("+ff.getName()
						+")", 70, y);
				y+=regularMetrics.getHeight();
			}
			g.drawString("Neurons: " + ff.getNeuronCount(), 70, y);
			y+=regularMetrics.getHeight();
			g.drawString("Activation Function:" + getActivationType(ff.getActivationFunction()), 70, y);
			y+=regularMetrics.getHeight();
			g.drawString("Matrix Size:" + getMatrix(ff.getMatrix()), 70, y);
		}
		else if( this.getLayer() instanceof HopfieldLayer )
		{
			HopfieldLayer hop = (HopfieldLayer)this.getLayer();
			this.iconHopfield.paintIcon(this, g, 4, 4);
			g.setFont(this.titleFont);
			g.setColor(Color.BLACK);
			g.drawString("Hopfield Layer ("+this.getLayerType(hop)+")", 70, y);
			y+=titleMetrics.getHeight();
			
			g.setFont(this.regularFont);
			
			if( shouldDisplayName(hop) )
			{
				g.drawString(hop.getDescription()
						+"("+hop.getName()
						+")", 70, y);
				y+=regularMetrics.getHeight();
			}
			g.drawString("Neurons: " + hop.getNeuronCount(), 70, y);
			y+=regularMetrics.getHeight();
			g.drawString("Matrix Size:" + getMatrix(hop.getMatrix()), 70, y);
		}
		else if( this.getLayer() instanceof SOMLayer )
		{
			SOMLayer som = (SOMLayer)this.getLayer();
			this.iconSOM.paintIcon(this, g, 4, 4);
			g.setFont(this.titleFont);
			g.setColor(Color.BLACK);
			g.drawString("Self Organizing Map (SOM) Layer ("+this.getLayerType(som)+")", 70, y);
			y+=titleMetrics.getHeight();
			
			g.setFont(this.regularFont);
			
			if( shouldDisplayName(som) )
			{
				g.drawString(som.getDescription()
						+"("+som.getName()
						+")", 70, y);
				y+=regularMetrics.getHeight();
			}
			g.drawString("Neurons: " + som.getNeuronCount(), 70, y);
			y+=regularMetrics.getHeight();
			g.drawString("Normalization: " + getNormalizationType(som), 70, y);
			y+=regularMetrics.getHeight();
			g.drawString("Matrix Size:" + getMatrix(som.getMatrix()), 70, y);
		}
		else if( this.getLayer() instanceof BasicLayer )
		{
			BasicLayer basic = (BasicLayer)this.getLayer();
			this.iconSimple.paintIcon(this, g, 4, 4);
			g.setFont(this.titleFont);
			g.setColor(Color.BLACK);
			g.drawString("Simple Layer ("+this.getLayerType(basic)+")", 70, y);
			y+=titleMetrics.getHeight();
			
			g.setFont(this.regularFont);
			
			if( shouldDisplayName(basic) )
			{
				g.drawString(basic.getDescription()
						+"("+basic.getName()
						+")", 70, y);
				y+=regularMetrics.getHeight();
			}
			g.drawString("Neurons: " + basic.getNeuronCount(), 70, y);
			
		}
		
	}

	/**
	 * @return the layer
	 */
	public Layer getLayer() {
		return layer;
	}

	/**
	 * @param layer
	 *            the layer to set
	 */
	public void setLayer(Layer layer) {
		this.layer = layer;
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	private boolean shouldDisplayName(EncogPersistedObject obj)
	{
		if( obj.getName()==null && obj.getDescription()==null )
		{
			return false;
		}
		
		if( obj.getName()!=null && obj.getName().length()>0 )
		{
			return true;
		}
		
		if( obj.getDescription()!=null && obj.getDescription().length()>0 )
		{
			return true;
		}
		
		return false;
	}

}
