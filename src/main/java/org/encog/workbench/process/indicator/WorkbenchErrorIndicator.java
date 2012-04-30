package org.encog.workbench.process.indicator;

import org.encog.cloud.indicator.basic.BasicIndicator;
import org.encog.cloud.indicator.server.IndicatorLink;
import org.encog.cloud.indicator.server.IndicatorPacket;

public class WorkbenchErrorIndicator extends BasicIndicator {	
	
	public WorkbenchErrorIndicator(String theErrorMessage) {
		super(false);
		this.setErrorMessage(theErrorMessage);
	}

	@Override
	public void notifyPacket(IndicatorPacket packet) {
		String[] args = { getErrorMessage() };
		this.getLink().writePacket(IndicatorLink.PACKET_ERROR, args);
		
	}

	@Override
	public void notifyTermination() {
		
	}

}
