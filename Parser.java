import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;

class Parser{
	//Parse file from options
		//FEATURE: proper exiting with file problems


	public static ArrayList<City> loadCities(String myFilename) throws FileNotFoundException{
		int testDimension;
		String testName;
		String testComment;

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
		return cities;
	}
}