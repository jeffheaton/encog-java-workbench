package org.encog.workbench.process.generate;

import org.encog.neural.networks.training.backpropagation.Backpropagation;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.GenerateCode;
import org.encog.workbench.frames.TextFrame;

public class CodeGeneration {
	public void processCodeGeneration()
	{
		GenerateCode dialog = new GenerateCode(EncogWorkBench.getInstance().getMainWindow());
		if( dialog.process() )
		{
			TextFrame text = new TextFrame("Generated code",true);
			
			Generate gen = null;
			
			switch( dialog.getLanguage() )
			{
				case Java:
					gen = new GenerateJava();
					break;
				case CS:
					gen = new GenerateCSharp();
					break;					
				case VB:
					gen = new GenerateVB();
					break;
			}
						
			String source = gen.generate(dialog.getNetwork(),dialog.getTrainingSet(), dialog.isCopyTraining(), dialog.getTrainingMethod());
			text.setText(source);
			text.setVisible(true);
		}
	}
}
