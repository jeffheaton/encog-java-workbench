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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import org.encog.ml.prg.EncogProgram;
import org.encog.parse.expression.common.RenderCommonExpression;
import org.encog.parse.expression.rpn.RenderRPN;
import org.encog.util.Format;
import org.encog.util.HTMLReport;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.select.SelectDialog;
import org.encog.workbench.dialogs.select.SelectItem;
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.tabs.EncogCommonTab;
import org.encog.workbench.tabs.query.general.RegressionQueryTab;
import org.encog.workbench.tabs.visualize.epl.EPLLatexTab;
import org.encog.workbench.tabs.visualize.epl.EPLTreeTab;

public class EncogProgramTab extends EncogCommonTab implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JToolBar toolbar;
	private JButton buttonQuery;
	private JButton buttonRestructure;
	private JButton buttonVisualize;
	private final JScrollPane scroll;
	private final JEditorPane editor;
	private EncogProgram program;

	public EncogProgramTab(final EncogProgram data) {
		super(null);

		this.program = (EncogProgram) data;
		setLayout(new BorderLayout());
		this.toolbar = new JToolBar();
		this.toolbar.setFloatable(false);
		this.toolbar.add(this.buttonQuery = new JButton("Query"));
		this.toolbar.add(this.buttonRestructure = new JButton("Restructure"));
		this.toolbar.add(this.buttonVisualize = new JButton("Visualize"));

		this.buttonQuery.addActionListener(this);
		this.buttonRestructure.addActionListener(this);
		this.buttonVisualize.addActionListener(this);

		add(this.toolbar, BorderLayout.PAGE_START);

		this.editor = new JEditorPane("text/html", "");
		this.editor.setEditable(false);
		this.scroll = new JScrollPane(this.editor);
		add(this.scroll, BorderLayout.CENTER);
		produceReport();
	}

	public void actionPerformed(final ActionEvent action) {
		try {
			if (action.getSource() == this.buttonQuery) {
				performQuery();
			} else if (action.getSource() == this.buttonVisualize) {
				this.handleVisualize();
			} else if (action.getSource() == this.buttonRestructure) {
				this.handleRestructure();
			}
		} catch (Throwable t) {
			EncogWorkBench.displayError("Error", t);
		}
	}

	private void handleRestructure() {
		try {
			String source = EncogWorkBench
					.displayInput("Enter a new expression.");
			this.program.compileExpression(source);
			this.produceReport();
		} catch (Throwable t) {
			EncogWorkBench.displayError("Compile Error", t);
		}
	}

	private void performQuery() {
		try {
			RegressionQueryTab tab = new RegressionQueryTab(this.program);
			EncogWorkBench.getInstance().getMainWindow().getTabManager()
					.openModalTab(tab, "Query Encog Program");

		} catch (Throwable t) {
			EncogWorkBench.displayError("Error", t);
		}
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void handleVisualize() {
		SelectItem selectTree;
		SelectItem selectLatex;

		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(selectTree = new SelectItem("Tree",
				"View the program as a tree."));
		list.add(selectLatex = new SelectItem("LaTeX Formula",
				"Render the program as a LaTex Formula."));
		SelectDialog sel = new SelectDialog(EncogWorkBench.getInstance()
				.getMainWindow(), list);
		sel.setVisible(true);

		if (sel.getSelected() == selectTree) {
			analyzeTree();
		} else if (sel.getSelected() == selectLatex) {
			analyzeLatex();
		}
	}

	private void analyzeTree() {
		try {
			EPLTreeTab tab = new EPLTreeTab(this.program);
			EncogWorkBench.getInstance().getMainWindow().getTabManager()
					.openModalTab(tab, "Encog Program");

		} catch (Throwable t) {
			EncogWorkBench.displayError("Error", t);
		}
	}

	private void analyzeLatex() {
		try {
			EPLLatexTab tab = new EPLLatexTab(this.program);
			EncogWorkBench.getInstance().getMainWindow().getTabManager()
					.openModalTab(tab, "Encog Program");

		} catch (Throwable t) {
			EncogWorkBench.displayError("Error", t);
		}
	}

	public void produceReport() {
		HTMLReport report = new HTMLReport();
		report.beginHTML();
		report.title("EncogProgram");
		report.beginBody();
		report.h1(this.program.getClass().getSimpleName());

		report.beginTable();

		report.tablePair("Input Count",
				Format.formatInteger(this.program.getInputCount()));
		
		String score = "N/A";
		String adjustedScore = "N/A";
		
		if( !Double.isNaN(this.program.getScore())) {
			score = Format.formatDouble(this.program.getScore(), 4);
		}
		
		if( !Double.isNaN(this.program.getAdjustedScore())) {
			adjustedScore = Format.formatDouble(this.program.getAdjustedScore(), 4);
		}
		

		report.tablePair("Size", Format.formatInteger(this.program.size()));
		report.tablePair("Score", score);
		report.tablePair("Adjusted Score",adjustedScore);

		RenderCommonExpression renderCommonFormat = new RenderCommonExpression();
		RenderRPN renderRPN = new RenderRPN();

		report.tablePair("Common Format",
				renderCommonFormat.render(this.program));

		report.tablePair("Reverse Polish Notation (RPN)",
				renderRPN.render(this.program));

		report.endTable();

		report.endBody();
		report.endHTML();
		this.editor.setText(report.toString());
	}

	@Override
	public String getName() {
		return "Encog Program";
	}

}
