package mohit.common.finances.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mohit.common.finances.utilities.data.AccountInfo;

public class RexParser {

	public static void main(String[] args)
	{
		
		
		
		String sampleStrCapOne = "As requested, we are notifying you that on MAY 22, 2016 at "
					  + "CVS/PHARMACY # a pending authorization or purchase in the "
					  + "amount of $6.98 was placed or charged on your Capital One® VISA SIGNATURE account";
		
		
		String sampleStr = "	<td align=\"left\">check</td>"+
																		"	<td align=\"right\">-$70.00</td>"+
																		"	<td align=\"center\">05/26/2016</td>"+
																		"	<td align=\"center\">posted</td>"+
																		"</tr>";

		
		String pattrenStart = "at";
		String patternEnd = "a pending";
		String mrchntName = "at CVS/PHARMACY # a pending";
		
		
		System.out.println(mrchntName);
//		System.out.println(  mrchntName.substring(pattrenStart.length()).substring(0, mrchntName.indexOf(patternEnd) - 2 ).trim()  );
		
		String before = mrchntName.substring (pattrenStart.length()).trim() ;
		
		System.out.println(before);
		
		System.out.println( mrchntName.replaceAll(patternEnd, "").replaceAll(pattrenStart, "").trim())	;

		System.out.println(sampleStr);
		String datePattern =  "[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}";
		String amountPattern = "\\-\\$\\d*\\.\\d*";
		String merchantNamePattern  ="WITHDRAWAL|TRANSFER_DEBIT|POSTED";
		

//		String merchantNamePattern  ="WITHDRAWAL|TRANSFER_DEBIT|POSTED";
		
		
		Pattern pattern = Pattern.compile(merchantNamePattern.toLowerCase());
		Matcher m = pattern.matcher(sampleStr.toLowerCase()) ;
		
		
//		account.1.date.regex=[a-zA-Z]{3}\\s[0-9]{1,2}, [0-9]{4}
//		account.1.amount.regex=\\$\\d*\\.\\d*
//		############this is optional, better to provide, before and after, the system will extract merchant name itself.
//		#############account.1.mrchnt.regex=\\sat\\s(.)+\\sa pending
//		account.1.mrchnt.regex.start=at
//		account.1.mrchnt.regex.end=a pending
		
		
		
		
		int count = 0;
System.out.println(count);
	       while(m.find()) {
	         count++;
	         System.out.println("Match number "+count);
	         System.out.println("start(): "+m.start());
	         System.out.println("end(): "+m.end());
	         
	         System.out.println(sampleStr.substring(m.start(), m.end()));
	      }

		
	}
}
