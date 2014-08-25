import java.util.ArrayList;

class TSPProblem{
	public static void main(String[] args){
		int n = 100;
		int iterations = 10000;

		ArrayList<City> cities = new ArrayList<City>(51);

		cities.add(new City(1, 37, 52));
		cities.add(new City(2, 49, 49));
		cities.add(new City(3, 52, 64));
		cities.add(new City(4, 20, 26));
		cities.add(new City(5, 40, 30));
		cities.add(new City(6, 21, 47));
		cities.add(new City(7, 17, 63));
		cities.add(new City(8, 31, 62));
		cities.add(new City(9, 52, 33));
		cities.add(new City(10, 51, 21));
		cities.add(new City(11, 42, 41));
		cities.add(new City(12, 31, 32));
		cities.add(new City(13, 5, 25));
		cities.add(new City(14, 12, 42));
		cities.add(new City(15, 36, 16));
		cities.add(new City(16, 52, 41));
		cities.add(new City(17, 27, 23));
		cities.add(new City(18, 17, 33));
		cities.add(new City(19, 13, 13));
		cities.add(new City(20, 57, 58));
		cities.add(new City(21, 62, 42));
		cities.add(new City(22, 42, 57));
		cities.add(new City(23, 16, 57));
		cities.add(new City(24, 8, 52));
		cities.add(new City(25, 7, 38));
		cities.add(new City(26, 27, 68));
		cities.add(new City(27, 30, 48));
		cities.add(new City(28, 43, 67));
		cities.add(new City(29, 58, 48));
		cities.add(new City(30, 58, 27));
		cities.add(new City(31, 37, 69));
		cities.add(new City(32, 38, 46));
		cities.add(new City(33, 46, 10));
		cities.add(new City(34, 61, 33));
		cities.add(new City(35, 62, 63));
		cities.add(new City(36, 63, 69));
		cities.add(new City(37, 32, 22));
		cities.add(new City(38, 45, 35));
		cities.add(new City(39, 59, 15));
		cities.add(new City(40, 5, 6));
		cities.add(new City(41, 10, 17));
		cities.add(new City(42, 21, 10));
		cities.add(new City(43, 5, 64));
		cities.add(new City(44, 30, 15));
		cities.add(new City(45, 39, 10));
		cities.add(new City(46, 32, 39));
		cities.add(new City(47, 25, 32));
		cities.add(new City(48, 25, 55));
		cities.add(new City(49, 48, 28));
		cities.add(new City(50, 56, 37));
		cities.add(new City(51, 30, 40));


		Population p = new Population(cities,n);

		p.setCrossover(new OrderCrossover(),0.75);
		p.setMutator(new InvertMutator(),0.2);

		for(int i=0; i<iterations;i++){
			p.crossover();
			p.mutateChildren();
			p.select_tournament(150,100);
			if(i%500==0){
				System.out.println("Average cost: "+p.average());
				System.out.println("Lowest cost: "+p.best());
			}
		}

	}
}