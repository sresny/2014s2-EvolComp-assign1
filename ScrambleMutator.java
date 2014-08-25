import java.util.ArrayList;
import java.util.Random;

public class ScrambleMutator implements Mutator{

	private double p;

	public ScrambleMutator(double p){
		this.p = p;
	}
	public void mutate(Individual ind){
		Random rand = new Random();
		ArrayList<Integer> locs = new ArrayList<Integer>();
		ArrayList<City> cities = new ArrayList<City>();
		for(int i=0;i<ind.size();i++){
			if(rand.nextFloat()<p){
				locs.add(i);
			}
		}

		for(int i=locs.size()-1;i>=0;i--){
			cities.add(ind.cityList.remove((int)locs.get(i)));
		}

		for(int i=cities.size();i>0;i--){
			ind.cityList.add(locs.remove(0),cities.remove(rand.nextInt(i)));
		}
	}
}