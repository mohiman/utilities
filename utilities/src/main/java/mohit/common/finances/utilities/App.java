package mohit.common.finances.utilities;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.StringUtils;
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
	    		String message = MyUtils.printTotals(transactionInfos, null);
    			logger.info("Sending this message as SMS " );
    			logger.info(" " + message );
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
    			accountInfo.setAccountEmailFrom(ConfigReader.getInstance().getProperty("account."+(i+1) + ".email.from"));
    			accountInfo.setAccountName(ConfigReader.getInstance().getProperty("account."+(i+1) + ".name"));
    			accountInfo.setDateRegex(ConfigReader.getInstance().getProperty("account."+(i+1) + ".date.regex"));
    			accountInfo.setAmtRegex(ConfigReader.getInstance().getProperty("account."+(i+1) + ".amount.regex"));
    			accountInfo.setMrchntRegxStart(ConfigReader.getInstance().getProperty("account."+(i+1) + ".mrchnt.regex.start"));
    			accountInfo.setMrchntRegxEnd(ConfigReader.getInstance().getProperty("account."+(i+1) + ".mrchnt.regex.end"));
    			
    			if (StringUtils.isNotEmpty(accountInfo.getMrchntRegxStart()) && StringUtils.isNotEmpty(accountInfo.getMrchntRegxEnd()) )
    			{
    				accountInfo.setMrchntRegx(   "\\s"+accountInfo.getMrchntRegxStart()+"\\s(.)+\\s"+accountInfo.getMrchntRegxEnd());	
    			}
    			else
    			{
    				accountInfo.setMrchntRegx(   "\\s"+ConfigReader.getInstance().getProperty("account."+(i+1) + ".mrchnt.regex")+"\\s");
    			}
    			
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
