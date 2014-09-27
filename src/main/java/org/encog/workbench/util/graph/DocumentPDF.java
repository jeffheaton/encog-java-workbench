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
import java.awt.geom.Rectangle2D;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.encog.workbench.WorkBenchError;
import org.jfree.chart.JFreeChart;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.FontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

public class DocumentPDF {
    public static void writeChartAsPDF(OutputStream out,
    		JFreeChart chart,
    		int width,
    		int height,
    		FontMapper mapper) throws IOException {
    		Rectangle pagesize = new Rectangle(width, height);
    		Document document = new Document(pagesize, 50, 50, 50, 50);
    		try {
    		PdfWriter writer = PdfWriter.getInstance(document, out);
    		document.addAuthor("JFreeChart");
    		document.addSubject("Demonstration");
    		document.open();
    		PdfContentByte cb = writer.getDirectContent();
    		PdfTemplate tp = cb.createTemplate(width, height);
    		Graphics2D g2 = tp.createGraphics(width, height, mapper);
    		Rectangle2D r2D = new Rectangle2D.Double(0, 0, width, height);
    		chart.draw(g2, r2D);
    		g2.dispose();
    		cb.addTemplate(tp, 0, 0);
    		}
    		catch (DocumentException de) {
    		System.err.println(de.getMessage());
    		}
    }

    public static void savePDF(File filename, JFreeChart chart, int width, int height) {
    	try {
    	// step 1
        Document document = new Document(new Rectangle(width, height));
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3
        document.open();
        // step 4
        PdfContentByte canvas = writer.getDirectContent();
        Graphics2D g2 = canvas.createGraphics(width, height);
        Rectangle2D area = new Rectangle2D.Double(0, 0, width, height);
        chart.draw(g2, area);
        g2.dispose();
        // step 5
        document.close();
    	} catch(DocumentException ex) {
    		throw new WorkBenchError(ex);
    	}
    	 catch(IOException ex) {
     		throw new WorkBenchError(ex);
     	}

    }

}
