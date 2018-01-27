import java.util.Random;

/**
 * Quero ter a maior quantidade de requisitos importantes que caibam no meu orçamento
 * @author danilo
 *
 */
public class GeneticAlgorithmNRP {
	
	private Random random = new Random();
	
	private int[] requirements =  {100, 60, 70, 15, 8}; // importância dos requisitos 
	private int[] values       =  {52, 23, 35, 15, 7}; //valor monetário de cada requisito
	
	//private int[] requirements =  {100, 60, 70, 15, 8}; // importância dos requisitos 
	//private int[] values       =  {52 , 23, 35, 15, 7}; //valor monetário de cada requisito
	
	private int qtPopulation = 100; //População
	private int budget 		 = 90; //orçamento
	private int qtGene       = 5;  //quantidade de gene

	private int generation   = 200; //Quantidade de Gerações
	private int qtTournament = 20; //tamanho do torneio
	
	/**Probabilidades */
	private double mutation_rate    = 0.2; //Probabilidade de Mutação - normalmente é menor que a de Cruzamento 
	private double crossover_rate   = 0.7; //Probabilidade de Cruzamento
	private double replacement_rate = 0.40; // specifies the fraction of the population that is replaced each generation
	
	private boolean elitism_based_immigrants = true;
	
	private int[][] population = new int[qtPopulation][qtGene]; //Clientes
	
	/** Inicializa a população
	 */
	private  void initializePopulation(){
		
		for(int i = 0; i < qtPopulation; i++){
			for(int j = 0; j < qtGene; j++){
				
				int gene = random.nextInt(qtGene);
				population[i][j] = gene;
			}
		}
	}
	
	/** Mostra da mopulação
	 */
	private  void displayPopulation(){
		for(int i = 0; i < qtPopulation; i++){
			
			System.out.print("Indivíduo ["+ i +"] : ");
			
			for(int j = 0; j < qtGene; j++){
				System.out.print(population[i][j] + " ");
			}
			System.out.print(" valor " + obterValorMonetario(population[i]));
			System.out.print(" importância " + obterImportancia(population[i]));
			System.out.println();
		}
	}
	
	/**
	 * mutação flip :: cada gene a ser mutado recebe um valor sorteado do alfabeto válido;
	 * @param individuo
	 */
	private  void mutation(int[] individuo){
		int geneComparativo;
		int geneAleatorio = random.nextInt(qtGene);
		
		/** Seleciona um gene aleatório, verifica se o individuo tem o gene, se sim muda o gene. */
			
		do{
			//Sorteio do gene
			geneComparativo = random.nextInt(qtGene);
		}while(values[individuo[geneAleatorio]] == values[geneComparativo]);
		
		individuo[geneAleatorio] = geneComparativo;
	}
	
	/**
	 * cruzamento de um ponto (1PX):
	 */
	private void crossover(int indicePai1, int indicePai2, int[] filho){
		
		int[] pai1 = population[indicePai1];
		int[] pai2 = population[indicePai2];
		
		/**Ponto de corte*/
		int ponto = random.nextInt(qtGene);
		
		for(int i = 0; i < ponto; i++){
			filho[i] = pai1[i];
		}
		
		for(int i = ponto; i < qtGene; i++){
			filho[i] = pai2[i];
		}
		
		/**Mostrando os genes*/
		/*
		System.out.println("Ponto de Cruzamento: "+ ponto);
		
		System.out.print("pai1: ");
		for(int k = 0; k < qtGene; k++){
			System.out.print(pai1[k] + ", ");
		}
		
		System.out.println();
		System.out.print("pai2: ");
		for(int k = 0; k < qtGene; k++){
			System.out.print(pai2[k] + ", ");
		}
		
		System.out.println();
		System.out.print("filho: ");
		for(int k = 0; k < qtGene; k++){
			System.out.print(filho[k] + ", ");
		}
		
		System.out.println(filho);
		*/
	}
	
