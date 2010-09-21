/*
 * Encog(tm) Workbench v2.5
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package org.encog.workbench.tabs.network;

import javax.swing.ImageIcon;

public class Icons {

	private static ImageIcon layerBasic;
	private static ImageIcon layerContext;
	private static ImageIcon layerRBF;
	private static ImageIcon synapseDirect;
	private static ImageIcon synapseOneToOne;
	private static ImageIcon synapseWeight;
	private static ImageIcon synapseWeightless;
	private static ImageIcon synapseNEAT;
	private static ImageIcon synapsePartial;
	
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
			Icons.synapseNEAT = new ImageIcon(Icons.class.getResource("/resource/synapseNEAT.png"));
			Icons.synapsePartial = new ImageIcon(Icons.class.getResource("/resource/synapsePartial.png"));
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

	public static ImageIcon getSynapseNEAT() {
		loadIcons();
		return synapseNEAT;
	}

	public static ImageIcon getSynapsePartial() {
		loadIcons();
		return synapsePartial;
	}
	
	
	
	
	
}
