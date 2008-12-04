package org.encog.workbench.process.validate;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.workbench.EncogWorkBench;
import org.encog.neural.networks.Layer;
import org.encog.neural.networks.layers.FeedforwardLayer;
import org.encog.neural.networks.layers.HopfieldLayer;
import org.encog.neural.networks.layers.SOMLayer;

public class ValidateTraining {
	
	BasicNetwork network;
	BasicNeuralDataSet training;
	
	public ValidateTraining(BasicNetwork network,BasicNeuralDataSet training)
	{
		this.network = network;
		this.training = training;
	}
	
	public boolean validateIsSupervised()
	{
		if( !training.isSupervised() )
		{
			EncogWorkBench.displayError("Training Error","This sort of training requires a suprvised training set,\n which means that it must have ideal data provided.");
			return false;
		}
		
		return true;
	}
	
	public boolean validateIsUnsupervised()
	{
		if( !training.isSupervised() )
		{
			EncogWorkBench.displayError("Training Error","This sort of training requires an unsuprvised training set,\n which means that it must not have ideal data provided.");
			return false;
		}
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public boolean validateContainsLayer(Class layerType)
	{
		for(Layer layer:network.getLayers())
		{
			if( layer.getClass().getName().equals(layerType.getName()))
			{
				return true;
			}
		}
		
		
		EncogWorkBench.displayError("Training Error", "This sort of training requires that at least one layer be of type:\n" + layerType.getSimpleName() );
		return false;
	}
	
	public boolean validateInputSize()
	{
		int inputNeurons = this.network.getInputLayer().getNeuronCount();
		int trainingInputs = this.training.getInputSize();
		
		if( inputNeurons != trainingInputs )
		{
			
			EncogWorkBench.displayError("Training Error", "Training input size must match the number of input neurons.\n Input neurons:"+inputNeurons + "\nTraining Input Size: " + trainingInputs );
			return false;
		}
		return true;
	}
	
	public boolean validateOutputSize()
	{
		int outputNeurons = this.network.getOutputLayer().getNeuronCount();
		int trainingOutputs = this.training.getIdealSize();
		
		if( outputNeurons != trainingOutputs )
		{
			
			EncogWorkBench.displayError("Training Error", "Training ideal size must match the number of output neurons.\n Output neurons:"+outputNeurons + "\nTraining Ideal Size: " + trainingOutputs );
			return false;
		}
		return true;
	}
	
	public boolean validateFeedForward()
	{
		if( !validateIsSupervised())
			return false;
		
		if( !validateContainsLayer(FeedforwardLayer.class))
			return false;
		
		if( !validateInputSize() )
			return false;
		
		if( !validateOutputSize() )
			return false;	
		
		return true;
	}
	
	public boolean validateHopfield()
	{
		if( !validateIsUnsupervised())
			return false;
		
		if( !validateContainsLayer(HopfieldLayer.class))
			return false;
		
		if( !validateIsSupervised())
			return false;
		
		if( !validateInputSize() )
			return false;
		
		return true;
	}
	
	public boolean validateSOM()
	{
		
		if( !validateIsSupervised())
			return false;
		
		if( !validateContainsLayer(SOMLayer.class))
			return false;
		
		if( !validateInputSize() )
			return false;
		
		return true;
	}
}
