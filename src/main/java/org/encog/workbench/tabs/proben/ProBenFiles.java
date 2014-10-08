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
package org.encog.workbench.tabs.proben;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.encog.util.csv.CSVFormat;
import org.encog.util.csv.ReadCSV;
import org.encog.util.file.FileUtil;
import org.encog.util.file.ResourceInputStream;
import org.encog.workbench.WorkBenchError;

public class ProBenFiles {
	private List<String> list = new ArrayList<String>();
	private File dir;
	private String methodName;
	private String trainingName;
	private String methodArchitecture;
	private String trainingArgs;
	
	public ProBenFiles() {
		try {
			InputStream is = ResourceInputStream
					.openResourceInputStream("org/encog/workbench/proben1/files.csv");
			final ReadCSV csv = new ReadCSV(is, false, CSVFormat.EG_FORMAT);

			while (csv.next()) {
				list.add(csv.get(0));
			}

			csv.close();
			is.close();
		} catch (IOException e) {
			throw new WorkBenchError(e);
		}
	}
	
	public List<String> getList() {
		return this.list;
	}
}
