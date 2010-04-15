/*
 * Encog(tm) Workbench v2.4
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
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
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
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
