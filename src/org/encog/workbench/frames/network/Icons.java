package org.encog.workbench.frames.network;

import javax.swing.ImageIcon;

public class Icons {

	private static ImageIcon layerBasic;
	private static ImageIcon layerContext;
	private static ImageIcon layerRBF;
	private static ImageIcon synapseDirect;
	private static ImageIcon synapseOneToOne;
	private static ImageIcon synapseWeight;
	private static ImageIcon synapseWeightless;
	
	public static void loadIcons()
	{
		if( Icons.layerBasic==null )
		{
			Icons.layerBasic = new ImageIcon(Icons.class.getResource("/resource/layerBasic.png"));
			Icons.layerContext = new ImageIcon(Icons.class.getResource("/resource/layerContext.png"));
			Icons.layerRBF = new ImageIcon(Icons.class.getResource("/resource/layerRBF.png"));
			Icons.synapseDirect = new ImageIcon(Icons.class.getResource("/resource/synapseDirect.png"));
			Icons.synapseOneToOne = new ImageIcon(Icons.class.getResource("/resource/synapseOneToOne.png"));
			Icons.synapseWeight = new ImageIcon(Icons.class.getResource("/resource/synapseWeight.png"));
			Icons.synapseWeightless = new ImageIcon(Icons.class.getResource("/resource/synapseWeightless.png"));
		}
	}

	public static ImageIcon getLayerBasic() {
		loadIcons();
		return layerBasic;
	}

	public static ImageIcon getLayerContext() {
		loadIcons();
		return layerContext;
	}

	public static ImageIcon getLayerRBF() {
		loadIcons();
		return layerRBF;
	}

	public static ImageIcon getSynapseDirect() {
		loadIcons();
		return synapseDirect;
	}

	public static ImageIcon getSynapseOneToOne() {
		return synapseOneToOne;
	}

	public static ImageIcon getSynapseWeight()  {
		loadIcons();
		return synapseWeight;
	}

	public static ImageIcon getSynapseWeightless() {
		loadIcons();
		return synapseWeightless;
	}
	
	
	
	
	
}
