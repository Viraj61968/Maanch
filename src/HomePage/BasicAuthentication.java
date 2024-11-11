package HomePage;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardDownRightHandler;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v129.emulation.Emulation;
import org.openqa.selenium.devtools.v129.network.model.ConnectionType;
import org.openqa.selenium.devtools.v130.network.Network;import org.openqa.selenium.devtools.v130.page.Page;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BasicAuthentication {

	@Test
	public void Authenction() // data provider use for invalid test case 
	{
		//-ve test case with invalid credentials 
		System.setProperty("webdriver.chrome.driver", "C:\\Learn it\\chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		// Predicate - act as a filter to capture the host of the URL for Basic authentication.
		Predicate<URI> uriPredicate = uri -> uri.getHost().contains("demo.maanch.com");
		
		
		//Provide the knowledge of authentication to the driver 
		((HasAuthentication)driver).register(uriPredicate,UsernameAndPassword.of("demo-uer", "Demo@321!"));
		driver.get("http://demo.maanch.com/");
		String message = driver.findElement(By.tagName("h1")).getText();
		System.out.println(message);
		Assert.assertEquals(message, "This site can’t be reached");
		driver.close();
	}
	@Test
	public void LinkBrokenTest() throws MalformedURLException, IOException
	{
		//Testing whether the link is broken or not 
		System.setProperty("webdriver.chrome.driver", "C:\\Learn it\\chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		Predicate<URI> uriPredicate = uri -> uri.getHost().contains("demo.maanch.com"); 
		((HasAuthentication)driver).register(uriPredicate,UsernameAndPassword.of("demo-user", "Demo@321!"));	
		driver.get("http://demo.maanch.com/");
		List<WebElement> elements = driver.findElements(By.cssSelector(".d-none"));
		for(WebElement element : elements)
		{
			String link = element.getAttribute("href");
			HttpURLConnection connect = (HttpURLConnection) new URL(link).openConnection();
			connect.setRequestMethod("HEAD");
			connect.connect();
			int code = connect.getResponseCode();
			Assert.assertEquals(code, "200");
		}
		driver.close();
	}
	
	
	@Test
	public void  titleVerification()
	{
		// checking if the title of page is updated when page is directed
		System.setProperty("webdriver.chrome.driver", "C:\\Learn it\\chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		// Predicate - act as a filter to capture the host of the URL for Basic authentication.
		Predicate<URI> uriPredicate = uri -> uri.getHost().contains("demo.maanch.com");
		
		
		 
		((HasAuthentication)driver).register(uriPredicate,UsernameAndPassword.of("demo-user", "Demo@321!"));	
		driver.get("http://demo.maanch.com/");
		driver.findElement(By.linkText("Sign Up")).click();
		String title = driver.getTitle();
		Assert.assertEquals(title, "Sign Up");
		driver.close();
	}
	
	@Test
	public void geoLocation()
	{
		// validating Localization
		System.setProperty("webdriver.chrome.driver", "C:\\Learn it\\chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		
		DevTools cdp = driver.getDevTools();
		cdp.createSession();
		Map<String,Object> coordinates = new HashMap<String,Object>();
		coordinates.put("latitude", 40);
		coordinates.put("longitude", 47);
		coordinates.put("accuracy", 1);
		
		Predicate<URI> uriPredicate = uri -> uri.getHost().contains("demo.maanch.com"); 
		((HasAuthentication)driver).register(uriPredicate,UsernameAndPassword.of("demo-user", "Demo@321!"));	
		driver.get("http://demo.maanch.com/");
		String text = driver.findElement(By.cssSelector(".text-watermark.left.bottom")).getText();
		Assert.assertEquals(text, "Fərq edin");
		
		driver.close();
	}
	
	@Test
	public void mobileEmulate() throws IOException
	{
		//validating overlapping UI. & Header link not visible.
		System.setProperty("webdriver.chrome.driver", "C:\\Learn it\\chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		
		DevTools cdp = driver.getDevTools();
		cdp.createSession();
		cdp.send(Emulation.setDeviceMetricsOverride(428, 926, 100, true, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
		Predicate<URI> uriPredicate = uri -> uri.getHost().contains("demo.maanch.com"); 
		((HasAuthentication)driver).register(uriPredicate,UsernameAndPassword.of("demo-user", "Demo@321!"));	
		driver.get("http://demo.maanch.com/");
		File source = driver.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(source, new File("C:\\Users\\viraj nalawade\\eclipse-workspace\\NiMapInfotech\\Maanch\\OverlappedUI.png"));
		driver.close();	
	}
	
	@Test
	public void SlowNetworkSpeed() throws IOException 
	{
		try {
		System.setProperty("webdriver.chrome.driver", "C:\\Learn it\\chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		DevTools cdp = driver.getDevTools();
		cdp.createSession();
		cdp.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		int downloadThroughput = 4 * 1024 * 1024; 
		int uploadThroughput = 5 * 1024;
		cdp.send(Network.emulateNetworkConditions(false, 3000, downloadThroughput, uploadThroughput, Optional.of(org.openqa.selenium.devtools.v130.network.model.ConnectionType.WIFI), 
				Optional.empty(), Optional.empty(), Optional.empty()));
		cdp.addListener(Network.loadingFailed(), loading->
		{
			loading.getErrorText();
			
		});
		Predicate<URI> uriPredicate = uri -> uri.getHost().contains("demo.maanch.com"); 
		((HasAuthentication)driver).register(uriPredicate,UsernameAndPassword.of("demo-user", "Demo@321!"));	
		driver.manage().deleteAllCookies();
		driver.get("http://demo.maanch.com/");
		
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".img-fluid.logo-img")));
		driver.close();
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		
		}
		
	}
	
	
	
	
	
	
	
	
}
