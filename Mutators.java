class Mutators{

	public static void mutate_insert(Individual ind){
		Random rand = new Random();

		int a = rand.nextInt(ind.size());
		int b = rand.nextInt(ind.size()-1);

		if(b>=a) b++;

		City c = ind.cityList.remove(max(a,b));

		ind.cityList.add(min(a,b)+1,c);
	}

	public static void mutate_swap(Individual ind){
		Random rand = new Random();

		int a = rand.nextInt(ind.size());
		int b = rand.nextInt(ind.size()-1);

		if(b>=a) b++;

		Collections.swap(ind.cityList,a,b);
	}

	public static void mutate_invert(Individual ind){
		Random rand = new Random();

		int a = rand.nextInt(ind.size());
		int b = rand.nextInt(ind.size()-1);

		if(b>=a) b++;

		int half = (abs(a-b)+1)/2;

		for(int i=0; i<half;i++){
			Collections.swap(ind.cityList,max(a,b)-i,min(a,b)+i);
		}
	}

	public static void mutate_scramble(Individual ind,float p){
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