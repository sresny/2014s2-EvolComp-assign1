public class Tools
{
	private static boolean debug;

	public static void setDebug(boolean b)
	{ debug = b; }
	public static void debugPrintln(Object s)
	{
		if (debug)
		{
			System.out.println(s);
		}
	}
}
