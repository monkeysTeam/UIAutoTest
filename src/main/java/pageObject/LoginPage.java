package pageObject;

import org.openqa.selenium.WebElement;
/**
 * 
 * @author zhangyingkai
 *	
 * 2018年8月13日,下午3:57:02
 */
public class LoginPage extends TestBase{
	
	private static TestBase driver;
	private static String url;
	private static String crmButton;
	private static String inputOfUsername;
	private static String username;
	private static String inputOfPassword;
	private static String password;
	private static String loginButton;
	private static String loginedTitle;
	/*
	 * 构造方法初始化driver对象
	 */
	public LoginPage() {
		driver=new TestBase();
		url="http://192.168.10.170/";
		crmButton="xpath=>/html/body/div[10]/div[2]/div[3]/ul/li[1]";
		inputOfUsername="id=>username";
		username="1000000";
		inputOfPassword="id=>password";
		password="dj123456";
		loginButton="xpath=>//*[@id=\"login-zhanghao\"]/div[7]/button";
		loginedTitle="浙江东经科技股份有限公司-CRM";
	}
	/*
	 * 访问crm测试环境登入地址
	 */
	public void accessUrl() {
		driver.accessToWeb(url);
	}
	/*
	 * 点击crm按钮
	 */
	public void clickCrmButton() {
		WebElement element=driver.findElement(crmButton);
		driver.click(element);
	}
	/*
	 * 输入账号
	 */
	public void typeUsername() {
		WebElement element=driver.webDriverWait(inputOfUsername, 10);
		element=driver.findElement(inputOfUsername);
		driver.type(element, username);
	}
	/*
	 * 输入密码
	 */
	public void typePassword() {
		WebElement element=driver.webDriverWait(inputOfPassword, 10);
		element=driver.findElement(inputOfPassword);
		driver.type(element, password);
	}
	/*
	 * 点击登入按钮
	 */
	public void clickLoginButton() {
		WebElement element=driver.webDriverWait(loginButton, 10);
		element=driver.findElement(loginButton);
		driver.click(element);
	}
}
