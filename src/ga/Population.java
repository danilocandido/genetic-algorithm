package ga;

public class Population {
	
	private Individual[] individuals;
	
	private static Individual[] individualsImmigrants;
	
	public Population(int populationSize){
		individuals = new Individual[populationSize];
		
		for(int i = 0; i < populationSize ; i++){
			Individual individual = new Individual();
			individual.generateIndividual();
			saveIndividual(i, individual);
		}
		//displayPopulation();
	}
	
	public Population(int populationSize, double replacementRate){
		this(populationSize);
		int immigrantSize = (int) (getSize() * replacementRate);
		individualsImmigrants = new Individual[immigrantSize];
	}
	
	/** Mostra da mopulação
	 */
	public void displayPopulation(){
		for (int i = 0; i < individuals.length; i++) {
			
			Individual individual = individuals[i];
			
			System.out.print("Indivíduo ["+ i +"] : ");
			
			for(int j = 0; j < individual.getSize(); j++){
				System.out.print(individual.getGene(j) + " ");
			}
			
			System.out.print(" valor " + FitnessCalc.getScoreValue(individual));
			System.out.print(" importância " + FitnessCalc.getScoreImportance(individual));
			System.out.println();
			
		}
	}
	
	/** Save individual
	 */
    public void saveIndividual(int index, Individual indiv) {
        individuals[index] = indiv;
    }
    
    /** Get individual by index
     */
    public Individual getIndividual(int index){
		return individuals[index];
	}
    
    public int getSize(){
    	return individuals.length;
    }
    
    public Individual getFittest() {
    	Individual fittest = individuals[0];
    	
    	int maxInportance = 0;
    	
    	// Loop through individuals to find fittest
    	for (int i = 0; i < getSize(); i++) {
			if(getIndividual(i).getFitness() > 0
					&& getIndividual(i).getFitness() >= fittest.getFitness()) {
				
				if(getIndividual(i).getImportance() > maxInportance){
					fittest = getIndividual(i);
					maxInportance = fittest.getImportance();
				}
			}
		}
		
    	return fittest;
    }

    /** Bubble Sort
     */
    public void ordenacao(){
    	for (int i = 0; i < getSize(); i++) {
			for(int j = 0; j < getSize() -1; j++){
				if(getIndividual(j).getValue() > getIndividual(j+1).getValue()){
					Individual aux = getIndividual(j);
					individuals[j] = getIndividual(j +1);
					individuals[j+1] = aux;
				}
			}
		}
    }
    
    /**
	 *  Replacing the worst ones
	 */
	public void generateElitismBasedImmigrants(double mutationRate){

		//A ordenação funciona como uma validação pois os melhores passarão para a próxima geração
		ordenacao();
		
    	//Adicionado Imigrantes
    	for (int i = 0; i < individualsImmigrants.length; i++) {
    		individualsImmigrants[i] = new Individual();
    		individualsImmigrants[i].setGenes(getIndividual(i).getGenes());
    		if(Math.random() <= mutationRate){
    			Algorithm.mutation(individualsImmigrants[i]);
    		}
		}
    	
    	replaceElitismBasedImmigrants();
	}
	
	private void replaceElitismBasedImmigrants(){
		ordenacao();
		int valorAux = getSize() - individualsImmigrants.length;
		for (int i = getSize() - 1, j = 0; i >= valorAux; i--, j++) {
			
			if(individualsImmigrants[j].getFitness() > getIndividual(i).getFitness()){ //evaluate
				getIndividual(i).setGenes(individualsImmigrants[j].getGenes());
			}
		}
	}
	
	public void ramdomImmigrants(double randomImmigrantsRate){
		ordenacao();
		int immigrantSize = (int) (getSize() * randomImmigrantsRate);
		
		for (int i = getSize() - 1; i >= (getSize()- immigrantSize); i--) {
			Individual individualGenerated = new Individual();
			individualGenerated.generateIndividual();
			
			if(individualGenerated.getFitness() > getIndividual(i).getFitness()){ //evaluate
				saveIndividual(i, individualGenerated);
			}
		}
	}
}
