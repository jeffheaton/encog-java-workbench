package org.encog.workbench.tabs.visualize.scatter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.encog.app.analyst.EncogAnalyst;
import org.encog.app.analyst.script.DataField;
import org.encog.app.analyst.script.prop.ScriptProperties;
import org.encog.util.csv.CSVFormat;
import org.encog.util.csv.ReadCSV;
import org.encog.workbench.WorkBenchError;

public class ScatterFile {
	
	private final EncogAnalyst analyst;
	private final Map<String,Integer> axisMapping = new HashMap<String,Integer>();
	private int targetIndex;
	private Map<String,List<double[]>> data = new HashMap<String,List<double[]>>();
	private DataField targetField;
	private List<String> axis;
	
	public ScatterFile(EncogAnalyst analyst, String target, List<String> axis) {
		this.analyst = analyst;
		this.axis = axis;
		buildMappings(target, axis);
		readRawFile();
	}
	
	private boolean isAxis(String name, List<String> axis) {
		for(String field: axis) {
			if( field.equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
	private void buildMappings(String target, List<String> axis) {		
		DataField[] fields = this.analyst.getScript().getFields();
		
		this.targetField = this.analyst.getScript().findDataField(target); 
		
		if( targetField==null ) {
			throw new WorkBenchError("Can't find target class: " + target);
		}
		this.targetIndex = this.analyst.getScript().findDataFieldIndex(targetField);
		
		for(int index =0; index<fields.length;index++) {
			DataField field = fields[index];
			if( isAxis(field.getName(),axis)) {
				this.axisMapping.put(field.getName().toLowerCase(), index);
			}			
		}
	}

	private void readRawFile() {
		ScriptProperties prop = this.analyst.getScript().getProperties();
		
		// get filenames, headers & format
		String sourceID = prop.getPropertyString(
				ScriptProperties.HEADER_DATASOURCE_rawFile);

		File sourceFile = this.analyst.getScript().resolveFilename(sourceID);

		CSVFormat inputFormat = this.analyst.getScript().determineInputFormat(sourceID);
		
		boolean headers = this.analyst.getScript().expectInputHeaders(sourceID);
	
		int rowSize = this.axisMapping.size();
		
		// read the file
		ReadCSV csv = new ReadCSV(sourceFile.toString(),headers,inputFormat);
		
		while(csv.next() ) {
			double[] row = new double[rowSize];
			List<double[]> dataList;
			
			// find a list for this class
			String cls = csv.get(this.targetIndex).toLowerCase();
			if( this.data.containsKey(cls) ) {
				dataList = this.data.get(cls);
			} else {
				dataList = new ArrayList<double[]>();
				this.data.put(cls, dataList);
			}
			
			// read the data row
			int rowIndex = 0;
			for(String key: this.axis) {
				int index = this.axisMapping.get(key);
				double d = csv.getDouble(index);
				row[rowIndex++] = d;
			}
			
			// store the data row
			dataList.add(row);
		}
		
		csv.close();
	}

	/**
	 * @return the axisMapping
	 */
	public Map<String, Integer> getAxisMapping() {
		return axisMapping;
	}

	/**
	 * @return the axis
	 */
	public List<String> getAxis() {
		return axis;
	}

	/**
	 * @return the targetIndex
	 */
	public int getTargetIndex() {
		return targetIndex;
	}

	/**
	 * @return the targetField
	 */
	public DataField getTargetField() {
		return targetField;
	}
	
	public List<double[]> getSeries(String name) {
		return this.data.get(name.toLowerCase());
	}
	
	public List<double[]> getSeries(int index) {
		String name = this.targetField.getClassMembers().get(index).getName();
		return this.data.get(name.toLowerCase());
	}

	public double findMin(int index) {
		double result = Double.POSITIVE_INFINITY;
		
		for(String key: this.data.keySet() ) {
			List<double[]> list = this.data.get(key);
			for(double[] array: list) {
				result = Math.min(result, array[index]);
			}
		}
		
		return result;
	}

	public double findMax(int index) {
		double result = Double.NEGATIVE_INFINITY;
		
		for(String key: this.data.keySet() ) {
			List<double[]> list = this.data.get(key);
			for(double[] array: list) {
				result = Math.max(result, array[index]);
			}
		}
		
		return result;
	}

}
