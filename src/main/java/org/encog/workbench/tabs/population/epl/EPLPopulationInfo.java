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
package org.encog.workbench.tabs.population.epl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.encog.ml.prg.train.PrgPopulation;
import org.encog.util.Format;
import org.encog.workbench.WorkbenchFonts;

public class EPLPopulationInfo extends JPanel {

	
	private PrgPopulation population;
	
	public EPLPopulationInfo(PrgPopulation thePopulation)
	{
		this.population = thePopulation;
	}
	
	public void paint(Graphics g)
	{
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getWidth()-1, getHeight()-1);
		g.setColor(Color.black);
		g.drawRect(0, 0, getWidth()-1, getHeight()-1);
		g.setFont(WorkbenchFonts.getTitle2Font());
		FontMetrics fm = g.getFontMetrics();
		// 20 300 580
		int y = fm.getHeight();
		g.drawString("Maximum Population Count:", 20, y);
		g.drawString("Input Count:", 300, y);
		y+=fm.getHeight();
		g.drawString("Current Population Count:", 20, y);
		g.drawString("Output Count:", 300, y);
		y+=fm.getHeight();
		g.drawString("Species Count:", 20, y);
		y+=fm.getHeight();
		g.drawString("Max Size:", 20, y);
		y+=fm.getHeight();
		g.drawString("Best Genome Score:", 20, y);
		
		int populationSize = population.flatten().size();
		String bestScore = "n/a";
		
		if( population.getBestGenome() !=null ) {
			double score = population.getBestGenome().getScore();
			if( !Double.isInfinite(score) && !Double.isNaN(score) ) {
				bestScore = Format.formatDouble(population.getBestGenome().getScore(),2);
			}
		}
		
		y = fm.getHeight();
		g.setFont(WorkbenchFonts.getTextFont());
		g.drawString(Format.formatInteger(population.getPopulationSize()), 200, y);
		g.drawString(Format.formatInteger(population.getContext().getDefinedVariables().size()), 480, y);
		y+=fm.getHeight();
		g.drawString(Format.formatInteger(populationSize), 200, y);
		g.drawString(Format.formatInteger(1), 480, y);
		y+=fm.getHeight();
		g.drawString(Format.formatInteger(population.getSpecies().size()), 200, y);
		y+=fm.getHeight();
		g.drawString(Format.formatInteger(population.getMaxIndividualSize()), 200, y);
		y+=fm.getHeight();
		g.drawString(bestScore, 200, y);
		
	}
	
	public Dimension getPreferredSize()
	{
		return new Dimension(620,100);
	}
}
