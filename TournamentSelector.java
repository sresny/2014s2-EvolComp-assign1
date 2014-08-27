import java.util.ArrayList;
import java.util.Collections;
import static java.lang.Math.*;

public class TournamentSelector implements Selector{
	public TournamentSelector(int size, int winners){
		this.size = size;
		this.winners = winners;
	}

	public ArrayList<Individual> select(ArrayList<Individual> individuals){
		Collections.shuffle(individuals);
		ArrayList<Individual> victors = new ArrayList<Individual>((int)(individuals.size()/size)*winners);
		for(int i=size; i<individuals.size(); i+=size){
			ArrayList<Individual> competitors = new ArrayList<Individual>(individuals.subList(i-size,i));
			Collections.sort(competitors);
			victors.addAll(competitors.subList(0,winners));
		}

		return victors;
	}

}