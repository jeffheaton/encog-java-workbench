package org.encog.workbench.util;

import java.awt.event.MouseEvent;

public class MouseUtil {
	public static boolean isRightClick(MouseEvent e)
	{
		if( (e.getButton() == MouseEvent.BUTTON3) ||
			(e.isControlDown() && e.getButton()==MouseEvent.BUTTON1)	) 
		{
			return true;
		}
		
		return false;
	}
}
