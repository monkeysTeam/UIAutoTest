package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ResourceCDN;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.TestAttribute;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import mail.MailConfig;
import mail.MailUtil;
import util.CleanDirectorysUtil;


import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.mail.MessagingException;



public class ExtentTestNGIReporterListener implements IReporter {

	//生成的路径以及文件名
    public static final String OUTPUT_FOLDER = "test-output/";
    public static final String FILE_NAME = "index.html";
    private MailUtil mailSender=new MailUtil();
    private ExtentReports extent;
    private CleanDirectorysUtil cleanUtil=new CleanDirectorysUtil();
    public static int allTests;
    public static int successTests;
    public static int failTests;

    @SuppressWarnings("static-access")
	@Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        init();
        boolean createSuiteNode = false;
        if(suites.size()>1){
            createSuiteNode=true;
        }
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> result = suite.getResults();
            //如果suite里面没有任何用例，直接跳过，不在报告里生成
            if(result.size()==0){
                continue;
            }
            //统计suite下的成功、失败、跳过的总用例数
            int suiteFailSize=0;
            int suitePassSize=0;
            int suiteSkipSize=0;
            ExtentTest suiteTest=null;
            //存在多个suite的情况下，在报告中将同一个一个suite的测试结果归为一类，创建一级节点。
            if(createSuiteNode){
                suiteTest = extent.createTest(suite.getName()).assignCategory(suite.getName());
            }
            boolean createSuiteResultNode = false;
            if(result.size()>1){
                createSuiteResultNode=true;
            }
            for (ISuiteResult r : result.values()) {
                ExtentTest resultNode;
                ITestContext context = r.getTestContext();
                if(createSuiteResultNode){
                    //没有创建suite的情况下，将在SuiteResult的创建为一级节点，否则创建为suite的一个子节点。
                    if( null == suiteTest){
                        resultNode = extent.createTest(r.getTestContext().getName());
                    }else{
                        resultNode = suiteTest.createNode(r.getTestContext().getName());
                    }
                }else{
                    resultNode = suiteTest;
                }
                if(resultNode != null){
                    resultNode.getModel().setName(suite.getName()+" : "+r.getTestContext().getName());
                    if(resultNode.getModel().hasCategory()){
                        resultNode.assignCategory(r.getTestContext().getName());
                    }else{
                        resultNode.assignCategory(suite.getName(),r.getTestContext().getName());
                    }
                    resultNode.getModel().setStartTime(r.getTestContext().getStartDate());
                    resultNode.getModel().setEndTime(r.getTestContext().getEndDate());
                    //统计SuiteResult下的数据
                    int passSize = r.getTestContext().getPassedTests().size();
                    int failSize = r.getTestContext().getFailedTests().size();
                    int skipSize = r.getTestContext().getSkippedTests().size();
                    suitePassSize += passSize;
                    suiteFailSize += failSize;
                    suiteSkipSize += skipSize;
                    if(failSize>0){
                        resultNode.getModel().setStatus(Status.FAIL);
                    }
                    resultNode.getModel().setDescription(String.format("Pass: %s ; Fail: %s ; Skip: %s ;",passSize,failSize,skipSize));
                }
                buildTestNodes(resultNode,context.getFailedTests(), Status.FAIL);
                buildTestNodes(resultNode,context.getSkippedTests(), Status.SKIP);
                buildTestNodes(resultNode,context.getPassedTests(), Status.PASS);
            }
            if(suiteTest!= null){
                suiteTest.getModel().setDescription(String.format("Pass: %s ; Fail: %s ; Skip: %s ;",suitePassSize,suiteFailSize,suiteSkipSize));
                if(suiteFailSize>0){
                    suiteTest.getModel().setStatus(Status.FAIL);
                }
            }

        }
