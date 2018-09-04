package pageObject.ProductManagement;

import org.openqa.selenium.WebElement;

import pageObject.TestBase;

/**
 * 
 * @author zhangyingkai
 *	
 * 2018年8月20日,下午1:40:10
 * 商品管理页面
 */

public class CommodityManagementPage extends TestBase{
	
	/*
	 * driver:测试驱动
	 * productManagement:产品管理
	 * commodityManagement：商品管理
	 * inputOfProvice：省输出框
	 * tianjinProviceOption：天津省选项
	 * inputOfCity:市输入框
	 * tianjinCityOption：天津市选项
	 */
	@SuppressWarnings("unused")
	private TestBase driver;
	private String productManagement;
	private String commodityManagement;
	private String inputOfProvice;
	private String tianjinProviceOption;
	private String inputOfCity;
	private String tianjinCityOption;
	private String searchButton;
	private String inputOfParter;
	private String baZhouParterOption;
	private String cell;
	
	public CommodityManagementPage() {
		driver=new TestBase();
		productManagement="xpath=>//*[@id=\"header\"]/ul/li[2]";
		commodityManagement="xpath=>//*[@id=\"menu\"]/div/ul/li[1]/span";
		inputOfProvice="xpath=>//*[@id=\"home\"]/div[2]/div/div/div[2]/div[1]/div[1]/div[1]/div/div[1]/input";
		tianjinProviceOption="xpath=>/html/body/div[2]/div/div[1]/ul/li[2]";
		inputOfCity="xpath=>//*[@id=\"home\"]/div[2]/div/div/div[2]/div[1]/div[1]/div[2]/div/div[1]/input";
		tianjinCityOption="xpath=>/html/body/div[3]/div/div[1]/ul/li";
		inputOfParter="xpath=>//*[@id=\"home\"]/div[2]/div/div/div[2]/div[1]/div[2]/div/div/div/div[1]/input";
		baZhouParterOption="xpath=>/html/body/div[4]/div/div[1]/ul/li[4]/span";
		searchButton="xpath=>//*[@id=\"home\"]/div[2]/div/div/div[2]/div[1]/div[4]/button";
		cell="xpath=>//*[@id=\"home\"]/div[2]/div/div/div[2]/div[2]/div/div[3]/table/tbody/tr/td[1]/div";
	}
	/*
	 * 点击产品管理
	 */
	public void clickProductManagement() {
		WebElement element=driver.findElement(productManagement);
		driver.webDriverWait(productManagement, 10);
		driver.click(element);
		driver.click(element);
	}
	/*
	 * 点击商品管理
	 */
	public void clickCommodityManagement() {
		WebElement element=driver.findElement(commodityManagement);
		driver.click(element);
	}
	/*
	 * 选择天津省
	 */
	public void selectTianJinProvice() {
		WebElement element=driver.findElement(inputOfProvice);
		driver.click(element);
		WebElement element2=driver.findElement(tianjinProviceOption);
		driver.click(element2);
	}
	/*
	 * 选择天津市
	 */
	public void selectTianJinCity() {
		WebElement element=driver.findElement(inputOfCity);
		driver.click(element);
		WebElement element2=driver.findElement(tianjinCityOption);
		driver.click(element2);
	}
	/*
	 * 选择霸州市胜芳镇圣凯纸板厂
	 */
	public void selectBaZhou() {
		WebElement element=driver.findElement(inputOfParter);
		driver.click(element);
		WebElement element2=driver.findElement(baZhouParterOption);
		driver.click(element2);
	}
	/*
	 * 点击搜索按钮
	 */
	public void clickSearchButton() {
		WebElement element=driver.findElement(searchButton);
		driver.click(element);
	}
	/*
	 * 获取搜索结果第一行第一列的内容
	 */
	public String getContentOfCell() {
		WebElement element=driver.findElement(cell);
		return driver.getText(element);
	}
}
