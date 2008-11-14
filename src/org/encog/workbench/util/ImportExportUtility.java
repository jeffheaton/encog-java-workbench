package org.encog.workbench.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.encog.neural.data.NeuralData;
import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.data.basic.BasicNeuralDataPair;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.persist.EncogPersistedCollection;
import org.encog.util.ReadCSV;
import org.encog.workbench.WorkBenchError;

public class ImportExportUtility {
	public static void exportCSV(NeuralDataSet set, String filename) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(filename);
		PrintStream out = new PrintStream(fos);
		for( NeuralDataPair pair: set)
		{
			StringBuilder line = new StringBuilder();
			
			NeuralData input = pair.getInput();
			NeuralData ideal = pair.getIdeal();
			
			// write input
			if( input!=null )
			{
				for(int i=0;i<input.size();i++)
				{
					if( i!=0 )
					{
						line.append(',');
					}
					line.append(""+input.getData(i));
				}
			}
			
			// write ideal
			if( ideal!=null )
			{
				for(int i=0;i<ideal.size();i++)
				{
					line.append(',');					
					line.append(""+ideal.getData(i));
				}
			}
			out.println(line.toString());
		}
		out.close();
		fos.close();
	}

	public static void exportXML(BasicNeuralDataSet obj, String filename) {
		EncogPersistedCollection save = new EncogPersistedCollection();
		save.add(obj);
		save.save(filename);
	}
	
	public static void importCSV(BasicNeuralDataSet set, String filename,boolean clear) throws IOException
	{
		int inputSize = set.getInputSize();
		int idealSize = set.getIdealSize();
		
		if( clear )
		{
			set.getData().clear();
		}
		
		int line = 0;
		ReadCSV csv = new ReadCSV(filename,false,',');
		while(csv.next())
		{
			line ++;
			BasicNeuralData input=null,ideal=null;
			
			if( (inputSize+idealSize)!=csv.getColumnCount() )
			{
				throw new WorkBenchError("Line #" + line + " has " + csv.getColumnCount() + " columns, but dataset expects " + (inputSize+idealSize) + " columns." );
			}
			
			if( inputSize>0 )
				input = new BasicNeuralData(inputSize);
			if( idealSize>0 )
				ideal = new BasicNeuralData(idealSize);
			
			BasicNeuralDataPair pair = new BasicNeuralDataPair(input,ideal);
			int index = 0;			
			
			for(int i=0;i<inputSize;i++)
			{
				input.setData(i, csv.getDouble(index++));
			}
			for(int i=0;i<idealSize;i++)
			{
				ideal.setData(i, csv.getDouble(index++));
			}
			
			set.add(pair);
		}
		csv.close();
	}
	
}
