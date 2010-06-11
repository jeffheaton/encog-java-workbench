/*
 * Encog(tm) Workbench v2.5
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
 */

package org.encog.workbench.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.encog.neural.data.NeuralData;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.CreateDataSet;
import org.encog.workbench.dialogs.select.SelectDialog;
import org.encog.workbench.dialogs.select.SelectItem;
import org.encog.workbench.frames.document.EncogDocumentFrame;
import org.encog.workbench.util.ImportExportUtility;

public class ImportExport {
	
	public static void performImport(final Object obj) {

		final JFrame frame = EncogWorkBench.getInstance().getMainWindow();
		final JFileChooser fc = new JFileChooser();
		fc.addChoosableFileFilter(EncogDocumentFrame.CSV_FILTER);
		final int result = fc.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			BasicNeuralDataSet set = (BasicNeuralDataSet) obj;
			boolean clear = false;
			boolean addit = false;

			if (obj != null) {
				final int o = JOptionPane
						.showConfirmDialog(
								frame,
								"Do you wish to delete information already in this result set?",
								"Delete", JOptionPane.YES_NO_CANCEL_OPTION);

				if (o == JOptionPane.CANCEL_OPTION) {
					return;
				} else if (o == JOptionPane.YES_OPTION) {
					clear = true;
				}
			} else {
				final CreateDataSet dialog = new CreateDataSet(frame);
				if (!dialog.process()) {
					return;
				}

				set = new BasicNeuralDataSet();
				NeuralData input = null;
				NeuralData ideal = null;
				input = new BasicNeuralData(dialog.getInputSize());
				if (dialog.getIdealSize() > 0) {
					ideal = new BasicNeuralData(dialog.getIdealSize());
				}
				set.add(input, ideal);
				set.setName(dialog.getName());
				set.setDescription(dialog.getDescription());
				clear = true;
				addit = true;
			}

			try {
				ImportExportUtility.importCSV(set, fc.getSelectedFile()
						.toString(), clear);
				if (addit) {
					EncogWorkBench.getInstance().getCurrentFile().add(set.getName(), set);
					EncogWorkBench.getInstance().getMainWindow().redraw();
				}
			} catch (final Exception e) {
				JOptionPane.showMessageDialog(frame, e.getMessage(),
						"Can't Import File", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public static void performExport(final Object obj) {
		final JFrame frame = EncogWorkBench.getInstance().getMainWindow();
		if (obj instanceof BasicNeuralDataSet) {
			SelectItem itemCSV, itemXML;
			final List<SelectItem> list = new ArrayList<SelectItem>();
			list.add(itemCSV = new SelectItem("Export CSV"));
			list.add(itemXML = new SelectItem("Export XML (EG format)"));
			final SelectDialog dialog = new SelectDialog(frame, list);
			if( !dialog.process() )
				return;
			
			SelectItem result = dialog.getSelected();

			final JFileChooser fc = new JFileChooser();

			if (result == itemCSV) {
				fc.setFileFilter(EncogDocumentFrame.CSV_FILTER);
			} else {
				fc.setFileFilter(EncogDocumentFrame.ENCOG_FILTER);
			}

			final int r = fc.showSaveDialog(frame);

			if (r != JFileChooser.APPROVE_OPTION) {
				return;
			}

			try {

				if (result == itemCSV) {
					ImportExportUtility.exportCSV((NeuralDataSet) obj, fc
							.getSelectedFile().toString());
				} else if (result == itemXML) {
					ImportExportUtility.exportXML((BasicNeuralDataSet) obj, fc
							.getSelectedFile().toString());
				}
				JOptionPane.showMessageDialog(frame, "Export succssful.",
						"Complete", JOptionPane.INFORMATION_MESSAGE);
			} catch (final IOException e) {
				JOptionPane.showMessageDialog(frame, e.getMessage(),
						"Can't Export File", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
