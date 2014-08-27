import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class Population{
	private ArrayList<City> cities;
	private ArrayList<Individual> individuals;
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

	public ArrayList<Individual> getIndividuals(){
		return individuals;
	}

	public void select(Selector s){
		individuals = s.select(individuals);
	}

	public void mutate(Mutator m, double p){
		for(Individual ind : individuals){
			Random rand = new Random();
			if(rand.nextDouble()<p){
				m.mutate(ind);
			} 
		}
	}

	public void mutateChildren(Mutator m, double p){
		for(int i=size; i<individuals.size(); i++){
			Random rand = new Random();
			if(rand.nextDouble()<p){
				m.mutate(individuals.get(i));
			} 
		}

	}

	public void crossover(Crossover c, double p){
		Collections.shuffle(individuals);
		Random rand = new Random();
		for(int i=0;i<size-1;i+=2){
			if(rand.nextDouble()<p){
				Children child = c.crossover(individuals.get(i),individuals.get(i+1));
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