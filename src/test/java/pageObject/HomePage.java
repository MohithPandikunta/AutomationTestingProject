package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver)  
	
	{
		super (driver);
	}
	

@FindBy(xpath="//span[normalize-space()='My Account']")
WebElement lnkMyAccount;

@FindBy(xpath="//a[normalize-space()='Register']")
WebElement lnkRegister;

@FindBy(linkText = "Login")
WebElement linklogin;

public void lnkMyAccount() 
{
	lnkMyAccount.click();
}

public void lnkRegister() 
{
	lnkRegister.click();
}
	

public void linklogin()
{
	linklogin.click();
}
	
	
	
	
	
}
