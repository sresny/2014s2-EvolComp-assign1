import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import static java.lang.Math.*;

public class SUSSelector implements Selector{
	private int n;

	public SUSSelector(int n){
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

		double spacing = sum/n;

		Random rand = new Random();

		double start = rand.nextDouble()*spacing;

		double progress = 0;
		Individual next = individuals.remove(0);
		double nextFitness = max-next.getFitness();
		for(int i=0; i<n;i++){
			while(progress+nextFitness<start+i*spacing){
				progress+=nextFitness;
				next=individuals.remove(0);
				nextFitness = max-next.getFitness();
			}
			selected.add(next);
		}

		return selected;

	}

}