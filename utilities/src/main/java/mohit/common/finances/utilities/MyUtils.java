package mohit.common.finances.utilities;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
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
			if (StringUtils.isNotBlank(passedInString)) {
				return NumberUtils.createFloat(passedInString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0f;
	}

	public static TransactionInfo parseEmailContent(Multipart content, AccountInfo accountInfo) 
	{
		TransactionInfo info = new TransactionInfo();

		try {
			int multiPartCount = content.getCount();
			for (int i = 0; i < multiPartCount; i++) {
				BodyPart bodyPart = content.getBodyPart(i);
				Object obj;
				obj = bodyPart.getContent();
				if (obj instanceof String) 
				{
					String data = ((String)obj).toLowerCase();

					int start = (data.indexOf(accountInfo.getAccountEmailParseStartText()));
					int end = (data.indexOf(accountInfo.getAccountEmailParsEendText()));
					
					if (start >= 0 && end >= 0)
					{
						String datePattern = "(.*)(\\w\\w\\w\\s\\d\\d,\\s\\d\\d\\d\\d)(.*)";
						Pattern pattern = Pattern.compile(datePattern);

						String someOtherStr = "\\s\\d\\d\\d\\d\\s\\w\\w\\s";
						Pattern somePattern = Pattern.compile(someOtherStr);

						String merchantEndString = "a pending";
						if (data != null) {
							info.setName(accountInfo.getAccountName());
							// System.out.println("\n"+data[i]);
							// Get the date
							Matcher m = pattern.matcher(data);
							String dateOfTxn = "";
							if (m.find()) {

								dateOfTxn = m.group(m.groupCount() - 1).toString();
								info.setTxnDate(MyUtils.getDateFromString(dateOfTxn));
							}
							// System.out.print("\n"+ dateOfTxn +"\t");

							// Currency
							int startCurrency = data.indexOf("$");
							int endCurrency = data.indexOf(" ", startCurrency);
							// System.out.print(data.substring(startCurrency+1, endCurrency) );
							info.setAmountDebited(MyUtils.getNumberFromString(data.substring(startCurrency + 1, endCurrency)));

							// Merchant
							m = somePattern.matcher(data);
							int merchantEndIdx = data.indexOf(merchantEndString);
							if (m.find() && merchantEndIdx >= m.start()) {
								// System.out.print("\t" + data.substring(m.start()+9 ,
								// merchantEndIdx) );
								info.setDescription(data.substring(m.start() + 9, merchantEndIdx));
							}
						}
					}
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
		return info;
	}
	
	
	
	public static TransactionInfo parseEmailText1(String data, String accountInd) {
		String datePattern = "(.*)(\\w\\w\\w\\s\\d\\d,\\s\\d\\d\\d\\d)(.*)";
		Pattern pattern = Pattern.compile(datePattern);

		String someOtherStr = "\\s\\d\\d\\d\\d\\s\\w\\w\\s";
		Pattern somePattern = Pattern.compile(someOtherStr);

		String merchantEndString = "a pending";
		TransactionInfo info = new TransactionInfo();
		if (data != null) {
			info.setName(ConfigReader.getInstance().getProperty("account.1.name"));
			// System.out.println("\n"+data[i]);
			// Get the date
			Matcher m = pattern.matcher(data);
			String dateOfTxn = "";
			if (m.find()) {

				dateOfTxn = m.group(m.groupCount() - 1).toString();
				info.setTxnDate(MyUtils.getDateFromString(dateOfTxn));
			}
			// System.out.print("\n"+ dateOfTxn +"\t");

			// Currency
			int startCurrency = data.indexOf("$");
			int endCurrency = data.indexOf(" ", startCurrency);
			// System.out.print(data.substring(startCurrency+1, endCurrency) );
			info.setAmountDebited(MyUtils.getNumberFromString(data.substring(startCurrency + 1, endCurrency)));

			// Merchant
			m = somePattern.matcher(data);
			int merchantEndIdx = data.indexOf(merchantEndString);
			if (m.find() && merchantEndIdx >= m.start()) {
				// System.out.print("\t" + data.substring(m.start()+9 ,
				// merchantEndIdx) );
				info.setDescription(data.substring(m.start() + 9, merchantEndIdx));
			}
		}
		return info;
	}

	
	private static NumberFormat FORMAT= NumberFormat.getNumberInstance();
	static
	{
		FORMAT.setMinimumFractionDigits(2);
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
			
			builder.append(DateFormatUtils.format( info.getTxnDate(), ConfigReader.getInstance().getProperty("sms.date.format", "MM/dd/yyyy") ) + 
					whiteSpace + info.getDescription() + whiteSpace + "$" +  info.getAmountDebited() + "\n");
			
			System.out.println(info.getName() + whiteSpace + DateFormatUtils.format( info.getTxnDate(), ConfigReader.getInstance().getProperty("sms.date.format", "MM/dd/yyyy") ) + 
					whiteSpace + info.getDescription() + whiteSpace + "$" +  info.getAmountDebited() + "\n");
		}
		System.out.println("Total for today : " + FORMAT.format(debit) + "\r\n");
		builder.append("\r\n------------------------------------------");
		builder.append("\r\nTotal for today : $" + FORMAT.format(debit));
		return builder.toString();
	}

}
