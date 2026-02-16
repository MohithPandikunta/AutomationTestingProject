package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {

	public AccountRegistrationPage(WebDriver driver) {
		
		super(driver);
		
	}
	
@FindBy(xpath="//input[@id='input-firstname']")
WebElement txtfirstname;

@FindBy(xpath="//input[@id='input-lastname']")
WebElement txtlastname;

@FindBy(xpath="//input[@id='input-email']")
WebElement txtEmail;

@FindBy(xpath="//input[@id='input-telephone']")
WebElement txttelephone;

@FindBy(xpath="//input[@id='input-password']")
WebElement txtpassword;

@FindBy(xpath="//input[@id='input-confirm']")
WebElement txtconfirmpassword;

/*@FindBy(xpath="//input[@value='0']")
WebElement txtvalue; */

@FindBy(xpath="//input[@name='agree']")
WebElement checkedagree;

@FindBy(xpath="//input[@value='Continue']")
WebElement btncontinue;

@FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
WebElement msgConfirmation;


public void setFirstName(String fname) {
	txtfirstname.sendKeys(fname);	
}

public void setLastName(String lname) {
	txtlastname.sendKeys(lname);
}

public void setEmail(String email) {
	txtEmail.sendKeys(email);
}

public void setTelephone(String tele) {
	txttelephone.sendKeys(tele);
}

public void setpassword(String pwd) {
	txtpassword.sendKeys(pwd);
}

public void setconfirmpassword(String cpwd) {
	txtconfirmpassword.sendKeys(cpwd);
}

/*public void setValue(String value) {
 * 
 * txtvalue
	
} */

public void setPriacyPolicy() {
	checkedagree.click();
}

public void clickContinue() {
	//sol1 
	btncontinue.click();
	
	//sol2 
	//btnContinue.submit();
	
	//sol3
	//Actions act=new Actions(driver);
	//act.moveToElement(btnContinue).click().perform();
				
	//sol4
	//JavascriptExecutor js=(JavascriptExecutor)driver;
	//js.executeScript("arguments[0].click();", btnContinue);
	
	//Sol 5
	//btnContinue.sendKeys(Keys.RETURN);
	
	//Sol6  
	//WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(10));
	//mywait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
	
}

public String msgConfirmationMsg() {
	try {
		return (msgConfirmation.getText());
	} catch (Exception e ) {
		return (e.getMessage());
	}
}


}
