package org.encog.workbench.frames.document;

import java.util.Date;

import javax.swing.tree.DefaultMutableTreeNode;

public class EncogObjectDirectory extends DefaultMutableTreeNode {
/** Dates created. */
private Date created;

public EncogObjectDirectory(String name) {
    super(name);
}

public void setName(String name) {
    setUserObject(name);
}

public String getName() {
    return (String)getUserObject();
}

public void setCreated(Date date) {
    this.created = date;
}

public Date getCreated() {
    return created;
}
}

