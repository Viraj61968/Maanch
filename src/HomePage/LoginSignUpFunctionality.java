package HomePage;

import java.net.URI;
import java.time.Duration;
import java.util.function.Predicate;

import org.openqa.selenium.By;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginSignUpFunctionality {

	@Test
	public void Login()
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Learn it\\chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		Predicate<URI> uriPredicate = uri -> uri.getHost().contains("demo.maanch.com"); 
		((HasAuthentication)driver).register(uriPredicate,UsernameAndPassword.of("demo-user", "Demo@321!"));	
		driver.get("http://demo.maanch.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		
		//logged in
		driver.findElement(By.linkText("Login/Sign up")).click();
		driver.findElement(By.id("email")).sendKeys("vn61968@gmail.com");
		driver.findElement(By.id("password")).sendKeys("1234Viraj");
		driver.findElement(By.cssSelector(".btn.col-6.btn.col-6.btn-outline-secondary")).click();
		driver.close();
	}
	
	@Test
	public void invalidcredentials()
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Learn it\\chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		Predicate<URI> uriPredicate = uri -> uri.getHost().contains("demo.maanch.com"); 
		((HasAuthentication)driver).register(uriPredicate,UsernameAndPassword.of("demo-user", "Demo@321!"));	
		driver.get("http://demo.maanch.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		
		//logged in
		driver.findElement(By.linkText("Login/Sign up")).click();
		driver.findElement(By.id("email")).sendKeys("vn6196@gmail.com");
		driver.findElement(By.id("password")).sendKeys("1234Viraj");
		driver.findElement(By.cssSelector(".btn.col-6.btn.col-6.btn-outline-secondary")).click();
		
		String errormsg = driver.findElement(By.cssSelector(".invalid-feedback")).getText();
		Assert.assertEquals(errormsg, "These credentials do not match our records.");	
		driver.close();
	}
	
	@Test
	public void logout()
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Learn it\\chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		Predicate<URI> uriPredicate = uri -> uri.getHost().contains("demo.maanch.com"); 
		((HasAuthentication)driver).register(uriPredicate,UsernameAndPassword.of("demo-user", "Demo@321!"));	
		driver.get("http://demo.maanch.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		
		//logged in
		driver.findElement(By.linkText("Login/Sign up")).click();
		driver.findElement(By.id("email")).sendKeys("vn61968@gmail.com");
		driver.findElement(By.id("password")).sendKeys("1234Viraj");
		driver.findElement(By.cssSelector(".btn.col-6.btn.col-6.btn-outline-secondary")).click();
		
		//log out
		driver.findElement(By.id("dropdownMenuLink")).click();
		driver.findElement(By.linkText("Logout")).click();
		driver.close();
	}
	
	
	
	
	
	
	
	
	
}
