package ga;

import java.util.Random;

public class FitnessCalc {
	private static int deZeroACem = 100;
	private static int deUmACinquemta = 49;
	
	private static int[] requirements =  {100, 60, 70, 15, 8}; // importância dos requisitos 
	private static int[] values       =  {52 , 23, 35, 15, 7}; //valor monetário de cada requisito
	
	public static int budget 		 = 60; //orçamento
	
	
	public static int getFitness(Individual individual){
		int scoreValue = getScoreValue(individual);
		if(scoreValue > budget){
			return 0;
		}
		return scoreValue;
    }
	
	public static int getScoreImportance(Individual individual){
		int score = 0;
		for (int i = 0; i < individual.getSize(); i++) {
			score += requirements[individual.getGene(i)] ;
		}
		return score;
	}
	
	public static int getScoreValue(Individual individual){
		int score = 0;
		for (int i = 0; i < individual.getSize(); i++) {
			score += values[individual.getGene(i)] ;
		}
		return score;
	}
	
	public static String display() {
		String text = "Requisitos: ";
		for(int i = 0; i < requirements.length; i++){
			text += requirements.length -1 != (i) ? requirements[i]+", " : requirements[i];
		}
		
		text += "\nValues: ";
		for(int j = 0; j < values.length; j++){
			text += values.length -1 != (j) ? values[j]+", " : values[j] ;
		}
		
		return text;
	}
	
	public static void changeSeverely(){
		for(int i = 0; i < requirements.length; i++){
			requirements[i] = new Random().nextInt(deZeroACem);
		}
		
		for(int j = 0; j < values.length; j++){
			values[j] = new Random().nextInt(deUmACinquemta) + 7;
		}
		System.out.println();
	}
	
	public static void changeSlightly(){
		int valorRequisito;
		int randomPosition;
		
		do{
			randomPosition = new Random().nextInt(requirements.length);
			valorRequisito = new Random().nextInt(deZeroACem);
			
		}while(valorRequisito > 0 && valorRequisito == requirements[randomPosition]);
		
		requirements[randomPosition] = valorRequisito;
	}
	
	public static void changeFitnessLandscape(){
		budget += 10;
	}
	
	public static int getFitnessLandscape(){
		return budget;
	}
}
