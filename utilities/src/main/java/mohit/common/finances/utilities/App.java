package mohit.common.finances.utilities;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        List<String> data = null;
        App app = new App();
        try
        {
        	data = app.readFile();
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        app.createTransactionInfoObjects(data);
    }
    
    private List<String> readFile() throws IOException
    {
    	 File file = new File("files/Transactions-Download-05-11-2016.csv");
    	 List<String> lines = FileUtils.readLines(file, "UTF-8");
    	 
    	 return lines;
    }
    
    private void createTransactionInfoObjects(List<String> data)
    {
	   	 if (data!=null)
	   	 {
	   		 Iterator<String> iterator = data.iterator();
	   		 while(iterator.hasNext())
	   		 {
	   			 String currentLine  = iterator.next();
	   			 List<String> items = Arrays.asList(currentLine.split("\\s*,\\s*"));
	   			 System.out.println("items " + items);
	   		 }	
	   	 }

    }
    
}
