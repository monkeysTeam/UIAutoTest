package listeners;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;


/**
 * 
 * @author fs
 * @version 1.0.0
 * @description 失败重跑监听
 * @date 2018年8月22日 上午9:45:33
 */
public class RetryListener implements IRetryAnalyzer{
	
	private int retrycount = 0;
	private int maxRetryCount = 1;
	private Logger logger=Logger.getLogger(RetryListener.class);

	public boolean retry(ITestResult result) {
		if(retrycount < maxRetryCount) {
			logger.info(">>>>用例失败，开始重试第"+(retrycount+1)+"次");
			retrycount++;
			return true;
		}
		return false;
	}

}
