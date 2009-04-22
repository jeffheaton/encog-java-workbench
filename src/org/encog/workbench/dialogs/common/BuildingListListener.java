package org.encog.workbench.dialogs.common;

public interface BuildingListListener {
	public void add(BuildingListField list, int index);
	public void edit(BuildingListField list,int index);
	public void del(BuildingListField list,int index);
}
