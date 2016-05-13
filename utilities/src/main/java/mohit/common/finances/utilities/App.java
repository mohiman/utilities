package mohit.common.finances.utilities;

/**
 *	@author mohiman
 *	This utility will read a CSV file
 */
public class App 
{
    public static void main( String[] args )
    {
    	try
    	{
    		CSVFileReader.parseData("datafile/Transactions-Download-05-11-2016.csv", "CC1");
    	}
    	catch (Exception e)
    	{
    		System.out.println("Error while parsing CSV file");
    		e.printStackTrace();
    	}
    }
}
