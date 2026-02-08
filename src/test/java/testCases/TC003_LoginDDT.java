package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.MyAccountPage;
import testBase.BaseClass;
import utilites.DataProviders;

public class TC003_LoginDDT extends BaseClass {
	
	
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class, groups="datadriven")  // LoginData is name of which dataprovider and dataProviderClass is which class inside  
	public void verify_loginDDT(String email, String pwd, String exp) throws InterruptedException
	{
		logger.info("********** Strating TC003_LoginDDT ****************");
		try {
		//HomePage
		HomePage hp=new HomePage(driver);   // Object of HomePage Create
		hp.clickMyAccount();
		hp.clickLogin();
		
		//LoginPage
		LoginPage lp=new LoginPage(driver);
		logger.info("Providing Login data");
		
		System.out.println(email+" | "+pwd+" | "+exp);
	
		lp.setEmail(email);
		lp.setPassword(pwd);
		lp.clickLogin();
		logger.info("Clicked on login button");
		//MyAccountPage
		MyAccountPage macc=new MyAccountPage(driver);
		boolean targetPage =macc.isMyAccountPageExists();
		
		
		
		/*
		 Data is Valid ----login success ---test pass---logout
		 			   ----login failed  ---test fail
		 		
		 Data is Invalid---login success	----test fail---logout
		 	   			---login failed ----test pass	
		 */
		if(exp.equalsIgnoreCase("Valid"))
		{
			if(targetPage==true)
			{
				macc.clickLogout();
				Assert.assertTrue(true);
			}
			else
			{
				Assert.fail();
			}
		}
		if(exp.equalsIgnoreCase("Invalid"))
		{
			if(targetPage==true)
			{
				macc.clickLogout();
				Assert.assertTrue(false);
			}
		}else
		{
			Assert.assertTrue(true);
		}
		}
		catch(Exception e) 
		{
			Assert.fail();
		}
		Thread.sleep(3000);
		logger.info("*********************End of TC003_LoginDDT********************");
	}

}
