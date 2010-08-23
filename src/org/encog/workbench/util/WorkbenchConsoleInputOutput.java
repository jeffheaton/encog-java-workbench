package org.encog.workbench.util;

import org.encog.script.ConsoleInputOutput;
import org.encog.workbench.EncogWorkBench;

public class WorkbenchConsoleInputOutput implements ConsoleInputOutput {

	public String input(String prompt) {
		return null;
	}

	public void print(String line) {
		EncogWorkBench.getInstance().output(line);
		
	}

	public void printLine(String line) {
		EncogWorkBench.getInstance().outputLine(line);		
	}

}
