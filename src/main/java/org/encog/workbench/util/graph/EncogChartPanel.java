package org.encog.workbench.util.graph;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.swing.JFileChooser;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.encog.util.file.FileUtil;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.WorkBenchError;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ExtensionFileFilter;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

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

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(EncogWorkBench.getInstance().getProjectDirectory());
        fileChooser.addChoosableFileFilter(new ExtensionFileFilter("PNG Image", ".png"));
        fileChooser.addChoosableFileFilter(new ExtensionFileFilter("JPG Image", ".jpg"));
        fileChooser.addChoosableFileFilter(new ExtensionFileFilter("PDF File", ".pdf"));
        fileChooser.addChoosableFileFilter(new ExtensionFileFilter("SVG File", ".svg"));

        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
        	
            File filename = fileChooser.getSelectedFile();
            String ext = FileUtil.getFileExt(filename);
            
            if( ext.equalsIgnoreCase("png")) {
            	ChartUtilities.saveChartAsPNG(filename, this.getChart(),
                        getWidth(), getHeight());	
            } else if( ext.equalsIgnoreCase("jpg")) {
            	ChartUtilities.saveChartAsPNG(filename, this.getChart(),
                        getWidth(), getHeight());	
            }  else if( ext.equalsIgnoreCase("pdf")) {
            	ChartUtilities.saveChartAsPNG(filename, this.getChart(),
                        getWidth(), getHeight());	
            } else if( ext.equalsIgnoreCase("svg")) {
            	saveSVG(filename, this.getChart(),
                        getWidth(), getHeight());	
            }
                       
            
            
        }

    }
    
    public static void saveSVG(File filename, JFreeChart chart, int width, int height) {
    	try {
    	// THE FOLLOWING CODE BASED ON THE EXAMPLE IN THE BATIK DOCUMENTATION...
    	// Get a DOMImplementation
    	DOMImplementation domImpl
    	= GenericDOMImplementation.getDOMImplementation();
    	// Create an instance of org.w3c.dom.Document
    	Document document = domImpl.createDocument(null, "svg", null);
    	// Create an instance of the SVG Generator
    	SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
    	// set the precision to avoid a null pointer exception in Batik 1.5
    	svgGenerator.getGeneratorContext().setPrecision(6);
    	// Ask the chart to render into the SVG Graphics2D implementation
    	chart.draw(svgGenerator, new Rectangle2D.Double(0, 0, width, height), null);
    	// Finally, stream out SVG to a file using UTF-8 character to
    	// byte encoding
    	boolean useCSS = true;
    	Writer out = new OutputStreamWriter(new FileOutputStream(filename), "UTF-8");
    	svgGenerator.stream(out, useCSS);
    	} catch(IOException ex) {
    		throw new WorkBenchError(ex);
    	}
    }

    public static void savePDF(File filename, JFreeChart chart, int width, int height) {
    	/*try {
    		
    	} catch(IOException ex) {
    		throw new WorkBenchError(ex);
    	}*/
    }
	

}
