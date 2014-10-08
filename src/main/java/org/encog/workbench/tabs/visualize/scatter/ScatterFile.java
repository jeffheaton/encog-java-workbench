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
package org.encog.workbench.tabs.visualize.scatter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.encog.app.analyst.EncogAnalyst;
import org.encog.app.analyst.script.AnalystClassItem;
import org.encog.app.analyst.script.DataField;
import org.encog.app.analyst.script.prop.ScriptProperties;
import org.encog.util.Format;
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
	private List<String> series = new ArrayList<String>();
	private double regressionSeriesSize;
	private double[] regressionSeriesPoint;
	
	
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
			throw new WorkBenchError("Can't find target field: " + target);
		}
		this.targetIndex = this.analyst.getScript().findDataFieldIndex(targetField);
		
		// determine series names
		if( targetField.isClass() ) {
			for(AnalystClassItem cls : targetField.getClassMembers()) {
				this.series.add(cls.getName().toLowerCase());
			}
		} else {
			this.regressionSeriesPoint = new double[10];
			double totalWidth = targetField.getMax() - targetField.getMin();
			this.regressionSeriesSize = totalWidth/12;
			double current = targetField.getMin()+this.regressionSeriesSize;
			for(int i=0;i<10;i++) {
				StringBuilder s = new StringBuilder();				
				s.append(Format.formatDouble(current, 1));
				this.data.put(s.toString().toLowerCase(), new ArrayList<double[]>());
				this.series.add(s.toString());
				this.regressionSeriesPoint[i] = current;
				current+=this.regressionSeriesSize;
			}
		}
		
		// build index to mappings
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
				ScriptProperties.HEADER_DATASOURCE_RAW_FILE);

		File sourceFile = this.analyst.getScript().resolveFilename(sourceID);

		CSVFormat inputFormat = this.analyst.getScript().determineFormat();
		
		boolean headers = this.analyst.getScript().expectInputHeaders(sourceID);
	
		int rowSize = this.axisMapping.size();
		
		boolean regression = !this.targetField.isClass();
		
		// read the file
		ReadCSV csv = new ReadCSV(sourceFile.toString(),headers,inputFormat);
		
		while(csv.next() ) {
			double[] row = new double[rowSize];
			List<double[]> dataList;
			
			// find a list for this class
			String cls = "?";
			
			if( regression ) {
				double d = csv.getDouble(targetIndex);
				for(int i=this.series.size()-1;i>=0;i--) {
					if( d>this.regressionSeriesPoint[i] ) {
						cls = this.series.get(i);
						break;
					}
				}
			} else {
				cls = csv.get(this.targetIndex); 
				cls = cls.toLowerCase();
			}
						
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
		String name = this.series.get(index).toLowerCase();
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

	public int getSeriesCount() {
		return this.series.size();
	}

	public List<String> getSeries() {
		return this.series;
	}

	public boolean isRegression() {
		return !this.targetField.isClass();
	}

}
