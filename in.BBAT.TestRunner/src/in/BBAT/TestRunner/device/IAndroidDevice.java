package in.BBAT.TestRunner.device;

import in.BBAT.testRunner.runner.UiAutoTestCaseJar;

import com.android.ddmlib.testrunner.ITestRunListener;


public interface IAndroidDevice {

	 String getModelName();

	 String getDeviceId();

	 String getStatus();

	 void addLogListener(ILogListener listener);
	
	 void startLogging();
	
	 void stopLogging();
	
	 void activate() ;
	
	 void deActivate() ;
	 
	 void pushTestJar(UiAutoTestCaseJar jar);
	 
	 void executeTestCase(String testCaseName,ITestRunListener listener);
	 
	
}
