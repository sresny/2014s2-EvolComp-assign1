import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import static java.lang.Math.*;

public class RouletteSelector implements Selector{
	private int n;

	public void setPopulation(int n){
		this.n = n;
	}
	
	public ArrayList<Individual> select(ArrayList<Individual> individuals){
		Collections.shuffle(individuals);

		ArrayList<Individual> selected = new ArrayList<Individual>(n);
		double sum = 0;
		double max = 0;
		for(Individual ind : individuals){
			double fitness = ind.getFitness();
			if(fitness > max){
				max = fitness;
			}
			sum += fitness;
		}
		sum = max*individuals.size() - sum;

		Random rand = new Random();

		ArrayList<Double> positions = new ArrayList<Double>(n);
		for(int i=0; i<n;i++){
			positions.add(rand.nextDouble()*sum);
		}
		double progress = 0;
		int j = 0;
		while(j<individuals.size()){
			Individual next=individuals.get(j);
			double nextFitness = max-next.getFitness();
			
			progress+=nextFitness;
			j++;

			ArrayList<Double> matched = new ArrayList<Double>(n);

			for(Double p : positions){
				if(progress>p){
					matched.add(p);
					selected.add(next);
				}
			}

			positions.removeAll(matched);
		}

		return selected;

	}
}