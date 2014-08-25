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
	private int size;

	public Population(ArrayList<City> cities,int n){
		this.cities = new ArrayList<City>(cities);
		this.individuals = new ArrayList<Individual>(n);
		size = n;
		for(int i=0;i<n;i++){
			Individual ind = new Individual(cities);
			ind.shuffle();
			individuals.add(ind);
		}
	}

	public void select_tournament(int competing, int winners){
		if(winners<individuals.size()){
			ArrayList<Individual> competitors;
			if(competing<individuals.size()){
				competitors = new ArrayList<Individual>(individuals.subList(0,competing));
				Collections.shuffle(individuals);
			}else{
				competitors = individuals;
			}
			Collections.sort(competitors);
			individuals = new ArrayList<Individual>(competitors.subList(0,winners));
		}
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

	public void mutateChildren(){
		for(int i=size; i<individuals.size(); i++){
			Random rand = new Random();
			if(rand.nextDouble()<mutProb){
				mutator.mutate(individuals.get(i));
			} 
		}

	}

	public void crossover(){
		Collections.shuffle(individuals);
		Random rand = new Random();
		for(int i=0;i<size-1;i+=2){
			if(rand.nextDouble()<crossProb){
				Children child = crosser.crossover(individuals.get(i),individuals.get(i+1));
				individuals.add(child.a);
				individuals.add(child.b);
			}else{
				individuals.add(new Individual(individuals.get(i)));
				individuals.add(new Individual(individuals.get(i+1)));
			}
		}
	}

	public double average(){
		double sum = 0;
		for(Individual ind : individuals){
			sum += ind.getFitness();
		}
		return sum/individuals.size();
	}

	public double best(){
		Collections.sort(individuals);
		return individuals.get(0).getFitness();
	}

}