import java.util.ArrayList;
import java.util.Collections;

class Individual implements Comparable<Individual>{
	ArrayList<City> cityList;

	public Individual(ArrayList<City> c){
		cityList = new ArrayList<City>(c);
	}

	public Individual(Individual i){
		cityList = new ArrayList<City>(i.getList());
	}

	public void shuffle(){
		Collections.shuffle(cityList);
	}

	public ArrayList<City> getList(){
		return cityList;
	}

	public int size(){
		return cityList.size();
	}

	public int getFitness(){
		return 0;
	}

	@Override
	public int compareTo(Individual other){
		return Integer.compare(this.getFitness(),other.getFitness());
	}

}