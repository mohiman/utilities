package mohit.common.finances.utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import mohit.common.finances.utilities.data.TransactionInfo;

public class CSVFileReader 
{
	
	//Define Format
	//Pass in Data
	//Create TransactionInfo Object
	//Return a List of Transaction Info Objects
	
	
	public static List<TransactionInfo> parseData(String fileName, String institutionName) throws Exception
	{
		ArrayList<List> parsedListedData = new ArrayList<List>();
		
		List<String> fileData = readFile(fileName);
		
		if (fileData.isEmpty()) return null;
		
		Iterator<String> iterator = fileData.iterator();
		
		while(iterator.hasNext())
		{
			String currentLine = iterator.next();
			List<String> splitLine = Arrays.asList(currentLine.split("\\s*,\\s*"));
//			System.out.println("splitLine = " + splitLine );
			parsedListedData.add(splitLine);
		}
		//here I need to put the logic to read different formats of the csv files.... for now I only have one format, eventually the format needs to be outside in a config
		return getTransactionInfoObjectFromStringList(institutionName, parsedListedData, true);
	}
	
    private static List<String> readFile(String fileName) throws IOException
    {
    	 File file = new File(fileName);
    	 List<String> lines = FileUtils.readLines(file, "UTF-8");
    	 
    	 return lines;
    }
    
    private static List<TransactionInfo> getTransactionInfoObjectFromStringList(String name, List<List> parsedListedData, boolean ignoreFirstRow) throws Exception
    {
    
    	/**
			Stage	 Transaction Date	 Posted Date	 Card No.	 Description	 Category	 Debit	 Credit
			POSTED	5/8/2016	5/10/2016	9125	DUNKIN #300342 Q35	Dining	8.32	
			POSTED	5/8/2016	5/10/2016	9125	DSW FAIRFIELD CENTRE	Merchandise	47.77	
			POSTED	5/8/2016	5/10/2016	9125	DSW FAIRFIELD CENTRE	Merchandise	37.17	
    	 */
    	List <TransactionInfo> txnInfos = new ArrayList<TransactionInfo>();
		Iterator<List> iterator = parsedListedData.iterator();
		if (ignoreFirstRow)
		{
			System.out.println("IgnoringXXXXX: " +  iterator.next());
			
		}
		
		
		while(iterator.hasNext())
		{
			List<String> currentRow = iterator.next();
	    	TransactionInfo transactionInfo = new TransactionInfo();
//			System.out.println("Parsing this Row: " +  currentRow.toString());
	    	
	    	if (currentRow!= null && currentRow.size() >= 7)
	    	{
		    	transactionInfo.setName(name);
		    	transactionInfo.setTxnStage(currentRow.get(0));
		    	transactionInfo.setTxnDate(MyUtils.getDateFromString(currentRow.get(1)));
		    	transactionInfo.setPostDate(MyUtils.getDateFromString(currentRow.get(2)));
		    	transactionInfo.setAccountEndingIn(currentRow.get(3));
		    	transactionInfo.setDescription(currentRow.get(4));
		    	transactionInfo.setCategory(currentRow.get(5));
//		    	System.out.println("Passing in this number for conversion:: " + currentRow.get(6) );
		    	transactionInfo.setAmountDebited(MyUtils.getNumberFromString(currentRow.get(6)));
		    	
		    	//there is a debit transaction
		    	if (currentRow.size() > 7)
		    		transactionInfo.setAmountCredited(MyUtils.getNumberFromString(currentRow.get(7)));
		    	
//		    	System.out.println(transactionInfo.toString());
		    	txnInfos.add(transactionInfo);
	    	}
	    	else
	    	{
	    		System.out.println("Skipping, invalid row size, expected 8, current row size is :" + (currentRow!=null ? ""+currentRow.size() : "null"));
	    	}
		}    	
    	return txnInfos;
    }
	

}
