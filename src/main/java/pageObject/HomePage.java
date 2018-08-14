package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends TestBase{
	/*
	 * driver:页面测试类基类
	 * url：访问地址
	 */
	private static TestBase driver;
	private static String url;
	public HomePage() {
		driver=new TestBase();
		url="http://www.baidu.com";
	}
	
	public void searchBaidu(String str) {
		
	}
	
	public static void main(String[] args) {
		HomePage h=new HomePage();
		driver.accessToWeb(url);
		driver.threadSleep(5);
		driver.maxBrowser();
		WebElement input=driver.findElement("id=>kw");
		driver.type(input, "selenium");
		WebElement searchButton=driver.findElement("id=>su");
		driver.click(searchButton);
		driver.screenAsFile();
		driver.quit();
	}
}
