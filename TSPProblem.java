import java.util.ArrayList;
import java.lang.Integer;


class TSPProblem{
	//Parsing variables
	private static String myFilename;

	//Class variables
	private static ArrayList<City> cities;
	private static int myIterations = 1000;
	private static int myPopulationSize = 10;


	public static void main(String[] args) throws java.io.FileNotFoundException	{
		//Parse options
		if (args.length <2)
		{ System.out.println("Usage: java TSPProblem -f filename\n"+
			"Other options:\n"+
			"-v      verbose flag\n"+
			"-i NUM  number of iterations in simulation "+
			"(default "+myIterations+")\n"+
			"-p NUM  population size (default "+myPopulationSize+")");
			System.exit(0);
		}
		for (int i = 0; i<args.length; i++)
		{
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
			}
			
			Tools.debugPrintln(args[i]);
		}
		
		Tools.debugPrintln("Filename is "+myFilename);
		Tools.debugPrintln("#iterations is "+myIterations);
		Tools.debugPrintln("Population size is "+myPopulationSize);
		
		cities = Parser.loadCities(myFilename);


		Population p = new Population(cities,myPopulationSize);

		Crossover c = new PMXCrossover();;
		Mutator m = new InvertMutator();
		Selector s = new RouletteSelector(myPopulationSize);

		long startTime =System.nanoTime();
		for(int i=0; i<=myIterations;i++){
			p.crossover(c,0.75);
			p.mutateChildren(m,0.9);
			p.select(s);
			if(i%500==0){
				System.out.println("After "+i+" iterations");
				System.out.println("Average cost: "+p.average());
				System.out.println("Lowest cost: "+p.best().getFitness());
				System.out.println();
				if(p.average()-p.best().getFitness()<0.000001){
					System.out.println("Converged ");
					break;
				}
			}
		}
		
		int time =(int)((System.nanoTime() - startTime)/1000000000);

		System.out.println("Time elapsed: "+time+"s");
	}
}