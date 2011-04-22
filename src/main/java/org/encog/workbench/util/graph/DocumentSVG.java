package org.encog.workbench.util.graph;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.encog.workbench.WorkBenchError;
import org.jfree.chart.JFreeChart;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

public class DocumentSVG {
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

}
