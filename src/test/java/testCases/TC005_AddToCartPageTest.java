package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.HomePage;
import pageObject.SearchPage;
import testBase.BaseClass;

public class TC005_AddToCartPageTest extends BaseClass {
	
	@Test(groups= {"Master"})
	public void verify_addToCart() throws InterruptedException {
		logger.info(" Starting TC_005_AddToCartPageTest ");
			
		HomePage hp = new HomePage(driver);
		
		hp.enterProductName("HP LP3065");
		hp.clickSearch();
		
		// Add a short wait to allow the search results to load
		Thread.sleep(3000); 
					
		SearchPage sp = new SearchPage(driver);
		
		// 1. Check if the product exists
		boolean productExists = sp.isProductExist("HP LP3065");
		
		// 2. HARD ASSERTION: If false, the test immediately stops and throws this error.
		Assert.assertTrue(productExists, "Error: The product 'iPhone' was not found in the search results.");
		
		// 3. Because of the Hard Assert above, no 'if' statement is needed.
		// If the code reaches this line, the product definitively exists.
		sp.selectProduct("HP LP3065");
		sp.setQuantity("1");
		sp.addToCart();
		
		// Add a short wait to allow the confirmation message banner to appear
		Thread.sleep(3000); 
					
		Assert.assertEquals(sp.checkConfMsg(), true, "Error: Confirmation message did not appear.");

		logger.info(" Finished TC_005_AddToCartPageTest ");
	}
}

