package org.encog.workbench.tabs.visualize.epl;

import org.encog.ml.prg.util.MappedNode;

public class EPLNodeHolder implements Comparable<EPLNodeHolder> {
	private final String id;
	private final MappedNode node;
	private static int idCounter = 0;
	
	public EPLNodeHolder(MappedNode theNode) {
		this.id = ""+(idCounter++);
		this.node = theNode;
	}
	
	public String toString() {
		return this.id;
	}
	

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	@Override
	public int compareTo(EPLNodeHolder other) {
		return this.id.compareTo(other.id);
	}
	
	public boolean equals(Object other) {
		if( other instanceof EPLNodeHolder) {
			return ((EPLNodeHolder)other).id.equals(this.id);
		}
		return false;
	}

	/**
	 * @return the node
	 */
	public MappedNode getNode() {
		return node;
	}
	
	
}
