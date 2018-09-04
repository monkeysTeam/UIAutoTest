package listeners;

import java.util.Iterator;

import org.testng.IResultMap;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

/**
 * 
 * @author fs
 * @version 1.0.0
 * @description 监听执行用例数量
 * @date 2018年8月22日 上午9:54:52
 */
public class TestListener implements ITestListener{

	@Override
	public void onTestStart(ITestResult result) {
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
	}

	@Override
	public void onStart(ITestContext context) {
		
	}

	@Override
	public void onFinish(ITestContext context) {
		Iterator<ITestResult> listOfFailedTests = context.getFailedTests()
				.getAllResults().iterator();
		while(listOfFailedTests.hasNext()) {
			ITestResult failedTest = listOfFailedTests.next();
			ITestNGMethod method = failedTest.getMethod();
			if(context.getFailedTests().getResults(method).size()>1) {
				listOfFailedTests.remove();
			}else {
				if(context.getPassedTests().getResults(method).size()>0) {
					listOfFailedTests.remove();
				}
			}
		}
		ExtentTestNGIReporterListener.allTests=context.getAllTestMethods().length;
		ExtentTestNGIReporterListener.failTests=context.getFailedTests().getAllResults().size();
		ExtentTestNGIReporterListener.successTests=ExtentTestNGIReporterListener.allTests-ExtentTestNGIReporterListener.failTests;
	}

}
