package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
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

	public WebDriver driver;
	public Logger logger;
	public Properties p;

	@BeforeClass(groups = { "Sanity", "Regression", "Master" })
	@Parameters({ "os", "browser" })
	public void setup(String os, String br) throws IOException {
		// 1. Loading config.properties
		FileReader file = new FileReader("./src/test/resources/config.properties");
		p = new Properties();
		p.load(file);
		logger = LogManager.getLogger(this.getClass());

		String executionEnv = p.getProperty("execution_env").toLowerCase().trim();

		// 2. REMOTE EXECUTION (Local Grid)
		if (executionEnv.equals("remote")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();

			if (os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN11);
			} else if (os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			}

			switch (br.toLowerCase()) {
			case "chrome": capabilities.setBrowserName("chrome"); break;
			case "firefox": capabilities.setBrowserName("firefox"); break;
			case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
			default: System.out.println("No matching browser"); return;
			}

			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
		}
		
		

		// 3. BROWSERSTACK EXECUTION
		else if (executionEnv.equals("browserstack")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			HashMap<String, Object> bstackOptions = new HashMap<>();
			
			// BrowserStack is strict: "OS X" is required for Mac
			if (os.equalsIgnoreCase("mac")) {
				bstackOptions.put("os", "OS X");
				bstackOptions.put("osVersion", "Sonoma");
			} else {
				bstackOptions.put("os", "Windows");
				bstackOptions.put("osVersion", "11");
			}
			
			bstackOptions.put("userName", "mohith_YOM3vi");
			bstackOptions.put("accessKey", "833FBqscyffpsuD3uxPY");
			bstackOptions.put("projectName", "Opencart Automation");
			
			 bstackOptions.put("networkLogs", true);
			    bstackOptions.put("consoleLogs", "info");
			
			capabilities.setCapability("bstack:options", bstackOptions);
			capabilities.setBrowserName(br);
			

			driver = new RemoteWebDriver(new URL("https://hub-cloud.browserstack.com/wd/hub"), capabilities);
		}
		
		

		// 4. LOCAL EXECUTION
		else if (executionEnv.equals("local")) {
			switch (br.toLowerCase()) {
			case "chrome": driver = new ChromeDriver(); break;
			case "firefox": driver = new FirefoxDriver(); break;
			case "edge": driver = new EdgeDriver(); break;
			}
		}
		
		

		// 5. COMMON SETUP
		if (driver != null) {
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.get(p.getProperty("appUrl"));
			driver.manage().window().maximize();
		} else {
			logger.error("Driver initialization failed. Check your config or grid status.");
		}
	}

	@AfterClass(groups = { "Sanity", "Regression", "Master" })
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	public String captureScreen(String tname) throws IOException {
		// Null check to prevent ExtentReport crashes
		if (driver == null) return "";

		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/screenshots/" + tname + "_" + timeStamp + ".png";

		try {
			FileUtils.copyFile(source, new File(destination));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return destination;
	}

	public String randomeString() { return RandomStringUtils.randomAlphabetic(5); }
	public String randomeNumber() { return RandomStringUtils.randomNumeric(10); }
	public String randomAlphaNumbric() {
		return (RandomStringUtils.randomAlphabetic(3) + "@" + RandomStringUtils.randomNumeric(2));
	}
}