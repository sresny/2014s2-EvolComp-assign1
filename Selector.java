import java.util.ArrayList;
public interface Selector{
	ArrayList<Individual> select(ArrayList<Individual> individuals);
	void setPopulation(int population);
}