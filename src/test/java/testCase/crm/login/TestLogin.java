package testCase.crm.login;

import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import listeners.RetryListener;

import junit.framework.Assert;
import pageObject.LoginPage;


public class TestLogin {
	private static LoginPage loginPage;
	@BeforeMethod
	public void setUp() {
		loginPage=new LoginPage();
		loginPage.openChrome();
		loginPage.accessUrl();
		loginPage.implicitlyWait(3);
	}
    @Test(description="测试登入")
    public void testLogin() {
    	Reporter.log("测试用例开始");
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
    	loginPage.assertEquals("浙江东经科技股份有限公司-CRM", title);
    	Reporter.log("断言title=浙江东经科技股份有限公司-CRM");
    }
    
    @AfterMethod
    public void tearDown() {
    	loginPage.quit();
    }
}
