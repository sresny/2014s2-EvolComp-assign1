import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class Population{
	private ArrayList<City> cities;
	private ArrayList<Individual> individuals;
	private Mutator mutator;
	private Crossover crosser;
	private double mutProb;
	private double crossProb;

	public Population(ArrayList<City> cities,int n){
		this.cities = new ArrayList<City>(cities);
		this.individuals = new ArrayList<Individual>(n);

		for(int i=0;i<n;i++){
			Individual ind = new Individual(cities);
			ind.shuffle();
			individuals.add(ind);
		}
	}

	public void select_tournament(int competing, int winners){
		Collections.shuffle(individuals);
		ArrayList<Individual> competitors = new ArrayList<Individual>(individuals.subList(0,competing));
		Collections.sort(competitors);
		individuals = new ArrayList<Individual>(competitors.subList(0,winners));
	}

	public ArrayList<Individual> getIndividuals(){
		return individuals;
	}

	public void setMutator(Mutator m, double p){
		mutator = m;
		mutProb = p;
	}

	public void setCrossover(Crossover c, double p){
		crosser = c;
		crossProb = p;
	}

	public void mutate(){
		for(Individual ind : individuals){
			Random rand = new Random();
			if(rand.nextDouble()<mutProb){
				mutator.mutate(ind);
			} 
		}
	}

	public void crossover(){
		Collections.shuffle(individuals);
		for(int i=0;i<individuals.size()-1;i+=2){
			Random rand = new Random();
			if(rand.nextDouble()<crossProb){
				Children child = crosser.crossover(individuals.get(i),individuals.get(i+1));
				individuals.add(child.a);
				individuals.add(child.b);
			}
		}
	}

}