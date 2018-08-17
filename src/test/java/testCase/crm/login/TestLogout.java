package testCase.crm.login;

import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import listeners.AutoTestListener;
import listeners.RetryListener;
import pageObject.HomePage;
import pageObject.LoginPage;

public class TestLogout {
	
	private static LoginPage loginPage;
	private static HomePage homePage;
	
	@BeforeMethod
	public void setUp() {
		loginPage=new LoginPage();
		homePage=new HomePage();
		Reporter.log("测试用例开始");
		loginPage.openChrome();
		Reporter.log("打开谷歌浏览器");
		loginPage.maxBrowser();
		Reporter.log("最大化浏览器");
		loginPage.accessUrl();
		Reporter.log("访问CRM测试环境地址");
		loginPage.implicitlyWait(3);
		Reporter.log("隐式等待3秒");
    	loginPage.clickCrmButton();
    	Reporter.log("点击CRM按钮");
    	loginPage.typeUsername();
    	Reporter.log("输入用户账号");
    	loginPage.typePassword();
    	Reporter.log("输入密码");
    	loginPage.threadSleep(3);
    	Reporter.log("睡眠3秒");
    	loginPage.clickLoginButton();
    	Reporter.log("点击登入按钮");
    	loginPage.threadSleep(3);
    	Reporter.log("线程等待三秒");
    	String title=loginPage.getCurrentPageTitle();
    	Reporter.log("获取页面title:"+title);
	}
	@Test(description="测试注销账户")
	public void testLogout() {
		homePage.clickUsername();
		Reporter.log("点击右上角的用户名");
		homePage.clickLogout();
		Reporter.log("点击注销账户");
		homePage.clickButtonOfQueDing();
		Reporter.log("点击确定按钮");
		String title=loginPage.getCurrentPageTitle();
		homePage.assertEquals("Login", title);
		
	}
	@AfterMethod
	public void tearDown() {
		loginPage.quit();
		Reporter.log("关闭浏览器");
	}
}
