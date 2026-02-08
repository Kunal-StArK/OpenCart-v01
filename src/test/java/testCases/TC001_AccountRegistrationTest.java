package testCases;


import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.AccountRegistrationPage;
import pageObject.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {
	
	@Test(groups={"Regression" ,"Master"})
	public void verify_account_registration()
	{
		logger.info("********Starting TC001_AccountRegistrationTest *********");
		try
		{
		HomePage hp=new HomePage(driver);   // Object of HomePage Create	
		hp.clickMyAccount();
		logger.info("Clicked on My Account link");
		hp.clickRegister();
		logger.info("Clicked on Register link");
			
		
		AccountRegistrationPage regpage=new AccountRegistrationPage(driver);  //Object of AccountRegistrationPage Created
		logger.info("Providing Customer Details.....");
		regpage.setFirstName(randomString());
		regpage.setLastName(randomString());
		regpage.setEmail(randomString()+"@gmail.com");   //randomly generated email id
		regpage.setTelephone(randomNumeric());
		
		String pass=randomAlphaNumeric();
		regpage.setPassword(pass);
		regpage.setConfirmPassword(pass);
		
		regpage.clickPolicy();
		regpage.clickContinue();
		
		logger.info("Validating expected message.....");
		String confmsg=regpage.getConfirmationMsg();
		
		if(confmsg.equals("Your Account Has Been Created!"))     //forced to failed
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test faild...");
			logger.debug("Debug logs");
			Assert.assertTrue(false);
		}
		
		//Assert.assertEquals(confmsg, "Your Account Has Been Created!");
		}
		catch(Exception e) 
		{
			Assert.fail();
		}
		logger.info("********Finished TC001_AccountRegistrationTest************");
	}

}
