import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;
import static java.lang.Math.*;

class PMXCrossover implements Crossover{
	public Children crossover(Individual parentA, Individual parentB){

		Random rand = new Random();

		int a = rand.nextInt(parentA.size()-1);
		int b = rand.nextInt(parentA.size());

		if(b<a){
			int swap = a+1;
			a = b;
			b = swap;
		}
		ArrayList<City> childA = new ArrayList<City>(parentA.getList());
		ArrayList<City> childB = new ArrayList<City>(parentB.getList());
		List<City> subA = childA.subList(a,b);
		List<City> subB = childB.subList(a,b);

		for(int i=a;i<b;i++){
			City cityA = subA.get(i-a);
			City cityB = childB.get(i-a);
			int j = childB.indexOf(cityA);

			childB.set(j,childB.get(i));
			childB.set(i,cityA);

			j = childA.indexOf(cityB);
			childA.set(j,childA.get(i));
			childA.set(i,cityB);
		}
		return new Children(new Individual(childA),new Individual(childB));
	}
}