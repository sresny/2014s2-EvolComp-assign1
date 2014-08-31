import java.util.ArrayList;
import java.lang.Integer;


class TSPProblem{
	//Parsing variables
	private static String myFilename;

	//Class variables
	private static ArrayList<City> cities;
	private static int myIterations = 1000;
	private static int myPopulationSize = 10;
	private static int terminationThreshold = 500;

	private static Crossover c;
	private static Mutator m;
	private static Selector s;

	public static void main(String[] args) throws java.io.FileNotFoundException	{

		//Parse options
		if (args.length <2)
		{
			System.out.println("Usage: java TSPProblem -f filename\n"+
			"Required options:\n"+
			"-c      name of crossover operator (pmx/cycle/order)\n"+
			"-m      name of mutator operator (insert/invert/swap/scramble)\n"+
			"-s      name of selection operator (roulette/sus/tournament)\n"+
			"Other options:\n"+
			"-v      verbose flag\n"+
			"-t NUM  repeated optimum termination threshold "+
			"(default "+terminationThreshold+")\n"+
			"-i NUM  number of iterations in simulation "+
			"(default "+myIterations+")\n"+
			"-p NUM  population size (default "+myPopulationSize+")");
			System.exit(0);
		}
		for (int i = 0; i<args.length; i++)
		{	
			//Parse command line arguments
			if (args[i].toLowerCase().equals("-f"))
			{
				myFilename = args[i+1];
			} else if (args[i].toLowerCase().equals("-v"))
			{
				Tools.setDebug(true);
			} else if (args[i].toLowerCase().equals("-i"))
			{
				myIterations = Integer.parseInt(args[i+1]);
			}else if (args[i].toLowerCase().equals("-p"))
			{
				myPopulationSize = Integer.parseInt(args[i+1]);
			}else if (args[i].toLowerCase().equals("-c"))
			{
				//Produce appropriate crossover object
				String name = args[i+1];
				switch(name){
					case "pmx":
						c = new PMXCrossover();
						break;
					case "cycle":
						c = new CycleCrossover();
						break;
					case "order":
						c = new OrderCrossover();
						break;
				}
			}else if (args[i].toLowerCase().equals("-m"))
			{
				//Produce appropriate mutator object
				String name = args[i+1];
				switch(name){
					case "insert":
						m = new InsertMutator();
						break;
					case "invert":
						m = new InvertMutator();
						break;
					case "swap":
						m = new SwapMutator();
						break;
					case "scramble":
						m = new ScrambleMutator();
						break;
				}
			}else if (args[i].toLowerCase().equals("-s"))
			{
				//Produce appropriate selector object
				String name = args[i+1];
				switch(name){
					case "roulette":
						s = new RouletteSelector();
						break;
					case "sus":
						s = new SUSSelector();
						break;
					case "tournament":
						s = new TournamentSelector(4,2);
						break;
				}
			}
			
			Tools.debugPrintln(args[i]);
		}
		

		Tools.debugPrintln("Filename is "+myFilename);
		Tools.debugPrintln("#iterations is "+myIterations);
		Tools.debugPrintln("Population size is "+myPopulationSize);
		
		//Load city data from file
		cities = Parser.loadCities(myFilename);

		//Pass values to selector object
		s.setPopulation(myPopulationSize);

		//Produce population
		Population p = new Population(cities,myPopulationSize);

		//Start timing
		long startTime =System.nanoTime();

		//Initialise variables for tracking convergence
		int sameBest = 0;
		double bestFitness = p.best().getFitness();


		for(int i=0; i<=myIterations;i++){
			//Apply operatiosn with given probabilities
			p.crossover(c,0.75);
			p.mutateChildren(m,0.9);
			p.select(s);

			//Test for convergence (repeated same value of best fitness)
			double newBest = p.best().getFitness();
			if(newBest<bestFitness){
				bestFitness = newBest;
				sameBest = 0;
			}else{
				sameBest++;
			}
			if(sameBest>=terminationThreshold){
				System.out.println("Terminated early due to unchanging best solution");
				break;
			}

			//Output status every 500 iterations
			if(i%500==0){
				System.out.println("After "+i+" iterations");
				System.out.println("Average cost: "+p.average());
				System.out.println("Lowest cost: "+p.best().getFitness());
				System.out.println();
			}
		}
		//Calculate run time in milliseconds
		int time =(int)((System.nanoTime() - startTime)/1000000);

		System.out.println("Time elapsed: "+time/1000.0+"s");
	}
}