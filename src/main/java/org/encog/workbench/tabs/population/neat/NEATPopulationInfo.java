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
package org.encog.workbench.tabs.population.neat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.encog.ml.ea.genome.Genome;
import org.encog.ml.genetic.MLMethodGenome;
import org.encog.neural.neat.NEATPopulation;
import org.encog.neural.neat.training.NEATGenome;
import org.encog.util.Format;
import org.encog.workbench.WorkbenchFonts;

public class NEATPopulationInfo extends JPanel {

	
	NEATPopulation population;
	
	public NEATPopulationInfo(NEATPopulation population)
	{
		this.population = population;
	}
	
	public void paint(Graphics g)
	{
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getWidth()-1, getHeight()-1);
		g.setColor(Color.black);
		g.drawRect(0, 0, getWidth()-1, getHeight()-1);
		g.setFont(WorkbenchFonts.getTitle2Font());
		FontMetrics fm = g.getFontMetrics();
		int y = fm.getHeight();
		g.drawString("Maximum Population Count:", 20, y);
		//g.drawString("Old Age Threshold:", 300, y);
		g.drawString("NEAT Act. Function:", 580, y);
		y+=fm.getHeight();
		g.drawString("Current Population Count:", 20, y);
		//g.drawString("Old Age Penalty:", 300, y);
		g.drawString("Input Count:", 580, y);
		y+=fm.getHeight();
		g.drawString("Species Count:", 20, y);
		//g.drawString("Youth Age Threshold:", 300, y);
		g.drawString("Output Count:", 580, y);
		y+=fm.getHeight();
		g.drawString("Innovation Count:", 20, y);
		//g.drawString("Youth Bonus:", 300, y);
		g.drawString("Cycles:", 580, y);
		y+=fm.getHeight();
		g.drawString("Population Type:", 20, y);
		g.drawString("Survival Rate:", 300, y);		
		y+=fm.getHeight();
		g.drawString("Best Genome Score:", 20, y);
		
		int populationSize = 0;
		int speciesSize = 0;
		int innovationsSize = 0;
		double bestScore = 0;
		
		populationSize = population.flatten().size();
		
		if( population.getSpecies()!=null)
			speciesSize = population.getSpecies().size();
		
		if( population.getInnovations()!=null )
			innovationsSize = population.getInnovations().getInnovations().size();
		
		String type = "Neat";
		
		y = fm.getHeight();
		g.setFont(WorkbenchFonts.getTextFont());
		g.drawString(Format.formatInteger(population.getPopulationSize()), 200, y);
		//g.drawString(Format.formatInteger(population.getOldAgeThreshold()), 450, y);
		String af;
		if( population.isHyperNEAT() ) {
			af = "HyperNEAT";
		} else {
			af = population.getActivationFunctions().getList().get(0).getObj().getClass().getSimpleName();
		}
		
		
		g.drawString(af, 730, y);
		y+=fm.getHeight();
		g.drawString(Format.formatInteger(populationSize), 200, y);
		//g.drawString(Format.formatPercent(population.getOldAgePenalty()), 450, y);
		g.drawString(Format.formatInteger(population.getInputCount()), 730, y);
		y+=fm.getHeight();
		g.drawString(Format.formatInteger(speciesSize), 200, y);
		///g.drawString(Format.formatInteger(population.getYoungBonusAgeThreshold()), 450, y);
		g.drawString(Format.formatInteger(population.getOutputCount()), 730, y);
		y+=fm.getHeight();
		g.drawString(Format.formatInteger(innovationsSize), 200, y);
		///g.drawString(Format.formatPercent(population.getYoungScoreBonus()), 450, y);
		g.drawString(Format.formatInteger(population.getActivationCycles()), 730, y);
		y+=fm.getHeight();
		g.drawString(type, 200, y);
		g.drawString(Format.formatPercent(population.getSurvivalRate()), 450, y);
		
		y+=fm.getHeight();
		g.drawString(Format.formatDouble(bestScore,2), 200, y);		
	}
	
	public Dimension getPreferredSize()
	{
		return new Dimension(620,100);
	}
}
