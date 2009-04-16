package org.encog.workbench.frames.network;

import javax.swing.ImageIcon;

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
