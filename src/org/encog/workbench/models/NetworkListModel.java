package org.encog.workbench.models;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Layer;

public class NetworkListModel extends CommonListModel {

	private BasicNetwork network;
	
	public NetworkListModel(BasicNetwork network)
	{
		this.network = network;
	}
	
	@Override
	public Object getElementAt(int i) {
		return this.network.getLayers().get(i);
	}

	@Override
	public int getSize() {
		return this.network.getLayers().size();
	}

	public void deleteLayer(int index) {
		Layer layer = this.network.getLayers().get(index);
		this.network.removeLayer(layer);
		this.invalidate();		
	}

	public void addLayer(Layer base,Layer layer) {	
		if( base!=null )
			this.network.addLayer(base,layer);
		else
			this.network.addLayer(layer);
		this.invalidate();		
	}
}
