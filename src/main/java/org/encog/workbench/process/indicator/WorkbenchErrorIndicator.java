package org.encog.workbench.process.indicator;

import org.encog.cloud.indicator.basic.BasicIndicator;
import org.encog.cloud.indicator.server.IndicatorLink;
import org.encog.cloud.indicator.server.IndicatorPacket;

public class WorkbenchErrorIndicator extends BasicIndicator {

	private String errorMessage;
	
	public WorkbenchErrorIndicator(String theErrorMessage) {
		super(false);
	}

	@Override
	public void notifyPacket(IndicatorPacket packet) {
		String[] args = { this.errorMessage };
		this.getLink().writePacket(IndicatorLink.PACKET_ERROR, args);
		
	}

	@Override
	public void notifyTermination() {
		
	}

}
