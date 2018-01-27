package ga;

import java.util.Random;

public class Individual {

	private Random random = new Random();
	
	static int geneLength = 5;
	private int[] genes = new int[geneLength];
	
	public Individual() {}
	
	public Individual(int[] genes) {
		this.genes = genes;
	}
	
	public void generateIndividual(){
		for (int i = 0; i < genes.length; i++) {
			genes[i] = random.nextInt(geneLength);
		}
	}
	
	public int getGene(int index){
		return genes[index];
	}
	
	public void setGene(int index, int gene){
		genes[index] = gene;
	}
	
	public int[] getGenes(){
		return genes;
	}
	
	public void setGenes(int[] genesParam){
		for (int i = 0; i < genes.length; i++) {
			genes[i] = genesParam[i];
		}
	}

	public int getSize(){
		return geneLength;
	}
	
	/** Get Aptidão
	 */
	public int getFitness(){
		return FitnessCalc.getFitness(this);
    }
	
	public int getImportance(){
		return FitnessCalc.getScoreImportance(this);
	}
	
	public int getValue(){
		return FitnessCalc.getScoreValue(this);
	}
	
	@Override
	public String toString() {
		String showGenes = "Genes: ";
		for (int gene : genes) {
			showGenes += gene + ",";
		}
		return showGenes;
	}
	
}
