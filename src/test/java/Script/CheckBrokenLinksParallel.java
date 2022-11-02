package Script;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager; //import for webdriver setup

public class CheckBrokenLinksParallel {
	
	public static void main(String [] args) {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.google.com/");// Enter link
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		List<WebElement>links = driver.findElements(By.tagName("a"));
		System.out.println("number of links are ----->" + links.size());
		List<String> urList = new ArrayList<String>();
		for(WebElement element : links) {
			String url = element.getAttribute("href");
			urList.add(url);
		}
		
		urList.parallelStream().forEach(e -> checkBrokenlink(e));
		driver.quit();
	}
		
		public static void checkBrokenlink(String linkUrl) {
			try {
				
				URL url = new URL (linkUrl);
				HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
				httpUrlConnection.setConnectTimeout(5000);
				httpUrlConnection.connect();
				if (httpUrlConnection.getResponseCode()>=400) {
					System.out.println(linkUrl+"----->" + httpUrlConnection.getResponseMessage()+"is a broken link");
					}
				else {
					System.out.println(linkUrl+"----->" + httpUrlConnection.getResponseMessage());
				}
				
			}
			catch (Exception e) {
				
			}
		
		}}

	