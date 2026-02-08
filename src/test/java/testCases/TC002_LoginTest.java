package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {
	
	@Test(groups={"Sanity" ,"Master"})
	public void verify_loginTest()
	{
		try
		{
		logger.info("*********Starting TC002_LoginTest ***********");
		//HomePage
		HomePage hp=new HomePage(driver);   // Object of HomePage Create
		hp.clickMyAccount();
		hp.clickLogin();
		
		//LoginPage
		LoginPage lp=new LoginPage(driver);
		logger.info("Providing Login data");
		lp.setEmail(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));
		lp.clickLogin();
		logger.info("Clicked on login button");
		//MyAccountPage
		MyAccountPage macc=new MyAccountPage(driver);
		boolean targetPage =macc.isMyAccountPageExists();
		
		
		Assert.assertTrue(targetPage);   //Assert.assertEquals(targetPage, true, "Login failed");
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		logger.info("*************Finished TC002_LoginTest ***********");

}
}
