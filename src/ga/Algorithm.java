package ga;

import java.util.Random;

public class Algorithm {
	
	private static Random random = new Random();
	
	private double mutationRate  = 0.01;
	private double crossoverRate = 0.6;
	private double elitismImmigrantsRate = 0.2;
	private double randomImmigrantsRate = 0.1;
	
	private int tournamentSize   = 20;
	private int generationSize   = 200;
	
	private int fasterChanges = 50; // Smaller τ means faster changes - 10 and 50
	private double severerChanges = 0.1; // Bigger ρ means severer changes - 0.1, 0.2, 0.5, and 1.0 respectively
	
	private static int randomIndex = -1;

	public void evolvePopulation(Population pop){
		Population newPopulation = new Population(pop.getSize(), elitismImmigrantsRate);
		
		for(int i = 0; i <= generationSize; i++){
			
				
			if(Math.random() <= crossoverRate){
				int index1 = getRandomIndividual(newPopulation);
				Individual individual1 = newPopulation.getIndividual(index1);
				
				int index2 = getRandomIndividual(newPopulation);
				Individual individual2 = newPopulation.getIndividual(index2);
				
				//Crossover
				Individual newIndv = crossover(individual1, individual2);
				
				if(Math.random() <= mutationRate){
					mutation(newIndv); //Mutation
				}
				
				//de extrema importância TODO
				if(newIndv.getFitness() > individual1.getFitness()){
					newPopulation.saveIndividual(index1, newIndv);
				}
			}
			
			if(i != 0 && i % 50 == 0){
				Individual fittest = newPopulation.getFittest();
				System.out.println(FitnessCalc.display());
				System.out.println("Fitness Landscape: " +FitnessCalc.getFitnessLandscape());
				System.out.println("geração: "+ i);
				System.out.println("Melhor importância de requisitos: "+ FitnessCalc.getScoreImportance(fittest));
				System.out.println("Melhor valor orçamentário : "+ FitnessCalc.getScoreValue(fittest));
				
				System.out.println(fittest);
				System.out.println("/////////////////////////////////////////////////////////////////");
				System.out.println();
				
				//DOP
				FitnessCalc.changeFitnessLandscape();;
			}
			
			if(i != 0){
				newPopulation.generateElitismBasedImmigrants(mutationRate);
			}
			newPopulation.ramdomImmigrants(randomImmigrantsRate);
		}//Geração Fim
		
		newPopulation.displayPopulation(); //TODO
	}
	
	
	/** cruzamento de um ponto (1PX):
	 */
	public Individual crossover(Individual individual1, Individual individual2){
		
		Individual son = new Individual();
		
		int cutNumber = random.nextInt(individual1.getSize());
		
		for(int i = 0; i < cutNumber; i++){
			son.setGene(i, individual1.getGene(i));
		}
		
		for(int i = cutNumber; i < individual2.getSize(); i++){
			son.setGene(i, individual2.getGene(i));
		}
		
		return son;
	}
	
	public static void mutation(Individual individual){
		int gene;
		int randomGene;
		
		do{
			randomGene = random.nextInt(individual.getSize());
			gene = individual.getGene(randomGene);
		}while(gene == randomGene);
		
		individual.setGene(gene, randomGene);
	}
	
	public int getRandomIndividual(Population population){
		int indexAux;
		do{
			indexAux = random.nextInt(population.getSize());
		}while(randomIndex == indexAux);
		
		randomIndex = indexAux;
		
		return randomIndex;
	}
}