	/**
	 * Retorna o Score do valor do indivíduo
	 */
	private int obterImportancia(int[] individuo){
		int scoreValor = 0;
		
		for(int i = 0; i < individuo.length; i++){
			scoreValor += requirements[individuo[i]];
		}
		
		return scoreValor;
	}
	
	/**
	 * Retorna o Score do peso do indivíduo
	 */
	private int obterValorMonetario(int[] individuo){
		int scorePeso = 0;
		
		for(int i = 0; i < individuo.length; i++){
			scorePeso += values[individuo[i]];
		}
		
		return scorePeso;
	}
	
	/**
	 * Fitness Evaluation
	 * @return
	 */
	private int obterMelhorIndividuo(){
		int indiceMelhor = 0;
		int scoreMelhorValor = 0; //obterValor(population[indiceMelhor]);
		int scoreMelhorPeso  = 0; //obterPeso(population[indiceMelhor]);
		
		for(int i = 0; i < qtPopulation; i++){
			int[] individuo = population[i];
			
			int scoreValor = obterImportancia(individuo);
			int scorePeso  = obterValorMonetario(individuo);
			
			if(scorePeso <= budget ){
				if(scoreValor > scoreMelhorValor){
					indiceMelhor = i; //melhor indivíduo atual
					scoreMelhorValor = scoreValor;
					scoreMelhorPeso  = scorePeso;
				}
			}else{
				//System.out.println(indiceMelhor);
			}
		}
		
		return indiceMelhor;
	}
	
	public void start() {
		initializePopulation();
		displayPopulation();
		
		/** Gerações */
		for(int i = 0; i < generation; i++){
			
			/** Quantidade de Torneios */
			for(int j = 0; j < qtTournament; j++){
				
			    double prob = ((double) random.nextInt(qtGene) / ((double)random.nextInt(Integer.MAX_VALUE)));
				
				/**Probabilidade de acontecer um cruzamento*/
				if(prob <= crossover_rate){ 
					
					/**Obtendo os Pais na ROLETA (roulette)*/
					int indicePai1 = random.nextInt(qtPopulation);
					int indicePai2;
					
					do{
						indicePai2 = random.nextInt(qtPopulation);
					}while(indicePai1 == indicePai2);
					
					
					int[] filho = new int[qtGene];
					
					/**Realiza o Cruzamento (crossover) */
					crossover(indicePai1, indicePai2, filho);
					
					prob = ((double) random.nextInt(qtGene) / ((double)random.nextInt(Integer.MAX_VALUE)));
					
					/**Probabilidade de acontecer uma mutação*/
					if(prob <= mutation_rate){
						mutation(filho);
						
						int[] pai1 = population[indicePai1];
						
						int pesoPai1  = obterValorMonetario(pai1);
						int valorPai1  = obterImportancia(pai1);
						
						int pesoFilho = obterValorMonetario(filho);
						int valorFilho = obterImportancia(filho);
						
						//isso é de extrema importância
						if(pesoFilho <= budget){
							if(pesoPai1 <= budget && valorFilho > valorPai1){
								population[indicePai1] = filho;
							}else{
								population[indicePai1] = filho;
							}
						}
					}
				}
			}

			/**Resultados*/
			int indice_melhor = obterMelhorIndividuo();
			int[] individuoMelhor = population[indice_melhor];
			
			int score_melhor_valor = obterImportancia(individuoMelhor);
			int score_melhor_peso  = obterValorMonetario(individuoMelhor);
			
			
			if(score_melhor_peso > budget){
				System.out.println(":: Ultrapassou o orçamento. ::");
			}	
			System.out.println("Geração : " + (i + 1) );
			System.out.print("Genes: " );
			
			
			
			
			for(int k = 0; k < qtGene; k++){
				System.out.print(individuoMelhor[k] + ", ");
			}
			
			System.out.println();
			System.out.println("Melhor importância de requisitos: "+ score_melhor_valor);
			System.out.println("Melhor valor orçamentário : "+ score_melhor_peso);
			System.out.println("Indice do Melhor : "+ indice_melhor);
			System.out.println();
			
		}
		
	}
	
}
