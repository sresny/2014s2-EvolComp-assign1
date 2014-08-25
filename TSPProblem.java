import java.util.ArrayList;
import java.lang.Integer;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

class TSPProblem{
	//Parsing variables
	private static String myFilename;
	private static String testName;
	private static String testComment;
	private static int testDimension;

	//Class variables
	ArrayList<City> cities;
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
		
		
		//Parse file from options
		//FEATURE: proper exiting with file problems
		BufferedReader reader = new BufferedReader(new FileReader(myFilename));
		Scanner input = new Scanner(reader);

		//Read metadata FIXME:semicolon in names/comments will break things
		//get the name out
		Scanner parseScanner = new Scanner(input.nextLine()).useDelimiter("\\s*:\\s*");
		parseScanner.next();
		testName = parseScanner.next();
		Tools.debugPrintln("testname is "+testName);
		//get the comment 
		parseScanner = new Scanner(input.nextLine()).useDelimiter("\\s*:\\s*");
		parseScanner.next();
		testComment = parseScanner.next();
		Tools.debugPrintln("commentname is "+testComment);
		//ignore type; get dimension
		input.nextLine();
		parseScanner = new Scanner(input.nextLine()).useDelimiter("\\s*:\\s*");
		parseScanner.next();
		testDimension = parseScanner.nextInt();
		Tools.debugPrintln("there are "+testDimension+" cities");
		//Pre-allocate for efficiency
		ArrayList<City> cities = new ArrayList<City>(testDimension);
		//skip two lines
		input.nextLine();
		input.nextLine();
					
		//or to process numbers line by line
		String line = input.nextLine();
		parseScanner = new Scanner(line);
		while(! "EOF".equals(line.toUpperCase())) 
		{
			City c = new City(parseScanner.nextInt(),parseScanner.nextInt(),parseScanner.nextInt());
			cities.add(c);
			
			line = input.nextLine();
			parseScanner = new Scanner(line);
		}

		input.close();


		Population p = new Population(cities,myPopulationSize);

		p.setCrossover(new OrderCrossover(),0.75);
		p.setMutator(new InvertMutator(),0.9);

		long startTime =System.nanoTime();
		for(int i=0; i<=myIterations;i++){
			p.crossover();
			p.mutateChildren();
			p.select_tournament(2*myPopulationSize,myPopulationSize);
			if(i%500==0){
				System.out.println("After "+i+" iterations");
				System.out.println("Average cost: "+p.average());
				System.out.println("Lowest cost: "+p.best());
				System.out.println();
				if(p.average()-p.best()<0.000001){
					System.out.print("Converged ");
					break;
				}
			}
		}
		
		int time =(int)((System.nanoTime() - startTime)/1000000000);

		System.out.println("Time elapsed: "+time+"s");
	}
}