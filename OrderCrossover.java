import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import static java.lang.Math.*;

class OrderCrossover implements Crossover{
	public Children crossover(Individual parentA, Individual parentB){
		
		Random rand = new Random();

		int a = rand.nextInt(parentA.size()-1);
		int b = rand.nextInt(parentA.size()-1);

		ArrayList<City> childA = new ArrayList<City>();
		ArrayList<City> childB = new ArrayList<City>();

		int end = max(a,b)+1;
		int start = min(a,b);

		int indexA = end;
		int indexB = end;

		for(int i=start;i<end;i++){
			childA.add(parentA.cityList.get(i));
			childB.add(parentB.cityList.get(i));
		}

		for(int i=end;i<parentA.size();i++){
			while(childA.contains(parentB.cityList.get(indexB))){
				indexB = (indexB+1)%parentA.size();
			}
			while(childB.contains(parentA.cityList.get(indexA))){
				indexA = (indexA+1)%parentA.size();
			}

			childA.add(parentB.cityList.get(indexB));
			childB.add(parentA.cityList.get(indexA));
		}
		for(int i=0;i<start;i++){
			while(childA.contains(parentB.cityList.get(indexB))){
				indexB = (indexB+1)%parentA.size();
			}
			while(childB.contains(parentA.cityList.get(indexA))){
				indexA = (indexA+1)%parentA.size();
			}

			childA.add(0,parentB.cityList.get(indexB));
			childB.add(0,parentA.cityList.get(indexA));
		}

		return new Children(new Individual(childA),new Individual(childB));
	}
}