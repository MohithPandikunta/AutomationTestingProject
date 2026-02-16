package testCases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObject.AccountRegistrationPage;
import pageObject.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {
	
	@Test (groups={"Regression","Master"})
	public void verify_account_registration()
	{
		logger.info("******** Starting TC001_AccountRegistrationTest *********");
		
		try 
		{
		HomePage hp=new HomePage(driver);
		hp.lnkMyAccount();
		logger.info("Clicked on MY Account");
		hp.lnkRegister();
		logger.info("Clicked on Register");
		
		AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
		logger.info("Providing customer details.... ");
		regpage.setFirstName(randomeString().toUpperCase());
		regpage.setLastName(randomeString().toUpperCase());
		regpage.setEmail(randomeString()+"@gmail.com"); // randomly generated the email
		regpage.setTelephone(randomeNumber());
		
		String password=randomAlphaNumbric();
		regpage.setpassword(password);
		regpage.setconfirmpassword(password);
		
		regpage.setPriacyPolicy(); 
		regpage.clickContinue();
		
		logger.info("Validting Expected Message.... ");
		
		String confmsg=regpage.msgConfirmationMsg();
		if(confmsg.equals("Your Account Has Been Created!"))
				
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Tested Failed....");
			logger.debug("Debug Logs....");
			Assert.assertTrue(false);
		}
		
		//Assert.assertEquals(conf msg, "Your Account Has Been Created!");
	}
	
	catch (Exception e)
	
	{
		
		Assert.fail();
	}
		
		logger.info("********* Test Finished *****");
	
}
}
	

