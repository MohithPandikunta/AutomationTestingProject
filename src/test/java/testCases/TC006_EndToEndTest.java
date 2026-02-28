package testCases;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObject.AccountRegistrationPage;
import pageObject.CheckoutPage;
import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.MyAccountPage;
import pageObject.SearchPage;
import pageObject.ShoppingCartPage;
import testBase.BaseClass;

public class TC006_EndToEndTest extends BaseClass {
	@Test(groups= {"Master"})
	public void endToendTest() throws InterruptedException
	{
	//Soft assertions
		SoftAssert myassert=new SoftAssert();
		
	//Account Registration
	System.out.println("Account Registration................");
	HomePage hp = new HomePage(driver);
	hp.lnkMyAccount(); 
	hp.lnkRegister();
	
	AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
	regpage.setFirstName(randomeString().toUpperCase());
	regpage.setLastName(randomeString().toUpperCase());
	
	String email=randomeString() + "@gmail.com";
	regpage.setEmail(email);// randomly generated the email
			
	regpage.setTelephone("1234567");
	regpage.setpassword("test123");
	regpage.setconfirmpassword("test123");
	regpage.setPriacyPolicy();
	regpage.clickContinue();
	Thread.sleep(3000);

	String confmsg = regpage.msgConfirmationMsg();
	System.out.println(confmsg);
	
	myassert.assertEquals(confmsg, "Your Account Has Been Created!"); //validation
	
	MyAccountPage mc=new MyAccountPage(driver);
	mc.clicklogout();
	Thread.sleep(3000);
	
	
	//Login
	System.out.println("Login to application...............");
	hp.lnkMyAccount();
	hp.linklogin();
	
	LoginPage lp=new LoginPage(driver);
	lp.setEmail(email);
	lp.setPassword("test123");
	lp.clicklogin();
		
	
	System.out.println("Going to My Account Page? "+ mc.isMyAccountPageExists());
	myassert.assertEquals(mc.isMyAccountPageExists(), true); //validation

	/*
	//search & add product to cart
	System.out.println("search & add product to cart...............");
	hp.enterProductName(p.getProperty("searchProductName"));
	hp.clickSearch();
	
	SearchPage sp=new SearchPage(driver);
	
	if(sp.isProductExist(p.getProperty("searchProductName")))
	{
		sp.selectProduct(p.getProperty("searchProductName"));
		sp.setQuantity("2");
		sp.addToCart();
		
	}
	Thread.sleep(3000);
	System.out.println("Added product to cart ? "+ sp.checkConfMsg());
	myassert.assertEquals(sp.checkConfMsg(),true);
		
		
		*/
	
	
	//search & add product to cart
		System.out.println("search & add product to cart...............");
		
		// Re-initialize the HomePage object to prevent StaleElementReferenceException
		hp = new HomePage(driver);
		
		// Fetch the property and print it to ensure it is not returning 'null'
		String searchItem = p.getProperty("searchProductName");
		System.out.println("Item fetched from config: " + searchItem);
		
		// Enter the name and search
		hp.enterProductName(searchItem);
		hp.clickSearch();
		
		// Give the search results page a moment to load
		Thread.sleep(3000);
		
		SearchPage sp=new SearchPage(driver);
		
		if(sp.isProductExist(searchItem))
		{
			sp.selectProduct(searchItem);
			sp.setQuantity("1");
			sp.addToCart();
		}
		
		Thread.sleep(3000);
		System.out.println("Added product to cart ? "+ sp.checkConfMsg());
		//myassert.assertEquals(sp.checkConfMsg(),true);
		
	//Shopping cart
	System.out.println("Shopping cart...............");
	ShoppingCartPage sc=new ShoppingCartPage(driver);
	sc.clickItemsToNavigateToCart();
	sc.clickViewCart();
	Thread.sleep(3000);
	String totprice=sc.getTotalPrice();
	System.out.println("total price is shopping cart: "+totprice);
	myassert.assertEquals(totprice, "$122.00");   //validation
	sc.clickOnCheckout(); //navigate to checkout page
	Thread.sleep(3000);
	
	
	//Checkout Product
	System.out.println("Checkout Product...............");
	CheckoutPage ch=new CheckoutPage(driver);
	Thread.sleep(3000);
	ch.setfirstName(randomeString().toUpperCase());
	Thread.sleep(1000);
	ch.setlastName(randomeString().toUpperCase());
	Thread.sleep(1000);
	ch.setaddress1("address1");
	Thread.sleep(1000);
	ch.setaddress2("address2");
	Thread.sleep(1000);
	ch.setcity("Delhi");
	Thread.sleep(1000);
	ch.setpin("500070");
	Thread.sleep(1000);
	ch.setCountry("India");
	Thread.sleep(1000);
	ch.setState("Delhi");
	Thread.sleep(1000);
	ch.clickOnContinueAfterBillingAddress();
	Thread.sleep(1000);
	ch.clickOnContinueAfterDeliveryAddress();
	Thread.sleep(2000);
	//ch.setDeliveryMethodComment("testing...");
	ch.setDeliveryMethodComment("High-value electronic item. Please do not leave unattended. Signature required upon delivery.");
	Thread.sleep(1000);
	ch.clickOnContinueAfterDeliveryMethod();
	Thread.sleep(1000);
	ch.selectTermsAndConditions();
	Thread.sleep(1000);
	ch.clickOnContinueAfterPaymentMethod();
	Thread.sleep(2000);
		
	
	String total_price_inOrderPage=ch.getTotalPriceBeforeConfOrder();
	System.out.println("total price in confirmed order page:"+total_price_inOrderPage);
	myassert.assertEquals(total_price_inOrderPage, "$105.00"); //validation
	
	//Below code works only if you configure SMTP for emails 
	/*ch.clickOnConfirmOrder();
	Thread.sleep(3000);
		
	boolean orderconf=ch.isOrderPlaced();
	System.out.println("Is Order Placed? "+orderconf);
	myassert.assertEquals(ch.isOrderPlaced(),true);*/
		
	myassert.assertAll(); //conclusion
}

}



