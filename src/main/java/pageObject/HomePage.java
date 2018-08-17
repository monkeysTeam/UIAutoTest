package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
/**
 * 
 * @author zhangyingkai
 *	
 * 2018年8月15日,下午1:36:20
 */
public class HomePage extends TestBase{
	
	/*
	 * driver:测试驱动
	 * username:右上角的用户名
	 * logout:注销账户
	 * buttonOfQueDing:确定按钮
	 */
	private static TestBase driver;
	private static String username;
	private static String logout;
	private static String buttonOfQueDing;
	/*
	 * 构造方法初始化driver
	 */
	public HomePage() {
		driver=new TestBase();
		username="xpath=>//*[@id=\"header\"]/div[2]/div[2]/div/spa";
		logout="xpath=>/html/body/ul/li[2]";
		buttonOfQueDing="xpath=>/html/body/div[2]/div/div[3]/button[2]/span";
	}
	/*
	 * 点击右上角的用户名
	 */
	public void clickUsername() {
		WebElement element=driver.findElement(username);
		driver.click(element);
	}
	/*
	 * 点击注销账户
	 */
	public void clickLogout() {
		WebElement element=driver.findElement(logout);
		driver.click(element);
	}
	/*
	 * 点击确定
	 */
	public void clickButtonOfQueDing() {
		WebElement element=driver.findElement(buttonOfQueDing);
		driver.click(element);
	}
}
