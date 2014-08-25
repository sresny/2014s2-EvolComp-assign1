import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import static java.lang.Math.*;

public class InvertMutator implements Mutator{
	public void mutate(Individual ind){
		Random rand = new Random();

		int a = rand.nextInt(ind.size());
		int b = rand.nextInt(ind.size()-1);

		if(b>=a) b++;

		int half = (abs(a-b)+1)/2;

		for(int i=0; i<half;i++){
			Collections.swap(ind.cityList,max(a,b)-i,min(a,b)+i);
		}
	}
}