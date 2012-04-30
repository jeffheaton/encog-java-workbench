package org.encog.workbench.process.indicator;

import java.io.File;
import java.util.List;

import org.encog.cloud.indicator.IndicatorFactory;
import org.encog.cloud.indicator.IndicatorListener;
import org.encog.cloud.indicator.basic.DownloadIndicator;

public class WorkbenchIndicatorFactory implements IndicatorFactory {

	private DownloadIndicator downloadIndicator;
	
	@Override
	public String getName() {
		return "workbench";
	}

	@Override
	public IndicatorListener create() {
		IndicatorListener result = null;
		
		if( downloadIndicator == null ) {
			result = new WorkbenchErrorIndicator("Workbench connected, but no indicators ready."); 
		} else {
			result = this.downloadIndicator;
			this.downloadIndicator = null;
		}
		return result;
	}

	public DownloadIndicator prepareDownload(File theTargetFile, List<String> list) {
		this.downloadIndicator = new DownloadIndicator(theTargetFile);
		for(String line : list)
		{
			this.downloadIndicator.requestData(line);
		}
		return this.downloadIndicator;
		
	}

	public void clear() {
		this.downloadIndicator = null;
		
	}

}
