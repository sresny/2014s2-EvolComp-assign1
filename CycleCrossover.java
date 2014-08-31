import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import static java.lang.Math.*;

class CycleCrossover implements Crossover{
	public Children crossover(Individual parentA, Individual parentB){
		
		Random rand = new Random();

		ArrayList<Integer> locs = new ArrayList<Integer>(parentA.size());
		ArrayList<City> childA = new ArrayList<City>(parentA.getList());
		ArrayList<City> childB = new ArrayList<City>(parentB.getList());

		while(locs.size()>0){
			ArrayList<Integer> cycle = new ArrayList<Integer>(parentA.size());
			cycle.add(locs.remove(0));
			City target = childA.get(cycle.get(0));
			int index;
			while((index = childB.indexOf(target)) != (int) cycle.get(0)){
				cycle.add(index);
				target = childA.get(index);
				locs.remove((Integer) index);
			}
			if(rand.nextBoolean()){
				for(int i:cycle){
					City temp = childA.get(i);
					childA.set(i,childB.get(i));
					childB.set(i,childA.get(i));
				}
			}
		}

		return new Children(new Individual(childA),new Individual(childB));
	}
}