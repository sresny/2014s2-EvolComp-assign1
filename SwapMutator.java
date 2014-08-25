import java.util.Collections;
import java.util.Random;

public class SwapMutator implements Mutator{

	public void mutate(Individual ind){
		Random rand = new Random();

		int a = rand.nextInt(ind.size());
		int b = rand.nextInt(ind.size()-1);

		if(b>=a) b++;

		Collections.swap(ind.cityList,a,b);
	}
}