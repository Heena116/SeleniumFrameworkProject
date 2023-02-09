package SeleniumFramework.SeleniumFramework;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import SeleniumFramework.TestComponents.BaseTest;
import SeleniumFramework.pageobject.CartPage;
import SeleniumFramework.pageobject.CheckoutPage;
import SeleniumFramework.pageobject.LandingPage;
import SeleniumFramework.pageobject.ProductCatalogue;
import io.github.bonigarcia.wdm.WebDriverManager;
import SeleniumFramework.pageobject.OrderPage;
import SeleniumFramework.pageobject.ConfirmationPage;
public class SubmitOrderTest extends BaseTest {
String productName = "ZARA COAT 3";

@Test(dataProvider="getData",groups= {"Purchase"})
//public void SubmitOrderTest(String email,String password,String productName) throws IOException, InterruptedException 

public void SubmitOrderTest(HashMap<String,String> input) throws IOException, InterruptedException 
{		
		ProductCatalogue productcatelogue=landingPage.loginApplication(input.get("email"),input.get("password"));
		landingPage.getErrorMessage();
		
		List<WebElement> products=productcatelogue.getProductList();
		productcatelogue.addProductToCart(input.get("product"));
		
		CartPage cartPage=productcatelogue.goToCartPage();
		Boolean match=cartPage.VerifyProductDisplay(input.get("product"));
		
		Assert.assertTrue(match);
		CheckoutPage checkoutpage=cartPage.goToCheckout();
		checkoutpage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutpage.submitOrder();
		


		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
}
@Test(dependsOnMethods= {"SubmitOrderTest"})
public void OrderHistoryTest()
{
		//"ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApplication("anshika@gmail.com", "Iamking@000");
		OrderPage ordersPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));
	
}
//Using Array
/*@DataProvider
public Object[][] getData()
{
  return new Object[][]  {{"anshika@gmail.com","Iamking@000","ZARA COAT 3"}, {"shetty@gmail.com","Iamking@000","ADIDAS ORIGINAL" } };
  
}*/
//Using HashMAp
/*@DataProvider
public Object[][] getData()
{
	HashMap<String,String> map = new HashMap<String,String>();
	map.put("email", "anshika@gmail.com");
	map.put("password", "Iamking@000");
	map.put("product", "ZARA COAT 3");
	
	HashMap<String,String> map1 = new HashMap<String,String>();
	map1.put("email", "shetty@gmail.com");
	map1.put("password", "Iamking@000");
	map1.put("product", "ADIDAS ORIGINAL");
	
	return new Object[][] {{map},{map1}};
	
}*/
//Using jSON File
@DataProvider
public Object[][] getData() throws IOException
{
	List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//SeleniumFramework//data//purchaseOrder.json");
	return new Object[][]  {{data.get(0)}, {data.get(1) } };
}

}
