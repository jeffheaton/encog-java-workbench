/*
 * Encog(tm) Workbench v2.5
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

package org.encog.workbench.frames.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.tree.DefaultTreeModel;

import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogMemoryCollection;


public class EncogCollectionModel extends DefaultTreeModel {


    public EncogCollectionModel(EncogObjectDirectory root) {
    	super(root);
    }

    /**
     * <code>isCellEditable</code> is invoked by the JTreeTable to determine
     * if a particular entry can be added. This is overridden to return true
     * for the first column, assuming the node isn't the root, as well as
     * returning two for the second column if the node is a BookmarkEntry.
     * For all other columns this returns false.
     */
    public boolean isCellEditable(Object node, int column) {
	switch (column) {
	case 0:
	    // Allow editing of the name, as long as not the root.
	    return (node != getRoot());
	case 1:
	    // Allow editing of the location, as long as not a
	    // directory
	    return (node instanceof EncogCollectionEntry);
	default:
	    // Don't allow editing of the date fields.
	    return false;
	}
    }
    
    public void invalidate(EncogMemoryCollection encog)
    {
    	EncogObjectDirectory root = (EncogObjectDirectory)this.getRoot();
    	
    	root.removeAllChildren();
    	
    	Map<String,Set<DirectoryEntry>> sorted = new HashMap();
    	
    	for(DirectoryEntry entry: encog.getDirectory() )
    	{
    		Set<DirectoryEntry> list = sorted.get(entry.getType());
    		if( list==null )
    		{
    			list = new TreeSet<DirectoryEntry>();
    			sorted.put(entry.getType(), list);
    		}
    		
    		list.add(entry);
    	}    	
    	
    	root.setName("Encog");
 
    	Set<String> dirsSorted = new TreeSet<String>();
    	dirsSorted.addAll(sorted.keySet());
    	
    	for(String key: dirsSorted)
    	{
    		EncogObjectDirectory dir = new EncogObjectDirectory(key); 
    		root.add(dir);
    		Set<DirectoryEntry> list = sorted.get(key);
    		
    		for(DirectoryEntry entry: list)
    		{
    			EncogCollectionEntry node = new EncogCollectionEntry(entry);
    			dir.add(node);
    		}
    	}    	
    }
}

