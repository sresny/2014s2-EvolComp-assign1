import java.util.ArrayList;
import java.lang.Integer;
import java.io.PrintWriter;

class Test{
	//Parsing variables
	private static String myFilename;
	private static String myOutname = "";

	//Class variables
	private static ArrayList<City> cities;
	private static int myIterations = 1000;
	private static int myPopulationSize = 10;
	private static int numTests = 100;
	private static int terminationThreshold = 500;


	public static void main(String[] args) throws java.io.FileNotFoundException, java.io.UnsupportedEncodingException	{
		//Parse options
		if (args.length <4)
		{ System.out.println("Usage: java Test -f filename -o outputfilename\n"+
			"Other options:\n"+
			"-v      verbose flag\n"+
			"-i NUM  number of iterations in simulation "+
			"(default "+myIterations+")\n"+
			"-t NUM  repeated optimum termination threshold "+
			"(default "+terminationThreshold+")\n"+
			"-n NUM  number of tests per setup "+
			"(default "+numTests+")\n"+
			"-p NUM  population size (default "+myPopulationSize+")");
			System.exit(0);
		}
		for (int i = 0; i<args.length; i++)
		{
			if (args[i].toLowerCase().equals("-f"))
			{
				myFilename = args[i+1];
			} else if (args[i].toLowerCase().equals("-o"))
			{
				myOutname = args[i+1];
			} else if (args[i].toLowerCase().equals("-v"))
			{
				Tools.setDebug(true);
			} else if (args[i].toLowerCase().equals("-i"))
			{
				myIterations = Integer.parseInt(args[i+1]);
			}else if (args[i].toLowerCase().equals("-p"))
			{
				myPopulationSize = Integer.parseInt(args[i+1]);
			}else if (args[i].toLowerCase().equals("-t"))
			{
				terminationThreshold = Integer.parseInt(args[i+1]);
			}else if (args[i].toLowerCase().equals("-n"))
			{
				numTests = Integer.parseInt(args[i+1]);
			}
			
			Tools.debugPrintln(args[i]);
		}
		
		Tools.debugPrintln("Filename is "+myFilename);
		Tools.debugPrintln("Output filename is "+myOutname);
		Tools.debugPrintln("#iterations is "+myIterations);
		Tools.debugPrintln("Population size is "+myPopulationSize);
		
		cities = Parser.loadCities(myFilename);

		Crossover[] crossovers = {new PMXCrossover(), new CycleCrossover(), new OrderCrossover()};
		Mutator[] mutators = {new InvertMutator(), new ScrambleMutator(0.5), new InsertMutator(), new SwapMutator()};
		Selector[] selectors = {new RouletteSelector(myPopulationSize), new TournamentSelector(4,2), new SUSSelector(myPopulationSize)};

		String csvOut = "";
		int combId = 0;
		int combs = 3*4*3;

		PrintWriter writer = new PrintWriter(myOutname, "UTF-8");


		for(Crossover c : crossovers){
			for(Mutator m : mutators){
				for(Selector s : selectors){
					Population p = new Population(cities,myPopulationSize);

					combId ++;
					System.out.println("Setup "+combId+" of "+combs);
					System.out.println("Using crossover "+c.getClass().getName());
					System.out.println("Using mutator "+m.getClass().getName());
					System.out.println("Using selector "+s.getClass().getName());
					System.out.println();
					for(int test=0; test<numTests; test++){
						System.out.println("Test "+test+" of "+numTests);
						long startTime =System.nanoTime();
						int sameBest = 0;
						double bestFitness = p.best().getFitness();
						int i;
						for(i=0; i<=myIterations;i++){
							p.crossover(c,0.75);
							p.mutateChildren(m,0.9);
							p.select(s);

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
						}

						int time =(int)((System.nanoTime() - startTime)/1000000);
						System.out.println("Completed "+i+" iterations in "+time/1000.0+" seconds.");
						System.out.println("Best solution:");
						Individual bestIndividual = p.best();
						for(City city : bestIndividual.cityList){
							System.out.print(city.getId()+" ");
						}
						System.out.println();
						System.out.println("With path length "+bestIndividual.getFitness());
						writer.println(c.getClass().getName()+","+m.getClass().getName()+","+s.getClass().getName()+","+time/1000.0+","+i+","+bestIndividual.getFitness());
					System.out.println();
					}
				}
			}
		}
		writer.close();
	}
}