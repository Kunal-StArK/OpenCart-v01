package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
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


import org.apache.logging.log4j.LogManager;     //logger
import org.apache.logging.log4j.Logger;        //logger

public class BaseClass {
	
    public static WebDriver driver;
    public Logger logger;    //log4j
    public Properties p;     //this is for properties file
    
    
	
	@BeforeClass(groups={"Sanity" ,"Regression","Master"})
	@Parameters({"os","browser"})
	public void setup(String os, String br) throws IOException
	{
		//Loading congif.properties file
		FileReader file=new FileReader("./src//test//resources//config.properties"); // for location object 
		p=new Properties();
		p.load(file); 
		
		logger=LogManager.getLogger(this.getClass());   // this is for logger
		
		if(p.getProperty("execution_evn").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilities=new DesiredCapabilities();
			
			//os
			if(os.equalsIgnoreCase("windows"))
			{
				capabilities.setPlatform(Platform.WIN10);
			}
			else if(os.equalsIgnoreCase("mac"))
			{
				capabilities.setPlatform(Platform.MAC);
			}
			else
			{
				System.out.println("No matching os");
				return ;
			}
			
			//browser
			switch(br.toLowerCase())
			{
			case "chrome" : capabilities.setBrowserName("chrome"); break;
			case "edge" : capabilities.setBrowserName("MicrosoftEdge"); break;
			default: System.out.println("No matching browser"); return;
			}
			
			driver= new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
		}
		
		
		if(p.getProperty("execution_evn").equalsIgnoreCase("local"))
		{
			//this is for which browser is we want to run our test cases
			switch(br.toLowerCase())
			{
			
			case "chrome" : driver=new ChromeDriver(); break;
			case "edge" : driver=new EdgeDriver();  break;
			case "firefox" : driver=new FirefoxDriver(); break;
			default : System.out.println("Invalid browser name ..."); return;
			
			}
			
		}
		
		
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//driver.get("https://tutorialsninja.com/demo/index.php?route=common/home");
		driver.get(p.getProperty("appURL"));      // reading URL from properties file
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups={"Sanity" ,"Regression","Master"})
	public void tearDown()
	{
		driver.quit();
	}
	
	//Generate random String
		public String randomString()
		{
			String generatedstring= RandomStringUtils.randomAlphabetic(5);
			return generatedstring;
		}
		
		//Generate random Numeric
		public String randomNumeric()
		{
			String generatednumeric= RandomStringUtils.randomNumeric(5);
			return generatednumeric;
		}
			
		//Generate random AlphaNumeric
		public String randomAlphaNumeric()
		{
			String generatedstring= RandomStringUtils.randomAlphabetic(5);
			String generatednumeric= RandomStringUtils.randomNumeric(5);
			return (generatedstring+"@"+generatednumeric);
		}
		
		
		//for screen shot 
		public static String captureScreen(String tname) throws IOException
		{
		    String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

		    TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		    File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

		    String targetFilePath =
		            System.getProperty("user.dir")
		            + File.separator + "screenshots"
		            + File.separator + tname + "_" + timeStamp + ".png";

		    File targetFile = new File(targetFilePath);

		    FileUtils.copyFile(sourceFile, targetFile);   // ✅ reliable

		    return targetFilePath;
		}

}
