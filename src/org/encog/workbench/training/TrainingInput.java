package org.encog.workbench.training;


public class TrainingInput {
	
	private String  neuralNetworkName;
	private String  trainingDataName;
	private double learningRate;
	private double  maximumError;
	private double  momentum;
	
	public String getneuralNetworkName() {
	    return neuralNetworkName;
	}

	public void setneuralNetworkName(String neuralNetworkName) {
	    this.neuralNetworkName = neuralNetworkName;
	}
	
	public String gettrainingDataName() {
	    return trainingDataName;
	}

	public void settrainingDataName(String trainingDataName) {
	    this.trainingDataName = trainingDataName;
	}
	
	public double getlearningRate() {
	    return learningRate;
	}

	public void setlearningRate(double learningRate) {
	    this.learningRate = learningRate;
	}
	
	public double getmaximumError() {
	    return maximumError;
	}

	public void setmaximumError(double maximumError) {
	    this.maximumError = maximumError;
	}

	public double getmomentum() {
	    return momentum;
	}

	public void setmomentum(double momentum) {
	    this.momentum = momentum;
	}

}
