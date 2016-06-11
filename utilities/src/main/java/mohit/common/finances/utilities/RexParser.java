package mohit.common.finances.utilities;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import mohit.common.finances.utilities.data.AccountInfo;
import mohit.common.finances.utilities.data.TransactionInfo;

public class RexParser {

	
	private static String getMatchedRegEx(String regEx, String data) 
	{
		Pattern p = Pattern.compile(regEx);
		Matcher m1 = p.matcher(data ) ;
		m1.groupCount();
		
	    while(m1.find())
	    {
	    	return data.substring(m1.start(), m1.end());
	    }
	    return "";
	}

	public static void main(String[] args)
	{
		
		
		
		String sampleStrCapOne = "As requested, we are notifying you that on MAY 22, 2016 at "
					  + "CVS/PHARMACY # a pending authorization or purchase in the "
					  + "amount of $6.98 was placed or charged on your Capital One® VISA SIGNATURE account";
		
		
		
		String sampleStr = 	"";
		try
		{
			sampleStr = FileUtils.readFileToString(new File("C:/users/mohiman/desktop/dcuEmail-two-Txns.txt"));
		}
		catch ( Exception e)
		{
			e.printStackTrace();
		}

		String datePattern="[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}";
		String amtPattern="\\-\\$[0-9]*?\\,??[0-9]+?\\.[0-9]{2}";
		String mrchntPattern="WITHDRAWAL|TRANSFER_DEBIT|CHECK|CHK";		
		System.out.println(sampleStr);
		int pos = 0;
		String merchantName = "";
		String date = "";
		String amt = "";
		
		while(true)
		{
			merchantName = getMatchedRegEx( mrchntPattern, sampleStr);
			date = getMatchedRegEx( datePattern, sampleStr);
			amt = getMatchedRegEx( amtPattern, sampleStr);
			if (StringUtils.isNotBlank(date) && StringUtils.isNotBlank(amt)&& StringUtils.isNotBlank(merchantName))
			{
				System.out.println(merchantName);
				System.out.println(date);
				System.out.println(amt);
				
				sampleStr = sampleStr.substring(sampleStr.indexOf(date)+date.length());
				
			}
			else
			{
				break;
			}
		}
		
//		
//		
		
		
	}
}
