import java.util.ArrayList;
import java.util.Random;
import static java.lang.Math.*;

public class InsertMutator implements Mutator{
	public void mutate(Individual ind){
		Random rand = new Random();

		int a = rand.nextInt(ind.size());
		int b = rand.nextInt(ind.size()-1);

		if(b>=a) b++;

		City c = ind.cityList.remove(max(a,b));

		ind.cityList.add(min(a,b)+1,c);
	}
}