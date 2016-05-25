package mohit.common.finances.utilities;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NumberUtils;
import org.apache.log4j.Logger;

import mohit.common.finances.utilities.data.AccountInfo;
import mohit.common.finances.utilities.data.TransactionInfo;

/**
 *	@author mohiman
 *	This utility will read a CSV file
 */
public class App 
{
	private static Logger logger = Logger.getLogger(App.class);
    public static void main( String[] args )
    {
    	try
    	{
//    		Find out how many accounts from config
//    		Create a List of Account objects, pass that over to the checkMaill Object for parsing
    		
    		
    		
    		
    		List<AccountInfo> accountInfos = getAccounts();
    		
    		if (accountInfos!=null && accountInfos.size() > 0 )
    		{
	    		List<TransactionInfo> transactionInfos = MailUtil.checkMail(accountInfos);
	//
	    		String message = MyUtils.printTotals(transactionInfos, null);
	    		
	    		if (logger.isDebugEnabled())
	    		{
	    			logger.debug("Sending this message as SMS " );
	    			logger.debug(" " + message );
	    		}
	    		
	    		MailUtil.sendEmail(message);
    		}
    	}
    	catch (Exception e)
    	{
    		System.out.println("Error while parsing CSV file");
    		e.printStackTrace();
    	}
    }
    
    
    
    
    private static List<AccountInfo> getAccounts()
    {
    	int totalAccounts = Integer.decode(ConfigReader.getInstance().getProperty(ConfigReader.TOTAL_ACCOUNTS, "0"));
		List<AccountInfo> accountInfos = new ArrayList<AccountInfo>();

    	if ( totalAccounts == 0)
    	{
    		logger.error("Error getting the accounts information, please ensure that Config.properties is propery updates with \"total.accounts\" property", new Exception("Missing Property total.accounts"));
    	}
    	else
    	{
    		for (int i=0; i<totalAccounts; i++)
    		{
    			
    			AccountInfo accountInfo = new AccountInfo();
    			accountInfo.setAccountEmailFrom(ConfigReader.getInstance().getProperty("account."+(i+1) + ".emailfrom"));
    			accountInfo.setAccountEmailParsEendText(ConfigReader.getInstance().getProperty("account."+(i+1) + ".endText"));
    			accountInfo.setAccountEmailParseStartText(ConfigReader.getInstance().getProperty("account."+(i+1) + ".startText"));
    			accountInfo.setAccountEmailSubLine(ConfigReader.getInstance().getProperty("account."+(i+1) + ".subject.line"));
    			accountInfo.setAccountName(ConfigReader.getInstance().getProperty("account."+(i+1) + ".name"));
    			
    			if(logger.isDebugEnabled())
    			{
    				logger.debug(accountInfo);
    			}
    			
    			accountInfos.add(accountInfo);
    		}
    	}
    	return accountInfos;
    }
    

}
