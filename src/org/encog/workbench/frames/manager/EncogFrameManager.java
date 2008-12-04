package org.encog.workbench.frames.manager;

import java.util.ArrayList;
import java.util.List;

import org.encog.neural.persist.EncogPersistedObject;
import org.encog.workbench.EncogWorkBench;


public class EncogFrameManager {
	private List<EncogCommonFrame> frames = new ArrayList<EncogCommonFrame>();
	private EncogCommonFrame owner;
	
	public EncogFrameManager(EncogCommonFrame owner)
	{
		this.owner = owner;
	}
	
	public EncogCommonFrame find(EncogPersistedObject object) {
		for (EncogCommonFrame frame : this.frames) {
			if( frame.getEncogObject()==object )
				return frame;			
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public boolean checkBeforeOpen(EncogPersistedObject object, Class c)
	{
		EncogCommonFrame existing = find(object);
		if( existing==null )
			return true;
		
		if( c.getName().equals(existing.getClass().getName()) )
		{
			existing.toFront();
			return false;
		}
		
		EncogWorkBench.displayMessage("Already Viewing that Object", "You must close the window \n\""+existing.getTitle()+"\"\n before this window can be opened.");
		return false;
	}


	public void add(EncogCommonFrame frame) {
		frames.add(frame);
		frame.setParent(this.getOwner());
	}

	public void remove(EncogCommonFrame frame) {
		frames.remove(frame);
	}

	/**
	 * @return the frames
	 */
	public List<EncogCommonFrame> getFrames() {
		return frames;
	}

	/**
	 * @return the owner
	 */
	public EncogCommonFrame getOwner() {
		return owner;
	}
	
	
	
}
