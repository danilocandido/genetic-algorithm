package ga;

public class GA {

	public static void main(String[] args) {
		Algorithm algorithm = new Algorithm();
		algorithm.evolvePopulation(new Population(100));
	}
}
