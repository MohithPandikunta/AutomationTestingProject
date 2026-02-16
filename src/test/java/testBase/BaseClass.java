package testBase;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	
	public  WebDriver driver;
	public Logger logger;
	public Properties p;

	@BeforeClass(groups= {"Sanity", "Regression", "Master"})
	@Parameters({"os", "browser"})
	public void setup(String os, String br) throws IOException
	{
		//Loading config.properties file
	
		FileReader file = new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
		logger=LogManager.getLogger(this.getClass());
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
		    DesiredCapabilities capabilities = new DesiredCapabilities();
		    
		    capabilities.setPlatform(Platform.MAC);
		    
		    // OS Configuration
		    if (os.equalsIgnoreCase("Mac"))
		    {
		        
		        capabilities.setPlatform(Platform.MAC);
		    }
		    
		    
		    else if (os.equalsIgnoreCase("Windows"))
		    {
		        capabilities.setPlatform(Platform.WINDOWS);
		    }
		    
		   else if (os.equalsIgnoreCase("Linux")) {
		       capabilities.setPlatform(Platform.LINUX); 
		    }
		    
		    else
		    {
		        System.out.println("No Matching OS found");
		        return;
		    }
		    
		    //browser
		    switch(br.toLowerCase())
		    {
		    case "chrome": capabilities.setBrowserName("chrome"); break;
		    case "edge" : capabilities.setBrowserName("MicrosoftEdge"); break;
		    case "firefox" : capabilities.setBrowserName("firefox"); break;
		    default: System.out.println("No Matching Browser Found"); return;
		    }   
		   //driver = new RemoteWebDriver(new URL("http://192.168.1.103:4444/wd/hub"), capabilities);
		   // driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444"), capabilities);
		    //driver = new RemoteWebDriver(new URL("http://192.168.1.103:4444//wd/hub"), capabilities);
		   // driver = new RemoteWebDriver(new URL("http://localhost:4444"), capabilities);
		    
		    driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
		}
		if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch (br.toLowerCase())
			{
			case "chrome" : driver=new ChromeDriver(); break;
			case "firefox" : driver=new FirefoxDriver(); break;
			case "edge" : driver=new EdgeDriver(); break;
			
			
			default : System.out.println("(**** Invalid Browser name: *****"); return;
			
			}
		}
		
		// 3. COMMON SETUP (Added Null Check Here)
				// ONLY run these commands if the driver was successfully created
				if (driver != null) {
					driver.manage().deleteAllCookies();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
					driver.get(p.getProperty("appUrl"));
					driver.manage().window().maximize();
				} else {
					// Log an error so you know why it failed without crashing with NullPointerException
					logger.error("Driver is NULL. Check 'execution_env' in config.properties.");
				}
	
	}
	
	
		@AfterClass (groups= {"Sanity", "Regression", "Master"})
		public void tearDown()
		{
			// Check if driver exists before quitting
		    if (driver != null) {
		        driver.quit();
		    }
		}
		
	
	public String randomeString()
	{
		String generatedstring=RandomStringUtils.randomAlphabetic(5);
				return generatedstring;
				
	}
	
	public String randomeNumber()
	{
		String generatednumber=RandomStringUtils.randomNumeric(10);
				return generatednumber;
				
	}
	
	public String randomAlphaNumbric()
	
	{
		String generatedstring=RandomStringUtils.randomAlphabetic(3);
		String generatednumber=RandomStringUtils.randomNumeric(2);
		return (generatedstring+"@"+generatednumber);
	}
	
	public String captureScreen(String tname) throws IOException {

		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/screenshots/" + tname + "_" + timeStamp + ".png";

		try {
			FileUtils.copyFile(source, new File(destination));
		} catch (Exception e) {
			e.getMessage();
		}
		return destination;
	}

}
