package org.encog.workbench.process.indicator;

import org.encog.cloud.indicator.IndicatorFactory;
import org.encog.cloud.indicator.IndicatorListener;

public class WorkbenchIndicatorFactory implements IndicatorFactory {

	@Override
	public String getName() {
		return "workbench";
	}

	@Override
	public IndicatorListener create() {
		return new WorkbenchErrorIndicator("Workbench connected, but no indicators ready.");
	}

}
