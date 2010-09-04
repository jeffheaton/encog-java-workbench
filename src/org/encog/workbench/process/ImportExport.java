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

import java.io.File;

import org.encog.neural.data.buffer.BinaryDataLoader;
import org.encog.neural.data.buffer.codec.CSVDataCODEC;
import org.encog.neural.data.buffer.codec.DataSetCODEC;
import org.encog.util.csv.CSVFormat;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.ImportExportDialog;
import org.encog.workbench.dialogs.binary.DialogBinary2External;
import org.encog.workbench.dialogs.binary.DialogCSV;
import org.encog.workbench.dialogs.binary.DialogExternal2Binary;
import org.encog.workbench.util.ExtensionFilter;
import org.encog.workbench.util.TaskComplete;

public class ImportExport {
	
	public static void performBin2External() {
		DialogBinary2External dialog  = new DialogBinary2External(EncogWorkBench.getInstance().getMainWindow());
		if(dialog.process())
		{
			File binaryFile = new File(dialog.getBinaryFile().getValue());
			File externFile = new File(dialog.getExternalFile().getValue());
			int fileType = dialog.getFileType().getSelectedIndex();
			DataSetCODEC codec;
			BinaryDataLoader loader;
			
			if( fileType==0)
			{
				DialogCSV dialog2 = new DialogCSV(EncogWorkBench.getInstance().getMainWindow());
				if( dialog2.process() ) {
					boolean headers = dialog2.getHeaders().getValue();
					CSVFormat format;
					
					if( dialog2.getDecimalComma().getValue() )
						format = CSVFormat.DECIMAL_COMMA;
					else
						format = CSVFormat.DECIMAL_POINT;
					
					codec = new CSVDataCODEC(externFile,format);
					loader = new BinaryDataLoader(codec);
					new ImportExportDialog(loader,binaryFile,false).setVisible(true);
				}
			}			
		}
	}

	public static File performExternal2Bin(File inBinaryFile, TaskComplete done) {
		
		File binaryFile = inBinaryFile;
		
		DialogExternal2Binary dialog  = new DialogExternal2Binary(EncogWorkBench.getInstance().getMainWindow());
		
		if( binaryFile!=null ) {
			dialog.getBinaryFile().setValue(binaryFile.toString());
		}
			
		if(dialog.process())
		{
			binaryFile = new File(dialog.getBinaryFile().getValue());
			File externFile = new File(dialog.getExternalFile().getValue());
			int fileType = dialog.getFileType().getSelectedIndex();
			int inputCount = dialog.getInputCount().getValue();
			int idealCount = dialog.getIdealCount().getValue();
			
			// no extension
			if (ExtensionFilter.getExtension(binaryFile) == null) {
				binaryFile = new File(binaryFile.getPath() + ".egb");
			}
			
			DataSetCODEC codec;
			BinaryDataLoader loader;
			
			if( fileType==0)
			{
				DialogCSV dialog2 = new DialogCSV(EncogWorkBench.getInstance().getMainWindow());
				if( dialog2.process() ) {
					boolean headers = dialog2.getHeaders().getValue();
					CSVFormat format;
					
					if( dialog2.getDecimalComma().getValue() )
						format = CSVFormat.DECIMAL_COMMA;
					else
						format = CSVFormat.DECIMAL_POINT;
					
					codec = new CSVDataCODEC(externFile,format,headers,inputCount,idealCount);
					loader = new BinaryDataLoader(codec);
					ImportExportDialog dlg = new ImportExportDialog(loader,binaryFile,true);
					dlg.process(done);
				}
			}
			return binaryFile;
		}
		else 
			return null;
	}

}
