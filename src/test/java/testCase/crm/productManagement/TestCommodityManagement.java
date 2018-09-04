package testCase.crm.productManagement;

import org.testng.annotations.Test;

import pageObject.LoginPage;
import pageObject.ProductManagement.CommodityManagementPage;

import org.testng.annotations.BeforeClass;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;

public class TestCommodityManagement {
	private static LoginPage loginPage;
	private static CommodityManagementPage comManPage;

    @BeforeClass
    public void setUpClass() {
    	loginPage=new LoginPage();
    	comManPage=new CommodityManagementPage();
		loginPage.openChrome();
		loginPage.maxBrowser();
		loginPage.accessUrl();
		loginPage.implicitlyWait(3);
    	loginPage.clickCrmButton();
    	loginPage.typeUsername();
    	loginPage.typePassword();
    	loginPage.threadSleep(3);
    	loginPage.clickLoginButton();
    	loginPage.threadSleep(10);    
    }
    @AfterClass
    public void tearDownClass() {
    	loginPage.quit();
    }
  
    @Test(description="搜索商品管理信息")
    public void testSerach() {
    	Reporter.log("前置条件：已登入成功");
    	comManPage.clickProductManagement();
    	Reporter.log("上方点击产品管理菜单");
    	comManPage.clickCommodityManagement();
    	Reporter.log("左侧点击商品管理页面");
    	comManPage.selectTianJinProvice();
    	Reporter.log("选择天津省");
    	comManPage.selectTianJinCity();
    	Reporter.log("选择天津市");
    	comManPage.selectBaZhou();
    	Reporter.log("选择合作方：霸州市胜芳镇圣凯纸板厂");
    	comManPage.clickSearchButton();
    	Reporter.log("点击搜索按钮");
    	comManPage.assertEquals(comManPage.getContentOfCell(),"1");
    	Reporter.log("断言=第一行第一列内容为1");
    }
    
    
}
