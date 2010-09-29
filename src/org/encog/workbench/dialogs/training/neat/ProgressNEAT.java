/*
 * Encog(tm) Workbench v2.5
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
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
package org.encog.workbench.dialogs.training.neat;

import java.awt.Frame;

import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationStep;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.synapse.neat.NEATSynapse;
import org.encog.neural.networks.training.CalculateScore;
import org.encog.neural.networks.training.TrainingSetScore;
import org.encog.neural.networks.training.neat.NEATTraining;
import org.encog.solve.genetic.population.Population;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.training.BasicTrainingProgress;

public class ProgressNEAT extends BasicTrainingProgress {

	private Population population;
	
	/**
	 * The activation mutation rate.
	 */
	private double paramActivationMutationRate = 0.1;

	/**
	 * The likelyhood of adding a link.
	 */
	private double paramChanceAddLink = 0.07;

	/**
	 * The likelyhood of adding a node.
	 */
	private double paramChanceAddNode = 0.04;

	/**
	 * THe likelyhood of adding a recurrent link.
	 */
	private double paramChanceAddRecurrentLink = 0.05;

	/**
	 * The compatibility threshold for a species.
	 */
	private double paramCompatibilityThreshold = 0.26;

	/**
	 * The crossover rate.
	 */
	private double paramCrossoverRate = 0.7;

	/**
	 * The max activation perturbation.
	 */
	private double paramMaxActivationPerturbation = 0.1;

	/**
	 * The maximum number of species.
	 */
	private int paramMaxNumberOfSpecies = 0;

	/**
	 * The maximum number of neurons.
	 */
	private double paramMaxPermittedNeurons = 100;

	/**
	 * The maximum weight perturbation.
	 */
	private double paramMaxWeightPerturbation = 0.5;

	/**
	 * The mutation rate.
	 */
	private double paramMutationRate = 0.2;

	/**
	 * The number of link add attempts.
	 */
	private int paramNumAddLinkAttempts = 5;

	/**
	 * The number of generations allowed with no improvement.
	 */
	private int paramNumGensAllowedNoImprovement = 15;

	/**
	 * The number of tries to find a looped link.
	 */
	private int paramNumTrysToFindLoopedLink = 5;

	/**
	 * The number of tries to find an old link.
	 */
	private int paramNumTrysToFindOldLink = 5;

	/**
	 * The probability that the weight will be totally replaced.
	 */
	private double paramProbabilityWeightReplaced = 0.1;
	
	/**
	 * Construct the dialog box.
	 * @param owner The owner.
	 * @param network The network to train.
	 * @param initialUpdate The initial update.
	 * @param learningRate The max step value.
	 * @param maxError The maximum error.
	 */
	public ProgressNEAT(
			final Frame owner,
			final NeuralDataSet trainingData,
			final Population population,
			final BasicNetwork model,
			final double maxError) {
		super(owner);

		setNetwork(model);
		setTrainingData(trainingData);
		setMaxError(maxError);
		this.population = population;
	}

	/**
	 * Perform one training iteration.
	 */
	@Override
	public void iteration() {

		getTrain().iteration();

	}

	/**
	 * Not used.
	 */
	@Override
	public void shutdown() {
	}

	/**
	 * Construct the training objects.
	 */
	@Override
	public void startup() {
		
		CalculateScore score = new TrainingSetScore(getTrainingData());
		// train the neural network
		ActivationStep step = new ActivationStep();
		step.setCenter(0.5);
		
		final NEATTraining train = new NEATTraining(
				score, this.getNetwork(),this.population);
		
		train.setParamActivationMutationRate(this.paramActivationMutationRate);
		train.setParamChanceAddLink(this.paramChanceAddLink);
		train.setParamChanceAddNode(this.paramChanceAddNode);
		train.setParamChanceAddRecurrentLink(this.paramChanceAddRecurrentLink);
		train.setParamCompatibilityThreshold(this.paramCompatibilityThreshold);
		train.setParamCrossoverRate(this.paramCrossoverRate);
		train.setParamMaxActivationPerturbation(this.paramMaxActivationPerturbation);
		train.setParamMaxNumberOfSpecies(this.paramMaxNumberOfSpecies);
		train.setParamMaxPermittedNeurons(this.paramMaxPermittedNeurons);
		train.setParamMaxWeightPerturbation(this.paramMaxWeightPerturbation);
		train.setParamMutationRate(this.paramMutationRate);
		train.setParamNumAddLinkAttempts(this.paramNumAddLinkAttempts);
		train.setParamNumGensAllowedNoImprovement(this.paramNumGensAllowedNoImprovement);
		train.setParamNumTrysToFindLoopedLink(this.paramNumTrysToFindLoopedLink);
		train.setParamNumTrysToFindOldLink(this.paramNumTrysToFindOldLink);
		train.setParamProbabilityWeightReplaced(this.paramProbabilityWeightReplaced);

		ActivationFunction outputActivation = this.getNetwork().getLayer(BasicNetwork.TAG_OUTPUT).getActivationFunction();
		ActivationFunction neatActivation = ((NEATSynapse)this.getNetwork().getLayer(BasicNetwork.TAG_INPUT).getNext().get(0)).getActivationFunction();
		
		train.setNeatActivationFunction(neatActivation);
		train.setOutputActivationFunction(outputActivation);

		setTrain(train);
	}
	
	@Override
	public void saveNetwork()
	{
		EncogWorkBench.getInstance().getCurrentFile().add(
				this.population.getName(), this.population);	
	}

	public double getParamActivationMutationRate() {
		return paramActivationMutationRate;
	}

	public void setParamActivationMutationRate(double paramActivationMutationRate) {
		this.paramActivationMutationRate = paramActivationMutationRate;
	}

	public double getParamChanceAddLink() {
		return paramChanceAddLink;
	}

	public void setParamChanceAddLink(double paramChanceAddLink) {
		this.paramChanceAddLink = paramChanceAddLink;
	}

	public double getParamChanceAddNode() {
		return paramChanceAddNode;
	}

	public void setParamChanceAddNode(double paramChanceAddNode) {
		this.paramChanceAddNode = paramChanceAddNode;
	}

	public double getParamChanceAddRecurrentLink() {
		return paramChanceAddRecurrentLink;
	}

	public void setParamChanceAddRecurrentLink(double paramChanceAddRecurrentLink) {
		this.paramChanceAddRecurrentLink = paramChanceAddRecurrentLink;
	}

	public double getParamCompatibilityThreshold() {
		return paramCompatibilityThreshold;
	}

	public void setParamCompatibilityThreshold(double paramCompatibilityThreshold) {
		this.paramCompatibilityThreshold = paramCompatibilityThreshold;
	}

	public double getParamCrossoverRate() {
		return paramCrossoverRate;
	}

	public void setParamCrossoverRate(double paramCrossoverRate) {
		this.paramCrossoverRate = paramCrossoverRate;
	}

	public double getParamMaxActivationPerturbation() {
		return paramMaxActivationPerturbation;
	}

	public void setParamMaxActivationPerturbation(
			double paramMaxActivationPerturbation) {
		this.paramMaxActivationPerturbation = paramMaxActivationPerturbation;
	}

	public int getParamMaxNumberOfSpecies() {
		return paramMaxNumberOfSpecies;
	}

	public void setParamMaxNumberOfSpecies(int paramMaxNumberOfSpecies) {
		this.paramMaxNumberOfSpecies = paramMaxNumberOfSpecies;
	}

	public double getParamMaxPermittedNeurons() {
		return paramMaxPermittedNeurons;
	}

	public void setParamMaxPermittedNeurons(double paramMaxPermittedNeurons) {
		this.paramMaxPermittedNeurons = paramMaxPermittedNeurons;
	}

	public double getParamMaxWeightPerturbation() {
		return paramMaxWeightPerturbation;
	}

	public void setParamMaxWeightPerturbation(double paramMaxWeightPerturbation) {
		this.paramMaxWeightPerturbation = paramMaxWeightPerturbation;
	}

	public double getParamMutationRate() {
		return paramMutationRate;
	}

	public void setParamMutationRate(double paramMutationRate) {
		this.paramMutationRate = paramMutationRate;
	}

	public int getParamNumAddLinkAttempts() {
		return paramNumAddLinkAttempts;
	}

	public void setParamNumAddLinkAttempts(int paramNumAddLinkAttempts) {
		this.paramNumAddLinkAttempts = paramNumAddLinkAttempts;
	}

	public int getParamNumGensAllowedNoImprovement() {
		return paramNumGensAllowedNoImprovement;
	}

	public void setParamNumGensAllowedNoImprovement(
			int paramNumGensAllowedNoImprovement) {
		this.paramNumGensAllowedNoImprovement = paramNumGensAllowedNoImprovement;
	}

	public int getParamNumTrysToFindLoopedLink() {
		return paramNumTrysToFindLoopedLink;
	}

	public void setParamNumTrysToFindLoopedLink(int paramNumTrysToFindLoopedLink) {
		this.paramNumTrysToFindLoopedLink = paramNumTrysToFindLoopedLink;
	}

	public int getParamNumTrysToFindOldLink() {
		return paramNumTrysToFindOldLink;
	}

	public void setParamNumTrysToFindOldLink(int paramNumTrysToFindOldLink) {
		this.paramNumTrysToFindOldLink = paramNumTrysToFindOldLink;
	}

	public double getParamProbabilityWeightReplaced() {
		return paramProbabilityWeightReplaced;
	}

	public void setParamProbabilityWeightReplaced(
			double paramProbabilityWeightReplaced) {
		this.paramProbabilityWeightReplaced = paramProbabilityWeightReplaced;
	}
	
	

}
