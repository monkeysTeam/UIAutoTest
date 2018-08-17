package pageObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.google.common.io.Files;



/**
 * 
 * @author zhangyingkai
 *	
 * 2018年8月13日,下午12:42:48
 */
@SuppressWarnings("unused")
public class TestBase {
	
	private static WebDriver driver;
	private static Logger logger=Logger.getLogger(TestBase.class);
	private static long time;
	private static String savePicturePath;
	/*
	 * 静态块，初始化浏览器对象
	 */
	public TestBase(){
		logger.setLevel(Level.INFO);
		
	}
	/*
	 * 启动谷歌浏览器
	 */
	public void openChrome() {
		logger.info("=============测试用例开始================");
		System.setProperty("webdriver.chrome.driver", ".\\tools\\chromedriver.exe"); 
		driver=new ChromeDriver();
		logger.info("启动chrome浏览器");
	}
	/*
	 * 
	 * 最大化浏览器
	 */
	public void maxBrowser() {
		try {
			driver.manage().window().maximize();
			logger.info("浏览器最大化");
		}catch(Exception e) {
			e.printStackTrace();
			this.screenAsFile();
			logger.error("浏览器最大化失败");
		}
		
	}
	/*
	 * 输入方法
	 */
	public void type(WebElement element,String str) {
		try {
			if(element.isDisplayed()) {
				element.clear();
				element.sendKeys(str);
				logger.info("控件("+element+")输入===>"+str);
			}else {
				logger.info("元素没有展示");
				this.screenAsFile();
			}
		}catch(Exception e) {
			e.printStackTrace();
			this.screenAsFile();
			logger.error("控件("+element+")输入失败");
		}
	}
	/*
	 * 
	 * 万能定位元素方法，以键值对的形式传入str,如id=>su，xpath=//*[@id=kw]
	 */
	public WebElement findElement(String str) {
		WebElement element = null;
		String k=str.split("=>")[0];
		String v=str.split("=>")[1];
		//定位方式的枚举值x
		int x;
		if(k.equals("id")){
			x=1;
		}else if(k.equals("className")) {
			x=2;
		}else if(k.equals("name")) {
			x=3;
		}else if(k.equals("cssSelector")) {
			x=4;
		}else if(k.equals("tagName")) {
			x=5;
		}else if(k.equals("xpath")) {
			x=6;
		}else if(k.equals("linkText")) {
			x=7;
		}else {
			x=8;
		}
		String message=null;
		switch(x) {
			case 1:
				try {
					element=driver.findElement(By.id(v));
					message=String.format("根据id找到定位到元素%s",v);
					logger.info(message);
					break;
				}catch(Exception e) {
					e.printStackTrace();
					message=String.format("根据id=>%s没有定位到该元素",v);
					this.screenAsFile();
					logger.error(message);
					break;
				}
			case 2:
				try {
					element=driver.findElement(By.className(v));
					message=String.format("根据className找到定位到元素%s",v);
					logger.info(message);
					break;
				}catch(Exception e) {
					e.printStackTrace();
					message=String.format("根据className=>%s没有定位到该元素",v);
					this.screenAsFile();
					logger.error(message);
					break;
				}
			case 3:
				try {
					element=driver.findElement(By.name(v));
					message=String.format("根据name找到定位到元素%s",v);
					logger.info(message);
					break;
				}catch(Exception e) {
					e.printStackTrace();
					message=String.format("根据name=>%s没有定位到该元素",v);
					this.screenAsFile();
					logger.error(message);
					break;
				}
			case 4:
				try {
					element=driver.findElement(By.cssSelector(v));
					message=String.format("根据cssSelector找到定位到元素%s",v);
					logger.info(message);
					break;
				}catch(Exception e) {
					e.printStackTrace();
					message=String.format("根据cssSelector=>%s没有定位到该元素",v);
					this.screenAsFile();
					logger.error(message);
					break;
				}
			case 5:
				try {
					element=driver.findElement(By.tagName(v));
					message=String.format("根据tagName找到定位到元素%s",v);
					logger.info(message);
					break;
				}catch(Exception e) {
					e.printStackTrace();
					message=String.format("根据tagName=>%s没有定位到该元素",v);
					this.screenAsFile();
					logger.error(message);
					break;
				}	
			case 6:
				try {
					element=driver.findElement(By.xpath(v));
					message=String.format("根据xpath找到定位到元素%s",v);
					logger.info(message);
					break;
				}catch(Exception e) {
					e.printStackTrace();
					message=String.format("根据xpath=>%s没有定位到该元素",v);
					this.screenAsFile();
					logger.error(message);
					break;
				}
			case 7:
				try {
					element=driver.findElement(By.linkText(v));
					message=String.format("根据linkText找到定位到元素%s",v);
					logger.info(message);
					break;
				}catch(Exception e) {
					e.printStackTrace();
					message=String.format("根据linkText=>%s没有定位到该元素",v);
					this.screenAsFile();
					logger.error(message);
					break;
				}
			case 8:
				try {
					element=driver.findElement(By.partialLinkText(v));
					message=String.format("根据partialLinkText找到定位到元素%s",v);
					logger.info(message);
					break;
				}catch (Exception e) {
					e.printStackTrace();
					message=String.format("根据partialLinkText=>%s没有定位到该元素",v);
					this.screenAsFile();
					logger.error(message);
					break;
				}
				
			default:
				logger.info("没有该枚举值");
		}
		return element;
	}
	/*
	 * 
	 * 万能定位多个元素方法，以键值对的形式传入str,如id=>su，xpath=//*[@id=kw]
	 */
	public List<WebElement> findElements(String str) {
		List<WebElement> elements = null;
		String k=str.split("=>")[0];
		String v=str.split("=>")[1];
		//定位方式的枚举值x
		int x;
		if(k.equals("className")) {
			x=1;
		}else if(k.equals("name")) {
			x=2;
		}else if(k.equals("cssSelector")) {
			x=3;
		}else if(k.equals("tagName")) {
			x=4;
		}else if(k.equals("xpath")) {
			x=5;
		}else if(k.equals("linkText")) {
			x=6;
		}else {
			x=7;
		}
		String message=null;
		switch(x) {
			case 1:
				try {
					elements=driver.findElements(By.className(v));
					message=String.format("根据className找到定位到元素集合%s",v);
					logger.info(message);
					break;
				}catch(Exception e) {
					e.printStackTrace();
					message=String.format("根据className=>%s没有定位到该元素集合",v);
					this.screenAsFile();
					logger.error(message);
					break;
				}
			case 2:
				try {
					elements=driver.findElements(By.name(v));
					message=String.format("根据name找到定位到元素集合%s",v);
					logger.info(message);
					break;
				}catch(Exception e) {
					e.printStackTrace();
					message=String.format("根据name=>%s没有定位到该元素集合",v);
					this.screenAsFile();
					logger.error(message);
					break;
				}
			case 3:
				try {
					elements=driver.findElements(By.cssSelector(v));
					message=String.format("根据cssSelector找到定位到元素集合%s",v);
					logger.info(message);
					break;
				}catch(Exception e) {
					e.printStackTrace();
					message=String.format("根据cssSelector=>%s没有定位到该元素集合",v);
					this.screenAsFile();
					logger.error(message);
					break;
				}
			case 4:
				try {
					elements=driver.findElements(By.tagName(v));
					message=String.format("根据tagName找到定位到元素集合%s",v);
					logger.info(message);
					break;
				}catch(Exception e) {
					e.printStackTrace();
					message=String.format("根据tagName=>%s没有定位到该元素集合",v);
					this.screenAsFile();
					logger.error(message);
					break;
				}	
			case 5:
				try {
					elements=driver.findElements(By.xpath(v));
					message=String.format("根据xpath找到定位到元素集合%s",v);
					logger.info(message);
					break;
				}catch(Exception e) {
					e.printStackTrace();
					message=String.format("根据xpath=>%s没有定位到该元素集合",v);
					this.screenAsFile();
					logger.error(message);
					break;
				}
			case 6:
				try {
					elements=driver.findElements(By.linkText(v));
					message=String.format("根据linkText找到定位到元素集合%s",v);
					logger.info(message);
					break;
				}catch(Exception e) {
					e.printStackTrace();
					message=String.format("根据linkText=>%s没有定位到该元素集合",v);
					this.screenAsFile();
					logger.error(message);
					break;
				}
			case 7:
				try {
					elements=driver.findElements(By.partialLinkText(v));
					message=String.format("根据partialLinkText找到定位到元素集合%s",v);
					logger.info(message);
					break;
				}catch (Exception e) {
					e.printStackTrace();
					message=String.format("根据partialLinkText=>%s没有定位到该元素集合",v);
					this.screenAsFile();
					logger.error(message);
					break;
				}
				
			default:
				logger.info("没有该枚举值");
		}
		return elements;
	}
	/*
	 * 
	 * 访问网址
	 */
	public void accessToWeb(String str) {
		try {
			driver.get(str);
			logger.info("访问网址："+str);
		}catch(Exception e) {
			e.printStackTrace();
			this.screenAsFile();
			logger.error(String.format("访问网址%s失败", str));
		}	
	}
	/*
	 * 截图
	 */
	public void screenAsFile() {
		
		try {
            /*
             	* 利用FileUtils工具类的copy()方法保存getScreenshotAs()返回的文件对象。
             	* 看到网上有使用File.copyFile()方法，我这里测试的结果需要使用copy()方法
             */ 
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd/");
			String filepath="screen/"+df.format(new Date());
			File filedir=new File(filepath);
			if(!filedir.exists()) {
				filedir.mkdirs();
			}
			File scrFile=((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
			time=System.currentTimeMillis();
			savePicturePath=filepath+"/"+time+"screenfile.png";
            Files.copy(scrFile, new File(savePicturePath));
            logger.info("在此处截图");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("截图失败");
        }
	}
	/*
	 * 点击
	 */
	public void click(WebElement element) {
		try {
			if(element.isDisplayed()) {
				element.click();
				logger.info("点击元素=>"+element);
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("元素=>"+element+"点击失败");
		}
	}
	/*
	 * 清楚输入框内容
	 */
	public void clean(WebElement element) {
		try {
			if(element.isEnabled()) {
				element.clear();
				logger.info("清楚元素=>"+element+"内容成功");
			}
		}catch(Exception e) {
			e.printStackTrace();
		    logger.error("清楚元素=>"+element+"内容失败");
		}
	}
	/*
	 * 
	 * 判断一个元素是否显示在当前页面,
	 */
	public boolean verifyElementIsDisplayed(WebElement element) {
		if(element.isDisplayed()) {
			logger.info("元素=>"+element+"已显示");
			return true;
		}else {
			logger.error("元素=>"+element+"未显示");
			return false;
		}	
	}
	/*
	 * 获取当前网页标题
	 */
	public String getCurrentPageTitle() {
		String pageTitle=driver.getTitle();
		logger.info("获取当前网页title:"+pageTitle);
		return pageTitle;
	}
	/*
	 * 获取页面的url
	 */
	public String getCurrentPageUrl() {
		String url=driver.getCurrentUrl();
		logger.info("获取当前网页url:"+url);
		return url;
	}
	/*
	 * 
	 * 处理多窗口之间的切换
	 */
	public void switchToCurrentWindowHandle() {
		String currentWindow=driver.getWindowHandle();
		Set<String> handles=driver.getWindowHandles();
		logger.info("当前窗口数量："+handles.size());
		Iterator<String> it=handles.iterator();
		while(it.hasNext()) {
			if(currentWindow.equals(it.next())) {
				continue;
			}
			try {
				//关闭旧窗口
				driver.close();
				//切换到新窗口
				driver=driver.switchTo().window(it.next());
				logger.info("切换到新窗口："+driver.getTitle());
			}catch(Exception e) {
				e.printStackTrace();
				logger.error("无法切换到新窗口");
				this.screenAsFile();
			}
		}
	}
	/*
	 * 强制睡眠
	 */
	public void threadSleep(int seconds) {
		try {
			Thread.sleep(seconds*1000);
			logger.info(String.format("线程等待ing...时间：%d秒", seconds));
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error("强制等待失败");
		}
	}
	/*
	 * 
	 * 显示等待
	 */
	public WebElement webDriverWait(String style,int seconds) {
		String k=style.split("=>")[0];
		String v=style.split("=>")[1];
		WebElement element=null;
		//定位方式的枚举值x
		int x;
		if(k.equals("id")){
			x=1;
		}else if(k.equals("className")) {
			x=2;
		}else if(k.equals("name")) {
			x=3;
		}else if(k.equals("cssSelector")) {
			x=4;
		}else if(k.equals("tagName")) {
			x=5;
		}else if(k.equals("xpath")) {
			x=6;
		}else if(k.equals("linkText")) {
			x=7;
		}else {
			x=8;
		}
		switch(x) {
			case 1:
				try {
					logger.info("显示等待开始...");
					element = (new WebDriverWait(driver,seconds))
							.until(ExpectedConditions.presenceOfElementLocated(By.id(v)));
					logger.info(String.format("显示等待%d秒内,找到元素", seconds));
					break;
				}catch(Exception e) {
					e.printStackTrace();
					logger.error("审查元素超时，未能找到该元素");
					break;
				}
			case 2:
				try {
					logger.info("显示等待开始...");
					element = (new WebDriverWait(driver,seconds))
							.until(ExpectedConditions.presenceOfElementLocated(By.className(v)));
					logger.info(String.format("显示等待%d秒内,找到元素", seconds));
					break;
				}catch(Exception e) {
					e.printStackTrace();
					logger.error("审查元素超时，未能找到该元素");
					break;
				}
			case 3:
				try {
					logger.info("显示等待开始...");
					element = (new WebDriverWait(driver,seconds))
							.until(ExpectedConditions.presenceOfElementLocated(By.name(v)));
					logger.info(String.format("显示等待%d秒内,找到元素", seconds));
					break;
				}catch(Exception e) {
					e.printStackTrace();
					logger.error("审查元素超时，未能找到该元素");
					break;
				}
			case 4:
				try {
					logger.info("显示等待开始...");
					element = (new WebDriverWait(driver,seconds))
							.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(v)));
					logger.info(String.format("显示等待%d秒内,找到元素", seconds));
					break;
				}catch(Exception e) {
					e.printStackTrace();
					logger.error("审查元素超时，未能找到该元素");
					break;
				}
			case 5:
				try {
					logger.info("显示等待开始...");
					element = (new WebDriverWait(driver,seconds))
							.until(ExpectedConditions.presenceOfElementLocated(By.tagName(v)));
					logger.info(String.format("显示等待%d秒内,找到元素", seconds));
					break;
				}catch(Exception e) {
					e.printStackTrace();
					logger.error("审查元素超时，未能找到该元素");
					break;
				}
			case 6:
				try {
					logger.info("显示等待开始...");
					element = (new WebDriverWait(driver,seconds))
							.until(ExpectedConditions.presenceOfElementLocated(By.xpath(v)));
					logger.info(String.format("显示等待%d秒内,找到元素", seconds));
					break;
				}catch(Exception e) {
					e.printStackTrace();
					logger.error("审查元素超时，未能找到该元素");
					break;
				}
			case 7:
				try {
					logger.info("显示等待开始...");
					element = (new WebDriverWait(driver,seconds))
							.until(ExpectedConditions.presenceOfElementLocated(By.linkText(v)));
					logger.info(String.format("显示等待%d秒内,找到元素", seconds));
					break;
				}catch(Exception e) {
					e.printStackTrace();
					logger.error("审查元素超时，未能找到该元素");
					break;
				}
			case 8:
				try {
					logger.info("显示等待开始...");
					element = (new WebDriverWait(driver,seconds))
							.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(v)));
					logger.info(String.format("显示等待%d秒内,找到元素", seconds));
					break;
				}catch(Exception e) {
					e.printStackTrace();
					logger.error("审查元素超时，未能找到该元素");
					break;
				}
			default:
				logger.error("没有该枚举值");
		}
		
		
		return element;
	}
	/*
	 * 
	 * 隐式等待
	 */
	public void implicitlyWait(int seconds) {
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
		logger.info(String.format("隐式等待ing%d秒", seconds));
	}
	/*
	 * 关闭浏览器
	 */
	public void quit() {
		try {
			driver.quit();
			logger.info("关闭浏览器，退出驱动");
			logger.info("===================测试用例结束=======================");
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("无法关闭浏览器，退出驱动失败");
		}
		
	}
	public void switchToFrame(int index) {
		try {
			driver.switchTo().frame(index);
			logger.info("切换iframe["+index+"]");
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("切换Iframe失败");
		}
	}
	public void switchToFrame(String frameIdOrName) {
		try {
			driver.switchTo().frame(frameIdOrName);
			logger.info("切换iframe["+frameIdOrName+"]");
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("切换Iframe失败");
		}
	}
	public void swithcToFrame(WebElement element) {
		try {
			driver.switchTo().frame(element);
			logger.info("切换iframe:"+element);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("切换Iframe失败");
		}
	}
	public void assertEquals(Object expected,Object actual) {
		try {
			Assert.assertEquals(expected, actual);
			this.threadSleep(5);
			this.screenAsFile();
			logger.info("断言成功："+expected+"="+actual);
			String sreenShotImg =String.format("<p>断言成功处截图:<img id='img' src='../%s' alt='截图' width='500' height='300'></p>", savePicturePath) ;
	        Reporter.log(sreenShotImg);
		}catch(AssertionError e) {
			logger.error("断言失败："+expected+"!="+actual);
			this.threadSleep(5);
			this.screenAsFile();
			String sreenShotImg = String.format("<p>断言成功处截图:<img id='img' src='../%s' alt='截图' width='500' height='300'></p>", savePicturePath);
	        Reporter.log(sreenShotImg);
	        throw e;
		}
	}
	public void moveToElement(WebElement element) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(element).perform();
			logger.info("移动鼠标到元素"+element);
		}catch(Exception e) {
			logger.error("移动鼠标失败");
		}
	}
}
