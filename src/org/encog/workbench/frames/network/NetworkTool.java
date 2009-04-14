package org.encog.workbench.frames.network;

public class NetworkTool {
	
	enum Type
	{
		synapse,
		layer
	}
	
	private final String name;
	private final Type type;
	private final Class classType;
	private int x;
	private int y;
	private int width;
	private int height;
	
	public NetworkTool(String name, Type type, Class classType)
	{
		this.name = name;
		this.type = type;
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
	
	
}
