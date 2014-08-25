import java.util.ArrayList;

class TSPProblem{
	public static void main(String[] args){
		int n = 10;
		int iterations = 1000;

		ArrayList<City> cities = new ArrayList<City>();
		for(int i=0;i<10;i++){
			City c = new City(i,0,0);
			cities.add(c);
		}

		Population p = new Population(cities,n);

		p.setCrossover(new OrderCrossover(),0.5);
		p.setMutator(new InvertMutator(),0.75);

		for(int i=0; i<iterations;i++){
			p.crossover();
			p.mutate();
			p.select_tournament(16,10);
		}

	}
}