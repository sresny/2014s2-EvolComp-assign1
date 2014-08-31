import java.util.ArrayList;
import java.util.Collections;
import static java.lang.Math.*;

class Individual implements Comparable<Individual>{
	ArrayList<City> cityList;
	private double dist;

	public Individual(ArrayList<City> c){
		cityList = new ArrayList<City>(c);
	}

	public void updateFitness(){
		dist = 0;
		for(int i=0; i<size(); i++){
			double dx = cityList.get(i).getX() - cityList.get((i+1)%size()).getX();
			double dy = cityList.get(i).getY() - cityList.get((i+1)%size()).getY();
			dist += sqrt(dx*dx + dy*dy);
		}
	}

	public Individual(Individual i){
		cityList = new ArrayList<City>(i.getList());
	}

	public void shuffle(){
		Collections.shuffle(cityList);
		updateFitness();
	}

	public ArrayList<City> getList(){
		return cityList;
	}

	public int size(){
		return cityList.size();
	}

	public double getFitness(){
		return dist;
	}

	@Override
	public int compareTo(Individual other){
		return Double.compare(this.getFitness(),other.getFitness());
	}

}