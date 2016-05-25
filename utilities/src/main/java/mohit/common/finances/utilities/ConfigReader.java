package mohit.common.finances.utilities;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ConfigReader 
{

	public static final String EMAIL_PASSWORD = "email.password";
	public static final String EMAIL_USERNAME = "email.user.name";
	public static final String TOTAL_ACCOUNTS = "total.accounts";
	public static final String EMAIL_SMS_ADDRESS = "email.sms.address";
	
	
	private static ConfigReader _this= null;
	
	private Properties props = new Properties();
	static Logger logger = Logger.getLogger(ConfigReader.class.getName()); 
	
	public static ConfigReader getInstance()
	{
		if (_this == null) initialize();
		return _this;
	}



	private static synchronized void initialize() 
	{
		if (_this == null) 
		{
			_this = new ConfigReader();
		}
		
	}
	
	
    public ConfigReader() 
    {
		super();
    	System.out.println("Initializing properties Reader");
		try
		{
			loadProperties();
		}
		catch (Exception e)
		{
			System.out.println("Error reading the properties file");
			e.printStackTrace();
		}
		
		
	    PropertyConfigurator.configure(props);
	    
	    logger.info("Log4J is now configured, Printing application properties");
	    
		
		printProperties();
	}

	private void loadProperties() throws Exception
    {
		InputStream inputStream = new FileInputStream("C:/Users/mohiman/Desktop/Utilities/config.properties");
		
		props.load(inputStream);
		
    }	

	public void printProperties()
	{
		Enumeration<?> e = props.propertyNames();
		
		while (e.hasMoreElements()) 
		{
			String key = (String) e.nextElement();
			String value = props.getProperty(key);
			logger.info(key + " = " + value);
		}
	}
	public String getProperty(String propName)
	{
		return getProperty(propName,null);
	}
	
	public String getProperty(String propName, String defaultVal)
	{
		return props.getProperty(propName) == null ? defaultVal : props.getProperty(propName); 
	}
	public Properties getAllProperties()
	{
		return props;
	}
}
