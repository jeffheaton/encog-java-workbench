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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListModel;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Layer;
import org.encog.neural.networks.Network;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.layers.FeedforwardLayer;
import org.encog.neural.networks.layers.HopfieldLayer;
import org.encog.neural.networks.layers.SOMLayer;
import org.encog.neural.persist.EncogPersistedObject;
import org.encog.util.NormalizeInput.NormalizationType;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.EditEncogObjectProperties;
import org.encog.workbench.dialogs.EditFeedforwardLayer;
import org.encog.workbench.dialogs.EditHopfieldLayer;
import org.encog.workbench.dialogs.EditSOMLayer;
import org.encog.workbench.dialogs.EditSimpleLayer;
import org.encog.workbench.dialogs.select.SelectDialog;
import org.encog.workbench.dialogs.select.SelectItem;
import org.encog.workbench.dialogs.training.backpropagation.UserInput;
import org.encog.workbench.models.NetworkListModel;
import org.encog.workbench.training.RunAnneal;
import org.encog.workbench.training.RunGenetic;

public class NetworkFrame extends EncogListFrame {

	private BasicNetwork data;
	private JToolBar toolbar;
	private JButton addLayer;
	private JButton deleteLayer;
	private JButton editLayer;	
	private JButton properties;
	private NetworkListModel model;

	private JPopupMenu popupNetworkLayer;
	private JMenuItem popupNetworkLayerDelete;
	private JMenuItem popupNetworkLayerEdit;
	private JMenuItem popupEditMatrix;

	public NetworkFrame(BasicNetwork data) {
		this.data = data;
		addWindowListener(this);
	}

	public void windowClosing(WindowEvent arg0) {
		EncogWorkBench.getInstance().getMainWindow().closeSubWindow(this);
	}

	public void windowOpened(WindowEvent arg0) {
		setSize(640, 480);
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

		content.add(this.toolbar, BorderLayout.PAGE_START);
		JPanel content2 = new JPanel();
		content2.setLayout(new BorderLayout());
		content.add(content2, BorderLayout.CENTER);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		topPanel.add(new JLabel("==Input=="));
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(new JLabel("==Output=="));

		content2.add(topPanel, BorderLayout.NORTH);
		content2.add(bottomPanel, BorderLayout.SOUTH);

		this.model = new NetworkListModel(this.data);
		this.contents = new JList(this.model);
		this.contents.addMouseListener(this);
		this.contents.setCellRenderer(new NetworkLayerRenderer());
		content2.add(new JScrollPane(this.contents), BorderLayout.CENTER);
		this.contents.setFixedCellHeight(72);

		this.setTitle("Edit Neural Network");

		this.popupNetworkLayer = new JPopupMenu();
		this.popupNetworkLayerEdit = this.addItem(this.popupNetworkLayer,
				"Edit Layer", 'e');
		this.popupEditMatrix = this.addItem(this.popupNetworkLayer,
				"Edit Matrix", 'm');
		this.popupNetworkLayerDelete = this.addItem(this.popupNetworkLayer,
				"Delete", 'd');

	}

	public void actionPerformed(ActionEvent action) {
		if ((action.getSource() == this.editLayer)
				|| (action.getSource() == this.popupNetworkLayerEdit)) {
			performEditLayer();
		} else if ((action.getSource() == this.deleteLayer)
				|| (action.getSource() == this.popupNetworkLayerDelete)) {
			performDeleteLayer();
		} else if ((action.getSource() == this.properties)) {
			performProperties();
		} else if( action.getSource()== this.addLayer ) {
			performAddLayer();
		} else if( action.getSource() == this.popupEditMatrix )
		{
			performEditMatrix();
		}
	}

	private void performEditMatrix() {
		Layer item = (Layer)this.contents.getSelectedValue();
		if( item.getMatrix()==null)
		{
			EncogWorkBench.displayError("Error", "This layer does not have a matrix.");
			return;
		}
		MatrixFrame frame = new MatrixFrame(this.data,item.getMatrix());
		frame.setVisible(true);
		
	}

