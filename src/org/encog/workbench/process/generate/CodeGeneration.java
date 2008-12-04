package org.encog.workbench.process.generate;

import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.training.backpropagation.Backpropagation;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.GenerateCode;
import org.encog.workbench.frames.TextFrame;
import org.encog.workbench.process.validate.ValidateTraining;

public class CodeGeneration {
	
	private boolean validate(GenerateCode dialog)
	{
		ValidateTraining validate = new ValidateTraining(dialog.getNetwork(),
				(BasicNeuralDataSet)dialog.getTrainingSet());
		switch(dialog.getTrainingMethod())
		{
			case Anneal:
			case Genetic:
			case Backpropagation:
				if( !validate.validateFeedForward())
					return false;
				break;
			case TrainHopfield:
				if( !validate.validateHopfield())
					return false;
				break;
			case TrainSOM:
				if( !validate.validateSOM())
					return false;
				break;
			default:
				break;
		}
		return true;
	}
	
	public void processCodeGeneration()
	{
		GenerateCode dialog = new GenerateCode(EncogWorkBench.getInstance().getMainWindow());
		if( dialog.process() )
		{
			if( !validate(dialog) )
				return;
			
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