//        for (String s : Reporter.getOutput()) {
//            extent.setTestRunnerOutput(s);
//        }

        extent.flush();
        //清除截图文件
        cleanUtil.clean("screen/");
        //cleanUtil.clean("test-output/");
        try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        String mes=String.format("<p style=\"font-family: Microsoft YaHei;\">本次自动化共执行用例<b style=\"color: blue;\">%d</b>条，其中成功用例<b style=\"color: green\">%d</b>条，失败用例<b style=\"color: red;\">%d</b>条，成功率为<b style=\"color: orange;\">%.2f</b>%%</p><br>",this.allTests,this.successTests,this.failTests,((float)this.successTests/(float)this.allTests)*100);
        System.out.println(mes);
        String content="<html>\r\n" + 
        		"<head>\r\n" + 
        		"<style type=\"text/css\">　　\r\n" + 
        		"    body {font-family: Microsoft YaHei;}　　\r\n" + 
        		"</style>\r\n" + 
        		"<meta charset=\"UTF-8\">\r\n" + 
        		"<title>自动化测试报告生成通知</title>\r\n" + 
        		"</head>\r\n" + 
        		"<body>\r\n" + 
        		"<h1>自动化测试报告已经生成,请查看附件！</h1>\r\n" + 
        		 mes+
        		"<p style=\"font-family: Microsoft YaHei;\">测试报告请查阅附件</p>\r\n" + 
        		"</body>\r\n" + 
        		"</html>";
		String[] to={"zhangyingkai@djcps.com"};
		if(MailConfig.start.equals("true")) {
			try {
				mailSender.sendMail(to, new SimpleDateFormat("yyyy年MM月dd日").format(new Date())+"自动化测试报告", content);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("邮件发送成功");
		}
    }

    private void init() {
        //文件夹不存在的话进行创建
        File reportDir= new File(OUTPUT_FOLDER);
        if(!reportDir.exists()&& !reportDir .isDirectory()){
            reportDir.mkdir();
        }
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(OUTPUT_FOLDER + FILE_NAME);
        // 设置静态文件的DNS
        //怎么样解决cdn.rawgit.com访问不了的情况
        htmlReporter.config().setResourceCDN(ResourceCDN.EXTENTREPORTS);

        htmlReporter.config().setDocumentTitle("UI自动化测试报告");
        htmlReporter.config().setReportName("UI自动化测试报告");
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setCSS(".node.level-1  ul{ display:none;} .node.level-1.active ul{display:block;}");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setReportUsesManualConfiguration(true);
     // 设置系统信息
        Properties properties = System.getProperties();
        extent.setSystemInfo("os.name",properties.getProperty("os.name","未知"));
        extent.setSystemInfo("os.arch",properties.getProperty("os.arch","未知"));
        extent.setSystemInfo("os.version",properties.getProperty("os.version","未知"));
        extent.setSystemInfo("java.version",properties.getProperty("java.version","未知"));
        extent.setSystemInfo("java.home",properties.getProperty("java.home","未知"));
        extent.setSystemInfo("user.name",properties.getProperty("user.name","未知"));
        extent.setSystemInfo("user.dir",properties.getProperty("user.dir","未知"));
    }

    private void buildTestNodes(ExtentTest extenttest, IResultMap tests, Status status) {
        //存在父节点时，获取父节点的标签
        String[] categories=new String[0];
        if(extenttest != null ){
            List<TestAttribute> categoryList = extenttest.getModel().getCategoryContext().getAll();
            categories = new String[categoryList.size()];
            for(int index=0;index<categoryList.size();index++){
                categories[index] = categoryList.get(index).getName();
            }
        }

        ExtentTest test;

        if (tests.size() > 0) {
            //调整用例排序，按时间排序
            Set<ITestResult> treeSet = new TreeSet<ITestResult>(new Comparator<ITestResult>() {
                @Override
                public int compare(ITestResult o1, ITestResult o2) {
                    return o1.getStartMillis()<o2.getStartMillis()?-1:1;
                }
            });
            treeSet.addAll(tests.getAllResults());
            for (ITestResult result : treeSet) {

                String name="";
                //如果有参数，则使用参数的toString组合代替报告中的name
                /*Object[] parameters = result.getParameters();
                for(Object param:parameters){
                    name+=param.toString();
                }*/
                if(name.length()>0){
                    if(name.length()>50){
                        name= name.substring(0,49)+"...";
                    }
                }else{
                    name = result.getMethod().getMethodName()+"---"+result.getMethod().getDescription();
                }
                if(extenttest==null){
                    test = extent.createTest(name);
                }else{
                    //作为子节点进行创建时，设置同父节点的标签一致，便于报告检索。
                    test = extenttest.createNode(name).assignCategory(categories);
                }
                //test.getModel().setDescription(description.toString());
                //test = extent.createTest(result.getMethod().getMethodName());
                for (String group : result.getMethod().getGroups())
                    test.assignCategory(group);

                List<String> outputList = Reporter.getOutput(result);
                System.out.println("日志输出="+outputList);
                for(String output:outputList){
                    //将用例的log输出报告中
                    test.info(output);
                }
                if (result.getThrowable() != null) {
                    test.log(status, result.getThrowable());
                }
                else {
                    test.log(status, "Test " + status.toString().toLowerCase() + "ed");
                }

                test.getModel().setStartTime(getTime(result.getStartMillis()));
                test.getModel().setEndTime(getTime(result.getEndMillis()));
            }
        }
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
	public static void main(String[] args) {
		//System.out.println((float)2/3);
		
		//System.out.println(mes);
		/*String mes=String.format("<p style='font-family: Microsoft YaHei;'>本次自动化共执行用例<b style='color: blue;'>%d</b>条，其中成功用例<b style='color: green'>%d</b>条，失败用例<b style='color: red;'>%d</b>条，成功率为<b style='color: orange;'>%d</b></p>",
				1,2,3,4);
			System.out.println(mes);*/
	}
		
   
}
