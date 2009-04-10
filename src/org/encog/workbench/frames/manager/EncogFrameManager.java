/*
 * Encog Workbench v1.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008, Heaton Research Inc., and individual contributors.
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
package org.encog.workbench.frames.manager;

import java.util.ArrayList;
import java.util.List;

import org.encog.neural.persist.DirectoryEntry;
import org.encog.neural.persist.EncogPersistedObject;
import org.encog.workbench.EncogWorkBench;

public class EncogFrameManager {
	private final List<EncogCommonFrame> frames = new ArrayList<EncogCommonFrame>();
	private final EncogCommonFrame owner;

	public EncogFrameManager(final EncogCommonFrame owner) {
		this.owner = owner;
	}

	public void add(final EncogCommonFrame frame) {
		this.frames.add(frame);
		frame.setParent(getOwner());
	}

	@SuppressWarnings("unchecked")
	public boolean checkBeforeOpen(final DirectoryEntry object,
			final Class c) {
		final EncogCommonFrame existing = find(object);
		if (existing == null) {
			return true;
		}

		if (c.getName().equals(existing.getClass().getName())) {
			existing.toFront();
			return false;
		}

		EncogWorkBench.displayMessage("Already Viewing that Object",
				"You must close the window \n\"" + existing.getTitle()
						+ "\"\n before this window can be opened.");
		return false;
	}

	public EncogCommonFrame find(final DirectoryEntry object) {
		for (final EncogCommonFrame frame : this.frames) {
			if (frame.getEncogObject() == object) {
				return frame;
			}
		}
		return null;
	}

	/**
	 * @return the frames
	 */
	public List<EncogCommonFrame> getFrames() {
		return this.frames;
	}

	/**
	 * @return the owner
	 */
	public EncogCommonFrame getOwner() {
		return this.owner;
	}

	public void remove(final EncogCommonFrame frame) {
		this.frames.remove(frame);
	}

}
