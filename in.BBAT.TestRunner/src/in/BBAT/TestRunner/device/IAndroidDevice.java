package in.BBAT.TestRunner.device;

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
	 
	 void pushTestJar();
	 
	 void executeTestCase(String testCaseName,ITestRunListener listener);
	 
	
}
