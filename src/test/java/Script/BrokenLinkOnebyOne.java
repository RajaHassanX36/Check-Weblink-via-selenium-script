package Script;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;//import for webdriver setup

public class BrokenLinkOnebyOne {

	public static void main(String[] args) throws InterruptedException, IOException {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.google.com/");//Enter link
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		// 1 Get all the available link and images
		List<WebElement> linksList = driver.findElements(By.tagName("a"));
		linksList.addAll(driver.findElements(By.tagName("img")));
		System.out.println("size of All links and images----->" + linksList.size());
		List<WebElement> activelinks = new ArrayList<WebElement>();

		// 2 iterate arraylist

		for (int i = 0; i < linksList.size(); i++) {
			// System.out.println(linksList.get(i).getAttribute("href"));
			if (linksList.get(i).getAttribute("href") != null
					&& (!linksList.get(i).getAttribute("href").contains("javascricpt"))) {
				activelinks.add(linksList.get(i));
			}
		}
		// get the size of active linklist

		System.out.println("size of active links and images----->" + activelinks.size());

		// 3 Check the href url , with http connection api
		for (int j = 0; j < activelinks.size(); j++) {
			HttpURLConnection connection = (HttpURLConnection) new URL(activelinks.get(j).getAttribute("href"))
					.openConnection();
			connection.connect();
			String response = connection.getResponseMessage();
			connection.disconnect();
			System.out.println(activelinks.get(j).getAttribute("href") + "---->" + response);

		}
	}
}
