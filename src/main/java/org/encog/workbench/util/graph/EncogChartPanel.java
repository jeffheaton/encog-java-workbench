/*
 * Encog(tm) Workbench v3.3
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-workbench
 *
 * Copyright 2008-2014 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package org.encog.workbench.util.graph;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.swing.JFileChooser;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.encog.util.file.FileUtil;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.WorkBenchError;
import org.encog.workbench.dialogs.SaveImageDialog;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ExtensionFileFilter;
import org.w3c.dom.DOMImplementation;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.FontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

public class EncogChartPanel extends ChartPanel {

	public EncogChartPanel(JFreeChart chart) {
		super(chart);
	}

	public EncogChartPanel(JFreeChart chart, boolean properties, boolean save,
			boolean print, boolean zoom, boolean tooltips) {
		super(chart, properties, save, print, zoom, tooltips);
	}

	public EncogChartPanel(JFreeChart chart, boolean useBuffer) {
		super(chart, useBuffer);
	}

	public EncogChartPanel(JFreeChart chart, int width, int height,
			int minimumDrawWidth, int minimumDrawHeight, int maximumDrawWidth,
			int maximumDrawHeight, boolean useBuffer, boolean properties,
			boolean copy, boolean save, boolean print, boolean zoom,
			boolean tooltips) {
		super(chart, width, height, minimumDrawWidth, minimumDrawHeight,
				maximumDrawWidth, maximumDrawHeight, useBuffer, properties, copy, save,
				print, zoom, tooltips);
	}

	public EncogChartPanel(JFreeChart chart, int width, int height,
			int minimumDrawWidth, int minimumDrawHeight, int maximumDrawWidth,
			int maximumDrawHeight, boolean useBuffer, boolean properties,
			boolean save, boolean print, boolean zoom, boolean tooltips) {
		super(chart, width, height, minimumDrawWidth, minimumDrawHeight,
				maximumDrawWidth, maximumDrawHeight, useBuffer, properties, save,
				print, zoom, tooltips);
	}
	
    /**
     * Opens a file chooser and gives the user an opportunity to save the chart
     * in PNG format.
     *
     * @throws IOException if there is an I/O error.
     */
    public void doSaveAs() throws IOException {

    	SaveImageDialog dialog = new SaveImageDialog(EncogWorkBench.getInstance().getMainWindow());
    	
    	dialog.getImageWidth().setValue(640);
    	dialog.getImageHeight().setValue(480);
    	
    	if( dialog.process() ) {
    		    	        	
            File filename = new File(dialog.getTargetFile().getValue());
            int width = dialog.getImageWidth().getValue();
            int height = dialog.getImageHeight().getValue();
            
            switch( dialog.getFileType().getSelectedIndex()) {
            	case 0:
            		filename = new File(FileUtil.forceExtension(filename.toString(), "png"));
            		ChartUtilities.saveChartAsPNG(filename, this.getChart(),
                            width,height);	
            		break;
            	case 1:
            		filename = new File(FileUtil.forceExtension(filename.toString(), "jpg"));
            		ChartUtilities.saveChartAsPNG(filename, this.getChart(),
                            width,height);	
            		break;
            	case 2:
            		filename = new File(FileUtil.forceExtension(filename.toString(), "pdf"));
            		DocumentPDF.savePDF(filename, getChart(), width, height);	
            		break;
            	case 3:
            		filename = new File(FileUtil.forceExtension(filename.toString(), "svg"));
            		DocumentSVG.saveSVG(filename, getChart(), width, height);	
            		break;
            		
            }
        }

    }
}
