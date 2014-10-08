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
package org.encog.workbench.tabs.visualize.epl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import org.encog.ml.prg.EncogProgram;
import org.encog.parse.expression.latex.RenderLatexExpression;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.tabs.EncogCommonTab;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

public class EPLLatexTab extends EncogCommonTab {

	private BufferedImage image;
	
	public EPLLatexTab(final EncogProgram prg) {
		super(null);
		
		try {
			RenderLatexExpression render = new RenderLatexExpression();
			String latex = render.render(prg);
			try {
			TeXFormula formula = new TeXFormula(latex);
			TeXIcon icon = formula
					.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
			icon.setInsets(new Insets(5, 5, 5, 5));

			this.image = new BufferedImage(icon.getIconWidth(),
					icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = image.createGraphics();
			g2.setColor(Color.white);
			g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
			JLabel jl = new JLabel();
			jl.setForeground(new Color(0, 0, 0));
			icon.paintIcon(jl, g2, 0, 0);
			} catch(Exception ex) {
				throw new UnsupportedOperationException("Can't parse LATEX: " + latex+"\nFrom: " + prg.toString(), ex);
			}

		} catch (Exception ex) {
			EncogWorkBench.displayError("Error", ex);		
		}
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(this.image, 0, 0, null );
	}

	@Override
	public String getName() {
		return "Structure: " + this.getEncogObject().getName();
	}
}
