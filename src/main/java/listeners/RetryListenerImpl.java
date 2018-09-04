package listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;


/**
 * 
 * @author fs
 * @version 1.0.0
 * @description 重试监听实现类
 * @date 2018年8月22日 上午9:49:51
 */
public class RetryListenerImpl implements IAnnotationTransformer{

	public void transform(ITestAnnotation annotation, Class testClass, 
			Constructor testConstructor, Method testMethod) {
		IRetryAnalyzer retry = annotation.getRetryAnalyzer();
		if(retry == null) {
			annotation.setRetryAnalyzer(RetryListener.class);
		}
		
	}

}
