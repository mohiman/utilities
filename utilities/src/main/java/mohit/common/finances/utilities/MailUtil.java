package mohit.common.finances.utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.sun.mail.smtp.SMTPTransport;

import mohit.common.finances.utilities.data.AccountInfo;
import mohit.common.finances.utilities.data.TransactionInfo;

public class MailUtil {

	private static 		Properties props = new Properties();
	final static Logger logger = Logger.getLogger(MailUtil.class);
	
	static
	{
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.pop3.user", "mymail@gmail.com");
		props.put("mail.smtp.socketFactory.port", 465);
		props.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.port", 465);
		props.put("mail.smtp.auth", true);		
	}
	
	public static void sendEmail(String msg1) throws Exception
	{
		
        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");

        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        // -- Create a new message --
        final MimeMessage msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress( ConfigReader.getInstance().getProperty(ConfigReader.EMAIL_USERNAME)) );
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ConfigReader.getInstance().getProperty(ConfigReader.EMAIL_SMS_ADDRESS), false));

        msg.setSubject("Daily expenses");
        msg.setText(msg1, "utf-8");
        msg.setSentDate(new Date());

        SMTPTransport t = (SMTPTransport)session.getTransport("smtps");

        t.connect("smtp.gmail.com", ConfigReader.getInstance().getProperty(ConfigReader.EMAIL_USERNAME), ConfigReader.getInstance().getProperty(ConfigReader.EMAIL_PASSWORD));
        t.sendMessage(msg, msg.getAllRecipients());      
        t.close();
	}
	
	public static List<TransactionInfo> checkMail( List<AccountInfo> accountInfos) 
	{

	if (logger.isDebugEnabled())
		logger.debug("Checking Email....");

		int numOfDays=1;
		try
		{
		 numOfDays = NumberUtils.createInteger(ConfigReader.getInstance().getProperty(ConfigReader.NUM_OF_DAYS, "1"));
		}
		catch (NumberFormatException nfe)
		{
			logger.error("Error converting the property " + ConfigReader.NUM_OF_DAYS + " to String. Will use default value of 1");
		}
		
		logger.debug("No of days ... " + numOfDays);
		
	
	
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.AM_PM, 0);
//		System.out.println(calendar.getTime());
		
		

		calendar.add(Calendar.DATE, (-1*numOfDays));
		
		
		logger.debug("Going to check until..." + calendar.getTime());
				
		
		
		List <TransactionInfo> transactionInfos = new ArrayList<TransactionInfo>();
		try {
			Session session = Session.getDefaultInstance(props, null);

			Store store = session.getStore("imaps");
			String userId = ConfigReader.getInstance().getProperty(ConfigReader.EMAIL_USERNAME);
			String pwd  = ConfigReader.getInstance().getProperty(ConfigReader.EMAIL_PASSWORD);
			store.connect("smtp.gmail.com", userId , pwd );

			
			FetchProfile fp = new FetchProfile();
			fp.add(FetchProfile.Item.CONTENT_INFO);

			Folder inbox = store.getFolder("inbox");
			inbox.open(Folder.READ_ONLY);
			int messageCount = inbox.getMessageCount();

			System.out.println("Total Messages:- " + messageCount);

			Message[] messages = inbox.getMessages();

			System.out.println("------------------------------");
			for (int i = (messages.length - 1); i >= 0; i--) {
				

				if (messages[i]!=null && messages[i].getSubject()!= null)
				{	
					
					AccountInfo accountInfo = findAMatch( messages[i].getFrom(), accountInfos);
					
					if (accountInfo!=null )
					{
						logger.debug(messages[i].getContentType());
						TransactionInfo transactionInfo = null;
						if (messages[i].getContentType().contains("TEXT"))
						{
//							System.out.println((String) messages[i].getContent());
							transactionInfo = MyUtils.parseEmail( (String) messages[i].getContent(),accountInfo);
						}
						else
						{
						
							transactionInfo = MyUtils.parseEmailContent( (Multipart) messages[i].getContent(),accountInfo);	
						}
						
						if (logger.isDebugEnabled())
						{
							logger.debug(transactionInfo.toString());
						}
						
						
						
						if (transactionInfo.getTxnDate().after(calendar.getTime()) || transactionInfo.getTxnDate().equals(calendar.getTime()  ))
						{
							transactionInfos.add(transactionInfo);
						}
						else
						{
							break; //break the loop
						}
					}
				}

			}
			inbox.close(true);
			store.close();

		} catch (Exception e) 
		{
			logger.error("Error checking email", e);
			
			e.printStackTrace();
		}
		System.out.println("Returning... " + transactionInfos.size());
		return transactionInfos;
	}

	private static AccountInfo findAMatch(Address[] from, List<AccountInfo> accountInfos) {
		if (accountInfos!=null && accountInfos.size()>0 && from!=null && from.length > 0)
		{
			for (AccountInfo accountInfo : accountInfos )
			{
//				if (subject.toLowerCase().contains(accountInfo.getAccountEmailSubLine().toLowerCase()))
				if (from[0].toString().toLowerCase().contains(accountInfo.getAccountEmailFrom().toLowerCase()))
				{
					if(logger.isDebugEnabled())
					{
						logger.debug("Match found :: " + accountInfo.toString());
						return accountInfo;
					}
				}
			}
		}
		
		
		return null;
	}


}
/**

    public static void Send(final String username, final String password, String recipientEmail, String ccEmail, String title, String message) throws AddressException, MessagingException {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

    }
}

**/
