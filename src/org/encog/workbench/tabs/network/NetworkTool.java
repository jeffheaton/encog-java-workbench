/*
 * Encog(tm) Workbench v2.6 
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

import org.encog.neural.networks.layers.Layer;

public class NetworkTool {
	
	enum Type
	{
		synapse,
		layer
	}
	
	public static final int HEIGHT = 32;
	public static final int WIDTH = 64;
	
	private final String name;
	private final Type type;
	private final Class classType;
	private int x;
	private int y;
	private int width;
	private int height;
	private ImageIcon icon;
	
	public NetworkTool(String name, ImageIcon icon, Type type, Class classType)
	{
		this.name = name;
		this.type = type;
		this.icon = icon;
		this.classType = classType;
	}

	public String getName() {
		return this.name;
	}

	public Type getType() {
		return this.type;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean contains(int x, int y) {
		if( (x>this.x) && (x<(this.x+this.width)) &&
			(y>this.y) && (y<(this.y+this.height)) )
			return true;
		else
			return false;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public Class getClassType() {
		return classType;
	}
	
}
