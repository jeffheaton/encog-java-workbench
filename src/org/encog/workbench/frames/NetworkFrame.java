package org.encog.workbench.frames;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListModel;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Layer;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.layers.FeedforwardLayer;
import org.encog.neural.networks.layers.HopfieldLayer;
import org.encog.neural.networks.layers.SOMLayer;
import org.encog.util.NormalizeInput.NormalizationType;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.CreateLayer;
import org.encog.workbench.dialogs.EditFeedforwardLayer;
import org.encog.workbench.dialogs.EditNetworkProperties;
import org.encog.workbench.dialogs.CreateLayer.CreateLayerResult;
import org.encog.workbench.models.NetworkListModel;

public class NetworkFrame extends JFrame implements WindowListener, ActionListener, MouseListener
{

	private BasicNetwork data;
	private JToolBar toolbar;
	private JButton addLayer;
	private JButton deleteLayer;
	private JButton editLayer;
	private JButton properties;
	private JList contents;
	private NetworkListModel model;
	
	public NetworkFrame(BasicNetwork data) {
		this.data = data;	
		addWindowListener(this);		
	}

	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosing(WindowEvent arg0) {		
		EncogWorkBench.getInstance().getMainWindow().closeSubWindow(this);
	}

	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowOpened(WindowEvent arg0) {
		setSize(640,480);
		Container content = this.getContentPane();
		content.setLayout(new BorderLayout());
		this.toolbar = new JToolBar();
		this.toolbar.setFloatable(false);
		this.toolbar.add(this.deleteLayer = new JButton("Delete Layer"));
		this.toolbar.add(this.addLayer = new JButton("Add Layer"));
		this.toolbar.add(this.editLayer = new JButton("Edit Layer"));
		this.toolbar.add(this.properties = new JButton("Network Properties"));
		
		this.addLayer.addActionListener(this);
		this.editLayer.addActionListener(this);
		this.deleteLayer.addActionListener(this);
		this.properties.addActionListener(this);
		
		content.add(this.toolbar,BorderLayout.PAGE_START);
		JPanel content2 = new JPanel();
		content2.setLayout(new BorderLayout());
		content.add(content2,BorderLayout.CENTER);
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());		
		topPanel.add(new JLabel("==Input=="));
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(new JLabel("==Output=="));
		
		content2.add(topPanel,BorderLayout.NORTH);
		content2.add(bottomPanel,BorderLayout.SOUTH);
		
		this.model  = new NetworkListModel(this.data);
		this.contents = new JList(this.model);
		this.contents.addMouseListener(this);
		this.contents.setCellRenderer(new NetworkLayerRenderer());
		content2.add(new JScrollPane(this.contents),BorderLayout.CENTER);
		this.contents.setFixedCellHeight(72);
		
		this.setTitle("Edit Neural Network");
		
	}

	public void actionPerformed(ActionEvent action) {
		if( action.getSource()==this.addLayer )
		{
			performAddLayer();
		}
		else if( action.getSource()==this.editLayer )
		{
			performEditLayer();
		}
		else if( action.getSource()==this.deleteLayer )
		{
			performDeleteLayer();
		}
		else if( action.getSource()==this.properties )
		{
			performProperties();
		}
	}

	private void performDeleteLayer() {
		int index = this.contents.getSelectedIndex();
		if( index!=-1 )
		{
			this.model.deleteLayer(index);
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Please select what you wish to delete.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	private void performEditLayer() {
		Object item = this.contents.getSelectedValue();
		editLayer(item);
	}

	private void performAddLayer() {
		CreateLayer dialog = new CreateLayer(this);
		dialog.pack();
		dialog.setVisible(true);
		CreateLayerResult result = dialog.getSelected();
		if( result!=null )
		{
			Layer layer;
			Layer baseLayer = null;
			int index = this.contents.getSelectedIndex();
			if( index!=-1 )
			{
				baseLayer = this.data.getLayers().get(index);
			}
			
			switch(result)
			{	
				case FEEDFORWARD:
					layer = new FeedforwardLayer(2);
					this.model.addLayer(baseLayer,layer);
					break;
					
				case SIMPLE:
					layer = new BasicLayer(2);
					this.model.addLayer(baseLayer,layer);
					break;
					
				case SOM:
					layer = new SOMLayer(2, NormalizationType.Z_AXIS );
					this.model.addLayer(baseLayer,layer);					
					break;
					
				case HOPFIELD:
					layer = new HopfieldLayer(2);
					this.model.addLayer(baseLayer,layer);					
					break;
			}
		}
		
	}
	
	public void performProperties()
	{
		EditNetworkProperties dialog = new EditNetworkProperties(this); 
		dialog.setResultName(this.data.getName());
		dialog.setResultDescription(this.data.getDescription());
		dialog.setVisible(true);
		if( dialog.getCommand()==EditNetworkProperties.Command.OK )
		{
			this.data.setName(dialog.getResultName());
			this.data.setDescription(dialog.getResultDescription());
		}
	}

	/**
	 * @return the data
	 */
	public BasicNetwork getData() {
		return data;
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getClickCount() == 2) {
			int index = this.contents.locationToIndex(e.getPoint());
			ListModel dlm = this.contents.getModel();
			Object item = dlm.getElementAt(index);
			this.contents.ensureIndexIsVisible(index);
			editLayer(item);
		}
		
	}
	
	private void editLayer(Object item)
	{
		if( item instanceof FeedforwardLayer )
		{
			FeedforwardLayer layer = (FeedforwardLayer)item;
			EditFeedforwardLayer dialog = new EditFeedforwardLayer(this);
			dialog.setResultActivation(layer.getActivationFunction());
			dialog.setResultDescription(layer.getDescription());
			dialog.setResultName(layer.getName());
			dialog.setResultNeuronCount(layer.getNeuronCount());
			dialog.setVisible(true);
			if( dialog.getCommand()== EditFeedforwardLayer.Command.OK)
			{
				layer.setName(dialog.getResultName());
				layer.setDescription(dialog.getResultDescription());
				layer.setActivationFunction(dialog.getResultActivation());
				// was there a neuron count change?
				if( layer.getNeuronCount()!=dialog.getResultNeuronCount() )
				{
					layer.setNeuronCount(dialog.getResultNeuronCount());
				}
			}
		}
	}		

}
