/*
 * Encog Workbench v2.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2009, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of 
 * individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

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