	private void performDeleteLayer() {
		int index = this.contents.getSelectedIndex();
		if (index != -1) {
			this.model.deleteLayer(index);
		} else {
			JOptionPane.showMessageDialog(this,
					"Please select what you wish to delete.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private void performEditLayer() {
		Object item = this.contents.getSelectedValue();
		openItem(item);
	}

	private void performAddLayer() {

		SelectItem itemFeedfoward, itemSimple, itemSOM, itemHopfield;
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(itemFeedfoward = new SelectItem("Feedforward Layer"));
		list.add(itemSimple = new SelectItem("Simple Layer"));
		list.add(itemSOM = new SelectItem("SOM Layer"));
		list.add(itemHopfield = new SelectItem("Hopfield Layer"));
		SelectDialog dialog = new SelectDialog(this, list);
		SelectItem result = dialog.process();

		Layer layer;
		Layer baseLayer = null;
		int index = this.contents.getSelectedIndex();
		if (index != -1) {
			baseLayer = this.data.getLayers().get(index);
		}

		if (result == itemFeedfoward) {
			layer = new FeedforwardLayer(2);
			this.model.addLayer(baseLayer, layer);
		} else if (result == itemSimple) {
			layer = new BasicLayer(2);
			this.model.addLayer(baseLayer, layer);
		} else if (result == itemSOM) {
			layer = new SOMLayer(2, NormalizationType.Z_AXIS);
			this.model.addLayer(baseLayer, layer);
		} else if (result == itemHopfield) {
			layer = new HopfieldLayer(2);
			this.model.addLayer(baseLayer, layer);
		}

	}

	public void performProperties() {
		EditEncogObjectProperties dialog = new EditEncogObjectProperties(this,
				this.data);
		dialog.process();
	}

	/**
	 * @return the data
	 */
	public BasicNetwork getData() {
		return data;
	}

	@Override
	protected void openItem(Object item) {
		if (item instanceof FeedforwardLayer) {
			FeedforwardLayer layer = (FeedforwardLayer) item;
			EditFeedforwardLayer dialog = new EditFeedforwardLayer(this);
			dialog.setResultActivation(layer.getActivationFunction());
			dialog.setResultDescription(layer.getDescription());
			dialog.setResultName(layer.getName());
			dialog.setResultNeuronCount(layer.getNeuronCount());
			dialog.setVisible(true);
			if (dialog.getCommand() == EditFeedforwardLayer.Command.OK) {
				layer.setName(dialog.getResultName());
				layer.setDescription(dialog.getResultDescription());
				layer.setActivationFunction(dialog.getResultActivation());
				// was there a neuron count change?
				if (layer.getNeuronCount() != dialog.getResultNeuronCount()) {
					layer.setNeuronCount(dialog.getResultNeuronCount());
				}
			}
		} else if (item instanceof HopfieldLayer) {
			HopfieldLayer layer = (HopfieldLayer) item;
			EditHopfieldLayer dialog = new EditHopfieldLayer(this);
			dialog.setResultDescription(layer.getDescription());
			dialog.setResultName(layer.getName());
			dialog.setResultNeuronCount(layer.getNeuronCount());
			dialog.setVisible(true);
			if (dialog.getCommand() == EditHopfieldLayer.Command.OK) {
				layer.setName(dialog.getResultName());
				layer.setDescription(dialog.getResultDescription());
				// was there a neuron count change?
				if (layer.getNeuronCount() != dialog.getResultNeuronCount()) {
					layer.setNeuronCount(dialog.getResultNeuronCount());
				}
			}
		} else if (item instanceof SOMLayer) {
			SOMLayer layer = (SOMLayer) item;
			EditSOMLayer dialog = new EditSOMLayer(this);
			dialog.setResultNormalization(layer.getNormalizationType());
			dialog.setResultDescription(layer.getDescription());
			dialog.setResultName(layer.getName());
			dialog.setResultNeuronCount(layer.getNeuronCount());
			dialog.setVisible(true);
			if (dialog.getCommand() == EditSOMLayer.Command.OK) {
				layer.setName(dialog.getResultName());
				layer.setDescription(dialog.getResultDescription());
				layer.setNormalizationType(dialog.getResultNormalization());
				// was there a neuron count change?
				if (layer.getNeuronCount() != dialog.getResultNeuronCount()) {
					layer.setNeuronCount(dialog.getResultNeuronCount());
				}
			}
		} else if (item instanceof BasicLayer) {
			BasicLayer layer = (BasicLayer) item;
			EditSimpleLayer dialog = new EditSimpleLayer(this);
			dialog.setResultDescription(layer.getDescription());
			dialog.setResultName(layer.getName());
			dialog.setResultNeuronCount(layer.getNeuronCount());
			dialog.setVisible(true);
			if (dialog.getCommand() == EditSimpleLayer.Command.OK) {
				layer.setName(dialog.getResultName());
				layer.setDescription(dialog.getResultDescription());
				// was there a neuron count change?
				if (layer.getNeuronCount() != dialog.getResultNeuronCount()) {
					layer.setNeuronCount(dialog.getResultNeuronCount());
				}
			}
		}

	}

	@Override
	public void rightMouseClicked(MouseEvent e, Object item) {
		if (item != null) {
			this.popupNetworkLayer.show(e.getComponent(), e.getX(), e.getY());
		}
	}
}
