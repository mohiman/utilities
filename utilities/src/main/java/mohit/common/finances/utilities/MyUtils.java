package mohit.common.finances.utilities;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import mohit.common.finances.utilities.data.AccountInfo;
import mohit.common.finances.utilities.data.TransactionInfo;

public class MyUtils {

	private static final String[] DATE_FORMATS = { "MM/dd/yyyy", "MMM dd, yyyy" };
	private static Logger logger = Logger.getLogger(MyUtils.class);
	private static NumberFormat FORMAT= NumberFormat.getNumberInstance();
	static
	{
		FORMAT.setMinimumFractionDigits(2);
	}
	

	
	public static Date getDateFromString(String passedInString) {
		Date dt = new Date();

		if (StringUtils.isNotBlank(passedInString)) {

			passedInString = passedInString.trim();
			try {
				dt = DateUtils.parseDate(passedInString, DATE_FORMATS);
			} catch (ParseException pse) {
				pse.printStackTrace();
			}
		}
		return dt;
	}

	public static Float getNumberFromString(String passedInString) {
		try {
			if (StringUtils.isNotBlank(passedInString)) 
			{
				passedInString=passedInString.replaceAll(",", "");
				if (passedInString.startsWith("$")) 
				{
					return NumberUtils.createFloat(passedInString.substring(1,passedInString.length() ));
				}
				else if (passedInString.startsWith("-$"))
				{
					return NumberUtils.createFloat(passedInString.substring(2,passedInString.length() ));
				}
						
				return NumberUtils.createFloat(passedInString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0f;
	}

	public static List<TransactionInfo> parseEmailContent(Multipart content, AccountInfo accountInfo) 
	{

		try {
			int multiPartCount = content.getCount();
			for (int i = 0; i < multiPartCount; i++) {
				BodyPart bodyPart = content.getBodyPart(i);
				Object obj;
				obj = bodyPart.getContent();
				if (obj instanceof String) 
				{
					String data = ((String)obj).toLowerCase();
					return parseEmail(data, accountInfo);
				} 
				else if (obj instanceof Multipart) 
				{
					parseEmailContent((Multipart) obj,accountInfo);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<TransactionInfo> parseEmail(String data,AccountInfo accountInfo)
	{
		List<TransactionInfo> infos = new ArrayList<TransactionInfo>();
//System.out.println(data);
		
		String date = "";
		String amount = "";
		String merchantName = "";
		while(true)
		{
			TransactionInfo info = new TransactionInfo(accountInfo.getAccountName());
			merchantName = getMatchedRegEx( accountInfo.getMrchntRegx(), data).replaceAll("\\<.*?>","").replaceAll("\\t", "").replaceAll("\\n", "");
			
			

			date = getMatchedRegEx( accountInfo.getDateRegex(), data);
			amount = getMatchedRegEx( accountInfo.getAmtRegex(), data);
			if (StringUtils.isNotBlank(date) && StringUtils.isNotBlank(amount)&& StringUtils.isNotBlank(merchantName))
			{
				System.out.println(merchantName);
				System.out.println(date);
				System.out.println(amount);
				
				data = data.substring(data.indexOf(date)+date.length());
				
				info.setTxnDate(MyUtils.getDateFromString(date));
				
				info.setAmountDebited(MyUtils.getNumberFromString(amount));
				
				info.setDescription(merchantName);
				
				if (StringUtils.isNotBlank(accountInfo.getMrchntRegxStart()) && 
						StringUtils.isNotBlank(accountInfo.getMrchntRegxStart()) )
				{
					info.setMerchantDisplayName(merchantName.replaceAll(accountInfo.getMrchntRegxStart(), "").trim().replaceAll(accountInfo.getMrchntRegxEnd(), "").trim() );
				}
				
				infos.add(info) ;
				
			}
			else
			{
				break;
			}
		}		
//		
//		while(true)
//		{
//				TransactionInfo info = new TransactionInfo(accountInfo.getAccountName());
//				
//				String date = getMatchedRegEx(accountInfo.getDateRegex(),data );
//				String amount = getMatchedRegEx(accountInfo.getAmtRegex(),data);
//				String merchantName = getMatchedRegEx(accountInfo.getMrchntRegx(),data );
//				
//				logger.debug("date = " + date + ", amount = " + amount + ", merchantName =" + merchantName );
//				
//				if (StringUtils.isNotBlank(date) && StringUtils.isNotBlank(amount) )
//				{
//					if (StringUtils.isNotBlank(merchantName))
//						data = data.substring(data.indexOf(merchantName)+merchantName.length());
//					else
//						data = data.substring(data.indexOf(date)+date.length());
//					
//					
//					info.setTxnDate(MyUtils.getDateFromString(date));
//					info.setAmountDebited(MyUtils.getNumberFromString(amount));
//					info.setDescription(merchantName);
//					if (StringUtils.isNotBlank(accountInfo.getMrchntRegxStart()) && 
//							StringUtils.isNotBlank(accountInfo.getMrchntRegxStart()) )
//					{
//						info.setMerchantDisplayName(merchantName.replaceAll(accountInfo.getMrchntRegxStart(), "").trim().replaceAll(accountInfo.getMrchntRegxEnd(), "").trim() );
//					}
////					System.out.println(info.toString() + " Place " + data.indexOf(date));
//					infos.add(info) ;
//				}
//				else
//				{
//					break;
//				}
//		}
		return infos;
	}
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
	public static String printTotals(List<TransactionInfo> transactionInfos, String whichField) {

		String whiteSpace = " ";
		StringBuilder builder = new StringBuilder();
		Iterator<TransactionInfo> iterator = transactionInfos.iterator();
		Float debit = new Float(0);
		while (iterator.hasNext()) {
			TransactionInfo info = iterator.next();
			builder.append("\r\n");

			debit += (info.getAmountDebited() == null ? 0f : info.getAmountDebited());
			
			builder.append(info.getName() );
			builder.append(whiteSpace);
			builder.append(DateFormatUtils.format( info.getTxnDate(), ConfigReader.getInstance().getProperty("sms.date.format", "MM/dd/yyyy") ) );
			builder.append(whiteSpace);
			if (StringUtils.isNotBlank(info.getMerchantDisplayName()))
			{
				builder.append(  info.getMerchantDisplayName()  );
			}
			else
			{
				builder.append(  info.getDescription()  );
			}
			builder.append(whiteSpace);
			builder.append("$" +  info.getAmountDebited()  );
			builder.append(whiteSpace);

		}
		builder.append("\r\n------------------------------------------");
		builder.append("\r\nTotal for today : $" + FORMAT.format(debit));

		System.out.println(builder.toString());
		return builder.toString();
	}

}
